package com.hu.Virtualize.controllers.user;

import com.hu.Virtualize.commands.user.LightCommand;
import com.hu.Virtualize.entities.LightEntity;
import com.hu.Virtualize.services.user.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/light")
@RestController
@CrossOrigin("*")
public class LightDetails {
    @Autowired
    private StatusService statusService;

    /**
     * This function will add new interest in user list.
     * @param userInterestCommand user interest details.
     * @return User entity details.
     */
    @PostMapping("/create")
    public ResponseEntity<?> insertLight(@RequestBody LightCommand lightCommand) {
        LightEntity lightEntity = statusService.insertLight(lightCommand);
        return new ResponseEntity<>(lightEntity, HttpStatus.OK);
    }

    /**
     * This function will delete the user interest in user list.
     * @param userInterestCommand user interest details.
     * @return updated user details.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLight(@RequestBody LightCommand lightCommand) {
        UserEntity userLight = statusService.deleteLight(lightCommand);
        return new ResponseEntity<>(lightEntity, HttpStatus.OK);
    }
}
