package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills";
    }


    //    responds to /skills route and passes a list of all skills in the database (skillRepository ref)
//    to the view using the Model object
    @GetMapping("/")
    public String index(Model model) {
        Iterable<Skill> skills = skillRepository.findAll();
        model.addAttribute("skills", skills);
        return "skills/index";
    }

    @PostMapping("/skills/add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
//            jumps right back up to the start of this method instead of continuing to save and return to /skills
            return "skills/add";
        }

        // Save the valid skill object to the database
        skillRepository.save(newSkill);

        return "redirect:/skills"; // Redirect to the skills list after successful save
    }

// old one for comparision
//     @GetMapping("view/{skillId}")
//    public String displayViewSkill(Model model, @PathVariable int skillId) {
//
//        Optional optSkill = null;
//        if (optSkill.isPresent()) {
//            Skill skill = (Skill) optSkill.get();
//            model.addAttribute("skill", skill);
//            return "skills/view";
//        } else {
//            return "redirect:../";
//        }
//
//    }
//}

    @GetMapping("/skills/view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {
        Optional<Skill> optSkill = skillRepository.findById(skillId);

        if (optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:/skills"; // Handle the case where the skill with the provided ID does not exist
        }
    }
}