package org.delcom.app.views;

import org.delcom.app.entities.InventoryItem;
import org.delcom.app.entities.User;
import org.delcom.app.services.InventoryService;
import org.delcom.app.utils.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeView {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    public String home(Model model) {
        // 1. Cek apakah user sudah login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/auth/login";
        }

        // 2. Ambil data User yang sedang login
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            return "redirect:/auth/login";
            
        }
        User authUser = (User) principal;
        model.addAttribute("auth", authUser);

        // 3. Ambil Data Inventory milik User tersebut
        // ✅ PERBAIKAN: Tambahkan .toString() agar UUID berubah jadi String
        List<InventoryItem> items = inventoryService.getAllItems(authUser.getId().toString());
        model.addAttribute("items", items);

        // 4. Tampilkan halaman Dashboard
        return ConstUtil.TEMPLATE_PAGES_HOME; 
    }
}