package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
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
    User user;
    BloodSugarEntry newEntry = new BloodSugarEntry();
    String meal = "None selected";

    // Variables for shared preferences access
    SharedPreferences myPref;
    Gson gson;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout files
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        myPref = getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        // Initalise firebase variables
        auth = FirebaseAuth.getInstance();
        uDbRef = FirebaseFirestore.getInstance();

        // Gets the current date and time components
        y = Calendar.getInstance().get(Calendar.YEAR);
        m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        min = Calendar.getInstance().get(Calendar.MINUTE);

        // Formats the date
        String da = d + "/" + m + "/" + y;
        try
        {
            date = new SimpleDateFormat("dd/MM/yyy").parse(da);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        // Calls timeFormat()
        time = timeFormat();

        // Initialize the widgets
        tvDate = findViewById(R.id.tv_date_display);
        tvDate.setText(d + "/" + (m) + "/" + y);
        tvTime = findViewById(R.id.tv_time_display);
        tvTime.setText(time);
        tvIF = findViewById(R.id.tv_if);
        tvIC = findViewById(R.id.tv_ic);
        tvIT = findViewById(R.id.tv_it);
        etBs = findViewById(R.id.et_bs);
        etCarbs = findViewById(R.id.et_carbs);
        etNotes = findViewById(R.id.et_notes);
        spnMeal = findViewById(R.id.spn_meal);
        btnSearch = findViewById(R.id.btn_search);
        btnCalc = findViewById(R.id.btn_calculate);
        btnSaveEntry = findViewById(R.id.btn_save_entry);
        btnBack = findViewById(R.id.btn_back);

        // ArrayAdapter for spnMeal
        ArrayAdapter<CharSequence> mealAdapter = ArrayAdapter.createFromResource(AddEntryActivity.this, R.array.meal_types, android.R.layout.simple_spinner_item);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMeal.setAdapter(mealAdapter);

        // onClickListener for tvDate
        tvDate.setOnClickListener(new View.OnClickListener()
        {
            /**
             * btnDate onClick() method
             * @param v - activity view
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
             * @param v - activity view
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
             * @param view - activity view
             */
            @Override
            public void onClick(View view)
            {
                // Loads up SearchCarbActivity
                Intent i = new Intent(AddEntryActivity.this, SearchCarbActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        // onClickListener for btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnBack
             * @param v - activity view
             */
            @Override
            public void onClick(View v) {
                // calls onBackPressed()
                onBackPressed();
            }
        });

        // onClickListener for btnCalc
        btnCalc.setOnClickListener(new View.OnClickListener() {
            /**
             * onCLick for btnCalc
             * @param view - activity view
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
                double targetTop = Double.parseDouble(user.getTop());
                double targetBottom = Double.parseDouble(user.getBottom());
                double hypo = Double.parseDouble(user.getHypo());
                double correction = Double.parseDouble(user.getCorrection());
                String prec = user.getPrecision();
                double portion = Double.parseDouble(user.getPortion());

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
                    Toast.makeText(AddEntryActivity.this,"Please enter a blood sugar.", Toast.LENGTH_SHORT).show();
                }
                // Checks if the blood sugar field contains any characters other than numbers and decimal points
                else if(isNumeric(b))
                {
                    // Informs the user of the validation error
                    Toast.makeText(AddEntryActivity.this, "Invalid type in blood sugar, please only input a number", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddEntryActivity.this,"Please enter a carbs amount, if no carbs eaten please enter 0.", Toast.LENGTH_SHORT).show();
                    }
                    // Checks if the carbs frield contains any characters other than numbers and decimal points
                    else if(isNumeric(c))
                    {
                        // Informs the user of the validation error
                        Toast.makeText(AddEntryActivity.this, "Invalid type in carbohydrates, please only input a number", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // Saves the value as a double
                        carbs = Double.parseDouble(c);
                        newEntry.setCarbs(carbs);

                        // Converts the carbs into grams if needed
                        if(user.getCb_m().equals("CP"))
                        {
                            carbs = carbs * portion;
                        }

                        // Works out the food insulin
                        Double inF = carbs / ratio;
                        newEntry.setInsulin_f(inF);

                        // Checks if the entered blood sugar is within target
                        if(bs >= targetBottom && bs <= targetTop || bs <= targetBottom && bs >= hypo)
                        {
                            // Calculates the total insulin and sets the insulin total and correction
                            newEntry.setInsulin_c(0);
                            newEntry.setInsulin_t(inF);

                            // calls displayInsulin() with relevant parameters
                            displayInsulin(prec, inF, 0, inF);

                        }
                        // Checks if the user has a low blood sugar
                        else if(bs < hypo)
                        {
                            // Informs the user to not do any insulin and consume carbohydrates
                            Toast.makeText(AddEntryActivity.this, "Low blood sugar, please do not take any insulin and make sure to eat 25g of carbohydrates. ", Toast.LENGTH_SHORT).show();
                        }
                        // Checks if the user has a high blood sugar
                        else if(bs > targetTop)
                        {
                            // Calculates and saves the correction dosgae
                            Double corr = (bs - targetTop) / correction;
                            newEntry.setInsulin_c(corr);

                            // Checks if correction can be ignored due to active insulin
                            if(corr - iob <= 0.0)
                            {
                                corr = 0.0;
                            }

                            // Calculates and saves total insulin
                            double totalIn = (inF + corr);
                            newEntry.setInsulin_t(totalIn);

                            // calls displayInsulin() with relevant parameters
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
             * @param view - activity view
             */
            @Override
            public void onClick(View view)
            {
                // Grabs the user's not input and saves it
                String note = etNotes.getText().toString();
                newEntry.setNotes(note);

                // Adds the new blood sugar entry to the list
                user.addBlood_sugar(newEntry);

                // Updates the firebase database
                uDbRef.collection("users").document(Objects.requireNonNull(auth.getUid())).set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        /**
                         * onSuccess() method
                         * @param aVoid - method parameter
                         */
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            // Informs the user that the entry was saved
                            Toast.makeText(AddEntryActivity.this, "Entry saved", Toast.LENGTH_SHORT).show();

                            // Updated the local data
                            SharedPreferences.Editor prefEd = myPref.edit();
                            String json = gson.toJson(user);
                            prefEd.putString(getResources().getString(R.string.user_key),json);
                            prefEd.apply();

                            // Loads the HomeActivity
                            Intent i = new Intent(AddEntryActivity.this, HomeActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        /**
                         * onFailure() method
                         * @param e - error
                         */
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Informs the user that the save failed
                            Toast.makeText(AddEntryActivity.this, "Entry failed to save, please try again", Toast.LENGTH_SHORT).show();
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
        int monthParam = m - 1;
        int dayParam = d;

        // Creates a new DatePickerDialog, displaying the current date initially
        DatePickerDialog datePickerDialog = new DatePickerDialog(con, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
        {
            /**
             * onDateSet() method
             * @param view - activity view
             * @param year - current year
             * @param month - current month
             * @param dayOfMonth - current day
             */
            @SuppressLint("SimpleDateFormat")
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
             * @param view - activity view
             * @param hourOfDay - current hour
             * @param minute - current minute
             */
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                // Grabs the time input from the user
                h = hourOfDay;
                min = minute;

                // Formats the date and sets the time TextView
                time = timeFormat();
                tvTime.setText(time);
            }
        }, h, min, true);

        // Shows the timePickerDialog
        timePickerDialog.show();
    }

    /**
     * getTimeBlocks() method
     * @return ratio
     */
    @SuppressLint("SimpleDateFormat")
    public double getTimeBlocks()
    {
        // Time variables
        Date ct;
        Date st;
        Date et;
        SimpleDateFormat tF = new SimpleDateFormat("HH:mm");
        double ratio = 123.123;

        try
        {
            // Parse the current time to a date to check if in range
            ct = tF.parse(time);
            System.out.println("====== Current time : " + ct);

            int no_blocks = user.getTime_blocks().size();

            // Loops through each time block
            for(TimeBlock t : user.getTime_blocks())
            {
                if(no_blocks == 1)
                {
                    ratio = Double.parseDouble(t.getRatio());
                }
                else
                {
                    // Parse the start and end times to date
                    st = tF.parse(t.getStart());
                    et = tF.parse(t.getEnd());

                    // Checks if the current time is within the time block range,
                    // includes if it is equal to one of the boundary times
                    if((Objects.requireNonNull(ct).after(st) && ct.before(et)))
                    {
                        ratio =  Double.parseDouble(t.getRatio());
                    }
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // Returns the correct ration
        return ratio;
    }

    /**
     * getInsulinOnboard() method
     * @return insRem
     */
    @SuppressLint("SimpleDateFormat")
    public double getInsulinOnBoard()
    {
        // Time variables
        Date ct;
        Date rt;
        SimpleDateFormat tF = new SimpleDateFormat("HH:mm");

        try
        {
            // Formats times and works out duration range
            ct = tF.parse(time);
            rt = new Date(Objects.requireNonNull(ct).getTime() - HOURS.toMillis(Integer.parseInt(user.getDuration())));

            // Loops through blood sugar entries
            for(BloodSugarEntry bse : user.getBlood_sugars())
            {
                // Checks if the entry date is today
                if(bse.getDate().equals(date))
                {
                    // Grabs the entry time
                    Date bt = tF.parse(bse.getTime());

                    // Checks if the entry time is int the duration range
                    if(Objects.requireNonNull(bt).after(rt))
                    {
                        // Calculates the differnce between current time and duration time
                        long diffSec = (bt.getTime() - rt.getTime()) / 1000;
                        int diffHour =(5 - (int) diffSec / 3600);

                        // Calculates percentage
                        double percRem = 1 - (diffHour * 0.2);

                        // Calculates percentage of insulin remaining and returns it
                        return bse.getInsulin_t() * percRem;
                    }
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * displayInsulin() method
     * @param prec - insuliln precision
     * @param food - insulin for food
     * @param corr - insulin for correction
     * @param total - total insulin
     */
    public void displayInsulin (String prec, double food, double corr, double total)
    {
        // Checks the precision does not equal 1
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

        // TextView displays
        String foodDisplay = "Insulin (food) : " + food + "U";
        String corrDisplay = "Insulin (correction) : " + corr + "U";
        String totalDisplay = "Total Insulin : " + total + "U";

        // Displays the insulin values
        tvIF.setText(foodDisplay);
        tvIC.setText(corrDisplay);
        tvIT.setText(totalDisplay);
    }

    /**
     * isNumeric() method
     * @param val - val to check
     * @return  numPat.matcher(val).matches()
     */
    public boolean isNumeric(String val)
    {
        // Matcher pattern
        Pattern numPat = Pattern.compile("\\d+(\\.\\d+)?");

        // Returns if a value is numeric
        return !numPat.matcher(val).matches();
    }

    /**
     * timeFormat() method
     * @return formatted time
     */
    public String timeFormat()
    {
        // Formats the currently stored time
        String hz = (h >= 10)? Integer.toString(h) : String.format("0%s", Integer.toString(h));
        String mz = (min >= 10)? Integer.toString(min) : String.format("0%s", Integer.toString(min));
        return hz + ":" + mz;
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