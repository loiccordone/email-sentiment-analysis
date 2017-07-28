package fr.sqli.cordialement.model.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sqli.cordialement.model.entity.Score;

// This will be AUTO IMPLEMENTED by Spring into a Bean called scoreRepository
// CRUD refers Create, Read, Update, Delete

public interface ScoreRepository extends CrudRepository<Score, Long> {

}
