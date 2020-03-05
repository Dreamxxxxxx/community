package life.maijiang.community.dto;

public class NotificationDTO {
    private String name;    //回复人的名字
    private String action;  //回复的是评论还是名字
    private String content; //回复的内容
    private Long id; //跳转到问题


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationDTO(String name, String action, String content, Long id) {
        this.name = name;
        this.action = action;
        this.content = content;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
