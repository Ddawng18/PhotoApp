package thanhdnh.ueh.edu.article_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  public GridView gridview;

  private AdapterView.OnItemClickListener onitemclick = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Intent intent = new Intent(getBaseContext(), ViewUserActivity.class);
      intent.putExtra("id", gridview.getAdapter().getItemId(position));
      startActivity(intent);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    gridview = findViewById(R.id.gridview);
    new UserData(getBaseContext(), gridview).loadData("https://raw.githubusercontent.com/thanhdnh/json/main/users.json", this);
    gridview.setOnItemClickListener(onitemclick);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      startActivity(new Intent(this, SettingsActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
