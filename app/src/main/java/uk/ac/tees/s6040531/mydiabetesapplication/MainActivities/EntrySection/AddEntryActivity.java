package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.EntrySection;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.TimeBlock;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

import static java.util.concurrent.TimeUnit.HOURS;

/**
 * AddEntryActivity
 */
public class AddEntryActivity extends AppCompatActivity
{
    // Variables for date and time access
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

    // Variable for Firebase access
    FirebaseFirestore uDbRef;
    FirebaseAuth auth;

    // Variable for user data
    User u;
    BloodSugarEntry newEntry = new BloodSugarEntry();
    String meal = "None selected";

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

        // Initalise firebase variables
        auth = FirebaseAuth.getInstance();
        uDbRef = FirebaseFirestore.getInstance();

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
                // Sets the date and time
                newEntry.setDate(date);
                newEntry.setTime(time);

                // Grabs the spinner inputs and sets the object's meal type
                meal = spnMeal.getSelectedItem().toString();
                newEntry.setMeal(meal);

                // Grabs the user's insulin settings
                double targetTop = Double.parseDouble(u.getTop());
                double targetBottom = Double.parseDouble(u.getTop());
                double hyper = Double.parseDouble(u.getHyper());
                double hypo = Double.parseDouble(u.getHypo());
                double correction = Double.parseDouble(u.getCorrection());
                String prec = u.getPrecision();
                double portion = Double.parseDouble(u.getPortion());

                // Calculates the ratio and iob to be used
                double ratio = getTimeBlocks();
                double iob = getInsulinOnBoard();

                // Grabs the user inputs
                String b = etBs.getText().toString();
                String c = etCarbs.getText().toString();
                double bs;
                double carbs ;


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
                    bs = Double.parseDouble(b);
                    newEntry.setBs(bs);

                    // Checks if the carbs field is empty
                    if(c.equals(""))
                    {
                        // Informs the user that field needs to be filled in
                        Toast.makeText(AddEntryActivity.this,"Please enter a carbs amount, if no carbs eaten please enter 0.", Toast.LENGTH_SHORT);
                    }
                    // Checks if the carbs frield contains any characters other than numbers and decimal points
                    else if(c.contains("[a-zA-Z]+"))
                    {
                        // Informs the user of the validation error
                        Toast.makeText(AddEntryActivity.this, "Invalid type in carbohydrates, please only input a number", Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        // Saves the value as a double
                        carbs = Double.parseDouble(c);
                        newEntry.setCarbs(carbs);

                        // Converts the carbs into grams if needed
                        if(u.getCb_m().equals("CP"))
                        {
                            carbs = carbs * portion;
                        }

                        // Works out the food insulin
                        Double inF = carbs / ratio;
                        newEntry.setInsulin_f(inF);

                        // Checks if the entered blood sugar is within target
                        if(bs >= targetBottom && bs <= targetTop)
                        {
                            // Calculates the total insulin and sets the insulin total and correction
                            Double totalIn = inF - iob;
                            newEntry.setInsulin_c(0);
                            newEntry.setInsulin_t(totalIn);

                            displayInsulin(prec, inF, 0, totalIn);

                        }
                        else if(bs <= hypo)
                        {
                            Toast.makeText(AddEntryActivity.this, "Low blood sugar, please do not take any insulin and make sure to eat 25g of carbohydrates. ", Toast.LENGTH_SHORT);
                        }
                        else if(bs >= hyper)
                        {
                            Double corr = (bs - hyper) / correction;
                            newEntry.setInsulin_c(corr);

                            Double totalIn = (inF + corr) - iob;
                            newEntry.setInsulin_t(totalIn);

                            displayInsulin(prec, inF, corr, totalIn);
                        }
                    }
                }

            }
        });

        // onClickListener for btnSaveEntry
        btnSaveEntry.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnSaveEntr\y
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // Grabs the user's not input and saves it
                String note = etNotes.getText().toString();
                newEntry.setNotes(note);

                u.addBlood_sugar(newEntry);

                uDbRef.collection("users").document(auth.getUid()).set(u)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(AddEntryActivity.this, "Entry saved", Toast.LENGTH_SHORT);

                                // Loads the HomeActivity
                                Intent i = new Intent(AddEntryActivity.this, HomeActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(AddEntryActivity.this, "Entry failed to save, please try again", Toast.LENGTH_SHORT);
                            }
                        });

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

    public double getTimeBlocks()
    {
        // Time variables
        Date ct;
        Date st;
        Date et;
        SimpleDateFormat tF = new SimpleDateFormat("HH:mm");

        try
        {
            // Parse the current time to a date to check if in range
            ct = tF.parse(time);

            // Loops through each time block
            for(TimeBlock t : u.getTime_blocks())
            {
                // Parse the start and end times to date
                st = tF.parse(t.getStart());
                et = tF.parse(t.getEnd());

                // Checks if the current time is within the time block range,
                // includes if it is equal to one of the boundary times
                if(!(ct.before(st) || ct.after(et)))
                {
                    return Double.parseDouble(t.getRatio());
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return 456;
    }

    public double getInsulinOnBoard()
    {
        Date ct;
        Date rt;
        SimpleDateFormat tF = new SimpleDateFormat("HH:mm");

        try
        {
            ct = tF.parse(time);
            rt = new Date(ct.getTime() - HOURS.toMillis(5));

            for(BloodSugarEntry bse : u.getBlood_sugars())
            {
                Date bt = tF.parse(bse.getTime());

                if(bt.after(rt))
                {
                    long diffSec = (bt.getTime() - rt.getTime()) / 1000;
                    int diffHour =(int) diffSec / 3600;

                    double percRem = 1 - (diffHour * 0.2);
                    double insRem = bse.getInsulin_t() * percRem;

                    return insRem ;
                }

            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return 456;
    }

    /**
     * displayInsulin() method
     * @param prec
     * @param food
     * @param corr
     * @param total
     */
    public void displayInsulin (String prec, double food, double corr, double total)
    {
        if(!prec.equals("1"))
        {
            // Formats the insulin based on the user's entered precision
            int dec = prec.substring(prec.indexOf(".")).length();
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(dec);
            food = Double.parseDouble(formatter.format(food));
            corr = Double.parseDouble(formatter.format(corr));
            total = Double.parseDouble(formatter.format(total));
        }
        else
        {
            // Rounds the insulin to the nearest whole number
            food = Math.rint(food);
            corr = Math.rint(corr);
            total = Math.rint(total);
        }

        // Displays the insulin values
        tvIF.setText("Insulin (food) : " + food + "U");
        tvIC.setText("Insulin (correction) : " + corr + "U");
        tvIT.setText("Total Insulin : " + total + "U");
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