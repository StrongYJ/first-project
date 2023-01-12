package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.ReviewImgEntity;

@Repository
public interface ReviewImgRepository extends JpaRepository<ReviewImgEntity,Long>{
    
}
