package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SettingsHelpActivity extends AppCompatActivity
{
    Button btnFAQ, btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_help);

        btnFAQ = (Button)findViewById(R.id.btn_faq);
        btnContact = (Button)findViewById(R.id.btn_contact);

        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsHelpActivity.this, SettingsFAQActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsHelpActivity.this, SettingsContactActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
