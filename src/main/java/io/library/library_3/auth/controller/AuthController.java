package io.library.library_3.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.library.library_3.auth.AuthExceptionMessages;
import io.library.library_3.auth.dtos.LibrarianCreationDTO;
import io.library.library_3.auth.dtos.StudentCreationDTO;
import io.library.library_3.auth.entity.AuthRequest;
import io.library.library_3.auth.entity.UserInfo;
import io.library.library_3.auth.mapper.AuthMapper;
import io.library.library_3.auth.service.JwtService;
import io.library.library_3.auth.service.UserInfoService;
import io.library.library_3.error_handling.exceptions.DuplicateEntityException;
import io.library.library_3.librarian.entity.Librarian;
import io.library.library_3.librarian.service.LibrarianService;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.service.StudentService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserInfoService userinfoService;
    private StudentService studentService;
    private LibrarianService librarianService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private AuthMapper authMapper;

    public AuthController(UserInfoService userinfoService, StudentService studentService,
            LibrarianService librarianService, JwtService jwtService,
            AuthenticationManager authenticationManager, AuthMapper authMapper) {
        this.userinfoService = userinfoService;
        this.studentService = studentService;
        this.librarianService = librarianService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authMapper = authMapper;
    }

    @PostMapping("/addNewUser/student")
    public String addNewStudent(@RequestBody StudentCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.getUsername())) {
            Student student = studentService.signUpStudent(authMapper.toStudent(dto));
            UserInfo user = authMapper.toUserInfo(dto);
            user.setId(student.getId());

            return userinfoService.addUser(user);
        } else {
            throw new DuplicateEntityException(AuthExceptionMessages.USERNAME_ALREADY_EXISTS(dto.getUsername()));
        }
    }

    @PostMapping("/addNewUser/librarian")
    public String addNewLibrarian(@RequestBody LibrarianCreationDTO dto) {
        if (!userinfoService.isDuplicateUsername(dto.getUsername())) {
            Librarian librarian = librarianService.addLibrarian(authMapper.toLibrarian(dto));
            UserInfo user = authMapper.toUserInfo(dto);
            user.setId(librarian.getId());

            return userinfoService.addUser(user);
        } else {
            throw new DuplicateEntityException(AuthExceptionMessages.USERNAME_ALREADY_EXISTS(dto.getUsername()));
        }
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
