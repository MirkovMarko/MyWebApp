package com.example.mywebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller

public class UserController {
    @Autowired private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> listUsers = service.listAllUsers();
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String getNewUser(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Add new user");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@Valid User user, BindingResult result, RedirectAttributes ra){
        User existingUser = service.findByEmail(user.getEmail());
        if(existingUser != null){
            result.rejectValue("email", null, "Ovaj mejl postoji u bazi");
        }
        if(result.hasErrors()){
            return "user_form";
        }
        service.save(user);
        ra.addFlashAttribute("message","User has added successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditFor(@PathVariable("id") Integer id,Model model,RedirectAttributes ra) throws UserNotFoundException, Exception {

        User user = service.get(id);
        model.addAttribute("user",user);
        model.addAttribute("pageTitle","Edit user (ID = "+id+" )");
        return "user_form";


    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,RedirectAttributes ra) throws UserNotFoundException {
        service.delete(id);
        ra.addFlashAttribute("message","User has removed successfully!");
        return "redirect:/users";
    }



}
