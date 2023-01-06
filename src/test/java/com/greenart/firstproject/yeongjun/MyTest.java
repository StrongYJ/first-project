package com.greenart.firstproject.yeongjun;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.UserJoinVO;

@SpringBootTest
@Transactional
class MyTest {
    
<<<<<<< HEAD
    @Autowired
    private UserRepository utestRepo;
    
    // @Test
    // @Rollback(false)
    // void joinUserVotoEntity() {
    //     JoinUserVO a = new JoinUserVO("권영장", "yeongjun@fesa.com", "12345", null, LocalDate.of(1992, 2, 3), "01012345678", "서울시 용산구 대통령실");
    //     UserEntity b = new UserEntity(a);
    //     utestRepo.save(b);
    // }
=======
    @Test
    void test() {
    }
>>>>>>> mydev
}
// "name":"들어가라",
// "email":"young@service.com",
// "pwd":"1234",
// "nickname":"이게닉네임",
// "birth":"1900-11-03",
// "phone":"01012345678",
// "address":"서울시 용산구 대통령실"
