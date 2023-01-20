package com.greenart.firstproject.controller.adminsuper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.AdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/super")
@RequiredArgsConstructor
public class AdminMainController {
    private final AdminService adminService;

    @GetMapping("/main")
    public String getSuperAdminMain(HttpSession session, Model model, @PageableDefault(sort = "seq", direction = Direction.DESC, size = 15) Pageable pageable) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        
        model.addAttribute("products", adminService.getMainProductsPage(pageable));
        return "superadmin/main";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
