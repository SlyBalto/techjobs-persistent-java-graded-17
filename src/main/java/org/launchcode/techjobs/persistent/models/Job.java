package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Job extends AbstractEntity{

//    Abstract Entity comes with ID and Name so those fields are automatically
//    taken care of.

    @ManyToOne
//    @JoinColumn(name = "employer_id")
    private Employer employer;

//    links Job and Skill tables with job_skills. Associates multiple skills with a single job in job_skills.
//    Many Jobs are linked to many skills.
    @ManyToMany
    @JoinTable(
            name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    List<Skill> skills = new ArrayList<>();

//    blank constructor
    public Job(){
    }

    // Initialize the id and value fields.
    public Job(Employer anEmployer, List<Skill> someSkills) {
        super();
        this.employer = anEmployer;
        this.skills = someSkills;
    }

    // Getters and setters.


    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public  List<Skill> getSkills() {
        return skills;
    }

    public void setSkills( List<Skill> skills) {
        this.skills = skills;
    }

}
