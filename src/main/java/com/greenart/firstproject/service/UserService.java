package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserJoinWelcomeVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository uRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Boolean isDuplicatedEmail(String email){
        if(uRepo.countByEmail(email) > 0) {
            return true;
        }
        return false;
    }

    public Boolean isDuplicatedNickname(String nickname){
        if(uRepo.countByNickname(nickname) > 0){
            return true;
        }
        return false;
    }

    public UserJoinWelcomeVO addUser(UserJoinVO data) {
        if(isDuplicatedEmail(data.getEmail())) return null;
        if(isDuplicatedNickname(data.getNickname())) return null;
        data.setPwd(passwordEncoder.encode(data.getPwd()));
        UserEntity newUser = new UserEntity(data);
        uRepo.save(newUser);
        return new UserJoinWelcomeVO(newUser.getSeq(), newUser.getName(), newUser.getNickname());
    }

    public Map<String, Object> loginUser(UserLoginVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = uRepo.findByEmail(data.getEmail()).orElseThrow(() -> 
                    new NoSuchElementException("이메일이나 비밀번호가 잘못되었습니다."));
        if(passwordEncoder.matches(data.getPwd(), loginUser.getPwd())){
            if(loginUser.getStatus() == 2){
                resultMap.put("status", false);
                resultMap.put("message", "정지 회원 입니다.");
                resultMap.put("code", HttpStatus.FORBIDDEN);
            }
            else if(loginUser.getStatus() == 3){
                resultMap.put("status", false);
                resultMap.put("message", "탈퇴한 회원 입니다.");
                resultMap.put("code", HttpStatus.FORBIDDEN);
            } else {
                resultMap.put("status", true);
                resultMap.put("message", "로그인 되었습니다.");
                resultMap.put("code", HttpStatus.ACCEPTED);
                String jwt = JwtProperties.TOKEN_PREFIX + jwtUtil.create(loginUser.getSeq());
                resultMap.put("Authorization", jwt);
            }
        }

        // if(loginUser.isEmpty()){
        //     resultMap.put("stats", false);
        //     resultMap.put("message", "이메일 또는 비밀번호 오류입니다.");
        //     resultMap.put("code", HttpStatus.BAD_REQUEST);
        // }

        return resultMap;
    }

    public Map<String, Object> modifyUser(UserUpdateVO data, Long seq){
        // Optional<UserEntity> findById = uRepo.findById(loginUser.getSeq());
        // if(findById.isEmpty()) {
            //     resultMap.put("status", false);
            //     resultMap.put("message", "만료된 세션입니다.");
            //     resultMap.put("code", HttpStatus.BAD_REQUEST);
            //     return resultMap;
            // }
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity userEntity = uRepo.findById(seq).orElseThrow();
        userEntity.updateUser(data);
        uRepo.save(userEntity);
        resultMap.put("status", true);
        resultMap.put("message", "수정이 완료 되었습니다.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        return resultMap;
    }

    @Transactional
    public Map<String, Object> deleteUser(UserEntity loginUser){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        if(loginUser == null){
            resultMap.put("status", false);
            resultMap.put("message", "만료된 세션입니다.");
            resultMap.put("code", HttpStatus.UNAUTHORIZED);
            return resultMap;
        }
        loginUser.changeStatus(3);
        resultMap.put("status", true);
        resultMap.put("message", "탈퇴가 완료 되었습니다.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        resultMap.put("loginUser", null);
        return resultMap;
    }

}
