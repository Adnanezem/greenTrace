package com.greentracer.app.responses;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResponseTest {

    @Test
    public void testUserResponseConstructorAndGetters() {
        User user = new User(); // Assuming User has a default constructor
        UserResponse userResponse = new UserResponse("Test Message", 200, user);

        assertEquals("Test Message", userResponse.getMessage());
        assertEquals(200, userResponse.getStatus());
        assertEquals(user, userResponse.getUser());
    }

    @Test
    public void testUserResponseSetters() {
        User user = new User();
        UserResponse userResponse = new UserResponse("Test Message", 200, user);
        User newUser = new User();

        userResponse.setMessage("New Test Message");
        userResponse.setStatus(404);
        userResponse.setUser(newUser);

        assertEquals("New Test Message", userResponse.getMessage());
        assertEquals(404, userResponse.getStatus());
        assertEquals(newUser, userResponse.getUser());
    }
}