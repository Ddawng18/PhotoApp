package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Hosts the whole Settings screen (the "Profile" category, 8 settings)
 * inside a single activity by displaying {@link SettingsFragment}.
 */
public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(android.R.id.content, new SettingsFragment())
          .commit();
    }

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(R.string.title_settings);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      getOnBackPressedDispatcher().onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
