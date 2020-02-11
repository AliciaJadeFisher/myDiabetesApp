package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spnBs, spnCarb;

    String bs, carb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        spnBs = (Spinner)findViewById(R.id.spn_bs);
        spnCarb = (Spinner)findViewById(R.id.spn_carb);

        ArrayAdapter<CharSequence> bsAdapter = ArrayAdapter.createFromResource(this, R.array.bs_types, android.R.layout.simple_spinner_item);
        bsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBs.setAdapter(bsAdapter);

        ArrayAdapter<CharSequence> carbAdapter = ArrayAdapter.createFromResource(this, R.array.carb_types, android.R.layout.simple_spinner_item);
        carbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCarb.setAdapter(carbAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.spn_bs:
                bs = parent.getItemAtPosition(position).toString();
                System.out.println("Blood sugar type : " + bs);
                break;
            case R.id.spn_carb:
                carb = parent.getItemAtPosition(position).toString();
                System.out.println("Carbohydrate type : " + carb);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
