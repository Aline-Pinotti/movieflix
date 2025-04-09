package com.devsuperior.movieflix.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.devsuperior.movieflix.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

   private Long id;
   @NotBlank(message = "Campo obrigatório")
   private String name;
   @Email(message = "Favor entrar um email válido")  
   private String email;
   private String password;
   private List<ReviewDTO> reviews = new ArrayList<>();
   private Set<RoleDTO> roles = new HashSet<>();

   public UserDTO() {
   }

   public UserDTO(String email, Long id, String name, String password) {
       this.email = email;
       this.id = id;
       this.name = name;
       this.password = password;
   }
    
   public UserDTO(User entity) {
       email = entity.getEmail();
       id = entity.getId();
       name = entity.getName();
       password = entity.getPassword();
   }

   public UserDTO(User entity, List<ReviewDTO> reviews) {
       this(entity);
       //this.review = review;
       reviews.forEach(r -> this.addReview(r));
   }

   public Long getId() {
    return id;
   }
   public void setId(Long id) {
    this.id = id;
   }
   public String getName() {
    return name;
   }
   public void setName(String name) {
    this.name = name;
   }
   public String getEmail() {
    return email;
   }
   public void setEmail(String email) {
    this.email = email;
   }
   public String getPassword() {
    return password;
   }
   public void setPassword(String password) {
    this.password = password;
   }
   public List<ReviewDTO> getReviews() {
    return reviews;
   }

   public void setReviews(List<ReviewDTO> review) {
       this.reviews = review;
   }

   public void addReview(ReviewDTO review) {
       reviews.add(review);
   }
   
   public Set<RoleDTO> getRoles() {
    return roles;
   }

   public void setRoles(Set<RoleDTO> roles) {
       this.roles = roles;
   }
   
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserDTO other = (UserDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
