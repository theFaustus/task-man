package com.evil.inc.taskman.repository.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserFileRepositoryImpl implements UserRepository {

    public static UserFileRepositoryImpl INSTANCE;

    private UserFileRepositoryImpl() {
    }

    public static UserFileRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserFileRepositoryImpl();
        }

        return INSTANCE;
    }

    public static final String FILE_LOCATION = "taskmanager.ser";

    @Override
    public void save(final User user) {
        try (
                FileOutputStream fout = new FileOutputStream(FILE_LOCATION);
                ObjectOutputStream oos = new ObjectOutputStream(fout)
        ) {
            List<User> users = findAll();
            users.add(user);
            oos.writeObject(users);
        } catch (IOException e) {
            log.error("Something bad happened during fetching user = {} ", user, e);
        }
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return findAll()
                .stream()
                .filter(u -> u.getUserName().equals(username)).findFirst();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        try (
                FileInputStream fin = new FileInputStream(FILE_LOCATION);
                ObjectInputStream oos = new ObjectInputStream(fin)
        ) {
            return (List<User>) oos.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void update(User user) {
        List<User> users = findAll();
        try (
                FileOutputStream fout = new FileOutputStream(FILE_LOCATION);
                ObjectOutputStream oos = new ObjectOutputStream(fout)
        ) {
            ListIterator<User> iterator = users.listIterator();
            while (iterator.hasNext()){
                if(iterator.next().getUserName().equals(user.getUserName())){
                    iterator.set(user);
                }
            }
            oos.writeObject(users);
        } catch (IOException e) {
            log.error("Something bad happened during fetching users = {} ", users, e);
        }
    }

    @Override
    public void deleteById(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findById(final Long id) {
        return Optional.empty();
    }
}