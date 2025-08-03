package org.rdutta.userservice.repository;

import org.rdutta.userservice.entity.PSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PSUser, Long> {}