package com.greenart.firstproject.controller.adminsuper;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenart.firstproject.config.MySessionkeys;
import com.greenart.firstproject.entity.enums.AlcoholType;
import com.greenart.firstproject.entity.enums.RawMaterial;
import com.greenart.firstproject.service.AdminService;
import com.greenart.firstproject.vo.superadmin.AdminAddProductVO;
import com.greenart.firstproject.vo.superadmin.AdminOptionVO;
import com.greenart.firstproject.vo.superadmin.AdminUpdateProductVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/super/products")
@RequiredArgsConstructor
public class AdminProductsController {
    
    private final AdminService adminService;

    @ModelAttribute("alcoholTypes")
    public AlcoholType[] alcoholType() {
        return AlcoholType.values();
    }

    @ModelAttribute("raws")
    public RawMaterial[] rawMaterial() {
        return RawMaterial.values();
    }

    @ModelAttribute("degrees")
    public int[] degrees() {
        return new int[]{0, 1, 2, 3};
    }


    @GetMapping("/add")
    public String getProductAdd(Model model, HttpSession session) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("product", new AdminAddProductVO());
        return "superadmin/productadd";
    }

    @PostMapping("/add")
    public String postProductAdd(@Validated @ModelAttribute("product") AdminAddProductVO prod, BindingResult bindingResult, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        if(bindingResult.hasErrors() || adminService.invalidFileFormat(prod.getBasicImg(), prod.getDetailImg())) {
            return "superadmin/productadd";
        }
        reat.addFlashAttribute("isProductSaved", "success");
        adminService.productAdd(prod);
        return "redirect:/admin/super/main";
    }


    @GetMapping("/{seq}")
    public String getProduct(@PathVariable Long seq, HttpSession session, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("product", adminService.getProductBySeq(seq));
        return "superadmin/productDetailInfo";
    }

    @GetMapping("/{seq}/edit")
    public String getProductUpdate(@PathVariable Long seq, HttpSession session, RedirectAttributes reat, Model model) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("product", adminService.getProductBySeq(seq));
        return "superadmin/productEdit";
    }

    @PostMapping("/{seq}/edit")
    public String postProductUpdate(@PathVariable Long seq, @ModelAttribute AdminUpdateProductVO data, BindingResult bindingResult, HttpSession session, RedirectAttributes reat) {
        if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
            return "redirect:/admin/login";
        }
        data.setSeq(seq);
        reat.addAttribute("seq", seq);
        if(bindingResult.hasErrors() || adminService.invalidFileFormat(data.getBasicImg(), data.getDetailImg())) {
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
        model.addAttribute("addOption", new AdminOptionVO());
        model.addAttribute("productName", adminService.getProductName(seq));
        return "superadmin/productOptions";
    }

    @PostMapping("/{seq}/options")
    public String postProductOptionAdd(@PathVariable Long seq, HttpSession session, @ModelAttribute AdminOptionVO optionVO, RedirectAttributes reat) {
        // if(session.getAttribute(MySessionkeys.SUPER_ADMIN_KEY) == null) {
        //     return "redirect:/admin/login";
        // }
        String msg = adminService.addProductOption(seq, optionVO);
        reat.addAttribute("seq", seq);
        reat.addFlashAttribute("msg", msg);
        return "redirect:/admin/super/products/{seq}/options";
    }
}
