package services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.services.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {
    @Mock
    UsersRepository usersRepository;

    @Test
    public void isAuthenticateWithCorrectCredentials() throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = new User(1L, "login", "password", false);
        when(usersRepository.findByLogin("login")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        doAnswer(invocation -> {
            User argUser = invocation.getArgument(0);
            argUser.setAuthentication(true);
            return null;
        }).when(usersRepository).update(user);
        assertTrue(usersService.authenticate("login", "password"));
        Mockito.verify(usersRepository).update(user);
    }

    @Test
    public void isWrongLogin() throws EntityNotFoundException {
        User user = new User(1L, "wrongLogin", "password", false);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        when(usersRepository.findByLogin(user.getLogin())).thenAnswer(invocation -> {
            String argLogin = invocation.getArgument(0);
            if ("login".equals(argLogin)) {
                return true;
            } else {
                throw new EntityNotFoundException("Неверные учетные данные");
            }
        });
        assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("wrongLogin", "password"));
    }

    @Test
    public void isWrongPassword() throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = new User(1L, "login", "password", false);
        when(usersRepository.findByLogin("login")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        usersService.authenticate("login", "wrongPassword");
        assertFalse(user.isAuthentication());
    }
}
