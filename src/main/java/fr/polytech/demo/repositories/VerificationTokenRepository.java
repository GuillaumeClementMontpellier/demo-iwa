package fr.polytech.demo.repositories;

import fr.polytech.demo.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {

}
