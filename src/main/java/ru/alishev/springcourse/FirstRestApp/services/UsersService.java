package ru.alishev.springcourse.FirstRestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.FirstRestApp.models.User;
import ru.alishev.springcourse.FirstRestApp.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public Optional<User> findByUsername(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user.isPresent() ? user : Optional.empty();
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }
}
