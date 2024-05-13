package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.ProcessBuilder.Redirect;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.controllers.UserController;
import com.example.aswe.project.controllers.adminController;
import com.example.aswe.project.models.User;
import com.example.aswe.project.repositories.adminRepository;

public class AdminTest {
    
    private adminRepository adminRepo = mock(adminRepository.class);
    private adminController adminCont = new adminController(adminRepo);

    @Test
    public void testAddUser() {
        ModelAndView mav = adminCont.addUser();
        assertEquals("/html/admin/add-user.html", mav.getViewName());
        User user = (User) mav.getModel().get("user");
        assertNotNull(user);
    }

    // Same function with the same functions another way
    // @Test
    // public void testAddUser2() {
    //     ModelAndView mav = adminCont.addUser();
    //     assertEquals("/html/admin/add-user.html", mav.getViewName());
    //     assertNotNull(mav.getModel().get("user"));
    // }

    @Test 
    public void testSaveUser() {
        // User user = new User();
        // RedirectView redirectView = adminCont.saveUserTest(user);
        // assertEquals("/Admin/List-Users", redirectView.getUrl());
        // verify(adminRepo, times(1)).save(user);
        User user = new User();
        RedirectView redirectView = adminCont.saveUser(user);
        assertEquals("/Admin/List-Users", redirectView.getUrl());
    }

}
