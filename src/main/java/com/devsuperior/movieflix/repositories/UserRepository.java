package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.UserDetailsProjection;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
        SELECT u.email AS username, u.password, r.id AS roleId, r.authority
        FROM tb_user u
        INNER JOIN tb_user_role ur ON u.id = ur.user_id
        INNER JOIN tb_role r ON ur.role_id = r.id
        WHERE u.email = :email
        """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

}
