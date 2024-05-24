package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        User originalUser = new User();
        originalUser.setId(1);
        originalUser.setFName("Minna");
        originalUser.setLName("Hany");
        originalUser.setEmail("minna.hany@gmail.com");
        originalUser.setPassword("password");

        when(userRepository.findById(1)).thenReturn(Optional.of(originalUser));
        when(userRepository.save(originalUser)).thenReturn(originalUser);

        User updatedUser = new User();
        updatedUser.setFName("Sara");
        updatedUser.setLName("Hany");
        updatedUser.setEmail("sara.hany@gmail.com");
        updatedUser.setPassword("newpassword");

        RedirectView redirectView = adminCont.saveProfile(1, updatedUser);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        when(userRepository.findById(1)).thenReturn(Optional.of(updatedUser));
        User savedUser = userRepository.findById(1).orElse(null);
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

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        RedirectView redirectView = adminCont.deleteUserAccount(1);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User deletedUser = userRepository.findById(1).orElse(null);
        assertNull(deletedUser);
    }
}
