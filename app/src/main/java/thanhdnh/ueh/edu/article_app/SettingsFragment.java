package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Displays the Profile settings defined in res/xml/preferences.xml.
 * Values are saved automatically in the default SharedPreferences.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

  public static final String KEY_GENDER = "gender";
  public static final String KEY_AGE = "age";
  public static final String KEY_DISPLAY_NAME = "display_name";
  public static final String KEY_EMAIL = "email";
  public static final String KEY_PHONE = "phone";
  public static final String KEY_COUNTRY = "country";
  public static final String KEY_NOTIFICATIONS = "notifications";
  public static final String KEY_EMAIL_UPDATES = "email_updates";

  private static final int MIN_AGE = 1;
  private static final int MAX_AGE = 120;

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);

    // Age: numeric keyboard, validated, summary reflects the current value
    EditTextPreference agePref = findPreference(KEY_AGE);
    if (agePref != null) {
      agePref.setOnBindEditTextListener(editText -> {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setSingleLine(true);
        editText.selectAll();
      });
      updateAgeSummary(agePref, agePref.getText());
      agePref.setOnPreferenceChangeListener(this::onAgeChanged);
    }

    // Keyboards that fit the content of the other text settings
    setInputType(KEY_EMAIL, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    setInputType(KEY_PHONE, InputType.TYPE_CLASS_PHONE);
    setInputType(KEY_DISPLAY_NAME, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
  }

  private void setInputType(String key, int inputType) {
    EditTextPreference pref = findPreference(key);
    if (pref != null) {
      pref.setOnBindEditTextListener(editText -> {
        editText.setInputType(inputType);
        editText.setSingleLine(true);
        editText.selectAll();
      });
    }
  }

  private boolean onAgeChanged(Preference preference, Object newValue) {
    String value = newValue == null ? "" : newValue.toString().trim();
    try {
      int age = Integer.parseInt(value);
      if (age < MIN_AGE || age > MAX_AGE) {
        throw new NumberFormatException();
      }
      updateAgeSummary(preference, String.valueOf(age));
      return true;
    } catch (NumberFormatException e) {
      Toast.makeText(requireContext(), R.string.pref_error_age, Toast.LENGTH_SHORT).show();
      return false; // reject the change; the old value stays saved
    }
  }

  private void updateAgeSummary(Preference preference, String age) {
    if (age == null || age.isEmpty()) {
      preference.setSummary(R.string.pref_summary_not_set);
    } else {
      preference.setSummary(getString(R.string.pref_summary_age, age));
    }
  }
}
