package com.hu.Virtualize.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecommendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendId;

    @Lob
    private Byte[] recommendImage;

    private String categoryType;
    private String description;
    private Date endDate;

    public RecommendEntity(  String categoryType, Date endDate) {
        this.categoryType = categoryType;
        this.endDate = endDate;
    }

    public RecommendEntity(  String categoryType, String endDate, String description) {
        this.categoryType = categoryType;
        this.endDate = Date.valueOf(endDate);
        this.description = description;
    }
}
