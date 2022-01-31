package web_crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/MyLogin")
    public String login() { return "MyLogin"; }

    @GetMapping("/")
    public String login1() {
        return "MyLogin";
    }
}
