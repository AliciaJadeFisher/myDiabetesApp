package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection.AddEntryActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.RecordsRecyclerViewAdapter;

/**
 * HomeFragment
 */
public class HomeFragment extends Fragment
{
    // Variables for layout access
    private TextView tvAverage, tvHypos, tvHypers;
    private Button btnToday, btnWeek, btnMonth, btnAll;
    private RecyclerView rvRecords;

    // Variables for calandar eaccess
    private Calendar cal = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date today = new Date();
    private Date week;
    private Date month;

    // Variable for the current user details
    private User user;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView() method
     * @param inflater - layout inflater for fragment
     * @param container - view group for fragment
     * @param savedInstanceState - instance bundle
     * @return root
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * onViewCreated() method
     * @param view - view for fragment
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Grabs the start of the week
        cal.set(Calendar.DAY_OF_WEEK,2);
        week = cal.getTime();

        // Grabs the start of the month
        cal.set(Calendar.DAY_OF_MONTH,1);
        month = cal.getTime();

        // Grabs the current user
        SharedPreferences myPref = Objects.requireNonNull(getActivity()).getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        // Initializes the widgets
        TextView tvWelcome = view.findViewById(R.id.tv_welcome);
        tvAverage = view.findViewById(R.id.tv_average);
        tvHypos = view.findViewById(R.id.tv_hypos);
        tvHypers = view.findViewById(R.id.tv_hypers);
        rvRecords = view.findViewById(R.id.rv_records);
        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add_entry);
        btnToday = view.findViewById(R.id.btn_today);
        btnWeek = view.findViewById(R.id.btn_week);
        btnMonth = view.findViewById(R.id.btn_month);
        btnAll = view.findViewById(R.id.btn_all);

        // Displays the welcome message
        String display = "Hello " + user.getName();
        tvWelcome.setText(display);

        // Sets the default display
        btnToday.setBackgroundResource(R.drawable.selection_button_selected_background);
        displayEntries("Today");

        // OnClickListener() for fabAdd
        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            /**
             *
             * onClick() method for fabAdd
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Loads the AddEntryActivity
                Intent i = new Intent(getActivity(), AddEntryActivity.class);
                i.putExtra("user",user);
                startActivity(i);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        // onClickListener for btnToday
        btnToday.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnToday
             * @param view - view for fragment
             */
            @Override
            public void onClick(View view)
            {
                // Updates the button backgrounds
                btnToday.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                // Calls displayEntries()
                displayEntries("Today");
            }
        });

        // onClickListener for btnWeek
        btnWeek.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnWeek
             * @param view - view for fragment
             */
            @Override
            public void onClick(View view)
            {
                // Updates the button backgrounds
                btnWeek.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                // Calls displayEntries()
                displayEntries("Week");
            }
        });

        // onClickListener for btnMonth
        btnMonth.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnMonth
             * @param view - view for fragment
             */
            @Override
            public void onClick(View view)
            {
                // Updates the button backgrounds
                btnMonth.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                // Calls displayEntries()
                displayEntries("Month");
            }
        });

        // onClickListener for btnAll
        btnAll.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnMonth
             * @param view - view for fragment
             */
            @Override
            public void onClick(View view)
            {
                // Updates the button backgrounds
                btnAll.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);

                // Calls displayEntries()
                displayEntries("All");
            }
        });
    }

    /**
     * displayEntries() method
     * @param input - display filter
     */
    private void displayEntries(String input)
    {
        // Gets the relevant entries
        List<BloodSugarEntry> entries = getEntries(input);

        // Works out the data overviews
        tvAverage.setText(String.valueOf(Math.round(getAverage(entries) * 10)/ 10.0));
        tvHypos.setText(String.valueOf(getHypos(entries)));
        tvHypers.setText(String.valueOf(getHypers(entries)));

        // Sets up the recycler view adapter
        RecordsRecyclerViewAdapter adapter = new RecordsRecyclerViewAdapter(this, entries);
        rvRecords.setAdapter(adapter);
        rvRecords.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }

    /**
     * getEntries() method
     * @param selected - entry filter
     * @return filteredList
     */
    private List<BloodSugarEntry> getEntries(String selected)
    {
        // Grabs the users blood sugars and creates a new list
        List<BloodSugarEntry> unfilteredList = user.getBlood_sugars();
        List<BloodSugarEntry> filteredList = new ArrayList<>();

        // Checks the filter
        switch (selected)
        {
            case "All":
                // Saves all entries
                filteredList = unfilteredList;
                break;
            case "Today":
                // Loops through all entries
                for (BloodSugarEntry e : unfilteredList)
                {
                    // Checks if the entry date is today
                    if (dateFormat.format(e.getDate()).equals(dateFormat.format(today)))
                    {
                        // Adds it to the list
                        filteredList.add(e);
                    }
                }
                break;
            case "Week":
                // Loops through all entries
                for (BloodSugarEntry e : unfilteredList)
                {
                    // Checks if the entry date is this week
                    if (e.getDate().after(week) || dateFormat.format(e.getDate()).equals(dateFormat.format(week)))
                    {
                        // Adds it to the list
                        filteredList.add(e);
                    }
                }
                break;
            case "Month":
                // Loops through all entries
                for (BloodSugarEntry e : unfilteredList)
                {
                    // Checks if the entry date is this month
                    if (e.getDate().after(month))
                    {
                        // Adds it to the list
                        filteredList.add(e);
                    }
                }
                break;
        }

        // Returns the filtered list
        return filteredList;
    }

    /**
     * getAverage()
     * @param list - list of blood sugar entries
     * @return average
     */
    private double getAverage(List<BloodSugarEntry> list)
    {
        // Initialise variables
        double total = 0;
        int count = 0;

        // Loops through all entries
        for(BloodSugarEntry e : list)
        {
            // Sums the blood sugar values
            total += e.getBs();

            // Increments count
            count++;
        }

        // Returns the average
        return total/count;
    }

    /**
     * getHypos()
     * @param list - list of blood sugar entries
     * @return count
     */
    private int getHypos(List<BloodSugarEntry> list)
    {
        // Intialise variable
        int count = 0;

        // Grabs ther user's hypo limit
        double hypo = Double.parseDouble(user.getHypo());

        // Loops through all entries
        for(BloodSugarEntry e : list)
        {
            // Checks if the blood sugar is below the hypo limit
            if(e.getBs() < hypo)
            {
                // Increments count
                count++;
            }
        }

        // Returns count
        return count;
    }

    /**
     * getHypers() method
     * @param list - list of blood sugar entries
     * @return count
     */
    private int getHypers(List<BloodSugarEntry> list)
    {
        // Initialise variable
        int count = 0;

        // Grabs the user's hyper limit
        double hyper = Double.parseDouble(user.getHyper());

        // Loops through all entries
        for(BloodSugarEntry e : list)
        {
            // Checks if the blood sugar is above the hyper limit
            if(e.getBs() > hyper)
            {
                // Increments count
                count++;
            }
        }

        // Returns count
        return count;
    }
}