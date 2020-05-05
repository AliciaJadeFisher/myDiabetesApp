package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * ViewRecordActivity
 */
public class ViewRecordActivity extends AppCompatActivity
{
    // Variables for layout access
    TextView tvDate, tvTime, tvBs, tvCarbs, tvMeal, tvInF, tvInC, tvInT, tvNote;

    // Variable for selected entry
    BloodSugarEntry entry;

    // Variable for the current user
    User user;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        // Grabs the current user
        SharedPreferences myPref = getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        // Initialise the widgets
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);
        tvBs = findViewById(R.id.tv_bs);
        tvCarbs = findViewById(R.id.tv_carbs);
        tvMeal = findViewById(R.id.tv_meal);
        tvInF = findViewById(R.id.tv_inFood);
        tvInC = findViewById(R.id.tv_inCorr);
        tvInT = findViewById(R.id.tv_inTotal);
        tvNote = findViewById(R.id.tv_note);

        // Calls getIncomingIntent()
        getIncomingIntent();
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    public void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference bs_entry
        if(this.getIntent().hasExtra("bs_entry"))
        {
            //Grabs the data in the extra
            entry = (BloodSugarEntry) this.getIntent().getSerializableExtra("bs_entry");

            // Calls setTextViews()
            setTextViews();
        }
    }

    /**
     * Sets the text for the text views
     */
    public void setTextViews()
    {
        // Saves the text displays
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date_display = format.format(entry.getDate());
        String time_display = entry.getTime();
        String bs_display = entry.getBs() + " " + user.getBs_m();
        String carbs_display = entry.getCarbs() + " " + user.getCb_m();
        String meal_display = entry.getMeal();
        String inF_display = entry.getInsulin_f() + " U";
        String inC_display = entry.getInsulin_c() + " U";
        String inT_display = entry.getInsulin_t() + " U";
        String note_display = entry.getNotes();

        // Update the text view displays
        tvDate.setText(date_display);
        tvTime.setText(time_display);
        tvBs.setText(bs_display);
        tvCarbs.setText(carbs_display);
        tvMeal.setText(meal_display);
        tvInF.setText(inF_display);
        tvInC.setText(inC_display);
        tvInT.setText(inT_display);
        tvNote.setText(note_display);
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the HomeActivity
        Intent i = new Intent(ViewRecordActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}
