package com.hu.Virtualize.commands.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestCommand {
    private Long userId;
    private Long userInterestId;
    private String interest;
}

/**
 * When you want to insert new interest, then it must be pass
 * userId, interest
 */

/**
 * When you want to delete interest, then it must be pass
 * userId, userInterestId
 */