package life.maijiang.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录,请登录后重试"),
    SYS_ERROR(2004,"系统服务器炸了,等会再来看看呗"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不匹配"),
    COMMENT_NOT_FOUND(2006,"你找的评论不存在，要不换个试试?"),
    CONTENT_IS_EMPTY(2007,"评论内容不能为空");


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
