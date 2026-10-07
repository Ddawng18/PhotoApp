package thanhdnh.ueh.edu.article_app;

import android.app.Activity;
import android.content.Context;
import android.widget.GridView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserData {
  public static UserList userData;
  private Context context;
  private GridView gridview;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  public UserData(Context context, GridView gridview) {
    this.context = context;
    this.gridview = gridview;
  }

  public static User getUserFromId(int id) {
    if (userData == null || userData.getUserList() == null) return null;
    for (int i = 0; i < userData.getUserList().size(); i++)
      if (userData.getUserList().get(i).getId() == id)
        return userData.getUserList().get(i);
    return null;
  }

  public void loadData(String url, Activity activity){
      executor.execute(()->{
          File file = Downloader.downloadFile(url, context.getCacheDir());
          UserList list = null;
          if(file != null) {
              try {
                  Gson gson = new Gson();
                  list = gson.fromJson(readText(file), (Type) UserList.class);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }

          if (list == null || list.getUserList() == null || list.getUserList().isEmpty()) {
              ArrayList<User> defaultUsers = new ArrayList<>();
              defaultUsers.add(new User(1, "User One", "password123", "https://picsum.photos/id/1011/300/400", "Nature photographer and traveler."));
              defaultUsers.add(new User(2, "User Two", "password123", "https://picsum.photos/id/1025/300/400", "Coffee enthusiast and software developer."));
              defaultUsers.add(new User(3, "User Three", "password123", "https://picsum.photos/id/1062/300/400", "Art director and minimalist designer."));
              defaultUsers.add(new User(4, "User Four", "password123", "https://picsum.photos/id/1074/300/400", "Mountain hiker and outdoor explorer."));
              list = new UserList(defaultUsers);
          }

          final UserList finalList = list;
          activity.runOnUiThread(()->{
              userData = finalList;
              UserAdapter adapter = new UserAdapter(userData.getUserList(), context);
              gridview.setAdapter(adapter);
          });
        });
  }

  public String readText(File file){
    BufferedReader reader = null;
    try {
      InputStream stream = new FileInputStream(file);
      reader = new BufferedReader(new InputStreamReader(stream));
      StringBuffer buffer = new StringBuffer();
      String line = "";
      while ((line = reader.readLine()) != null) {
        buffer.append(line + "\n");
      }
      return buffer.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }
    return reader.toString();
  }
}
