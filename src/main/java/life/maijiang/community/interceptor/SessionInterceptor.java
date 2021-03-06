package life.maijiang.community.interceptor;

import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(null !=cookies && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    request.getSession().getAttribute("user");
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }

//                    User user=null;
//                    user = (User) request.getSession().getAttribute("user");
//                    if(null == user) {
//                        user = userMapper.findByToken(token);
//                        request.getSession().setAttribute("user", user);
//                    }
                    break;
                }
            }
        }
        return true;
    }
}
