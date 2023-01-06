package com.greenart.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
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
        return "login";
    }

    @PostMapping("/login")
    public String postAdminLogin(AdminLoginVO data, RedirectAttributes reat, HttpSession session) {
        if(adminService.loginCheckIdAndPwd(data) == false) {
            reat.addFlashAttribute("loginFailed", true);
            return "redirect:/admin/login";
        }
        if(adminService.isSuper(data)) {
            session.setAttribute(MySessionkeys.SUPER_ADMIN_KEY, data.getId());
            return "redirect:/admin/super/main";
        }
        reat.addAttribute("seq", adminService.getMarketSeq(data));
        return "redirect:/admin/local/{seq}";
    }
}
