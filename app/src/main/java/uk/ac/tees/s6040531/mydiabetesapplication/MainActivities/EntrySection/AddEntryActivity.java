package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.EntrySection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TooManyListenersException;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * AddEntryActivity
 */
public class AddEntryActivity extends AppCompatActivity
{
    // Variables for date and time acess
    int y, m,d, h,min;
    Calendar cal;
    Context con = this;
    Date date;
    String time;

    // Variables for layout access
    TextView tvDate, tvTime, tvIF, tvIC, tvIT;
    EditText etBs, etCarbs, etNotes;
    Button btnSearch, btnCalc, btnSaveEntry, btnBack;
    Spinner spnMeal;

    // Variable for user data
    User u;

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout files
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        // Calls getIncomingIntent()
        getIncomingIntent();

        // Gets the current date and time components
        y = Calendar.getInstance().get(Calendar.YEAR);
        m = Calendar.getInstance().get(Calendar.MONTH);
        d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        min = Calendar.getInstance().get(Calendar.MINUTE);

        // Initialize the widgets
        tvDate = (TextView)findViewById(R.id.tv_date_display);
        tvDate.setText(d + "/" + (m) + "/" + y);
        tvTime = (TextView)findViewById(R.id.tv_time_display);
        tvTime.setText(h + ":" + min);
        tvIF = (TextView)findViewById(R.id.tv_if);
        tvIC = (TextView)findViewById(R.id.tv_ic);
        tvIT = (TextView)findViewById(R.id.tv_it);
        etBs = (EditText)findViewById(R.id.et_bs);
        etCarbs = (EditText)findViewById(R.id.et_carbs);
        etNotes = (EditText)findViewById(R.id.et_notes);
        spnMeal = (Spinner)findViewById(R.id.spn_meal);
        btnSearch = (Button)findViewById(R.id.btn_search);
        btnCalc = (Button)findViewById(R.id.btn_calculate);
        btnSaveEntry = (Button)findViewById(R.id.btn_save_entry);
        btnBack = (Button)findViewById(R.id.btn_back);

        // ArrayAdapter for spnMeal
        ArrayAdapter<CharSequence> mealAdapter = ArrayAdapter.createFromResource(AddEntryActivity.this, R.array.meal_types, android.R.layout.simple_spinner_item);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMeal.setAdapter(mealAdapter);

        // onClickListener for tvDate
        tvDate.setOnClickListener(new View.OnClickListener()
        {
            /**
             * btnDate onClick() method
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Calls show_DatePicker()
                show_DatePicker();
            }
        });

        // onClickListener for tvTime
        tvTime.setOnClickListener(new View.OnClickListener()
        {
            /**
             * btnTime onClick() method
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Calls show_TimePicker()
                show_TimePicker();
            }
        });

        // onClickListener for btnSearch
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick for btnSearch
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Loads up SearchCarbActivity
            }
        });

        // onClickListener for btnCalc
        btnCalc.setOnClickListener(new View.OnClickListener() {
            /**
             * onCLick for btnCalc
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Grabs the user inputs
                String b = etBs.getText().toString();
                String c = etCarbs.getText().toString();

                // Checks if the blood sugar field is empty
                if(b.equals(""))
                {
                    // Informs the user that the field needs to filled in
                    Toast.makeText(AddEntryActivity.this,"Please enter a blood sugar.", Toast.LENGTH_SHORT);
                }
                // Checks if the blood sugar field contains any characters other than numbers and decimal points
                else if(!b.matches("[0-9.]"))
                {
                    // Informs the user of the validation error
                    Toast.makeText(AddEntryActivity.this, "Invalid type in blood sugar, please only input a number", Toast.LENGTH_SHORT);
                }
                else
                {
                    // Saves the value as a double
                    double bs = Double.parseDouble(b);
                }

                // Checks if the carbs field is empty
                if(c.equals(""))
                {
                    // Iforms the user that field needs to be filled in
                    Toast.makeText(AddEntryActivity.this,"Please enter a carbs amount, if no carbs eaten please enter 0.", Toast.LENGTH_SHORT);
                }
                // Checks if the carbs frield contains any characters other than numbers and decimal points
                else if(c.contains("[a-zA-Z]+"))
                {
                    // Infroms the user of the validation error
                    Toast.makeText(AddEntryActivity.this, "Invalid type in carbohydrates, please only input a number", Toast.LENGTH_SHORT);
                }
                else
                {
                    // Saves the value as a doible
                    double carbs = Double.parseDouble(c);
                }
            }
        });
    }

    /**
     * show_DatePicker() method
     */
    public void show_DatePicker()
    {
        // Initializes the calendar and date components
        cal = Calendar.getInstance();
        int yearParam = y;
        int monthParam = m;
        int dayParam = d;

        // Creates a new DatePickerDialog, displaying the current date initially
        DatePickerDialog datePickerDialog = new DatePickerDialog(con, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
        {
            /**
             * onDateSet() method
             * @param view
             * @param year
             * @param month
             * @param dayOfMonth
             */
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                // Grabs the date input from the user
                String da = dayOfMonth + "/" + (month + 1) + "/" + year;

                // Formats the date
                try
                {
                    date = new SimpleDateFormat("dd/MM/yyy").parse(da);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                // Sets the date TextVIew
                tvDate.setText(da);
            }
        }, yearParam, monthParam, dayParam);

        // Shows the dataPickerDialog
        datePickerDialog.show();
    }

    /**
     * showTimePicker() method
     */
    public void show_TimePicker()
    {
        // Creates a new TimePickerDialog, and displays the current time initially
        TimePickerDialog timePickerDialog = new TimePickerDialog(con, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener()
        {
            /**
             * onTimeSet() method
             * @param view
             * @param hourOfDay
             * @param minute
             */
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                // Grabs the time input from the user
                h = hourOfDay;
                min = minute;

                // Formats the date and sets the time TextView
                time = h + ":" + min;
                tvTime.setText(time);
            }
        }, h, min, true);

        // Shows the timePickerDialog
        timePickerDialog.show();
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    private void getIncomingIntent()
    {

        //Checks if the intent has an extra with the reference user
        if (this.getIntent().hasExtra("user")) {
            //Grabs the data in the extra
            u = (User) this.getIntent().getSerializableExtra("user");
        }
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the HomeActivity
        Intent i = new Intent(AddEntryActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}