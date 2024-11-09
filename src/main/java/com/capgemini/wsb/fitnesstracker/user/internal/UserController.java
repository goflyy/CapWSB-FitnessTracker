package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.CreateUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UpdateUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserSummaryDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserOlderThanDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailDto;





import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Kontroler odpowiedzialny za operacje na użytkownikach, takie jak tworzenie, aktualizacja oraz 
 * pobieranie listy użytkowników.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Pobiera listę wszystkich użytkowników w systemie.
     *
     * @return ResponseEntity zawierające listę użytkowników w postaci obiektów UserSummaryDto
     */
    @GetMapping("/simple")
    public ResponseEntity<List<UserSummaryDto>> getAllUsers() {
        List<UserSummaryDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public Optional<UserDetailsDto> getDetailsUserById(@PathVariable("id") Long id) {
        return userService.getUser(id).map(userMapper::toDetailsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchByEmail")
    public List<UserEmailDto> searchUsersByEmail(@RequestParam("email") String emailFragment) {
        return userService.findUsersByEmailFragment(emailFragment);
    }



    /**
     * Tworzy nowego użytkownika w systemie.
     * 
     * @param createUserDto obiekt CreateUserDto zawierający dane nowego użytkownika
     * @return ResponseEntity zawierające obiekt UserSummaryDto nowo utworzonego użytkownika
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserSummaryDto> createUser(@RequestBody CreateUserDto createUserDto) {
        UserSummaryDto newUser = userService.createUser(createUserDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
 * Pobiera listę użytkowników urodzonych przed określoną datą.
 * 
 * @param time data, przed którą użytkownicy muszą się urodzić
 * @return ResponseEntity zawierające listę użytkowników urodzonych przed określoną datą
 */
@GetMapping("/older/{time}")
public ResponseEntity<List<UserOlderThanDto>> getUsersBornBefore(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
    List<UserOlderThanDto> users = userService.getUsersBornBefore(time);
    return ResponseEntity.ok(users);
}

    /**
     * Aktualizuje dane istniejącego użytkownika.
     * 
     * @param id identyfikator użytkownika, którego dane mają zostać zaktualizowane
     * @param updateUserDto obiekt UpdateUserDto zawierający zaktualizowane dane użytkownika
     * @return ResponseEntity zawierające zaktualizowany obiekt UserSummaryDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserSummaryDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        UserSummaryDto updatedUser = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
}
