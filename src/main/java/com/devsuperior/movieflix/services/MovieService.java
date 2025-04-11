package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<MovieMinDTO> findAll(Pageable pageable) {
        Page<Movie> list = repository.findAll(pageable);
        return list.map(x -> new MovieMinDTO(x));
    }
    
    @Transactional(readOnly = true)
    public Page<MovieMinDTO> findByGenre(String genreId, Pageable pageable) {
        System.out.println("findByGenre - genreId: " + genreId);

        Long genreIdLong = Long.valueOf(genreId);
        System.out.println("findByGenre - genreIdLong: " + genreIdLong);

        Pageable pageableSorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSortOr(Sort.by("title").ascending()));
        
        Page<MovieProjection> page = repository.searchByGenre(genreIdLong, pageableSorted);
        System.out.println("page: " + page.getContent().get(0).getTitle());

      //  List<Movie> entities = repository.findMovieWithGenres(page.map(x -> x.getId()).toList());

      // public MovieMinDTO(Long genre, Long id, String imgUrl, String subTitle, String title, Integer year) {
      List<MovieMinDTO> dtos = page.map(x -> new MovieMinDTO(x.getGenreId(), x.getId(), x.getImgUrl(), x.getSubTitle(), x.getTitle(), x.getPublishedAt())).toList();
         //entities.stream().map(x -> new MovieMinDTO(x)).toList();
    /*	List<ProductDTO> dtos = entities.stream().map(p -> new ProductDTO(p, p.getCategories())).toList();*/

        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());

        
    }

	@Transactional(readOnly = true)
    public MovieDTO findById(Long id) { //detalhes do filme
        Optional<Movie> obj = repository.findById(id);
        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieDTO(entity);
    }
    
	@Transactional
	public MovieDTO insert(MovieDTO dto) {
		Movie entity = new Movie();
		copyDtoToEntity(dto, entity);
        entity = repository.save(entity);        
		return new MovieDTO(entity);
	}


    @Transactional
	public MovieDTO update(Long id, MovieDTO dto) {
		try {
			Movie entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new MovieDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    
    
    private void copyDtoToEntity(MovieDTO dto, Movie entity) {
        entity.setGenre(genreRepository.getReferenceById(dto.getGenre().getId()));
        entity.setTitle(dto.getTitle());
        entity.setSubTitle(dto.getSubTitle());
        entity.setYear(dto.getPublishedAt());
        entity.setImgUrl(dto.getImgUrl());
        entity.setSynopsis(dto.getSynopsis());
        /*for (ReviewDTO review : dto.getReviews()) {
            entity.getReviews().add(reviewRepository.getReferenceById(review.getId()));
        }*/
        // map não é mais rápido/ágil??
        dto.getReviews().stream().map(x -> entity.getReviews().add(reviewRepository.getReferenceById(x.getId())));
        
    }

}
