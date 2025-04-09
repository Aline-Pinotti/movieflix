package com.devsuperior.movieflix.dto;

import java.util.Objects;

import com.devsuperior.movieflix.entities.Review;

public class ReviewDTO {

    private Long id;   
    private String text;
    private MovieDTO movie;
    private UserDTO user;

    public ReviewDTO(Long id, String text, MovieDTO movie, UserDTO user) {
        this.id = id;
        this.text = text;
        this.movie = movie;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        text = entity.getText();
        movie = new MovieDTO(entity.getMovie());
        user = new UserDTO(entity.getUser());
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
    public MovieDTO getMovie() {
        return movie;
    }
    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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
