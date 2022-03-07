package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

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
    return userGetDTOs;
  }


  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public tokensDTO createUser(@RequestBody UserPostDTO userPostDTO) {
    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // create user
    User createdUser = userService.createUser(userInput);


    // convert internal representation of user back to API
    return DTOMapper.INSTANCE.convertEntityToTokensDTO(createdUser);
}
  @PostMapping("/users/entry")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public tokensDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
      // convert API user to internal representation
      User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
      //System.out.println(userInput.getPassword());

      // check password
      User xuser = userRepository.findByUsername(userInput.getUsername());//get data
      //if passwords match OK else error is thrown
      if (!userInput.getPassword().equals(xuser.getPassword())){
          throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("FeelsBadMan wrong password"));
      }
      // convert internal representation of user back to API
      return DTOMapper.INSTANCE.convertEntityToTokensDTO(xuser);
  }
  @GetMapping ("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO stalking(@PathVariable("userId") Long userId) {
      //System.out.println(userId);
      // convert API user to internal representation
      UserIdDTO userIdDTO = new UserIdDTO();
      userIdDTO.setId(userId);
      User userInput = DTOMapper.INSTANCE.convertUserIdDTOToEntity(userIdDTO);


      User yUser = userRepository.getOne(userInput.getId());//get id
      System.out.println(yUser);



      // convert internal representation of user back to API
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(yUser);
  }

  @PutMapping ("/users/{userId}") //for editing we use put
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void editing(@PathVariable("userId") Long userId, @RequestBody editDTO editDTO)  {

      System.out.println("hallo"); //just a test to see if I even reach this state
      // convert API user to internal representation
      UserIdDTO userIdDTO = new UserIdDTO();
      userIdDTO.setId(userId);
      User userInput = DTOMapper.INSTANCE.convertUserIdDTOToEntity(userIdDTO);



      System.out.println(editDTO.getBirthDate());

      User zUser = userRepository.getOne(userInput.getId());



      userService.changeUser(zUser, editDTO);//update information


  }

  @PostMapping ("/users/editRights/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void checkingRights(@PathVariable("userId") Long userId, @RequestBody tokensDTO tokensDTO)  {

      //System.out.println("hallo");

      User bUser = userRepository.getOne(userId); //getting id

//checking if tokens match for editing. if match then no error is thrown so we continue in front end
      if(!bUser.getToken().equals(tokensDTO.getToken())){
          throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("FeelsBadMan you're not the same person"));
      }

  }

}

