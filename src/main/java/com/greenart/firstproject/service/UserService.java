package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;

@Service
public class UserService {
    @Autowired UserRepository uRepo;

    public Map<String, Object> addUser(UserEntity data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        data.setSeq(seq);
        uRepo.save(data);
        resultMap.put("status", true);
        resultMap.put("message", "파일이 저장되었습니다.");
        resultMap.put("code", HttpStatus.OK);
        return resultMap;
    }
}
