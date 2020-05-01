package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SettingsAboutActivity
 */
public class SettingsAboutActivity extends AppCompatActivity
{
    // Variables used for layout access
    Button btnTerms, btnPrivacy;

    // Variable used to store the previous activity
    String previous;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);

        // Initialises the widgets
        btnTerms = (Button)findViewById(R.id.btn_terms);
        btnPrivacy = (Button)findViewById(R.id.btn_privacy);

        // onClickListener() for btnTerms
        btnTerms.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnTerms
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Loads the SettingsTermsActivity
                Intent i = new Intent(SettingsAboutActivity.this, SettingsTermsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        // onClickListener() for btnPrivacy
        btnPrivacy.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnPrivacy
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Loads the SettingsTermsActivity
//                Intent i = new Intent(SettingsAboutActivity.this, SettingsPrivacyActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                finish();
            }
        });

        // Calls getIncomingIntent()
        getIncomingIntent();
    }

    /**
     * Retrieves the data sent with in intent in the extra field
     */
    private void getIncomingIntent()
    {
        // Checks if the intent has an extra with the reference previous
        if(this.getIntent().hasExtra("previous"))
        {
            // Grabs the data in the extra
            previous = this.getIntent().getStringExtra("previous");
        }
    }
}
