package com.devsuperior.movieflix.dto;

import java.util.Objects;

import com.devsuperior.movieflix.entities.User;


public class UserMinDTO {

   private Long id;
   private String name;
   private String email;
   
   public UserMinDTO() {
   }

   public UserMinDTO(String email, Long id, String name) {
       this.email = email;
       this.id = id;
       this.name = name;
   }
    
   public UserMinDTO(User entity) {
       email = entity.getEmail();
       id = entity.getId();
       name = entity.getName();
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
  
   
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserMinDTO other = (UserMinDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
