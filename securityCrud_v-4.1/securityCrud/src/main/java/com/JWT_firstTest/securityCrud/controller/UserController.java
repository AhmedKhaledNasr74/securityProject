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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if( ( userInfo.getName() == null ) || ( userInfo.getPassword() == null ) )
            return "email or password can't be empty !";
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInfo.getName());
        if (!matcher.matches()){
            return "InValid email !";
        }
        if( ( !userInfo.getRoles().equals("ROLE_USER") ) && ( !userInfo.getRoles().equals( "ROLE_ADMIN" ) ) ){
            return "InValid Role !";
        }
        Optional<UserInfo> user =  userInfoRepository.findByName(userInfo.getName());
        if( user.isPresent() ){
            return "User is Already Exist !";
        }
        if( userInfo.getPassword().length() < 8 ){
            return "password length must be at least 8 characters !";
        }
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
//test end points

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
