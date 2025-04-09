package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
	public Page<GenreDTO> findAllPaged(Pageable pageable) {
		Page<Genre> list = repository.findAll(pageable);
		return list.map(x -> new GenreDTO(x));
	}

	@Transactional(readOnly = true)
	public GenreDTO findById(Long id) {
		Optional<Genre> obj = repository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new GenreDTO(entity);
	}

	@Transactional
	public GenreDTO insert(GenreDTO dto) {
		Genre entity = new Genre();
		copyDtoToEntity(dto, entity);
        //TODO: Pegar User da Sessão
        entity = repository.save(entity);        
		return new GenreDTO(entity);
	}


    @Transactional
	public GenreDTO update(Long id, GenreDTO dto) {
		try {
			Genre entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new GenreDTO(entity);
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
    
    
    private void copyDtoToEntity(GenreDTO dto, Genre entity) {
        entity.setName(dto.getName());
        dto.getMovies().stream().map(x -> entity.getMovies().add(movieRepository.getReferenceById(x.getId())));
    }

}
