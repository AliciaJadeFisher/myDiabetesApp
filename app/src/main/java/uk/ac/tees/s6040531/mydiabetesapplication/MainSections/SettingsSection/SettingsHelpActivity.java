package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}
