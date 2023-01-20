package com.greenart.firstproject.controller.adminsuper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.AdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/super/users")
public class AdminUsersController {
    private final AdminService adminService;

    @GetMapping("")
    public String getUsers(HttpSession session, @PageableDefault(sort = "seq", direction = Direction.DESC, size = 15) Pageable pageable, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("changeStatus", "status");
        model.addAttribute("users", adminService.getAllUsers(pageable));
        return "superadmin/users";
    }

    @GetMapping("/{seq}")
    public String getUserDetailInfo(@PathVariable Long seq, HttpSession session, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("user", adminService.getUserInfoBySeq(seq));
        return "superadmin/userinfo";
    }

    @GetMapping("/{seq}/{status}")
    public String getUserStatus(@PathVariable Long seq, @PathVariable Integer status, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        adminService.changeUserStatus(seq, status);
        reat.addAttribute("seq", seq);
        return "redirect:/admin/super/users/{seq}";
    }
}
