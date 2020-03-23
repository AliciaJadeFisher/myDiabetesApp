package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.EntrySection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    TextView tvDate, tvTime;
    Button btnDate, btnTime;

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

        // Gets the current date and time components
        y = Calendar.getInstance().get(Calendar.YEAR);
        m = Calendar.getInstance().get(Calendar.MONTH);
        d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        min = Calendar.getInstance().get(Calendar.MINUTE);

        // Initailize the widgets
        tvDate = (TextView)findViewById(R.id.tv_date_display);
        tvTime = (TextView)findViewById(R.id.tv_time_display);
        btnDate = (Button)findViewById(R.id.btn_set_date);
        btnTime = (Button)findViewById(R.id.btn_set_time);

        // onClickListener for btnDate
        btnDate.setOnClickListener(new View.OnClickListener()
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

        // onClickListener for btnTime
        btnTime.setOnClickListener(new View.OnClickListener()
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
}