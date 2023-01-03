package com.greenart.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.AdminLoginVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/login")
    public String adminLogin(Model model) {
        model.addAttribute("data", new AdminLoginVO());
        return "login";
    }

    @PostMapping("/login")
    public String postAdminLogin(AdminLoginVO data, RedirectAttributes reat) {
        if(adminService.loginCheckIdAndPwd(data) == false) {
            reat.addAttribute("loginFailed", true);
            return "redirect:/admin/login";
        }
        if(adminService.isSuper(data)) {
            return "redirect:/admin/main";
        }
        reat.addAttribute("id", adminService.getMarketId(data));
        return "redirect:/admin/local/{id}";
    }

    @GetMapping("/main")
    public String getSuperAdminMain() {
        return "superadmin/main";
    }
}
