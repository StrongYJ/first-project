package com.greenart.firstproject.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.ProductInfoEntity;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminLoginVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
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
            return "redirect:/admin/main";
        }
        reat.addAttribute("id", adminService.getMarketId(data));
        return "redirect:/admin/local/{id}";
    }

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
            return "redirect:/admin/product/add";
        }
        reat.addFlashAttribute("isProductSaved", "success");
        adminService.productSave(prod);
        return "redirect:/admin/main";
    }
    
    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Long id, HttpSession session, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        AdminUpdateProductVO product = adminService.getProductById(id);
        if(product == null) {
            return "redirect:/admin/main";
        }
        model.addAttribute("product", product);
        return "superadmin/productDetailInfo";
    }

    @GetMapping("/products/{id}/edit")
    public String getProductUpdate(@PathVariable Long id, HttpSession session, RedirectAttributes reat, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        AdminUpdateProductVO product = adminService.getProductById(id);
        if(product == null) {
            return "redirect:/admin/main";
        }
        model.addAttribute("product", product);
        return "superadmin/productEdit";
    }

    @PostMapping("/products/{id}/edit")
    public String postProductUpdate(@PathVariable Long id, AdminUpdateProductVO data, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        data.setSeq(id);
        reat.addAttribute("id", id);
        if(adminService.isInvalidProductUpdate(data)) {
            reat.addFlashAttribute("isProductSaved", "fail");
            return "redirect:/admin/products/{id}";
        }
        adminService.productUpdate(data);
        return "redirect:/admin/products/{id}";
    }
}
