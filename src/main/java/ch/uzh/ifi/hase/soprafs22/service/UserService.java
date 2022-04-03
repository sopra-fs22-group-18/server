package ch.uzh.ifi.hase.soprafs22.service;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;

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
    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //create a user
    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setCreation_date(new Date());
        setUserOnlineandLoggedin(newUser);
        checkIfUserExists(newUser);
        // save user and make it persistent
        newUser = userRepository.save(newUser);
        userRepository.flush();
        return newUser;
    }

    //getallUsers
    public List<User> getUsers() {
        return this.userRepository.findAll();}

    //get a User
    public User getUser(Long id) {
        //Retrieve user from repository using ID
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();}
        else{//throw error if no user found for this id in the repository
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", id));}}

    //login user check password and username
    public User checkingUser(User tocheckuser) {
        User foundUser = userRepository.findByUsername(tocheckuser.getUsername());
        //checking if username exists if not throw error
        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not found, can't log in");}
        //checking if password matches username if nott throw error
        if (!foundUser.getPassword().equals(tocheckuser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password used");}
        //set status to online and loggedin true
        setUserOnlineandLoggedin(foundUser); 
        return foundUser;}

    //update user
    public User updateUser(User userInputed) {
        Optional<User> foundUser = userRepository.findById(userInputed.getId());
        //check if the user that should be editet exists
        if (!foundUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", userInputed.getId()));}    
        //if user that should be editet exists, get user and update it
        User databaseuser=getUser(userInputed.getId());
        if (userRepository.findByUsername(userInputed.getUsername())!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user with username %d was not found", userInputed.getUsername()));}
        databaseuser.setUsername(userInputed.getUsername());
        databaseuser.setBirthday(userInputed.getBirthday());
        User upgedateUser=userRepository.save(databaseuser);
        return upgedateUser;}

    
    //set user offline and loggedin false
    public void setUserOfflineandLoggedout(User usertobelogedoutandsetofflien){
        usertobelogedoutandsetofflien.setStatus(UserStatus.OFFLINE);
        usertobelogedoutandsetofflien.setLogged_in(false);}
    
    //set user online and loggedin true
    public void setUserOnlineandLoggedin(User usertobesetonlineandloggedin){
        usertobesetonlineandloggedin.setStatus(UserStatus.ONLINE);
        usertobesetonlineandloggedin.setLogged_in(true);}

    //check if username already in use, if so throw error
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        String baseErrorMessage = "add User failed because username already exists";
        if(userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));}
    }

    public void checkAuthorization(UserPutDTO updatedUserpUTdto, Long id) {
        if(updatedUserpUTdto.getId()!=id){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format( "not authorized"));
        }
    }
    
}
