package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
//import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
//import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */
@RestController
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    //registration of user
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        // create user
        User createdUser = userService.createUser(userInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);

    }
    //login user
    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        userInput.setStatus(UserStatus.ONLINE);
        userInput.setLogged_in(true);
        // check login credentials
        User checkuser = userService.checkingUser(userInput);
        //converting internal representation to api representation
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(checkuser);
    }

   //Get specific user
    @GetMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@PathVariable Long id) {
        // fetch user
        User foundUser = userService.getUser(id);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(foundUser);} 
    
    //get all users
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();
        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;}


    // update user 
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    @ResponseBody
    public void updateUser(@RequestBody UserPutDTO updatedUserpUTdto) {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(updatedUserpUTdto);
        System.out.println("bitte hier schau");
        System.out.println(userInput.getId());
        userService.updateUser(userInput);

}



}