package com.JWT_firstTest.securityCrud.controller;

import com.JWT_firstTest.securityCrud.entity.AuthRequest;
import com.JWT_firstTest.securityCrud.entity.UserInfo;
import com.JWT_firstTest.securityCrud.repository.TaskRepository;
import com.JWT_firstTest.securityCrud.repository.UserInfoRepository;
import com.JWT_firstTest.securityCrud.service.JwtService;
import com.JWT_firstTest.securityCrud.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping("/signUp")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/signIn")
    public String authenticateAndGetToken(@RequestBody UserInfo user ) {
        Optional<UserInfo> user2 =  userInfoRepository.findByName(user.getName());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user2.get());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
//test end point

//    @GetMapping("/user/userProfile")
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    public String userProfile() {
//        return "Welcome to User Profile";
//    }
//
//    @GetMapping("/admin/adminProfile")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String adminProfile() {
//        return "Welcome to Admin Profile";
//    }
//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this endpoint is not secure";
//    }

}
