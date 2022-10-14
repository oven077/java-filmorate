package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Valid
@RestController
@RequestMapping("/users")
@Slf4j

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get users
    @GetMapping()
    public List<User> returnUsers() {
        log.info("controller:method userController -> returnUsers");
        return userService.returnUsers();
    }

    //get user
    @GetMapping("/{id}")
    public User returnUser(@PathVariable int id) {
        log.info("controller:method userController -> returnUser");
        return userService.returnUserById(id);
    }


    //add user
    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("controller:method userController -> addUser");
        userService.addUser(user);
        return user;
    }

    //update user
    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        log.info("controller:method userController -> updateUser");
        userService.updateUser(user);
        return user;
    }

    //add user to friend list
    @PutMapping("/{id}/friends/{friendId}")
    public void addFilmLike(@PathVariable int id,
                            @PathVariable int friendId
    ) {
        userService.addFriend(id, friendId);
    }

    //user remove friend list
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriendFromList(@PathVariable int id,
                                     @PathVariable int friendId
    ) {
        userService.deleteFromFriendList(id, friendId);
    }

    //get common friends
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> popular(@PathVariable int id,
                              @PathVariable int otherId) {
        return userService.returnCommonFriends(id, otherId);
    }

    //get user friends
    @GetMapping("/{id}/friends")
    public List<User> returnUserFriends(@PathVariable Integer id) {
        return userService.returnUserFriends(id);
    }


}
