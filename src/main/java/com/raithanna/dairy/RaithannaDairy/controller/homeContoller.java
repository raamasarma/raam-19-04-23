package com.raithanna.dairy.RaithannaDairy.controller;

import com.raithanna.dairy.RaithannaDairy.models.userLog;
import com.raithanna.dairy.RaithannaDairy.models.userLog;
import com.raithanna.dairy.RaithannaDairy.models.userModel;
import com.raithanna.dairy.RaithannaDairy.models.userLog;
import com.raithanna.dairy.RaithannaDairy.repositories.UserLogRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.UserModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class homeContoller {
    Logger logger = LoggerFactory.getLogger(homeContoller.class);
    @Autowired
    private UserModelRepository userModelRepository;
    @Autowired
    private UserLogRepository userLogRepository;

    @GetMapping("/")
    public String loginHtml() {
        return "welcomeScreen";
    }

    @PostMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }
   @PostMapping("/login")
    public String login(@RequestParam String mobile, Model model, @RequestParam String password, HttpServletRequest request, HttpSession session)throws UnknownHostException {
       logger.info("home method info");
       logger.warn("home method warning");
       logger.error("home method error");
       System.out.println(mobile);
       System.out.println(password);
       List<String> messages = new ArrayList<>();
       InetAddress localHost = InetAddress.getLocalHost();
       System.out.println(localHost.getHostAddress());
       userModel user = userModelRepository.findByMobileAndPassword(mobile, password);
       userLog userLog = new userLog();
       if (user == null) {
           userLog.setMobile(mobile);
           userLog.setIpAddress(localHost.getHostAddress());
           //userLog.setIpAddress("0.0.0.0");
           userLog.setStatus("fail");
           userLogRepository.save(userLog);
           messages.add("Account not found! retry ");
           model.addAttribute("messages", messages);
           return "/loginPage";
       } else {
           System.out.println(user);
           userLog.setMobile(mobile);
          // userLog.setIpAddress("0.0.0.0");
           userLog.setIpAddress(localHost.getHostAddress());
           userLog.setStatus("success");
           userLogRepository.save(userLog);
           System.out.println("user mobile:" + user.getMobile());
           messages.add("login Successfully ");
           model.addAttribute("messages", messages);
           //System.out.println(messages);
           session.setAttribute("loggedIn", "yes");
           return "/home";
       }
   }
    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Logged out successfully");
        model.addAttribute("messages", messages);
        session.setAttribute("loggedIn", "no");
        return "login";
    }

    @RequestMapping("/homePage")
    public String homePage(Model model, HttpServletRequest request, HttpSession session) {
        List<String> messages = new ArrayList<>();
        messages.add("Home Page");
        model.addAttribute("messages", messages);
        session.setAttribute("loggedIn", "yes");
        return "home";
    }
}
