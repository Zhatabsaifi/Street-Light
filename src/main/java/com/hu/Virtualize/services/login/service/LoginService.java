package com.hu.Virtualize.services.login.service;

import com.hu.Virtualize.commands.login.LoginCommand;

public interface LoginService {
    Object login(LoginCommand loginCommand);
    Boolean validEmail(LoginCommand loginCommand);
    Object updatePassword(LoginCommand loginCommand);
    String loginWithGoogleValidation(LoginCommand loginCommand);
}
