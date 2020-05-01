package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SettingsPrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_privacy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
