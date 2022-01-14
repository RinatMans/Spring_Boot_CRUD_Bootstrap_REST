package web_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web_crud.model.User;
import web_crud.service.UserService;

@Controller
@PreAuthorize("hasAnyRole('USER')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String getUserInfo(Authentication authentication, Model model) {

        User user = userService.findByUserName(authentication.getName());
        model.addAttribute("user", user);

        return "user";
    }

}


