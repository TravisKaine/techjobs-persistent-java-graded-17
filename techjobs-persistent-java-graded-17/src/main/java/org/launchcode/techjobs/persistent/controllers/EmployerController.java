package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {


    //TASK2 CONTROLLERS.1
    @Autowired
    private EmployerRepository employerRepository;

    //TASK2 CONTROLLERS.2 add an index method
    @GetMapping("/")
    public String index(Model model) {
        List employers = (List<Employer>) employerRepository.findAll();
        model.addAttribute("employers", employers); //To figure out the name of the model attribute you should use to pass employers into the view, review this template.
        return "employers/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        //TASK 2 CONTROLLER.3
        employerRepository.save(newEmployer);
        //
        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        //I replaced NULL with employerRepository.findById(***employerId***);
        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }


}