package com.greenart.firstproject.controller.superadmin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/super/products")
@RequiredArgsConstructor
public class AdminProductsController {
    
    private final AdminService adminService;

    @GetMapping("/{seq}")
    public String getProduct(@PathVariable Long seq, HttpSession session, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        AdminUpdateProductVO product = adminService.getProductBySeq(seq);
        if(product == null) {
            return "redirect:/admin/super/main";
        }
        model.addAttribute("product", product);
        return "superadmin/productDetailInfo";
    }

    @GetMapping("/{seq}/edit")
    public String getProductUpdate(@PathVariable Long seq, HttpSession session, RedirectAttributes reat, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        AdminUpdateProductVO product = adminService.getProductBySeq(seq);
        if(product == null) {
            return "redirect:/admin/super/main";
        }
        model.addAttribute("product", product);
        return "superadmin/productEdit";
    }

    @PostMapping("/{seq}/edit")
    public String postProductUpdate(@PathVariable Long seq, AdminUpdateProductVO data, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        data.setSeq(seq);
        reat.addAttribute("seq", seq);
        if(adminService.isInvalidProductUpdate(data)) {
            reat.addFlashAttribute("isProductSaved", "fail");
            return "redirect:/admin/super/products/{seq}";
        }
        adminService.productUpdate(data);
        return "redirect:/admin/super/products/{seq}";
    }

    @GetMapping("/{seq}/delete")
    public String getProductDelete(@PathVariable Long seq, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        reat.addFlashAttribute("deleteProductMsg", adminService.productDelete(seq));
        return "redirect:/admin/super/main";
    }

    @GetMapping("/{seq}/options")
    public String getProductOptions(@PathVariable Long seq, HttpSession session, Model model) {
        // if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
        //     return "redirect:/admin/login";
        // }
        List<AdminOptionVO> options = adminService.getOptionsByProductSeq(seq);
        model.addAttribute("options", options);
        return "superadmin/productOptions";
    }
}
