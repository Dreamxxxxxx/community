package life.maijiang.community.controller;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.service.CommentService;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        List<QuestionDTO> relatedQuestion=questionService.selectRelated(questionDTO);
        List<CommentDTO> comments=commentService.listByQuestionId(id, CommentTypeEnum.QUESTION.getType());

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestion",relatedQuestion);
        return "question";
    }

}
