// package com.example.aswe.project;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;
// import static org.junit.Assert.assertNull;
// import static org.mockito.Mockito.when;

// import java.util.Collections;

// import org.junit.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mindrot.jbcrypt.BCrypt;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.servlet.view.RedirectView;

// import com.example.aswe.project.controllers.UserController;
// import com.example.aswe.project.models.User;
// import com.example.aswe.project.models.UserType;
// import com.example.aswe.project.repositories.UserRepository;

// import jakarta.servlet.http.HttpSession;

// @ExtendWith(MockitoExtension.class)
// class UserTest {

//     @InjectMocks
//     private UserController userController;

//     @Mock
//     private UserRepository userRepository;

//     private MockMvc mockMvc;

//     @BeforeEach
//     void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//     }

//     @Test
//     public void testAddUser() {
//         ModelAndView mav = userController.addUser();
//         assertNotNull(mav);
//         assertEquals("/html/user/registration.html", mav.getViewName());
//         assertNotNull(mav.getModel().get("user"));
//     }

//     @Test
//     public void testSaveUser() {
//         User user = new User();
//         user.setEmail("test@example.com");
//         user.setPassword("password");
//         UserType userType = new UserType();
//         userType.setId(2); // id 2 corresponds to User type
//         userType.setName(UserType.TYPE_USER);
//         user.setType(userType);

//         when(userRepository.save(user)).thenReturn(user);

//         RedirectView redirectView = userController.saveUser(user, new BindingResult() {
//         });
//         assertNotNull(redirectView);
//         assertEquals("/User/Home", redirectView.getUrl());

//         when(userRepository.findByid(user.getId())).thenReturn(user);
//         User savedUser = userRepository.findByid(user.getId());
//         assertNotNull(savedUser);
//         assertEquals("test@example.com", savedUser.getEmail());
//     }

//     @Test
//     public void testLogin() {
//         ModelAndView mav = userController.login();
//         assertNotNull(mav);
//         assertEquals("/html/user/login.html", mav.getViewName());
//         assertNotNull(mav.getModel().get("user"));
//     }

//     @Test
//     public void testLoginProcess() {
//         User dbUser = new User();
//         dbUser.setId(1);
//         dbUser.setEmail("test@example.com");
//         dbUser.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(12)));

//         when(userRepository.findAll()).thenReturn(Collections.singletonList(dbUser));

//         RedirectView redirectView = userController.loginProcess("test@example.com", "password", new HttpSession() {

//         });
//         assertNotNull(redirectView);
//         assertEquals("/User/Home", redirectView.getUrl());
//     }
// }