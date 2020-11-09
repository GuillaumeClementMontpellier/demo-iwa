package fr.polytech.demo.repositories;

import fr.polytech.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUserByUsername(String username);

    User findByUsername(String username);
}
