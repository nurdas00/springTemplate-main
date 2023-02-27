package task.repository;

import org.springframework.data.repository.CrudRepository;
import task.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findUserByNameAndLastname(String name, String lastname);
}
