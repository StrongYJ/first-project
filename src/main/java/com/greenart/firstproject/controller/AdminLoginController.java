package com.greenart.firstproject.controller;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.AdminEntity;
import com.greenart.firstproject.entity.MarketInfoEntity;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminLoginVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminLoginController {
    private final AdminService adminService;

    @GetMapping("/login")
    public String adminLogin(Model model) {
        model.addAttribute("data", new AdminLoginVO());
        return "adminLogin";
    }

    @PostMapping("/login")
    public String postAdminLogin(AdminLoginVO data, RedirectAttributes reat, HttpSession session) {
        AdminEntity loginAdmin = adminService.login(data);
        if(loginAdmin == null) {
            reat.addFlashAttribute("loginFailed", true);
            return "redirect:/admin/login";
        }
        if(loginAdmin.getGrade() == 1) {
            session.setAttribute(MySessionkeys.SUPER_ADMIN_KEY, data.getId());
            return "redirect:/admin/super/main";
        }
        MarketInfoEntity marketInfo = loginAdmin.getMarketInfo();
        if(marketInfo == null) throw new NoSuchElementException();
        session.setAttribute(MySessionkeys.LOCAL_ADMIN_KEY, marketInfo.getSeq());
        reat.addAttribute("seq", marketInfo.getSeq());
        return "redirect:/admin/local/{seq}";
    }
}
