package com.ashenone.AshenOne.repo;

import com.ashenone.AshenOne.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String>
{
    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String id);
}
