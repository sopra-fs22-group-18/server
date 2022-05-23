package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserType;
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
        setUserOnlineStandard(newUser);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found, can't log in");}
        //checking if password matches username if nott throw error
        if (!foundUser.getPassword().equals(tocheckuser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password used");}
        //set status to online and loggedin true
        setUserOnlineStandard(foundUser);
        return foundUser;}

    //update user
    public User updateUser(User inputUser) {
        Optional<User> foundUser = userRepository.findById(inputUser.getUserId());
        //checks if the user that should be edited exists
        if (!foundUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", inputUser.getUserId()));}
        //if user that should be edited exists, get user and update it
        User databaseUser=getUser(inputUser.getUserId());
        if (userRepository.findByUsername(inputUser.getUsername())!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user with username %d was not found", inputUser.getUsername()));}

        // if the username is not null or "", then update it!
        if (inputUser.getUsername()!=null && !inputUser.getUsername().equals("")) {
            databaseUser.setUsername(inputUser.getUsername());
        }
        // if the password is not null or "", then update it!
        if (inputUser.getPassword()!=null && !inputUser.getPassword().equals("")) {
            databaseUser.setPassword(inputUser.getPassword());
        }
        // if the name is not null or "", then update it!
        if (inputUser.getName()!=null && !inputUser.getName().equals("")) {
            databaseUser.setName(inputUser.getName());
        }
        // if the avatarUrl is not null or "", then update it!
        if (inputUser.getAvatarUrl()!=null && !inputUser.getAvatarUrl().equals("")) {
            databaseUser.setAvatarUrl(inputUser.getAvatarUrl());
        }
        // if the bio is not null or "", then update it!
        if (inputUser.getBio()!=null && !inputUser.getBio().equals("")) {
            databaseUser.setBio(inputUser.getBio());
        }


        User updatedUser = userRepository.save(databaseUser);
        return updatedUser;}


    public User updateUserStatics(User inputUser){
        Optional<User> foundUser = userRepository.findById(inputUser.getUserId());
        if (!foundUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user with userid %d was not found", inputUser.getUserId()));}
        User databaseUser = getUser(inputUser.getUserId());
        databaseUser.setParticipated_sessions((inputUser.getParticipated_sessions()));
        databaseUser.setWonSessions(inputUser.getWonSessions());
        User updatedUser = userRepository.save(databaseUser);
        return updatedUser;
    }


    //set user offline and loggedin false
    public void setUserOffline(User userstatustobechanged){
        userstatustobechanged.setUserStatus(UserStatus.OFFLINE);}
    //usertobelogedoutandsetofflien.setLogged_in(false);}

    //set user online and loggedin true
    public void setUserOnlineStandard(User userstatustobechanged){
        userstatustobechanged.setUserStatus(UserStatus.ONLINE);
        userstatustobechanged.setUserType(UserType.STANDARD);}

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

