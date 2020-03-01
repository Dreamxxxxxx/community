package life.maijiang.community.controller;

import life.maijiang.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1")String page,
                          @RequestParam(name = "size",defaultValue = "5")String size,
                          Model model,
                          HttpServletRequest  request){
        User user= (User) request.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }
}
