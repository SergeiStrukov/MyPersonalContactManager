package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.exceptions.EasyUserPasswordException;
import com.example.MyPersonalContactManager.exceptions.InvalidLoginPasswordException;
import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import com.example.MyPersonalContactManager.repository.UserRepository;
import com.example.MyPersonalContactManager.utils.UtilsRegistration;
import com.example.MyPersonalContactManager.utils.UtilsUserAuthorization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DataBaseUserService implements InterfaceUserService {

    private final UserRepository userRepository;
    private final UtilsRegistration utilsRegistration;
    private final UtilsUserAuthorization utilsUserAuth;

    @Override
    @Transactional
    public UserDTOResponse registerUser(UserDTORegister userDTORegister) {
        User existingUser = userRepository.findByLogin(userDTORegister.getLogin());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (!utilsRegistration.checkCorrectPassword(userDTORegister.getPassword())) {
            throw new EasyUserPasswordException("Your password is too easy");
        }

        User newUser = new User();
        newUser.setLogin(userDTORegister.getLogin());
        newUser.setPassword(userDTORegister.getPassword());
        newUser.setUserName(userDTORegister.getName());
        newUser.setRole(false);

        userRepository.save(newUser);

        String token = utilsRegistration.generateToken(newUser.getLogin(), newUser.getPassword());
        saveToken(token, newUser.getUserId());
        return UserDTOResponse.builder()
                .login(newUser.getLogin())
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public UserDTOResponse loginUser(UserDTOLogin userDTOLogin) {
        User existingUser = userRepository.findByLogin(userDTOLogin.getLogin());
        if (existingUser == null || !existingUser.getPassword().equals(userDTOLogin.getPassword())) {
            throw new InvalidLoginPasswordException("Invalid login or password.");
        }

        String token = utilsRegistration.generateToken(userDTOLogin.getLogin(), userDTOLogin.getPassword());
        saveToken(token, existingUser.getUserId());
        return UserDTOResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUserById(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDTOResponse updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            // Implement proper update logic
            return UserDTOResponse.builder().build();
        }
        return null;
    }

    @Override
    public String generateToken(String login, String password) {
        return utilsRegistration.generateToken(login, password);
    }

    @Override
    public String getUserIdByToken(String token) {
        return userRepository.findUserIdByToken(token);
    }

    @Override
    public boolean getUserRoleByToken(String token) {
        return userRepository.existsByToken(token);
    }

    private void saveToken(String token, String userId) {
        if (!userRepository.existsByToken(token)) {
            userRepository.insertToken(token, userId, LocalDateTime.now(), LocalDateTime.now());
        } else {
            userRepository.updateToken(token, userId, LocalDateTime.now());
        }
    }
}
