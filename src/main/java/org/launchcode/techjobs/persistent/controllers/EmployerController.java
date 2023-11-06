package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }


//    responds to /employers route and passes a list of all employers in the databaase (employerRepository ref)
//    to the view using the Model object
    @GetMapping("employers")
    public String index(Model model) {
        Iterable<Employer> employers = employerRepository.findAll();
        model.addAttribute("employers", employers);
        return "employers/index";
    }

    @PostMapping("/employers/add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "employers/add";
        }

        // Save the valid employer object to the database
        employerRepository.save(newEmployer);

        return "redirect:/employers"; // Redirect to the employers list after successful save
    }


    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = null;
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }

    }
}
