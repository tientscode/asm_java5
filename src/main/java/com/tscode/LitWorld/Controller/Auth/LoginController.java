package com.tscode.LitWorld.Controller.Auth;

import com.tscode.LitWorld.Database.UserClass.QuerryUser;
import com.tscode.LitWorld.Database.UserClass.UserClass;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {

    @Autowired
    private QuerryUser querryUser;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login"; // Trả về trang đăng nhập (login-layout.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        // Kiểm tra username và password trong cơ sở dữ liệu
        UserClass user = querryUser.findByAccount(username);
        System.out.println("là gì đây" + user);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getActive()) {
                int userId = user.getId();
//                Cookie cookie = new Cookie("username", Integer.toString(userId));
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(24 * 60 * 60); // Thời gian tồn tại của cookie (ví dụ: 1 ngày)
                response.addCookie(cookie);
                return "redirect:/home"; // Đăng nhập thành công, chuyển hướng đến trang home
            }
        } else {
            model.addAttribute("error", "Invalid username or password!");
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/"); // Đặt phạm vi của cookie là toàn bộ ứng dụng
                response.addCookie(cookie);
            }
        }
        return "redirect:/login"; // Đăng xuất thành công, chuyển hướng đến trang đăng nhập
    }


}