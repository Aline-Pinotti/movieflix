package com.devsuperior.movieflix.dto;

import java.util.Objects;

import com.devsuperior.movieflix.entities.Review;

import jakarta.validation.constraints.NotBlank;

public class ReviewDTO {

    private Long id;
    @NotBlank(message = "Não é permitido texto vazio na avaliação")
    private String text;
    private Long movieId;
    private UserMinDTO user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, Long movie, UserMinDTO user) {
        this.id = id;
        this.text = text;
        this.movieId = movie;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        System.out.println("ID: " + id);
        text = entity.getText();
        movieId = entity.getMovie().getId();
        user = new UserMinDTO(entity.getUser());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public UserMinDTO getUser() {
        return user;
    }

    public void setUser(UserMinDTO user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ReviewDTO other = (ReviewDTO) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
