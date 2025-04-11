package com.devsuperior.movieflix.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;

import jakarta.validation.constraints.NotBlank;

public class MovieDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String title;
    private String subTitle;
    private Integer publishedAt;
    private String imgUrl;    
    private String synopsis;
    private GenreDTO genre;
    private List<ReviewDTO> reviews = new ArrayList<>();

    public MovieDTO() {
        
    }

    public MovieDTO(GenreDTO genre, Long id, String imgUrl, String subTitle, String synopsis, String title, Integer year) {       
        this.id = id;
        this.imgUrl = imgUrl;
        this.subTitle = subTitle;
        this.synopsis = synopsis;
        this.title = title;
        this.publishedAt = year;
        this.genre = genre;
    }

    public MovieDTO(Movie entity) {
        id = entity.getId();
        title = entity.getTitle();
        subTitle = entity.getSubTitle();
        publishedAt = entity.getYear();
        imgUrl = entity.getImgUrl();
        synopsis = entity.getSynopsis();
        genre = new GenreDTO(entity.getGenre());
        for (Review review : entity.getReviews()) {
            reviews.add(new ReviewDTO(review));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Integer year) {
        this.publishedAt = year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreDTO genre) {
        this.genre = genre;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    @Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieDTO other = (MovieDTO) obj;
		return Objects.equals(id, other.id);
	}

}
