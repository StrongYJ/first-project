package com.greenart.firstproject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MileagePointEntity;
import com.greenart.firstproject.entity.UserEntity;

@Repository
public interface MilegePointRepository extends JpaRepository<MileagePointEntity, Long>{

    List<MileagePointEntity> findByUser(UserEntity loginUser);
    
    // seq번호로 찾기
    @Query(value = "select c from MileagePointEntity c left join fetch c.user where c.user.seq = :uiSeq")
    List<MileagePointEntity> findByFetchByUserSeq(@Param("uiSeq") Long userSeq);

    List<MileagePointEntity> findByUserAndMpExpirationDateGreaterThanEqual(UserEntity user, LocalDate mpExpirationDate);

}
