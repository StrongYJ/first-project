package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MileagePointEntity;
import com.greenart.firstproject.entity.UserEntity;

import java.util.List;

@Repository
public interface MilegePointRepository extends JpaRepository<MileagePointEntity, Long>{
    List<MileagePointEntity> findByUser(UserEntity user);
}
