package com.hu.Virtualize.services.admin;

import com.hu.Virtualize.commands.admin.ShopCommand;
import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.LightEntity;
import com.hu.Virtualize.repositories.AdminRepository;
import com.hu.Virtualize.repositories.LightRepository;
import com.hu.Virtualize.services.admin.service.LightService;
import com.hu.Virtualize.services.login.service.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

@Slf4j
@Service
public class LightServiceImpl implements LightService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LightRepository lightRepository;

    @Autowired
    private SendMail sendMail;

    /**
     * This function will add new shop in admin list.
     * @param shopCommand shop details.
     * @return admin details.
     */
    @Transactional
    public AdminEntity insertShop(ShopCommand shopCommand) {
        LightEntity lightEntity = new LightEntity(shopCommand.getShopName(), shopCommand.getGST());

        // if location is given by admin
        if (shopCommand.getShopLocation() != null) {
            lightEntity.setShopLocation(shopCommand.getShopLocation());
        }

        // if description is given by admin
        if (shopCommand.getShopDescription() != null) {
            lightEntity.setShopDescription(shopCommand.getShopDescription());
        }

        AdminEntity admin = adminRepository.findByAdminId(shopCommand.getAdminId());

        if (admin == null) {
            log.error("Invalid Admin when admin add shop in his list");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Admin when admin add shop in his list");
        }

        String subject = "Status of your request for adding shop ";
        String body = "Your request for adding store is under consideration. We will let you know  once verification is done.";
        // send mail to admin about shop status
        sendShopRegisterSuccessMessage(admin.getAdminEmail(), subject,body);

        // check GST is valid or not
        checkGSTValidity(lightEntity.getGST(), admin.getAdminEmail());

        subject = "Your shop is successfully register on CouponPoint.";
        body = "Thanks to add your " + lightEntity.getShopName() + " shop (Location =  " + lightEntity.getShopLocation() + ") " +
                "on CouponPoint.";
        // send mail to admin about shop status
        sendShopRegisterSuccessMessage(admin.getAdminEmail(), subject,body);

        admin.getAdminShops().add(lightEntity);

        admin = adminRepository.save(admin);

        return admin;
    }

    /**
     * This function will check GST is valid or not
     * @param GST GST number
     * @return return true if GST valid otherwise throw exception
     */
    public boolean checkGSTValidity(String GST, String adminEmail) {
        String subject = "Status of your request for adding shop ";
        String body = "Your request for adding store is under consideration. We will let you know  once verification is done.";


        // GST number will be 15 number and first two digit will be state pin code
        LightEntity lightEntity = lightRepository.findByGST(GST);
        if(GST.length() != 15 || !Character.isDigit(GST.charAt(0)) || !Character.isDigit(GST.charAt(1))) {
            subject = "Your request for adding shop is rejected due to invalid GST No";
            body = "Your GST no for the shop is invalid. Please enter the valid GST No";
            sendShopRegisterSuccessMessage(adminEmail, subject,body);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, body);
        }

        // duplicate GST
        if(lightEntity != null) {
            subject = "Your request for adding shop is rejected due to duplicate GST Number.";
            body = "This GST Number is already registered with us.";
            sendShopRegisterSuccessMessage(adminEmail, subject,body);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, body);
        }
        return true;
    }

    /**
     * This function will send mail to the admin. In which list shop is added.
     * @param adminEmail admin email id
     * @param subject email subject
     * @param body email body
     * @return if mail send successfully then return true otherwise throw exception with internal server error.
     */
    public boolean sendShopRegisterSuccessMessage(String adminEmail, String subject, String body) {

        // this will send the message to admin
        boolean mailStatus =  sendMail.sendEmail(subject,body,adminEmail);
        if(!mailStatus) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is some internal error. Please try again");
        }
        return true;
    }

    /**
     * This function will update the shop details in admin list.
     * @param shopCommand shop and admin details.
     * @return admin details.
     */
    @Transactional
    public AdminEntity updateShop(ShopCommand shopCommand) {

        AdminEntity admin = adminRepository.findByAdminId(shopCommand.getAdminId());

        if (admin == null) {
            log.error("Invalid Admin when admin update shop details");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Admin when admin update shop details");
        }

        boolean presentShop = false;
        LightEntity adminUpdateShop = null;

        for (LightEntity shop : admin.getAdminShops()) {
            if (shop.getShopId().equals(shopCommand.getShopId())) {
                presentShop = true;

                // if shop name will change
                if (shopCommand.getShopName() != null) {
                    shop.setShopName(shopCommand.getShopName());
                }

                // if shop location will change
                if (shopCommand.getShopLocation() != null) {
                    shop.setShopLocation(shopCommand.getShopLocation());
                }
                adminUpdateShop = shop;
                break;
            }
        }

        if (!presentShop) {
            log.error("This shop doesn't comes under given admin");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This shop doesn't comes under given admin");
        }

        // update shop value
        admin.getAdminShops().add(adminUpdateShop);
        // update information
        admin = adminRepository.save(admin);
        return admin;
    }

    /**
     * This function will delete the shop in admin list and all shop product.
     * @param shopCommand shop or admin details.
     * @return status
     */
    @Transactional
    public AdminEntity deleteShop(ShopCommand shopCommand) {
        AdminEntity admin = adminRepository.findByAdminId(shopCommand.getAdminId());

        if (admin == null) {
            log.error("Invalid Admin when admin delete shop");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Admin when admin delete shop");
        }

        Set<LightEntity> adminShops = new HashSet<>();

        // check shop is valid or not
        boolean presentShop = false;
        for (LightEntity shop : admin.getAdminShops()) {
            if (shop.getShopId().equals(shopCommand.getShopId())) {
                presentShop = true;
            } else {
                adminShops.add(shop);
            }
        }

        if (!presentShop) {
            log.error("This shop doesn't comes under given admin");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This shop doesn't comes under given admin");
        }

        admin.setAdminShops(adminShops);
        admin = adminRepository.save(admin);

        // delete shop and all its product and with all product discount
        lightRepository.deleteById(shopCommand.getShopId());

        return admin;
    }

    /**
     * This function will gives all shops of particular admin through its admin Id
     * @param id Admin Id
     * @return Shop Entity
     */
    @Transactional
    public Set<LightEntity> getAllShopsByAdminId(Long id) {

        Optional<AdminEntity> adminEntityOptional = adminRepository.findById(id);
        if (adminEntityOptional.isEmpty()) {
            log.error("Invalid Admin ");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid admin");
        }

        return adminEntityOptional.get().getAdminShops();
    }

    /**
     * This function will insert image into Shop
     * @param shopId shop Id
     * @param multipartFile shop Image
     * @return status message
     */
    public String insertShopImage(Long shopId, MultipartFile multipartFile) {
        try{
            LightEntity lightEntity = findShopById(shopId);

            // convert MultipartFile into byte array and store in product entity
            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];

            // copy the file data into byte array
            int i = 0;
            for (byte b : multipartFile.getBytes()){
                byteObjects[i++] = b;
            }

            // set the profile image
            lightEntity.setShopImage(byteObjects);
            lightEntity = lightRepository.save(lightEntity);
            log.info("Insert image for shop is successfully done");
            return "Image update successfully for shop: " + lightEntity.getShopId();
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * This function will fetch shop through its Id
     * @param shopId Shop Id
     * @return ShopEntity
     */
    public LightEntity findShopById(Long shopId) {
        Optional<LightEntity> shopEntity = lightRepository.findById(shopId);

        // if shopId isn't valid
        if(shopEntity.isEmpty()) {
            log.error("Invalid shop");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This shopId isn't valid. Please enter valid number");
        }
        return shopEntity.get();
    }
}
