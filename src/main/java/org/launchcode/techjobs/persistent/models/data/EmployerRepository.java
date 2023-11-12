package org.launchcode.techjobs.persistent.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.launchcode.techjobs.persistent.models.Employer;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, Integer> {

}