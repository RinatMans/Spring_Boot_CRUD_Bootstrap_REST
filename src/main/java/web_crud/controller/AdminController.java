package web_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web_crud.model.User;
import web_crud.service.RoleService;
import web_crud.service.UserService;



@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String index(@AuthenticationPrincipal User user, Model model) {
        //User userLogin = userService.findByEmail(authentication.getName());

        model.addAttribute("user", userService.getUsersList());
        model.addAttribute("AllRoles", roleService.getAllRoles());
        model.addAttribute("userLogin", user);
        //model.addAttribute("user2", new User());
        return "admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "newRoles") String [] nameRoles,
                         @PathVariable(value = "id") long id) {
        user.setRoles(roleService.getSetOfRoles(nameRoles));
        userService.updateUser(user);
        System.out.println(user.getFirstname()+" "+user.getEmail()+" "+user.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(User user, Model model) {
        model.addAttribute("user2", new User());
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "newRoles") String [] newRoles) {
        user.setRoles(roleService.getSetOfRoles(newRoles));
        userService.saveNewUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/remove/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}

