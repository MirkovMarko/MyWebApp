package com.example.mywebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> listAllUsers(){
        return (List<User>) repository.findAll();
    }

    public void save(User user) {
        repository.save(user);

    }

    public User get(Integer id) throws Exception, UserNotFoundException {
        Optional<User> result = repository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find any user with id:"+id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repository.countById(id);
        if(count == null){
            throw new UserNotFoundException("User not found with id "+id);
        }
        repository.deleteById(id);
    }
}
