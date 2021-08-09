package com.example.week5challenge_jobslist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private static long idSetter = 0;

    ArrayList<Job> jobs = new ArrayList<>();

    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model, Principal principal) {
        String author = principal.getName();
        Job job = new Job();
        job.setAuthor(author);
        setId(job);
        model.addAttribute("job", job);
        return "add";
    }
    @PostMapping("/processAddJob")
    public String processAddJob(@Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "add";
        }
        for(Job j: jobs) {
            if(j.getId() == job.getId()) {
                j.setTitle(job.getTitle());
                j.setAuthor(job.getAuthor());
                j.setDescription(job.getAuthor());
                j.setPhone(job.getPhone());
                j.setPostedDate(job.getPostedDate());
                return "redirect:/";
            }
        }
        jobs.add(job);
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @GetMapping("/register")
    public String showRegPage(Model model) {
        model.addAttribute("user",  new User());
        return "register";
    }

    @PostMapping("processregister")
    public String processRegPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setPassword("");
            model.addAttribute("user", user);
            return "register";
        } else {
            model.addAttribute("user", user);
            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "index";
    }



    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable long id, Model model){
        for(Job job:jobs) {
            if (id == job.getId()) {
                model.addAttribute("job", job);
                break;
            }
        }
        return "add";
    }
    @RequestMapping("/details/{id}")
    public String jobDetails(@PathVariable("id") long id, Model model) {
        for(Job job: jobs) {
            if(job.getId() == id) {
                model.addAttribute("job", job);
                break;
            }
        }
        return "details";
    }

    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id, Model model) {
        for (Job job: jobs) {
            if (job.getId() == id) {
                jobs.remove(job);
                break;
            }
        }
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    public void setId(Job job) {
        idSetter += 1;
        job.setId(idSetter);
    }
}
