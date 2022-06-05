package thirdproject.groupchat.Model;

public class MyMessage {

    private String sender;
    private String date;
    private String time;
    private String content;

    public String getSender() {
        return sender;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyMessage(String sender , String content , String date ,String time) {
        setSender(sender);
        setDate(date);
        setContent(content);
        setTime(time);
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
