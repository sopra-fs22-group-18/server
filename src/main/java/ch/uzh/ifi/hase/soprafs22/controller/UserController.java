package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        this.userService = userService;}

    //registration of user
    @PostMapping("/users") //mapping of post request /users to the following methods
    @ResponseStatus(HttpStatus.CREATED) //marking following method class with status
    @ResponseBody //marking return as serizialiable into json and passing it back into the http response object
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        // create user
        User createdUser = userService.createUser(userInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);}

    //login user
    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        // check login credentials
        User checkuser = userService.checkingUser(userInput);
        //set status online and loggedin true
        userService.setUserOnlineandLoggedin(userInput);
        //converting internal representation to api representation
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(checkuser);}

   //Get specific user
    @GetMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@PathVariable Long id) {
        // fetch user
        User foundUser = userService.getUser(id);
        //converting internal representation to api representation
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
    public void updateUser(@RequestBody UserPutDTO updatedUserpUTdto, @PathVariable Long id) {
        //set id, to be able to identify user after username change
        if(updatedUserpUTdto.getId()!=id){

        }
        updatedUserpUTdto.setId(id);
        // convert API user to internal representation and update user
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(updatedUserpUTdto);
        userService.updateUser(userInput);}


    // logout user 
    @PutMapping("/logout/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO logout(@PathVariable Long id){
        //get user and set status offline and logged_in false
        User usertosetoffline=userService.getUser(id);
        userService.setUserOfflineandLoggedout(usertosetoffline);
         // convert API user to internal representation 
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(usertosetoffline);}

}


