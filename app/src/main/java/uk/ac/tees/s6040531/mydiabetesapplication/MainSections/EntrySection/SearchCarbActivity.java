package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection;

import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

import android.os.Bundle;

public class SearchCarbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carb);
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
