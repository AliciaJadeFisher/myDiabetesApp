package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * ViewRecordActivity
 */
public class ViewRecordActivity extends AppCompatActivity
{
    // Variables for layout access
    TextView tvDate, tvTime, tvBs, tvCarbs, tvMeal, tvInF, tvInC, tvInT, tvNote;

    // Variable for selected entry
    BloodSugarEntry entry;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        // Initialise the widgets
        tvDate = (TextView)findViewById(R.id.tv_date);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvBs = (TextView)findViewById(R.id.tv_bs);
        tvCarbs = (TextView)findViewById(R.id.tv_carbs);
        tvMeal = (TextView)findViewById(R.id.tv_meal);
        tvInF = (TextView)findViewById(R.id.tv_inFood);
        tvInC = (TextView)findViewById(R.id.tv_inCorr);
        tvInT = (TextView)findViewById(R.id.tv_inTotal);
        tvNote = (TextView)findViewById(R.id.tv_note);

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
        // Saves the text dispalys
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date_display = "Date: " + format.format(entry.getDate());
        String time_display = "Time: " + entry.getTime();
        String bs_display = "Blood Sugar: " + entry.getBs();
        String carbs_display = "Carbohydrates: " + entry.getCarbs();
        String meal_display = "Meal Type: " + entry.getMeal();
        String inF_display = "Insulin (Food): " + entry.getInsulin_f();
        String inC_display = "Insulin (Correction): " + entry.getInsulin_c();
        String inT_display = "Total Insulin: " + entry.getInsulin_t();
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
