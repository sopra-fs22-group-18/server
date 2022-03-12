package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
//import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  //private MockMvc mockMvc1;

  @MockBean
  private UserService userService;


  @Test
  public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
    // given
    User user = new User();
    user.setId(1L);
    user.setUsername("firstname@lastname");
    user.setPassword("test");
    user.setCreation_date(new Date());
    user.setLogged_in(true);
    user.setBirthday(null);
    List<User> allUsers = Collections.singletonList(user);

      given(userService.getUsers()).willReturn(allUsers);

    // this mocks the UserService -> we define above what the userService should
    // return when getUsers() is called
    given(userService.getUsers()).willReturn(allUsers);

    // when
    MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);
    System.out.print(mockMvc.perform(getRequest));
    // then
    mockMvc.perform(getRequest).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].username", is(user.getUsername())));
        //.andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
  }

    @Test
    public void getemptyUsers() throws Exception {
        // when
        MockHttpServletRequestBuilder getRequest = get("/users");

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNONexistentUser() throws Exception {
        // when
        MockHttpServletRequestBuilder getRequest = get("/users/1");

        // then
        mockMvc.perform(getRequest);
    }

  @Test
  public void createUser_validInput_userCreated() throws Exception {
    // given
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("test");
    user.setLogged_in(true);
    user.setCreation_date(new Date());
    user.setStatus(UserStatus.ONLINE);
    
    

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("testUsername");
    userPostDTO.setPassword("testPassword");

    given(userService.createUser(Mockito.any())).willReturn(user);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
        //.andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.username", is(user.getUsername())));
        //.andExpect(jsonPath("$.status", is(user.getStatus().toString())));
  }

    @Test
    public void maketwousers() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setCreation_date(new Date());
        user.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        //userPostDTO.setId(1L);
        userPostDTO.setUsername("testUsername");
        userPostDTO.setPassword("test");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));

        try {
            // when/then -> do the request + validate the result
            MockHttpServletRequestBuilder postRequest2 = post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userPostDTO));

            // then
            mockMvc.perform(postRequest2).andExpect(status().isConflict());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "cant create user");
        }
    }
/*
  @Test
  public void test_PUT_endpoint_working() throws Exception {
      // given
      User user = new User();
      user.setId(1L);
      user.setUsername("testUsername");
      user.setPassword("testPassword");
      user.setCreationDate(java.time.LocalDate.now());
      user.setStatus(OnlineStatus.ONLINE);

      given(userService.updateUser(user, user)).willReturn(user);

      UserPutDTO userPutDTO = new UserPutDTO();
      userPutDTO.setUsername("testUsername");
      userPutDTO.setPassword("test");

      // when/then -> do the request + validate the result
      MockHttpServletRequestBuilder postRequest = put("/users/" + user.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(userPutDTO));

      // then
      mockMvc.perform(postRequest)
              .andExpect(status().isNoContent())
              .andExpect(jsonPath("$.id", is(user.getId().intValue())))
              .andExpect(jsonPath("$.username", is(user.getUsername())))
              .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
  }

  @Test
  public void test_PUT_endpoint_NOTworking() throws Exception {
      // given
      User user = new User();
      user.setId(1L);
      user.setUsername("testUsername");
      user.setPassword("testPassword");
      user.setCreationDate(java.time.LocalDate.now());
      user.setStatus(OnlineStatus.ONLINE);

      given(userService.updateUser(user, user)).willReturn(user);

      UserPutDTO userPutDTO = new UserPutDTO();
      userPutDTO.setUsername("testUsername");
      userPutDTO.setPassword("test");

      // when/then -> do the request + validate the result
      MockHttpServletRequestBuilder postRequest = put("/users/" + user.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(userPutDTO));

      // then
      mockMvc.perform(postRequest)
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(user.getId().intValue())))
              .andExpect(jsonPath("$.username", is(user.getUsername())))
              .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
  }
*/

  /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test User", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e.toString()));
    }
  }
}