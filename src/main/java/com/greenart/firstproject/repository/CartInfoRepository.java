package com.greenart.firstproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.UserEntity;

@Repository
public interface CartInfoRepository extends JpaRepository<CartInfoEntity, Long> {
    @Query(value = "select c from CartInfoEntity c join c.user u join fetch c.option o join fetch o.product where u.seq = :seq")
    List<CartInfoEntity> findByUserSeqWithFetch(@Param("seq") Long userSeq);

    List<CartInfoEntity> findByUser(UserEntity user);
    
    @Query(value = "SELECT c FROM CartInfoEntity c join c.user u join c.option o WHERE u = :user AND o.seq = :optionSeq")
    Optional<CartInfoEntity> findByUserAndOptionSeq(@Param("user") UserEntity user, @Param("optionSeq") Long optionSeq);

    @Query(value = "SELECT c FROM CartInfoEntity c join c.user u join c.option o WHERE u.seq = :user AND o.seq = :optionSeq")
    Optional<CartInfoEntity> findByUserSeqAndOptionSeq(@Param("user") Long userSeq, @Param("optionSeq") Long optionSeq);
}
