package ch.uzh.ifi.hase.soprafs22.controller;
import ch.uzh.ifi.hase.soprafs22.constant.Status;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import static org.mockito.BDDMockito.given;
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
    user.setUserId(1L);
    user.setUsername("firstname@lastname");
    user.setPassword("test");
    //user.setCreation_date(new Date());
    //user.setLogged_in(true);
    //user.setBirthday(null);
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
  public void given_no_Users_getUsers() throws Exception {
      // when
      MockHttpServletRequestBuilder getRequest = get("/users");
      // then
      mockMvc.perform(getRequest).andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void getUser_that_does_not_exist() throws Exception {
      // when
      MockHttpServletRequestBuilder getRequest = get("/users/1");
      // then
      mockMvc.perform(getRequest);
    }

  @Test
  public void createUser_validInput_userCreated() throws Exception {
    // given
    User user = new User();
    user.setUserId(1L);
    user.setUsername("firstname@lastname");
    user.setPassword("test");
    //user.setLogged_in(true);
    //user.setCreation_date(new Date());
    user.setStatus(Status.ONLINE);

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
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
              .andExpect(jsonPath("$.username", is(user.getUsername())));
              //.andExpect(jsonPath("$.logged_in", is(user.getLogged_in())));
              //.andExpect(jsonPath("$.creation_date", is(getFormatedDate(user.getCreation_date()))))
              //.andExpect(jsonPath("$.status", is(user.getStatus().toString())));


  }


    /*@Test
    public void testtwousers() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setLogged_in(true);
        user.setCreation_date(new Date());
        user.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
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
                  .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                  .andExpect(jsonPath("$.username", is(user.getUsername())));
                  //.andExpect(jsonPath("$.status", is(user.getStatus().toString())));

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
  */
  @Test
  public void test_Put_working() throws Exception {
      // given
      User user = new User();
      user.setUserId(1L);
      user.setUsername("testUsername");
      user.setPassword("testPassword");
      //user.setCreation_date(new Date());
      user.setStatus(Status.ONLINE);

      given(userService.updateUser(user)).willReturn(user);

      UserPutDTO userPutDTO = new UserPutDTO();
      userPutDTO.setUsername("testUsername");
      userPutDTO.setUserId(1L);

      // when/then -> do the request + validate the result
      MockHttpServletRequestBuilder postRequest = put("/users/" + user.getUserId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(userPutDTO));

      // then
      mockMvc.perform(postRequest)
              .andExpect(status().isNoContent())
              .andExpect(jsonPath("$").doesNotExist());}


    /*@Test
    public void testing_puntendpoints_failing() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUsername");
        user.setPassword("test");
        user.setCreation_date(new Date());
        user.setStatus(UserStatus.ONLINE);

        given(userService.updateUser(user)).willReturn(user);

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("testUsername");
        userPutDTO.setBirthday(null);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putrequest = put("/users/"+2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        // then

        mockMvc.perform(putrequest)
                .andExpect(status().isNotFound());
    }*/


    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
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