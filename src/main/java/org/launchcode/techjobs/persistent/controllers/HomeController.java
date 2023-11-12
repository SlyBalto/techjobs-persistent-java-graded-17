package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Job> jobs = jobRepository.findAll();

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobs);
        return "index";
    }

//    displays the add job form
    @GetMapping("/add")
    public String displayAddJobForm(Model model) {
	model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll()); // load employers data
        model.addAttribute("skills", skillRepository.findAll()); //load skill data

        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("/add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob, BindingResult bindingResult,
                                    Model model, @RequestParam int employerId,
                                    @RequestParam List<Integer> skills ) {

        if (bindingResult.hasErrors()) {
            // Handle validation errors and return to the form
            return "add";
        }

//      creates a List of skills called "skillObjs".
//        matches id's of skills selected with skill objects with matching id's in the repository. stores this list
//        of skill objects as skillObjs and gives it to newJob.
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        // Only proceed if the form data is validated
        if (!bindingResult.hasErrors()) {
            // Retrieve the selected employer using the employerId
            Optional<Employer> selectedEmployer = employerRepository.findById(employerId);

            if (selectedEmployer.isPresent()) {
                // Associate the selected employer with the new job
                newJob.setEmployer(selectedEmployer.get());
            } else {
                // Handle the case where the selected employer doesn't exist
            }

            // Save the new job with the selected employer to the database
            jobRepository.save(newJob);
        }
//takes the id from the job just created and uses that for the url. if the job has an id of 5, we are going to /view/5, etc.
        return "redirect:/view/" + newJob.getId(); // Redirect to the job list or wherever you want to navigate after successful save
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        // Use jobRepository to find the job by ID
        Optional<Job> optJob = jobRepository.findById(jobId);

        if (optJob.isPresent()) {
            Job job = optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            // Handle the case where the job with the provided ID does not exist
            return "redirect:/add"; // Redirect to a suitable page (e.g., homepage)
        }
    }
}