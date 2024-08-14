package com.example.UserDetailsProject.service.impl;

import com.example.UserDetailsProject.exception.CustomApiException;
import com.example.UserDetailsProject.model.CreateUserRequestDto;
import com.example.UserDetailsProject.model.PageInfo;
import com.example.UserDetailsProject.model.UserEntity;
import com.example.UserDetailsProject.model.UserWrapperDto;
import com.example.UserDetailsProject.repository.UserRepository;
import com.example.UserDetailsProject.service.UserService;
import com.example.UserDetailsProject.sorting.UserSortStrategy;
import com.example.UserDetailsProject.sorting.impl.SortByAgeStrategy;
import com.example.UserDetailsProject.sorting.impl.SortByNameStrategy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final Map<String, UserSortStrategy> sortStrategies;
  @Qualifier("api1WebClient")
  private final WebClient api1WebClient;

  @Qualifier("api2WebClient")
  private final WebClient api2WebClient;

  @Qualifier("api3WebClient")
  private final WebClient api3WebClient;


  @Override
  public List<Optional<UserEntity>> createUser(CreateUserRequestDto dto) {
    List<Optional<UserEntity>> usersList = IntStream.range(0, dto.getNumberOfUsers())
        .mapToObj(i -> createUserFromRandomUserApi())
        .map(userOptional -> userOptional.map(this::verifyUser))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());

    // Extract the actual UserEntity objects from Optional
    List<UserEntity> validUsers = usersList.stream()
        .map(Optional::get)
        .collect(Collectors.toList());

    // Save the valid users to the database
    List<UserEntity> savedUsers = userRepository.saveAll(validUsers);

    // Return the list of saved users
    return savedUsers.stream()
        .map(Optional::ofNullable)
        .collect(Collectors.toList());
  }

  private Optional<UserEntity> createUserFromRandomUserApi() {
    try {
      String responseBody = api1WebClient.get()
          .uri("https://randomuser.me/api/")
          .retrieve()
          .bodyToMono(String.class)
          .block();

      // Parse the JSON response
      // (Note: Adapt this based on the actual response structure)
      JSONObject user = new JSONObject(responseBody).getJSONArray("results").getJSONObject(0);


      String userName = user.getJSONObject("name").getString("first");
      String lastName = user.getJSONObject("name").getString("last");
      String dob = user.getJSONObject("dob").getString("date");
      Integer age = user.getJSONObject("dob").getInt("age");
      String fullName = userName + " " + lastName;
      String userGender = user.getString("gender");
      String userNationality = user.getString("nat");

      UserEntity newUser = new UserEntity();
      newUser.setName(fullName);
      newUser.setGender(userGender);
      newUser.setNationality(userNationality);
      newUser.setDateCreated(String.valueOf(LocalDateTime.now()));
      newUser.setDateModified(String.valueOf(LocalDateTime.now()));
      newUser.setAge(age);
      newUser.setDob(dob);

      return Optional.of(newUser);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private Optional<UserEntity> verifyUser(UserEntity user) {
    try {
      String nationalityResponse = api2WebClient.get()
          .uri("https://api.nationalize.io/?name=" + user.getName().split(" ")[0])
          .retrieve()
          .bodyToMono(String.class)
          .block();

      // Parse the JSON response
      JSONArray nationalities = new JSONObject(nationalityResponse).getJSONArray("country");

      // Check if IN (India) is one of the predicted nationalities
      boolean isNationalityMatch = nationalities.toList().stream()
          .map(entry -> ((Map<String, Object>) entry).get("country_id").toString())
          .anyMatch(country -> country.equals("IN"));

      // Use WebClient to make the API call for gender prediction
      String genderResponse = api3WebClient.get()
          .uri("https://api.genderize.io/?name=" + user.getName().split(" ")[0])
          .retrieve()
          .bodyToMono(String.class)
          .block();

      // Parse the JSON response
      String genderFromApi = new JSONObject(genderResponse).getString("gender");

      // Check if predicted nationality is IN and gender matches
      if (isNationalityMatch && user.getGender().equals(genderFromApi)) {
        user.setVerificationStatus("Verified");
      } else {
        user.setVerificationStatus("TO_BE_VERIFIED");
      }

      return Optional.of(user);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public UserWrapperDto getUsers(String sortType, String sortOrder, int limit, int offset) {
    // Fetch all users from the repository
    List<UserEntity> allUsers = userRepository.findAll();

    // Apply sorting strategy based on sortType
    if(sortType.equalsIgnoreCase("name")){
    UserSortStrategy sortStrategy = sortStrategies.getOrDefault(sortType, new SortByNameStrategy());
    allUsers = sortStrategy.sort(allUsers);}
    else {
        UserSortStrategy sortStrategy = sortStrategies.getOrDefault(sortType, new SortByAgeStrategy());
        allUsers = sortStrategy.sort(allUsers);
      }

    // Apply sortOrder
    if ("ODD".equalsIgnoreCase(sortOrder)) {
      allUsers.removeIf(user -> user.getId() % 2 == 0); // Remove even user IDs
    } else if ("EVEN".equalsIgnoreCase(sortOrder)) {
      allUsers.removeIf(user -> user.getId() % 2 != 0); // Remove odd user IDs
    }

    // Calculate total count of users
    int totalUsers = allUsers.size();

    // Apply limit and offset
    int startIndex = Math.min(offset, totalUsers);
    int endIndex = Math.min(offset + limit, totalUsers);
    List<UserEntity> paginatedUsers = allUsers.subList(startIndex, endIndex);

    // Create PageInfo object
    PageInfo pageInfo = createPageInfo(totalUsers, offset, limit);

    // Create UserWrapperDto object
    return new UserWrapperDto(paginatedUsers, pageInfo);
  }

  private PageInfo createPageInfo(int total, int offset, int limit) {
    PageInfo pageInfo = new PageInfo();
    pageInfo.setTotal(total);
    pageInfo.setHasNextPage((offset + limit) < total ? 1 : 0);
    pageInfo.setHasPreviousPage(offset > 0 ? 1 : 0);

    return pageInfo;
  }


}
