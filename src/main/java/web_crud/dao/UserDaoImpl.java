package web_crud.dao;

import org.springframework.stereotype.Repository;
import web_crud.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("select distinct user from User user join fetch user.roles ", User.class).getResultList();
    }


    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }


    @Override
    public void updateUser(User user) {
        user.setId(user.getId());
        user.setFirstname(user.getFirstname());
        user.setLastName(user.getLastName());
        user.setAge(user.getAge());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setRoles(user.getRoles());

        entityManager.merge(user);
    }


    @Override
    public void deleteUser(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public User findByEmail(String email) {
        return (User) entityManager.createQuery("select user from User user join fetch user.roles where user.email=:email")
                .setParameter("email", email).getSingleResult();
        }


}

