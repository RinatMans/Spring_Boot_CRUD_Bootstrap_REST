package web_crud.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web_crud.service.RoleService;
import web_crud.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class CreateUser {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public CreateUser(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @PostConstruct
    public void init() {
        User user = new User();
        User admin = new User();

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleAdmin);
        roleSet.add(roleUser);

        admin.setRoles(roleSet);
        admin.setName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@mail.ru");
        admin.setAge(36);
        admin.setPassword("admin");

        user.setRoles(Collections.singleton(roleUser));
        user.setName("user");
        user.setLastName("user");
        user.setEmail("user@mail.ru");
        user.setAge(34);
        user.setPassword("user");

        userService.saveNewUser(admin);
        userService.saveNewUser(user);

    }

}