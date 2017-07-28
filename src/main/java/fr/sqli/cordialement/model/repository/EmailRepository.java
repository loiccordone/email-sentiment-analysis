package fr.sqli.cordialement.model.repository;

import fr.sqli.cordialement.model.entity.EmailScore;
import org.springframework.data.repository.CrudRepository;

import fr.sqli.cordialement.model.entity.Email;

// This will be AUTO IMPLEMENTED by Spring into a Bean called emailRepository
// CRUD refers Create, Read, Update, Delete

public interface EmailRepository extends CrudRepository<Email, Long> {

    Email findById(Long id);
}
