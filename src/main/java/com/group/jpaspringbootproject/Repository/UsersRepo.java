package com.group.jpaspringbootproject.Repository;

import com.group.jpaspringbootproject.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);
}
