    package org.launchcode.techjobs.persistent.models;

    import jakarta.persistence.Entity;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.JoinTable;
    import jakarta.persistence.ManyToMany;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    public class Skill extends AbstractEntity {

        @ManyToMany(mappedBy = "skills")
        private List<Job> jobs = new ArrayList<>();

        @Size(min=2, max = 150, message = "Description must be between 2 to 150 characters")
        private String description;

//        failing test looking for getJobs as a List, yet it is returning it anyway.
        public  List<Job> getJobs() {
            return jobs;
        }

        public void setJobs(List<Job> jobs) {
            this.jobs = jobs;
        }

        public Skill() {
            // No-arg constructor for Hibernate to create objects when retrieving data from the database.
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
