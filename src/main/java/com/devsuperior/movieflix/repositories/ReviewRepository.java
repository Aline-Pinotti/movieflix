package com.devsuperior.movieflix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Long, ReviewRepository> {
    

}
