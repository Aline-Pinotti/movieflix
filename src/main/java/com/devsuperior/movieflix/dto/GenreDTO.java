package com.devsuperior.movieflix.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.devsuperior.movieflix.entities.Genre;

import jakarta.validation.constraints.NotBlank;

public class GenreDTO {

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String name;
    private List<MovieDTO> movies = new ArrayList<>();

    public GenreDTO() {
    }

    public GenreDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Genre entity) {
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
    public List<MovieDTO> getMovies() {
        return movies;
    }
    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
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
		GenreDTO other = (GenreDTO) obj;
		return Objects.equals(id, other.id);
	}
}
