package com.greenart.firstproject.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.entity.CouponInfoEntity;
import com.greenart.firstproject.repository.CouponInfoRefository;
import com.greenart.firstproject.service.JwtService;
import com.greenart.firstproject.service.JwtServiceImpl;
import com.greenart.firstproject.service.UserService;
import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserJoinWelcomeVO;
import com.greenart.firstproject.vo.user.UserLoginVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPIController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/checkEmail")
    public ResponseEntity<Object> check(String email){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("duplicated", userService.isDuplicatedEmail(email));
        return ResponseEntity.ok().body(resultMap);
    }

    @PostMapping("/join")
    public ResponseEntity<Object> userJoin(@Validated @RequestBody UserJoinVO data){
        Map<String, Object> resultMap = new LinkedHashMap<>();
        UserJoinWelcomeVO addUser = userService.addUser(data);
        if(addUser == null){
            resultMap.put("message", "Failed");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        resultMap.put("message", "회원이 등록되었습니다.");
        resultMap.put("data", addUser);
        return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserLoginVO data, HttpServletResponse res){
        Map<String, Object> resultMap = userService.loginUser(data);
        
        // session.setAttribute(MySessionkeys.USER_LOGIN_KEY, resultMap.get(MySessionkeys.USER_LOGIN_KEY));
        // return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
        UserEntity loginUser = (UserEntity)resultMap.get("loginUser");
        Long id = loginUser.getSeq();
        String token = jwtService.getToken("id", id);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PutMapping("/login/update")
    public ResponseEntity<Object> userUpdate(@RequestBody UserUpdateVO data, HttpSession session){
        Map<String, Object> resultMap = null;
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        if(loginUser == null) {
            resultMap = new LinkedHashMap<String, Object>();
            resultMap.put("status", false);
            resultMap.put("message", "로그인 사용자 정보가 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        resultMap = userService.modifyUser(data, (UserEntity)loginUser);
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @PutMapping("/login/delete")
    public ResponseEntity<Object> userDelete(HttpSession session) {
        Map<String, Object> resultMap = null;
        Object loginUser = session.getAttribute(MySessionkeys.USER_LOGIN_KEY);
        // Map<String, Object> resultMap = userService.deleteUser((UserEntity) loginUser);
        if(loginUser == null){
            resultMap = new LinkedHashMap<String, Object>();
            resultMap.put("status", false);
            resultMap.put("message", "로그인 사용자 정보가 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        resultMap = userService.deleteUser((UserEntity) loginUser);
        session.invalidate();
        return new ResponseEntity<>(resultMap, (HttpStatus)resultMap.get("code"));
    }
}
