package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection.AddEntryActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * HomeFragment
 */
public class HomeFragment extends Fragment
{
    // Variables for layout access
    TextView tvWelcome, tvDaily, tvWeekly, tvMonthly;
    FloatingActionButton fabAdd;

    // Variable for the current user details
    User user;

    Date today = new Date();
    Calendar cal = Calendar.getInstance();

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Initializes the widgets
        tvWelcome = (TextView)view.findViewById(R.id.tv_welcome);
        tvDaily = (TextView)view.findViewById(R.id.tv_dailyDisplay);
        tvWeekly = (TextView)view.findViewById(R.id.tv_weeklyDisplay);
        tvMonthly = (TextView)view.findViewById(R.id.tv_monthlyDisplay);
        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add_entry);

        // Sets the deafult welcome text
        tvWelcome.setText("Welcome!");
        tvDaily.setText(Double.toString(getDailyAverage()));
        tvWeekly.setText(Double.toString(getWeeklyAverage()));
        tvMonthly.setText(Double.toString(getMonthlyAverage()));

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
    }

    /**
     * setUser() method
     * @param u
     */
    public void setUser(User u)
    {
        // Grabs the passed in user and sets the welcome text
        if(u != null) {
            user = u;
            tvWelcome.setText("Welcome " + user.getName());
        }
    }

    public double getDailyAverage()
    {
        double average = 7.3;
        double total;
        int count;
        System.out.println("======= Today's Date : " + today + "===========");

        return average;
    }

    public double getWeeklyAverage()
    {
        double average = 15.4;
        double total;
        int count;
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date start = cal.getTime();

        System.out.println("======= Week Start : " + start + "===========");

        return average;
    }

    public double getMonthlyAverage()
    {
        double average = 21.8;
        double total;
        int count;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date start = cal.getTime();

        System.out.println("======= Month Start : " + start + "===========");

        return average;
    }
}