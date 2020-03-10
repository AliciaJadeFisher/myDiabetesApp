package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.EntrySection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class AddEntryActivity extends AppCompatActivity
{
    int y, m,d, h,min;
    Calendar cal;
    Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        y = Calendar.getInstance().get(Calendar.YEAR);
        m = Calendar.getInstance().get(Calendar.MONTH);
        d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        min = Calendar.getInstance().get(Calendar.MINUTE);
    }

    public void show_DatePicker()
    {
        cal = Calendar.getInstance();
        int yearParam = y;
        int monthParam = m-1;
        int dayParam = d;

        DatePickerDialog datePickerDialog = new DatePickerDialog(con, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                m = month + 1;
                y = year;
                d = dayOfMonth;
            }
        }, yearParam, monthParam, dayParam);

        datePickerDialog.show();
    }

    public void show_TimePicker()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(con, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                h = hourOfDay;
                min = minute;
            }
        }, h, min, true);

        timePickerDialog.show();
    }
}
