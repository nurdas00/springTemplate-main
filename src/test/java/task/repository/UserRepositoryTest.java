package task.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import task.entity.User;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Sql({"/sql/create-data-for-test.sql"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserByUsername() {
        User user = userRepository.findByUsername("voldemort");

        assertNotNull(user);
    }

    @Test
    public void shouldReturnUserByNameAndLastname() {
        User user = userRepository.findUserByNameAndLastname("tom", "reddle");

        assertNotNull(user);
    }
}
