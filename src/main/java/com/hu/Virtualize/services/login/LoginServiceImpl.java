package com.hu.Virtualize.services.login;

import com.hu.Virtualize.commands.login.LoginCommand;
import com.hu.Virtualize.enums.LightTypeCommand;
import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.UserEntity;
import com.hu.Virtualize.geolocation.GeoLocation;
import com.hu.Virtualize.repositories.AdminRepository;
import com.hu.Virtualize.repositories.UserRepository;
import com.hu.Virtualize.services.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final GeoLocation geoLocation;

    public LoginServiceImpl(UserRepository userRepository,AdminRepository adminRepository,
                            BCryptPasswordEncoder passwordEncoder, GeoLocation geoLocation) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.geoLocation = geoLocation;
    }

    /**
     * This function will check the details is available or not in database.
     * If available then it will return the details, other return null.
     * @param loginCommand login details
     * @return status
     */
    @Transactional
    @Override
    public Object login(LoginCommand loginCommand) {
        // for user
        if(loginCommand.getType().equals(LightTypeCommand.USER.toString())) {
            UserEntity userEntity = userRepository.findByUserEmail(loginCommand.getId());

            // when user enter invalid user id or password
            if(userEntity == null || !passwordEncoder.matches(loginCommand.getPassword(), userEntity.getUserPassword())) {
                log.info("Not valid user details");
                return null;
            }

            // if latitude or longitude given by user then we will check the location
            if(loginCommand.getLatitude() != null && loginCommand.getLongitude() != null) {
                // update user location
                String currentCity = geoLocation.findCityByLatAndLng(loginCommand.getLatitude(), loginCommand.getLongitude());
                userEntity.setLocation(currentCity);

                log.info("" + loginCommand.getLatitude() + " " + loginCommand.getLongitude() + " " + currentCity);

                // save again the update user location
                userEntity = userRepository.save(userEntity);
            }

            return userEntity;
        } else {
            // for admin login
            Optional<AdminEntity> adminEntityOptional = adminRepository.findByAdminEmail(loginCommand.getId());

            // when admin enter wrong id password
            if(adminEntityOptional.isEmpty() || !passwordEncoder.matches(loginCommand.getPassword(), adminEntityOptional.get().getAdminPassword())) {
                log.info("Not valid admin details");
                return null;
            }
            return adminEntityOptional.get();
        }
    }

    /**
     * This function will check the email is valid or not.
     * @param loginCommand login details.
     * @return status (true or false) if available then return true, otherwise return false.
     */
    @Transactional
    public Boolean validEmail(LoginCommand loginCommand) {
        if(loginCommand.getType().equals(LightTypeCommand.USER.toString())) {
            UserEntity user = userRepository.findByUserEmail(loginCommand.getId());
            return user != null;
        } else {
            Optional<AdminEntity> adminEntity = adminRepository.findByAdminEmail(loginCommand.getId());
            return adminEntity.isPresent();
        }
    }

    /**
     * This function will update the password.
     * @param loginCommand login details
     * @return update object
     */
    @Transactional
    public Object updatePassword(LoginCommand loginCommand) {
        // encrypt the password
        loginCommand.setPassword(passwordEncoder.encode(loginCommand.getPassword()));

        // for user password update
        if(loginCommand.getType().equals(LightTypeCommand.USER.toString())) {
            UserEntity userEntity = userRepository.findByUserEmail(loginCommand.getId());

            userEntity.setUserPassword(loginCommand.getPassword());
            userEntity = userRepository.save(userEntity);

            return userEntity;
        } else {
            // for admin password update
            Optional<AdminEntity> adminEntity = adminRepository.findByAdminEmail(loginCommand.getId());

            if(adminEntity.isEmpty()) {
                log.error("This admin isn't valid in update password. Please check again");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This admin isn't valid in update password. Please check again");
            }

            AdminEntity admin = adminEntity.get();
            admin.setAdminPassword(loginCommand.getPassword());

            admin = adminRepository.save(admin);

            return admin;
        }
    }

    /**
     * This function will check the user type and email validation
     * @param loginCommand user id
     * @return "USER","ADMIN", and throw exception if not valid
     */
    @Transactional
    public String loginWithGoogleValidation(LoginCommand loginCommand) {
        UserEntity user = userRepository.findByUserEmail(loginCommand.getId());
        // id is present in the database
        if(user != null) {
            return LightTypeCommand.USER.toString();
        }

        // id is present in the database
        Optional<AdminEntity> adminEntity = adminRepository.findByAdminEmail(loginCommand.getId());
        if(adminEntity.isPresent()) {
            return LightTypeCommand.ADMIN.toString();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email address");
    }
}
