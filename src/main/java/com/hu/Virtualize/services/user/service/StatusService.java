package com.hu.Virtualize.services.user.service;

import com.hu.Virtualize.commands.user.UserInterestCommand;
import com.hu.Virtualize.entities.UserEntity;

public interface StatusService {
    UserEntity insertInterest(UserInterestCommand userInterestCommand);
    UserEntity deleteInterest(UserInterestCommand userInterestCommand);
}
