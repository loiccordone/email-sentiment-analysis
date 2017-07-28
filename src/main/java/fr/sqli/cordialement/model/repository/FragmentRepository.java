package fr.sqli.cordialement.model.repository;

import fr.sqli.cordialement.model.entity.Email;
import org.springframework.data.repository.CrudRepository;

import fr.sqli.cordialement.model.entity.Fragment;

// This will be AUTO IMPLEMENTED by Spring into a Bean called fragmentRepository
// CRUD refers Create, Read, Update, Delete

public interface FragmentRepository extends CrudRepository<Fragment, Long> {

}
