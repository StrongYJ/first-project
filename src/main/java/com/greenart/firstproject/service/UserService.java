package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserJoinWelcomeVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository uRepo;

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
        UserEntity newUser = new UserEntity(data);
        uRepo.save(newUser);
        return new UserJoinWelcomeVO(newUser.getSeq(), newUser.getName(), newUser.getNickname());
    }

    public Map<String, Object> loginUser(UserLoginVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = null;
        loginUser = uRepo.findByEmailAndPwd(data.getEmail(), data.getPwd());
        
        if(loginUser == null){
            resultMap.put("stats", false);
            resultMap.put("message", "이메일 또는 비밀번호 오류입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
        }
        else if(loginUser.getStatus() == 2){
            resultMap.put("status", false);
            resultMap.put("message", "정지 회원 입니다.");
            resultMap.put("code", HttpStatus.FORBIDDEN);
        }
        else if(loginUser.getStatus() == 3){
            resultMap.put("status", false);
            resultMap.put("message", "탈퇴한 회원 입니다.");
            resultMap.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            Long id = loginUser.getSeq();
                // JwtService jwtService = new JwtServiceImpl();
            // String token = jwtService.getToken("id", id);
            
            // JwtService jwtService = new JwtServiceImpl();
            // Long id = loginUser.getSeq();
            // String token = jwtService.getToken("id", id);
            // Cookie cookie = new Cookie("token", (String) resultMap.get("token"));
            // cookie.setHttpOnly(true);
            // cookie.setPath("/");
            // res.addCookie(cookie);
            // return new ResponseEntity<>(id, HttpStatus.OK);
            
            resultMap.put("status", true);
            resultMap.put("message", "로그인 되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            resultMap.put("loginUser", loginUser);
        }
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
