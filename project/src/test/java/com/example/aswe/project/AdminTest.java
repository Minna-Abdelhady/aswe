package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.controllers.adminController;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.adminRepository;

@RunWith(MockitoJUnitRunner.class)
public class AdminTest {

    @Mock
    private adminRepository adminRepo;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private adminController adminCont;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        ModelAndView mav = adminCont.addUser();
        assertNotNull(mav);
        assertEquals("/html/admin/add-user.html", mav.getViewName());
        assertNotNull(mav.getModel().get("user"));
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFName("Minna");
        user.setLName("Hany");
        user.setEmail("minna.hany@email.com");
        user.setPassword("password");
        UserType userType = new UserType();
        userType.setId(2); // id 2 corresponds to User type
        userType.setName(UserType.TYPE_USER);
        user.setType(userType);

        when(userRepository.save(user)).thenReturn(user);

        RedirectView redirectView = adminCont.saveUser(user);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("Minna", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
    }

    @Test
    public void testEditUser() {
        // Step 1: Mock the original user returned by findByid
        User originalUser = new User();
        originalUser.setId(1);
        originalUser.setFName("Minna");
        originalUser.setLName("Hany");
        originalUser.setEmail("minna.hany@gmail.com");
        originalUser.setPassword("password");

        // Mock findByid to return the originalUser
        when(userRepository.findByid(1)).thenReturn(originalUser);

        // Step 2: Prepare the updated user details
        User updatedUser = new User();
        updatedUser.setFName("Sara");
        updatedUser.setLName("Hany");
        updatedUser.setEmail("sara.hany@gmail.com");
        updatedUser.setPassword("newpassword");

        // Mock the save method to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Step 4: Call the saveProfile method
        RedirectView redirectView = adminCont.saveProfile(1, updatedUser);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        // Step 5: Verify the updated user details
        User savedUser = userRepository.findByid(1);
        assertNotNull(savedUser);
        assertEquals("Sara", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
        assertEquals("sara.hany@gmail.com", savedUser.getEmail());
    }

    @Test
    public void testDeleteUserAccount() {
        User user = new User();
        user.setId(1);
        user.setFName("Minna");
        user.setLName("Hany");
        user.setEmail("minna.hany@gmail.com");
        user.setPassword("password");

        // Mock the findById method to return the user
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RedirectView redirectView = adminCont.deleteUserAccount(1);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        // Mock the findById method to return empty after the user is deleted
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User deletedUser = userRepository.findById(1).orElse(null);
        assertNull(deletedUser);
    }
}
