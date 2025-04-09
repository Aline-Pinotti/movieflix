package com.devsuperior.movieflix.dto;

import java.util.Objects;

import com.devsuperior.movieflix.entities.Review;

import jakarta.validation.constraints.NotBlank;

public class ReviewDTO {

    private Long id;
    @NotBlank(message = "Não é permitido texto vazio na avaliação")
    private String text;
    private Long movieId;
    private Long userId;   

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, Long movie, Long user) {
        this.id = id;
        this.text = text;
        this.movieId = movie;
        this.userId = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        text = entity.getText();
        movieId = entity.getMovie().getId();
        userId = entity.getUser().getId();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
