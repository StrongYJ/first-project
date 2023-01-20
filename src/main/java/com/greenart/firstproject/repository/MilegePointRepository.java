package com.greenart.firstproject.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MileagePointEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.vo.MileageInfoVO;

@Repository
public interface MilegePointRepository extends JpaRepository<MileagePointEntity, Long>{

    List<MileagePointEntity> findByUser(UserEntity loginUser);
    
}
