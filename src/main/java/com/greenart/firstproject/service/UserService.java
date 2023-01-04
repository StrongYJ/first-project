package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.LoginVo;
import com.greenart.firstproject.vo.UpdateUserVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository uRepo;

    public Map<String, Object> addUser(UserEntity data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(uRepo.countByEmail(data.getEmail()) == 1){
            resultMap.put("status", false);
            resultMap.put("message", data.getEmail()+"은/는 이미 등록된 사용자입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }else{
            uRepo.save(data);
            resultMap.put("status", true);
            resultMap.put("message", "회원이 등록되었습니다.");
            resultMap.put("code", HttpStatus.CREATED);
        }
        return resultMap;
    }

    public Map<String, Object> loginUser(LoginVo data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = null;
        loginUser = uRepo.findByEmailAndPwd(data.getEmail(), data.getPwd());
        if(loginUser == null){
            resultMap.put("stats", true);
            resultMap.put("message", "이메일 또는 비밀번호 오류입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
        }
        else{
            resultMap.put("status", true);
            resultMap.put("message", "로그인 되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            resultMap.put("loginUser", loginUser);
        }
        return resultMap;
    }

    public Map<String, Object> modifyUser(UpdateUserVo data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = null;
        loginUser = uRepo.findByEmailAndPwd(data.getEmail(), data.getPwd());
        if(loginUser.getPwd().equals(data.getPwd())){
            uRepo.save(loginUser);
            resultMap.put("status", true);
            resultMap.put("message", "수정이 완료 되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            resultMap.put("loginUser", null);
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호가 일치하지 않습니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
        }

        return resultMap;
    }
}
