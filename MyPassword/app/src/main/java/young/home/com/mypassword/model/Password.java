package young.home.com.mypassword.model;

import java.util.UUID;

/**
 * Created by YOUNG on 2016/4/4.
 */
public class Password {
    private long id;
    private long publish;
    private String title;
    private String userName;
    private String password;
    private String note;
    private String groupName;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getPublish(){
        return publish;
    }

    public void setPublish(long publish){
        this.publish = publish;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getGroupName(){
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
}
