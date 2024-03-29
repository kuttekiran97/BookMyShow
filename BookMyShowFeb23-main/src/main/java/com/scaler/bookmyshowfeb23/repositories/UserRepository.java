package com.scaler.bookmyshowfeb23.repositories;

import com.scaler.bookmyshowfeb23.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long userId);
    //select * users where user_id = userId;

    Optional<User> findAllByEmail(String email);

    @Override
    User save(User user);
}
