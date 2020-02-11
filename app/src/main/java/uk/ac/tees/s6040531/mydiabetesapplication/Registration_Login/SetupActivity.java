package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SetupActivity
 */
public class SetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    // Variables for layout access
    EditText etName, etHypo, etBottom, etTop, etHyper, etDuration, etPrecision, etPortion, etRatio, etCorrection;
    Spinner spnBs, spnCarb;
    Button btnSave;

    // Variables for user data
    String bs, carb, id;

    // Variable for database reference
    DatabaseReference userDbRef;

    // Variables for Firebase access
    FirebaseAuth auth;
    FirebaseUser cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grab the layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Grabs the id of the current user
        auth = FirebaseAuth.getInstance();
        cUser = auth.getCurrentUser();
        id = auth.getUid();
        
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
