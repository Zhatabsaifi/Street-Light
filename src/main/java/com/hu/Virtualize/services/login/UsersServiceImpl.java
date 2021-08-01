package com.hu.Virtualize.services.login;

import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.UserEntity;
import com.hu.Virtualize.repositories.AdminRepository;
import com.hu.Virtualize.repositories.UserRepository;
import com.hu.Virtualize.services.login.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    /**
     * This function will register new user.
     * @param userEntity user details.
     * @return user entity
     */
    @Override
    public UserEntity addUser(UserEntity userEntity) {
        UserEntity email = userRepository.findByUserEmail(userEntity.getUserEmail());

        if(email != null){
            log.error("Email is already registered!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already exists!");
        }

        // encrypt the user password
        userEntity.setUserPassword(passwordEncoder.encode(userEntity.getUserPassword()));

        userEntity = userRepository.save(userEntity);

        return userEntity;
    }

    /**
     * This function will register new admin.
     * @param adminEntity admin details.
     * @return admin entity.
     */
    public AdminEntity addAdmin(AdminEntity adminEntity) {
        Optional<AdminEntity> duplicateEntity = adminRepository.findByAdminEmail(adminEntity.getAdminEmail());

        // if email already available in database
        if(duplicateEntity.isPresent()) {
            log.error(adminEntity.getAdminEmail() + " already available in database");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already present.");
        }

        // encrypt the admin password
        adminEntity.setAdminPassword(passwordEncoder.encode(adminEntity.getAdminPassword()));
        adminEntity = adminRepository.save(adminEntity);
        return adminEntity;
    }
}
