package com.ra.controller;

import com.ra.model.entity.Account;
import com.ra.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "home";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("account", new Account());
        return "add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("account") Account account, Model model) {
        if (account.getId() != null) {
            // Trường hợp chỉnh sửa: kiểm tra tài khoản tồn tại
            Account existing = accountService.findById(account.getId());
            if (existing == null) {
                model.addAttribute("error", "Không tìm thấy tài khoản để cập nhật.");
                model.addAttribute("account", account);
                return "edit";
            }
        }

        try {
            accountService.save(account);
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lưu tài khoản: " + e.getMessage());
            model.addAttribute("account", account);
            return (account.getId() != null) ? "edit" : "add";
        }
    }


    @GetMapping("/edit/{id}")
    public String editAccount(@PathVariable Long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "edit";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            Account account = accountService.findById(id);
            if (account == null) {
                model.addAttribute("error", "Tài khoản không tồn tại!");
                return "redirect:/accounts";
            }
            accountService.delete(id);
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi xóa tài khoản: " + e.getMessage());
            return "redirect:/accounts";
        }
    }
}