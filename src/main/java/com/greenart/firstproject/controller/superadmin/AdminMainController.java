package com.greenart.firstproject.controller.superadmin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;

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

    @GetMapping("/product/add")
    public String getProductAdd(Model model, HttpSession session) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("product", new AdminAddProductVO());
        return "superadmin/productadd";
    }

    @PostMapping("/product/add")
    public String postProductAdd(AdminAddProductVO prod, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        if(adminService.isInvalidProductAdd(prod)) {
            reat.addFlashAttribute("isProductSaved", "fail");
            return "redirect:/admin/super/product/add";
        }
        reat.addFlashAttribute("isProductSaved", "success");
        adminService.productSave(prod);
        return "redirect:/admin/super/main";
    }
}
