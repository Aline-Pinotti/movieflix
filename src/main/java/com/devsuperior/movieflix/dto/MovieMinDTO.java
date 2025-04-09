package com.devsuperior.movieflix.dto;

import java.util.Objects;

import com.devsuperior.movieflix.entities.Movie;

import jakarta.validation.constraints.NotBlank;

public class MovieMinDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String title;
    private String subTitle;
    private Integer year;
    private String imgUrl;    
    private Long genreId;

    public MovieMinDTO() {
        
    }

    public MovieMinDTO(Long genre, Long id, String imgUrl, String subTitle, String title, Integer year) {
        this.genreId = genre;
        this.id = id;
        this.imgUrl = imgUrl;
        this.subTitle = subTitle;
        this.title = title;
        this.year = year;
    }

    public MovieMinDTO(Movie entity) {
        id = entity.getId();
        title = entity.getTitle();
        subTitle = entity.getSubTitle();
        year = entity.getYear();
        imgUrl = entity.getImgUrl();
        genreId = entity.getGenre().getId();        
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genre) {
        this.genreId = genre;
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
		MovieMinDTO other = (MovieMinDTO) obj;
		return Objects.equals(id, other.id);
	}

}
