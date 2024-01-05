package com.notes.tests;

import com.notes.domain.UserDomain;
import com.notes.domain.query.QUserDomain;
import com.notes.dto.RequestDto;
import com.notes.response.Response;
import com.notes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class UserSignupTests {

    @Mock
    QUserDomain qUserDomain;

    @InjectMocks
    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewUserSuccess() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUserName("john_doe");
        requestDto.setFirstName("John");
        requestDto.setPassword("password");
        requestDto.setConfirmPassword("password");

        UserDomain userDomain = new UserDomain();
        when(qUserDomain.userName.eq(anyString()).exists()).thenReturn(false);
        Response result = userService.registerUser(requestDto);

        assertTrue(result.isSuccessful());
        assertEquals(200, result.getStatus());
        assertNotNull(result.getResponseObject());
    }

//    @Test
//    public void testCreateNewUserPasswordMismatch() {
//        RequestDto requestDto = new RequestDto();
//        requestDto.setPassword("password1");
//        requestDto.setConfirmPassword("password2");
//
//        Response result = userService.registerUser(requestDto);
//
//        assertFalse(result.isSuccessful());
//        assertEquals(400, result.getStatus());
//        assertEquals("InvalidRequest", result.getError().getCode());
//        assertEquals("Password doesn't match", result.getError().getMessage());
//    }
//
//    @Test
//    public void testCreateNewUserExistingUsername() {
//        RequestDto requestDto = new RequestDto();
//        when(new QUserDomain().userName.eq(anyString()).exists()).thenReturn(true);
//
//        Response result = userService.registerUser(requestDto);
//
//        assertFalse(result.isSuccessful());
//        assertEquals(400, result.getStatus());
//        assertEquals("InvalidRequest", result.getError().getCode());
//        assertEquals("Username already exists", result.getError().getCode());
//    }
//
//    @Test
//    public void testCreateNewUserMissingDetails() {
//        RequestDto requestDto = new RequestDto();
//
//        Response result = userService.registerUser(requestDto);
//
//        assertFalse(result.isSuccessful());
//        assertEquals(400, result.getStatus());
//        assertEquals("MissingDetails", result.getError().getCode());
//        assertEquals("Fill all required information", result.getError().getCode());
//    }
}
