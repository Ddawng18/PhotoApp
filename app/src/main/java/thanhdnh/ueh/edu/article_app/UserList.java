package thanhdnh.ueh.edu.article_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {

  @SerializedName("users")
  @Expose
  private ArrayList<User> userList;

  public UserList(ArrayList<User> userList) {
    this.setUserList(userList);
  }

  public ArrayList<User> getUserList() {
    return userList;
  }

  public void setUserList(ArrayList<User> userList) {
    this.userList = userList;
  }
}
