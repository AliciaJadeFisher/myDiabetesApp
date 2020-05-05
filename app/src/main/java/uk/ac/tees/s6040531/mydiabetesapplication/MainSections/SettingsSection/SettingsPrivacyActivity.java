package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SettingsPrivacyActivity
 */
public class SettingsPrivacyActivity extends AppCompatActivity {

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_privacy);
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed() {
        // Returns to the previous activity
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
