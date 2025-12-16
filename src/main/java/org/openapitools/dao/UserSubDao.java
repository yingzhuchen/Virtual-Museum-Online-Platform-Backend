package org.openapitools.dao;

import org.openapitools.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserSubDao extends JpaRepository<UserSub, Long> {
    Optional<UserSub> findByUserId(Long userId);
}