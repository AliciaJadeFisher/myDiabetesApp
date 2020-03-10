package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.EntrySection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class AddEntryActivity extends AppCompatActivity
{
    int y, m,d, h,min;
    Calendar cal;
    Context con = this;

    TextView tvDate, tvTime;
    Button btnDate, btnTime;

    Date date;
    Time time;

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

        tvDate = (TextView)findViewById(R.id.tv_date_display);
        tvTime = (TextView)findViewById(R.id.tv_time_display);
        btnDate = (Button)findViewById(R.id.btn_set_date);
        btnTime = (Button)findViewById(R.id.btn_set_time);

        btnDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                show_DatePicker();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                show_TimePicker();

            }
        });
    }

    public void show_DatePicker()
    {
        cal = Calendar.getInstance();
        int yearParam = y;
        int monthParam = m;
        int dayParam = d;

        DatePickerDialog datePickerDialog = new DatePickerDialog(con, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                String da = dayOfMonth + "/" + (month + 1) + "/" + year;

                try
                {
                    date = new SimpleDateFormat("dd/MM/yyy").parse(da);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                tvDate.setText(da);
            }
        }, yearParam, monthParam, dayParam);

        datePickerDialog.show();
    }

    public void show_TimePicker()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(con, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                h = hourOfDay;
                min = minute;

                String time = h + ":" + min;
                tvTime.setText(time);
            }
        }, h, min, true);

        timePickerDialog.show();
    }
}
