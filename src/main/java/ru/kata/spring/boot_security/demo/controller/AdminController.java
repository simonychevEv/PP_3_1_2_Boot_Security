package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getIndex(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/{username}")
    public String getUserByUsername(Model model, @PathVariable("username") String username) {
        model.addAttribute("user", userService.getUserByName(username));
        return "user";
    }

    @GetMapping("admin/user_info")
    public String createNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.getListRoles());
        return "user_info";
    }

    @PostMapping("/user_info")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/update_user")
    public String getUpdateUser(@RequestParam("userId") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listRoles", roleService.getListRoles());
        return "user_info";
    }

    @DeleteMapping("admin/delete_user")
    public String delete(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
