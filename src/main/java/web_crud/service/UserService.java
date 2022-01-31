package web_crud.service;

import web_crud.model.User;

import java.util.List;

public interface UserService {
    User saveNewUser(User user);

    List<User> getUsersList();

    User findById(Long id);

    User updateUser(User user);

    void deleteUser(Long id);

    User findByEmail(String email);

}
