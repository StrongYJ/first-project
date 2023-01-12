package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MileagePointEntity;

@Repository
public interface MilegePointRepository extends JpaRepository<MileagePointEntity, Long>{
    
}
