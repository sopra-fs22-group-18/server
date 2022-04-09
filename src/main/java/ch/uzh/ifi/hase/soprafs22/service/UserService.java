package ch.uzh.ifi.hase.soprafs22.service;
import ch.uzh.ifi.hase.soprafs22.constant.Status;

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
        //newUser.setCreation_date(new Date());
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
    public User getUser(Long userId) {
        //Retrieve user from repository using ID

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return optionalUser.get();}
        else{//throw error if no user found for this id in the repository
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", userId));}}

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
    public User updateUser(User inputUser) {
        Optional<User> foundUser = userRepository.findById(inputUser.getUserId());
        //check if the user that should be editet exists
        if (!foundUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", inputUser.getUserId()));}
        //if user that should be editet exists, get user and update it
        User databaseuser=getUser(inputUser.getUserId());
        if (userRepository.findByUsername(inputUser.getUsername())!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user with username %d was not found", inputUser.getUsername()));}
        databaseuser.setUsername(inputUser.getUsername());
        databaseuser.setBirthday(inputUser.getBirthday());
        User upgedateUser=userRepository.save(databaseuser);
        return upgedateUser;}

    
    //set user offline and loggedin false
    public void setUserOfflineandLoggedout(User usertobelogedoutandsetofflien){
        usertobelogedoutandsetofflien.setStatus(Status.OFFLINE);}
        //usertobelogedoutandsetofflien.setLogged_in(false);}
    
    //set user online and loggedin true
    public void setUserOnlineandLoggedin(User usertobesetonlineandloggedin){
        usertobesetonlineandloggedin.setStatus(Status.ONLINE);}
        //usertobesetonlineandloggedin.setLogged_in(true);}

    //check if username already in use, if so throw error
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        String baseErrorMessage = "add User failed because username already exists";
        if(userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));}
    }

    public void checkAuthorization(UserPutDTO updatedUserpUTdto, Long id) {
        if(updatedUserpUTdto.getUserId()!=id){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format( "not authorized"));
        }
    }
    
}
