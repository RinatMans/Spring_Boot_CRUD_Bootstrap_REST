package web_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web_crud.model.Role;
import web_crud.model.User;
import web_crud.service.RoleService;
import web_crud.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String getUserList(Model model) {
        model.addAttribute("user", userService.getUsersList());
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user) {
        Set<Role> roleSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role role2 = roleService.findRoleByRoleName(role.getRole());
            roleSet.add(role2);
        }
        user.setRoles(roleSet);
        userService.updateUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/CreateNewUser")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "CreateNewUser";
    }

    @PostMapping("/CreateNewUser")
    public String create(@ModelAttribute("user") User user) {
        Set<Role> roleSet = new HashSet<>();
        for (Role r : user.getRoles()) {
            Role role = roleService.findRoleByRoleName(r.getRole());
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        userService.saveNewUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/remove/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}

