package life.maijiang.community.service;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.enums.NotificationStatusEnum;
import life.maijiang.community.enums.NotificationTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.exception.CustomizeException;
import life.maijiang.community.mapper.CommentMapper;
import life.maijiang.community.mapper.NotificationMapper;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.Notification;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment=commentMapper.selectById(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //创建通知
            createNotify(comment,dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT);
        }else{
            Question question=questionMapper.selectById(comment.getParentId());
            //回复问题
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question);

            //创建通知
            createNotify(comment,question.getCommentCount(), NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    private void createNotify(Comment comment, int receiver, NotificationTypeEnum replyComment) {
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(replyComment.getType());
        notification.setOuterId((comment.getParentId()).intValue());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver((long) receiver);
        notificationMapper.insert(notification);
    }

    //获取评论
    public List<CommentDTO> listByQuestionId(Integer id,Integer type) {
        List<Comment> comments = commentMapper.selectIdTypeByDesc(id,type);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //查到评论的父类
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        //根据父类 查找所有的用户 user
        List<User> users=new ArrayList<>();
        for (Integer commentator : commentators) {
            User user = userMapper.findById(commentator);
            users.add(user);
        }

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换了comment为commentDTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));

            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOs;
    }


}
