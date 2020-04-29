package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.BloodSugarEntry;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.RecordsRecyclerViewAdapter;

/**
 * RecordsFragment
 */
public class RecordsFragment extends Fragment
{
    // Variables for layout access
    TextView tvAverage, tvHypos, tvHypers;
    RecyclerView rvRecords;

    RecordsRecyclerViewAdapter adapter;

    int hypo,hyper;
    User user;

    Calendar cal = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    Date week;
    Date month;

    String selected;


    /**
     * RecordsFragment constructor
     * @param sel
     * @return fragment
     */
    public static RecordsFragment newInstance(String sel)
    {
        RecordsFragment fragment = new RecordsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sel", sel);
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

        // Checks for any arguments
        if (getArguments() != null)
        {
            selected = getArguments().getString("sel");
        }
    }

    /**
     * onCreateView() method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        View root = inflater.inflate(R.layout.fragment_records, container, false);
        tvAverage = (TextView)root.findViewById(R.id.tv_average);
        tvHypos = (TextView)root.findViewById(R.id.tv_hypos);
        tvHypers = (TextView)root.findViewById(R.id.tv_hypers);
        rvRecords = (RecyclerView)root.findViewById(R.id.rv_records);

        return root;
    }

    /**
     * onViewCreated() method
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
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

        System.out.println("====== Selected: " + selected + " =======");
        displayEntries();
    }

    public void displayEntries()
    {
        List<BloodSugarEntry> entries = getEntries();

        tvAverage.setText(String.valueOf(Math.round(getAverage(entries) * 10)/ 10.0));
        tvHypos.setText(String.valueOf(getHypos(entries)));
        tvHypers.setText(String.valueOf(getHypers(entries)));

        adapter = new RecordsRecyclerViewAdapter(this,entries);
        rvRecords.setAdapter(adapter);
        rvRecords.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }

    public List<BloodSugarEntry> getEntries()
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
