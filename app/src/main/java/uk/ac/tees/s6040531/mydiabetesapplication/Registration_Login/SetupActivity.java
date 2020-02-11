package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SetupActivity extends AppCompatActivity
{
    Spinner spnBs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        spnBs = (Spinner)findViewById(R.id.spn_bs);

        ArrayAdapter<CharSequence> bsAdapter = ArrayAdapter.createFromResource(this, R.array.bs_types, android.R.layout.simple_spinner_item);
        bsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBs.setAdapter(bsAdapter);


    }
}
