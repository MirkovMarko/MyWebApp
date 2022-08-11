package com.example.mywebapp;

import com.example.mywebapp.user.User;
import com.example.mywebapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    @Test
    public void
    testAddNew(){
        User user = new User();
        user.setEmail("msdadmasdf@mm.com");
        user.setPassword("password1");
        user.setFirstName("Petar");
        user.setLastName("Petrovic");
       User saveUser = repository.save(user);
       Assertions.assertThat(saveUser).isNotNull();
       Assertions.assertThat(saveUser.getId()).isGreaterThan(0);
    }
     @Test
    public void testListAll(){
        Iterable<User> userList = repository.findAll();
        Assertions.assertThat(userList).hasSizeGreaterThan(0);
        for(User user : userList){
            System.out.println(user);
        }
    }
    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUser = repository.findById(userId);
        User user = optionalUser.get();
        user.setPassword("hello");
        repository.save(user);

        User updateUser = repository.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("hello");
    }
    @Test
    public void testGet(){
        Integer userId = 2;
        Optional<User> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }
    @Test
    public void testDelete(){
        Integer userId = 4;
        repository.deleteById(userId);
        Optional<User> optionalUser = repository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }

}
