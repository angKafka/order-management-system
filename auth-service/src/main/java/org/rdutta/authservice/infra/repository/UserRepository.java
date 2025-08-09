package org.rdutta.authservice.infra.repository;

import org.rdutta.commonlibrary.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findAuthUserByEmail(String email);
}
