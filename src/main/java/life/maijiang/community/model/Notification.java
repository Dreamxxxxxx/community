package life.maijiang.community.model;

public class Notification {
    private Long id;
    private int notifier;
    private Long receiver;
    private int outerId;
    private int type;
    private Long gmtCreate;
    private int status;

    public Notification() {
    }

    public Notification(Long id, int notifier, Long receiver, int outerId, int type, Long gmtCreate, int status) {
        this.id = id;
        this.notifier = notifier;
        this.receiver = receiver;
        this.outerId = outerId;
        this.type = type;
        this.gmtCreate = gmtCreate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNotifier() {
        return notifier;
    }

    public void setNotifier(int notifier) {
        this.notifier = notifier;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public int getOuterId() {
        return outerId;
    }

    public void setOuterId(int outerId) {
        this.outerId = outerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
