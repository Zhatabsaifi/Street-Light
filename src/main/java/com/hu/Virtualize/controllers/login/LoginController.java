package com.hu.Virtualize.controllers.login;

import com.hu.Virtualize.commands.login.LoginCommand;
import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.commands.login.EmailRequest;
import com.hu.Virtualize.commands.login.EmailResponse;
import com.hu.Virtualize.entities.UserEntity;

import com.hu.Virtualize.services.login.service.AdminService;
import com.hu.Virtualize.services.login.service.SendMail;
import com.hu.Virtualize.services.login.service.LoginService;
import com.hu.Virtualize.services.login.service.UsersService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private SendMail sendMail;

    @Autowired
    private UsersService usersService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    /**
     * This function will help you to login .
     * @param loginCommand login details
     * @return user object.
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginCommand loginCommand) {
        log.info("Try to login user or admin");
        Object obj = loginService.login(loginCommand);
        if(obj == null ) {
            log.error("Wrong username or password. Please enter correct details");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
    }

    /**
     * This function will register the user.
     * @param userEntity user details.
     * @return user details object
     */
    @PostMapping("/users/add")
    public UserEntity addUser(@RequestBody UserEntity userEntity) {
        log.info("New user try to register yourself");
        return usersService.addUser(userEntity);
    }

    /**
     * This function help admin to register yourself.
     * @param adminEntity admin details.
     * @return admin details
     */
    @PostMapping("/admin/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminEntity adminEntity) {
        log.info("New Admin try to register yourself");
        AdminEntity admin = usersService.addAdmin(adminEntity);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    /**
     * This function will send you to the email for forgot password.
     * @param emailRequest receiver email.
     * @return status
     */
    @PostMapping("/sendInformation")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
        log.info("Send mail when user/admin forgot the password");
        boolean result = sendMail.sendEmail(emailRequest.getSubject(),emailRequest.getMessage(),emailRequest.getTo());
        if(result) {
            return ResponseEntity.ok(new EmailResponse("Email is sent successfully.."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Email not sent..."));
        }
    }

    /**
     * This function will return the admin details according to adminId.
     * @param id admin id
     * @return admin details.
     */
    @GetMapping(value = "/admin/{id}")
    public AdminEntity getAdminById(@PathVariable Long id){
        return adminService.getAdminById(id);
    }

    /**
     * This function will check the email is present or not in database.
     * @param loginCommand login command contains - id and type.
     * @return status
     */
    @PostMapping("/validLight")
    public ResponseEntity<?> validEmail(@RequestBody LoginCommand loginCommand) {
        Boolean status = loginService.validEmail(loginCommand);

        if(status) {
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This function will update the password .
     * @param loginCommand login details must give id, password and type
     * @return user object
     */
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody LoginCommand loginCommand) {
        Object obj = loginService.updatePassword(loginCommand);
        if(obj == null ) {
            log.error("Wrong username or password. Please enter correct details");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
    }
}
