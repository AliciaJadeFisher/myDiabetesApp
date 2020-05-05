package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SettingsHelpActivity
 */
public class SettingsHelpActivity extends AppCompatActivity
{
    // Variables for layout access
    Button btnFAQ, btnContact;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_help);

        // Initialises the widgets
        btnFAQ = findViewById(R.id.btn_faq);
        btnContact = findViewById(R.id.btn_contact);

        // onClickListener for btnFAQ
        btnFAQ.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnFAQ
             * @param v - activity view
             */
            @Override
            public void onClick(View v) {
                // Loads the SettingsFAQActivity
                Intent i = new Intent(SettingsHelpActivity.this, SettingsFAQActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        // onClickListener for btnContact
        btnContact.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnContact
             * @param v - activity view
             */
            @Override
            public void onClick(View v) {
                // Loads the SettingsContactActivity
                Intent i = new Intent(SettingsHelpActivity.this, SettingsContactActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
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
