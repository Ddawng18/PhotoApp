package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
  private ArrayList<User> userList;
  private Context context;

  public UserAdapter(ArrayList<User> userList, Context context) {
    this.userList = userList;
    this.context = context;
  }

  @Override
  public int getCount() {
    return userList.size();
  }

  @Override
  public Object getItem(int position) {
    return userList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return userList.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final MyView dataitem;
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {
      dataitem = new MyView();
      convertView = inflater.inflate(R.layout.user_disp_tpl, null);
      dataitem.iv_profile = convertView.findViewById(R.id.imv_profile);
      dataitem.tv_uname = convertView.findViewById(R.id.tv_uname);
      convertView.setTag(dataitem);
    } else {
      dataitem = (MyView) convertView.getTag();
    }

    Picasso.get()
        .load(userList.get(position).getUrl_profile())
        .placeholder(android.R.drawable.ic_menu_gallery)
        .error(android.R.drawable.ic_menu_report_image)
        .resize(300, 400)
        .centerCrop()
        .into(dataitem.iv_profile);
    dataitem.tv_uname.setText(userList.get(position).getUname());
    return convertView;
  }

  private static class MyView {
    ImageView iv_profile;
    TextView tv_uname;
  }
}
