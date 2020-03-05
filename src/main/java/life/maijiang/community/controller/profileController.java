package life.maijiang.community.controller;

import life.maijiang.community.dto.NotificationDTO;
import life.maijiang.community.mapper.NotificationMapper;
import life.maijiang.community.model.Notification;
import life.maijiang.community.model.User;
import life.maijiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class profileController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationMapper notificationMapper;

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
        List<NotificationDTO> notificationDTOS = null;
        List<Notification> notifications=notificationMapper.findAll();
        if(notifications != null){
            notificationDTOS= notificationService.findByContent(notifications);
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("notificationDTOS",notificationDTOS);
        }


        return "profile";
    }
}
