package com.evil.inc.taskman.service.impl;

import java.util.List;
import java.util.Optional;

import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.UserRepository;
import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
    public static UserServiceImpl INSTANCE;

    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserServiceImpl getInstance(UserRepository userRepository) {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl(userRepository);
        }

        return INSTANCE;
    }


    @Override
    public void saveUser(User user) {
        log.info("Entered saveUser with user = {}", user);
        userRepository.save(user);
    }


    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        log.info("Entered deleteUserById with id = {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Entered getAllUsers");
        return userRepository.findAll();
    }

    @Override
    public void update(final User user) {
        userRepository.update(user);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        log.info("Entered findByUsername with username = {}", username);
        return this.userRepository.findByUsername(username);
    }
}
