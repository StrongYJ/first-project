package com.greenart.firstproject.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.UserRepository;
import com.greenart.firstproject.vo.UserJoinVO;
import com.greenart.firstproject.vo.UserLoginVO;
import com.greenart.firstproject.vo.UserUpdateVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository uRepo;

    public Map<String, Object> addUser(UserJoinVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data.getEmail());
        if(m.matches()){
            if(uRepo.countByEmail(data.getEmail()) == 1){
                resultMap.put("status", false);
                resultMap.put("message", data.getEmail()+"은/는 이미 등록된 사용자입니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }else{
                Pattern pwdPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
                Matcher pwdMatcher = pwdPattern.matcher(data.getPwd());
                if(pwdMatcher.find()){
                    Pattern phonePattern = Pattern.compile("^010(\\d{3}\\d{4})(\\d{4})$");
                    Matcher matcher = phonePattern.matcher(data.getPhone());
                    if(matcher.matches()) {
                        UserEntity newUser = new UserEntity(data);
                        uRepo.save(newUser);
                        resultMap.put("status", true);
                        resultMap.put("message", "회원이 등록되었습니다.");
                        resultMap.put("code", HttpStatus.CREATED);
                    }
                    else{
                        resultMap.put("status", false);
                        resultMap.put("message", "잘못된 핸드폰 번호입니다.");
                        resultMap.put("code", HttpStatus.NOT_ACCEPTABLE);
                        return resultMap;
                    }
                }
                else{
                    resultMap.put("status", false);
                    resultMap.put("message", "영문,숫자,특수문자 포함하여 8자리 이상 입력하여야 합니다.");
                    resultMap.put("code", HttpStatus.NOT_ACCEPTABLE);
                    return resultMap;
                }
            }
            return resultMap;
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", "이메일 형식이 잘못 되었습니다.");
            resultMap.put("code", HttpStatus.NOT_ACCEPTABLE);
            return resultMap;
        }
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
            resultMap.put("status", true);
            resultMap.put("message", "로그인 되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            resultMap.put("loginUser", loginUser);
        }
        return resultMap;
    }

    public Map<String, Object> modifyUser(UserUpdateVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = null;
        loginUser = uRepo.findByEmailAndPwd(data.getEmail(), data.getPwd());
        String pre_pwd = loginUser.getPwd();
        if(loginUser.getPwd().equals(data.getPwd())){
            loginUser.updateUser(data);
            uRepo.save(loginUser);
            resultMap.put("status", true);
            resultMap.put("message", "수정이 완료 되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            if(pre_pwd.equals(loginUser.getPwd())){
                resultMap.put("loginUser", null);
            }
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호가 일치하지 않습니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
        }
        return resultMap;
    }

    public Map<String, Object> deleteUser(UserUpdateVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        UserEntity loginUser = null;
        loginUser = uRepo.findByEmailAndPwd(data.getEmail(), data.getPwd());
        if(loginUser.getPwd().equals(data.getPwd())){
            uRepo.delete(loginUser);
            resultMap.put("status", true);
            resultMap.put("message", "탈퇴가 완료 되었습니다.");
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
