package web_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web_crud.model.User;
import web_crud.service.RoleService;
import web_crud.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RestAdminController {

    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.getUsersList();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        User userNew = user;
        return userService.saveNewUser(userNew);
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "/users/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }


}

