package com.greenart.firstproject.controller.adminsuper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminUpdateOptionVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/super/options")
@RequiredArgsConstructor
public class AdminOptionController {
    private final AdminService adminService;

    @GetMapping("/{seq}/delete")
    public String getDeleteOption(@PathVariable Long seq, RedirectAttributes reat, HttpSession session) {
        // if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
        //     return "redirect:/admin/login";
        // }
        Long productSeq = adminService.deleteProductOptionByOptionSeq(seq);
        if(productSeq == null) {
            return "redirect:/admin/super/main";
        }
        reat.addAttribute("productSeq", productSeq);
        return "redirect:/admin/super/products/{productSeq}/options";
    }

    @PostMapping("/{seq}/modify")
    public String getModifyOption(@PathVariable("seq") Long optionSeq, @ModelAttribute AdminUpdateOptionVO data, HttpSession session, RedirectAttributes reat) {
        // if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
        //     return "redirect:/admin/login";
        // }
        Long productSeq = adminService.updateOption(data, optionSeq);
        if(productSeq == null) {
            return "redirect:/admin/super/main";
        }
        reat.addAttribute("seq", productSeq);
        return "redirect:/admin/super/products/{seq}/options";
    }

    
}
