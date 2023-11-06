package org.launchcode.techjobs.persistent.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.launchcode.techjobs.persistent.models.Skill;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer> {
    // You can add custom query methods here if needed
}