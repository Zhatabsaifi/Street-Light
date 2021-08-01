package com.hu.Virtualize.repositories;

import com.hu.Virtualize.entities.LightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightRepository extends JpaRepository<LightEntity, Long> {
    LightEntity findByGST(String gst);
}
