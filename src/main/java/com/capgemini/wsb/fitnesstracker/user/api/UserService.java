package com.capgemini.wsb.fitnesstracker.user.api;
import java.time.LocalDate;
import java.util.List;

import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserOlderThanDto;
/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);
    List<UserSummaryDto> getAllUsers();
    UserSummaryDto createUser(CreateUserDto createUserDto);
    List<UserOlderThanDto> getUsersOlderThan(int age);
    UserSummaryDto updateUser(Long id, UpdateUserDto updateUserDto);
    List<UserOlderThanDto> getUsersBornBefore(LocalDate date);
    List<UserEmailDto> findUsersByEmailFragment(String emailFragment);

    boolean deleteUserById(Long id);

}
