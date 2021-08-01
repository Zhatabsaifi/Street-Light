package com.hu.Virtualize.services.login;

import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.repositories.AdminRepository;
import com.hu.Virtualize.services.login.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    public AdminEntity getAdminById(Long id){
        Optional<AdminEntity> admin = adminRepository.findById(id);

        if (admin.isEmpty()) {
            log.error("Invalid Admin");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid admin");
        }
        return admin.get();
    }
}
