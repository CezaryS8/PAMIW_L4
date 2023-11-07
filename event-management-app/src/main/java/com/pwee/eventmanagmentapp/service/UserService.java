package com.pwee.eventmanagmentapp.service;

import com.pwee.eventmanagmentapp.dto.EventDto;
import com.pwee.eventmanagmentapp.dto.UserDto;
import com.pwee.eventmanagmentapp.entity.User;
import com.pwee.eventmanagmentapp.exception.UserNotFoundException;
import com.pwee.eventmanagmentapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User doesn't exist!"));
    }

    public UserDto getUserDtoById(Long userId) {
        return userRepository
                .findById(userId)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail()))
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map((user) -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail()))
                .collect(Collectors.toList());
    }

    public UserDto createUser(User user) {

        User createdUser = userRepository.save(user);

        return UserDto
                .builder()
                .id(createdUser.getId())
                .name(createdUser.getName())
                .surname(createdUser.getSurname())
                .email(createdUser.getEmail())
                .build();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto updateUserWithoutPasswd(UserDto user) {
        User userInDb = userRepository.findById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("User doesn't exist! Nothing updated."));

        userInDb
                .toBuilder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();

        userRepository.save(userInDb);

        return user;
    }

    public UserDto updateUser(User user) {
        User userInDb = userRepository.findById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("User doesn't exist! Nothing updated."));

        userInDb
                .toBuilder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();

        User updatedUser = userRepository.save(userInDb);

        return UserDto
                .builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .surname(updatedUser.getSurname())
                .email(updatedUser.getEmail())
                .build();
    }
}
