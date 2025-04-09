package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    @Query(nativeQuery = true, value = """
                SELECT * FROM (
                SELECT m.id, m.title, m.sub_title, m.synopsis, m.release_year, m.img_url
                FROM tb_movie m
                INNER JOIN tb_genre g ON g.id = m.genre_id
                WHERE (:genreId IS NULL OR tb_genre.id = :genreId)
                ) AS tb_result
            """,
            countQuery = """
                SELECT * FROM (
                SELECT COUNT(*) FROM tb_movie m
                INNER JOIN tb_genre g ON g.id = m.genre_id
                        WHERE (:genreId IS NULL OR tb_genre.id = :genreId)
                ) AS tb_result
            """)
    Page<MovieProjection> searchByGenre(List<Long> genreId, Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :ids")
    List<Movie> findMovieWithGenres(List<Long> ids);

}
