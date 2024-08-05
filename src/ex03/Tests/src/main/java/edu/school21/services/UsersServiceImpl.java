package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = usersRepository.findByLogin(login);
        if(user.isAuthentication()) {
            throw new AlreadyAuthenticatedException("He's already come in");
        }

        if(user.getPassword().equals(password)) {
            usersRepository.update(user);
        }
        return user.isAuthentication();
    }
}
