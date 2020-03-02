package life.maijiang.community.service;

import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.exception.CustomizeException;
import life.maijiang.community.mapper.CommentMapper;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComent=commentMapper.selectById(comment.getParentId());
            if(dbComent == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            Question question=questionMapper.selectById(comment.getParentId());
            //回复问题
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question);
        }
    }
}
