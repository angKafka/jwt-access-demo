package org.rdutta.jwt_access_demo.repository;

import java.util.Optional;

import org.rdutta.jwt_access_demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
