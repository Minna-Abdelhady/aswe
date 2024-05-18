package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.controllers.adminController;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.adminRepository;

public class AdminTest {
    
    private adminRepository adminRepo = mock(adminRepository.class);
    private adminController adminCont = new adminController(adminRepo);
    // private adminController adminCont = new adminController();

    @Autowired
    private UserRepository userRepository = mock(UserRepository.class);

    // @Mock
    // private adminRepository adminRepo = mock(adminRepository.class);;

    // @InjectMocks
    // private adminController adminCont = new adminController(adminRepo);

    // @BeforeEach
    // public void setUp() {
    //     MockitoAnnotations.openMocks(this);
    //     adminRepo = mock(adminRepository.class);
    //     userRepository = mock(UserRepository.class);
    //     adminCont = new adminController(adminRepo, userRepository);
    // }

    @Test
    public void testAddUser() {
        ModelAndView mav = adminCont.addUser();
        assertNotNull(mav);        
        assertEquals("/html/admin/add-user.html", mav.getViewName());
        assertNotNull(mav.getModel().get("user"));
        // User user = (User) mav.getModel().get("user");
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

        RedirectView redirectView = adminCont.saveUser(user);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

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
        // System.out.println(userRepository);
        userRepository.save(originalUser);

        User updatedUser = new User();
        updatedUser.setFName("Sara");
        updatedUser.setLName("Hany");
        updatedUser.setEmail("sara.hany@gmail.com");
        updatedUser.setPassword("newpassword");

        RedirectView redirectView = adminCont.saveProfile(1, updatedUser);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

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
        userRepository.save(user);

        RedirectView redirectView = adminCont.deleteUserAccount(1);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        User deletedUser = userRepository.findByid(1);
        assertNull(deletedUser);
    }

}
