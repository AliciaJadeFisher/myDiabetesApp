package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SettingsTermsActivity
 */
public class SettingsTermsActivity extends AppCompatActivity
{
    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_terms);
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
