package com.hu.Virtualize.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LightTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String discountName;
    private Integer discountPercentage;
    private Date endDate;

    private String discountDescription;

    public LightTypeEntity(String discountName, Integer discountPercentage, String date) {
        this.discountName = discountName;
        this.discountPercentage = discountPercentage;
        this.endDate = Date.valueOf(date);
    }

    public LightTypeEntity(String discountName, Integer discountPercentage, String date, String discountDescription) {
        this.discountName = discountName;
        this.discountPercentage = discountPercentage;
        this.endDate = Date.valueOf(date);
        this.discountDescription = discountDescription;
    }
}
