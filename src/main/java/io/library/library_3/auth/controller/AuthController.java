package io.library.library_3.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.auth.entity.AuthRequest;
import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.auth.service.JwtService;
import io.library.library_3.auth.service.UserInfoService;
import io.library.library_3.librarian.service.LibrarianService;
import io.library.library_3.student.service.StudentService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserInfoService userinfoService;
    private StudentService studentService;
    private LibrarianService librarianService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthController(UserInfoService userinfoService, StudentService studentService,
            LibrarianService librarianService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userinfoService = userinfoService;
        this.studentService = studentService;
        this.librarianService = librarianService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/addNewUser/student")
    public String addNewStudent(@RequestBody UserInfo userInfo) {
        // TODO: Add student to database
        // FIXME: Create DTO for creation instead of UserInfo
        return userinfoService.addUser(userInfo);
    }

    @PostMapping("/addNewUser/librarian")
    public String addNewLibrarian(@RequestBody UserInfo userInfo) {
        // TODO: Add librarian to database
        // FIXME: Create DTO for creation instead of UserInfo
        return userinfoService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
