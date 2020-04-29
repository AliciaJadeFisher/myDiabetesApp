package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    TextView tvWelcome, tvAverage, tvHypos, tvHypers;
    Button btnToday, btnWeek, btnMonth, btnAll;
    RecyclerView rvRecords;
    FloatingActionButton fabAdd;

    Calendar cal = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    Date week;
    Date month;

    RecordsRecyclerViewAdapter adapter;

    // Variable for the current user details
    User user;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * HomeFragment constructor
     * @param index
     * @return fragment
     */
    public static HomeFragment newInstance(int index)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * onCreate() method
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int index = 1;

        // Checks for any arguments
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    /**
     * onCreateView() method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    /**
     * onViewCreated() method
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        cal.set(Calendar.DAY_OF_WEEK,2);
        week = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH,1);
        month = cal.getTime();

        SharedPreferences myPref = getActivity().getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        // Initializes the widgets
        tvWelcome = (TextView)view.findViewById(R.id.tv_welcome);
        tvAverage = (TextView)view.findViewById(R.id.tv_average);
        tvHypos = (TextView)view.findViewById(R.id.tv_hypos);
        tvHypers = (TextView)view.findViewById(R.id.tv_hypers);
        rvRecords = (RecyclerView)view.findViewById(R.id.rv_records);
        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add_entry);
        btnToday = (Button)view.findViewById(R.id.btn_today);
        btnWeek = (Button)view.findViewById(R.id.btn_week);
        btnMonth = (Button)view.findViewById(R.id.btn_month);
        btnAll = (Button)view.findViewById(R.id.btn_all);

        tvWelcome.setText("Hello " + user.getName());
        btnToday.setBackgroundResource(R.drawable.selection_button_selected_background);
        displayEntries("Today");

        // OnClickListener() for fabAdd
        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            /**
             *
             * onClick() method for fabAdd
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Loads the AddEntryActivity
                Intent i = new Intent(getActivity(), AddEntryActivity.class);
                i.putExtra("user",user);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnToday.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                displayEntries("Today");
            }
        });

        btnWeek.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnWeek.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                displayEntries("Week");
            }
        });

        btnMonth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnMonth.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);
                btnAll.setBackgroundResource(R.drawable.selection_button_background);

                displayEntries("Month");
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnAll.setBackgroundResource(R.drawable.selection_button_selected_background);
                btnWeek.setBackgroundResource(R.drawable.selection_button_background);
                btnMonth.setBackgroundResource(R.drawable.selection_button_background);
                btnToday.setBackgroundResource(R.drawable.selection_button_background);

                displayEntries("All");
            }
        });
    }

    public void displayEntries(String input)
    {
        List<BloodSugarEntry> entries = getEntries(input);

        tvAverage.setText(String.valueOf(Math.round(getAverage(entries) * 10)/ 10.0));
        tvHypos.setText(String.valueOf(getHypos(entries)));
        tvHypers.setText(String.valueOf(getHypers(entries)));

        adapter = new RecordsRecyclerViewAdapter(this,entries);
        rvRecords.setAdapter(adapter);
        rvRecords.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }

    public List<BloodSugarEntry> getEntries(String selected)
    {
        List<BloodSugarEntry> unfilteredList = user.getBlood_sugars();
        List<BloodSugarEntry> filteredList = new ArrayList<>();

        if(selected.equals("All"))
        {
            filteredList = unfilteredList;
        }
        else if(selected.equals("Today"))
        {
            for(BloodSugarEntry e : unfilteredList)
            {
                System.out.println("===== E Date : " + dateFormat.format(e.getDate()));
                System.out.println("===== Today : " + dateFormat.format(today));

                if(dateFormat.format(e.getDate()).equals(dateFormat.format(today))) {
                    filteredList.add(e);
                }
            }
        }
        else if(selected.equals("Week"))
        {
            for(BloodSugarEntry e : unfilteredList)
            {
                if(e.getDate().after(week) || dateFormat.format(e.getDate()).equals(dateFormat.format(week)))
                {
                    filteredList.add(e);
                }
            }
        }
        else if(selected.equals("Month"))
        {
            for(BloodSugarEntry e : unfilteredList)
            {
                if(e.getDate().after(month))
                {
                    filteredList.add(e);
                }
            }
        }

        return filteredList;
    }

    public double getAverage(List<BloodSugarEntry> list)
    {
        double total = 0;
        int count = 0;

        for(BloodSugarEntry e : list)
        {
            total += e.getBs();
            count++;
        }

        double average = total/count;
        return average;
    }

    public int getHypos(List<BloodSugarEntry> list)
    {
        int count = 0;
        double hypo = Double.parseDouble(user.getHypo());

        for(BloodSugarEntry e : list)
        {
            if(e.getBs() < hypo)
            {
                count++;
            }
        }

        return count;
    }

    public int getHypers(List<BloodSugarEntry> list)
    {
        int count = 0;
        double hyper = Double.parseDouble(user.getHyper());

        for(BloodSugarEntry e : list)
        {
            if(e.getBs() > hyper)
            {
                count++;
            }
        }

        return count;
    }
}