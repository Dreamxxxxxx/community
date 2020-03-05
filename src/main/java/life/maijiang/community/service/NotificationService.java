package life.maijiang.community.service;

import life.maijiang.community.dto.NotificationDTO;
import life.maijiang.community.enums.NotificationTypeEnum;
import life.maijiang.community.mapper.CommentMapper;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.Notification;
import life.maijiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    public List<NotificationDTO> findByContent(List<Notification> notifications) {
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for (Notification notification : notifications) {
            String name=userMapper.findById(notification.getNotifier()).getName();
            if(notification.getType()== NotificationTypeEnum.REPLY_QUESTION.getType()){
                //回复问题
                String action=NotificationTypeEnum.REPLY_QUESTION.getName();
                Question question = questionMapper.getById(notification.getOuterId());
                String content=question.getTitle();
                notificationDTOS.add(new NotificationDTO(name,action,content, (long) notification.getOuterId()));
            }else{
                //回复评论
                String action=NotificationTypeEnum.REPLY_COMMENT.getName();
                Comment comment = commentMapper.selectById((long) notification.getOuterId());
                String content=comment.getContent();
                notificationDTOS.add(new NotificationDTO(name,action,content,comment.getParentId()));
            }
        }
        return notificationDTOS;
    }
}
