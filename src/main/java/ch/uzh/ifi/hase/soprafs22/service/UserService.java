package ch.uzh.ifi.hase.soprafs22.service;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(Long id) {
        //Retrieve user from repository using ID
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        //throw error if no user found for this id in the repository
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", id));    }

        public User checkingUser(User tocheckuser) {
            User foundUser = userRepository.findByUsername(tocheckuser.getUsername());
            //checking if user exisists
            if (foundUser == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found, can't log in");
            }
            //checking password
            if (!foundUser.getPassword().equals(tocheckuser.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password used");
            }
            //set status to online
            foundUser.setStatus(UserStatus.ONLINE);
            foundUser.setLogged_in(true);

            return foundUser;
        }












    public User updateUser(User userInputed) {
        Optional<User> foundUser = userRepository.findById(userInputed.getId());
        if (!foundUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", userInputed.getId()));
        }    
        User databaseuser=getUser(userInputed.getId());
        databaseuser.setUsername(userInputed.getUsername());
        databaseuser.setBirthday(userInputed.getBirthday());
        User upgedateUser=userRepository.save(databaseuser);
        return upgedateUser;
    }











            /*
        //check if user exists

        //change the things und the found user
        
        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";

        userinut.setId(foundUser.getId());
        System.out.println(foundUser.getId());
        if(userinut.getUsername()==null)userinut.setUsername(foundUser.getUsername());
        if(userinut.getUsername()==foundUser.getUsername()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));

        System.out.println(foundUser.getUsername());
        if(userinut.getBirthday()==null)userinut.setBirthday(foundUser.getBirthday());
       
        System.out.println(foundUser.getBirthday());
        userinut.setPassword(foundUser.getPassword());
        System.out.println(foundUser.getPassword());
        userinut.setStatus(foundUser.getStatus());
        System.out.println(foundUser.getStatus());
        userinut.setToken(foundUser.getToken());
        System.out.println(foundUser.getToken());
        userinut.setCreation_date(foundUser.getCreation_date());
        System.out.println(foundUser.getCreation_date());
        checkIfUserExists(userinut);

        userinut= userRepository.save(userinut);
       


        //create log login
        log.debug("Found Information for User: {}", userinut);
        return userinut;
    }*/
    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setLogged_in(true);
        newUser.setCreation_date(new Date());
        newUser.setStatus(UserStatus.ONLINE);
        checkIfUserExists(newUser);
        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        return newUser;
    }

    

    public void logoutUser(User logoutUser) {
        //Maybe remove token or something like that?

        //set status to offline
        logoutUser.setLogged_in(false);
        logoutUser.setStatus(UserStatus.OFFLINE);

        log.debug("Logged out User: {}", logoutUser);
    }



    /**
     * This is a helper method that will check the uniqueness criteria of the username and the name
     * defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        //User userByName = userRepository.findByName(userToBeCreated.getPassword());

        String baseErrorMessage = "add User failed because username already exists";
        //if (userByUsername != null && userByName != null) {
         //   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username and the name", "are"));
        //}
        if(userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
        }
        
        //else if (userByName != null) {
           // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
        //}
    }
    // get user form users by id
    public User getUserById(long id) {
        User user = this.userRepository.findById(id);
        if(user!=null){
            return user;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with userid %d was not found", id));
        }
    }
}
