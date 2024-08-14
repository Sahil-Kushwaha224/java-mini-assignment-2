package com.example.UserDetailsProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id") // Added @Column annotation for the ID field
  private Long id;

  @Column(name = "name") // Added @Column annotation for the name field
  private String name;

  @Column(name = "age") // Added @Column annotation for the age field
  private Integer age;

  @Column(name = "gender") // Added @Column annotation for the gender field
  private String gender;

  @Column(name = "dob") // Added @Column annotation for the dob field
  private String dob;

  @Column(name = "nationality") // Added @Column annotation for the nationality field
  private String nationality;

  @Column(name = "verification_status") // Added @Column annotation for the verificationStatus field
  private String verificationStatus;

  @CreationTimestamp
  @Column(name = "date_created") // Added @Column annotation for the dateCreated field
  private String dateCreated;

  @CreationTimestamp
  @Column(name = "date_modified") // Added @Column annotation for the dateModified field
  private String dateModified;
}
