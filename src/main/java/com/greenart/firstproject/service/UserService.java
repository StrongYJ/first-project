package com.greenart.firstproject.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.greenart.firstproject.config.security.JwtProperties;
import com.greenart.firstproject.config.security.JwtUtil;
import com.greenart.firstproject.entity.TokenBlackList;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.TokenBlackListRepository;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserJoinWelcomeVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserResponseVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository uRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlackListRepository tokenBlackListRepo;

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

    @Transactional
    public UserResponseVO addUser(UserJoinVO data) {
        if(isDuplicatedEmail(data.getEmail())) return null;
        if(isDuplicatedNickname(data.getNickname())) return null;

        data.setPwd(passwordEncoder.encode(data.getPwd()));
        UserEntity newUser = new UserEntity(data);
        uRepo.save(newUser);
        return new UserResponseVO(newUser);
    }

    public Map<String, Object> loginUser(UserLoginVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = uRepo.findByEmail(data.getEmail()).orElseThrow(() -> 
                    new NoSuchElementException("??????????????? ??????????????? ?????????????????????."));
        if(passwordEncoder.matches(data.getPwd(), loginUser.getPwd())){
            if(loginUser.getStatus() == 2){
                resultMap.put("status", false);
                resultMap.put("message", "?????? ?????? ?????????.");
                resultMap.put("code", HttpStatus.FORBIDDEN);
            }
            else if(loginUser.getStatus() == 3){
                resultMap.put("status", false);
                resultMap.put("message", "????????? ?????? ?????????.");
                resultMap.put("code", HttpStatus.FORBIDDEN);
            } else {
                resultMap.put("status", true);
                resultMap.put("message", "????????? ???????????????.");
                resultMap.put("code", HttpStatus.ACCEPTED);
                resultMap.put("Authorization", JwtProperties.TOKEN_PREFIX + jwtUtil.create(loginUser.getSeq()));
                resultMap.put("data", new UserResponseVO(loginUser));
            }
        } else {
            resultMap.put("status", false);
            resultMap.put("message", "passwordEncoder Error");
            resultMap.put("code", HttpStatus.BAD_GATEWAY);
        }
        return resultMap;
    }

    @Transactional
    public Map<String, Object> modifyUser(UserUpdateVO data, Long seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity userEntity = uRepo.findById(seq).orElseThrow();
        if(!userEntity.getNickname().equals(data.getNickname()) && isDuplicatedNickname(data.getNickname()))
            throw new IllegalArgumentException("????????? ??????????????????.");
        /*
         * ??????????????? ????????????
         * seq??? ????????? ???????????? ?????????
         * JSON ???????????? VO??? ????????? ??? ?????????
         * ????????? pwd??? ????????? ?????????????????? matches??? ?????? ??????????????? ????????? ?????????
         * ?????? ????????? ?????? ???????????? pwd??? ??????????????? ??????????????? ?????? ????????? ?????? ???
         */
        data.setPwd(passwordEncoder.encode(data.getPwd()));
        userEntity.updateUser(data);
        
        resultMap.put("status", true);
        resultMap.put("message", "????????? ?????? ???????????????.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        resultMap.put("data", new UserResponseVO(userEntity));
        return resultMap;
    }

    @Transactional
    public void deleteUser(Long userSeq){
        UserEntity user = uRepo.findById(userSeq).orElseThrow(() -> new NoSuchElementException("?????????????????? ???????????????."));
        if(user.getStatus() == 2) {
            throw new IllegalArgumentException("????????? ????????? ????????? ???????????????.");
        } else if(user.getStatus() == 3) {
            throw new IllegalArgumentException("?????? ????????? ???????????????.");
        }
        user.changeStatus(3);
    }

    @Transactional
    public void blackListToken(String token) {
        long expireTime = jwtUtil.getExpireTime(token);
        tokenBlackListRepo.save(new TokenBlackList(token, expireTime));
    }

}
