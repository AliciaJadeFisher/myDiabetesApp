package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

import android.os.Bundle;
import android.widget.TextView;

public class ViewRecordActivity extends AppCompatActivity
{
    TextView tvDate, tvTime, tvBs, tvCarbs, tvMeal, tvInF, tvInC, tvInT, tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }
}
