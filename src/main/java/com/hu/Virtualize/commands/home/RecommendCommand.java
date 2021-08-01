package com.hu.Virtualize.commands.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendCommand {
    private Long recommendId;

    private String categoryType;
    private String description;
    private String endDate;   // YYYY-MM-DD
}

/**
 * When you want to insert new recommend then
 * pass categoryType, description, endDate
 */

/**
 * When you want to delete then just pass
 * recommendId
 */