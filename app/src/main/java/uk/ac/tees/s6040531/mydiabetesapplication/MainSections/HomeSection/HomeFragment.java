package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.EntrySection.AddEntryActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * HomeFragment
 */
public class HomeFragment extends Fragment
{
    // Variables for layout access
    TextView tvWelcome;
    FloatingActionButton fabAdd;
    FragmentTabHost tabHost;

    // Variable for the current user details
    User user;
    FirebaseAuth auth;
    FirebaseFirestore udbRef;

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

        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        // Initializes the widgets
        tvWelcome = (TextView)view.findViewById(R.id.tv_welcome);
        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add_entry);

        SharedPreferences myPref = getActivity().getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        tvWelcome.setText("Hello " + user.getName());

        setupTabs(view);

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

    public void setupTabs(View view)
    {
        HomeSectionPagerAdapter pagerAdapter = new HomeSectionPagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_records);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setEnabled(false);

        // Initializes the tabLayout
        TabLayout tabs = view.findViewById(R.id.tabs_home);
        tabs.setEnabled(true);
        tabs.setupWithViewPager(viewPager);
    }
}