package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    Button btnAll, btnToday, btnWeek, btnMonth;
    TextView tvDisplay;
    RecyclerView rvRecords;

    RecordsRecyclerViewAdapter adapter;

    User user;
    List<BloodSugarEntry> entries;
    Date today;
    Date week;
    Date month;

    String selected = "All";

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * RecordsFragment constructor
     * @param index
     * @return fragment
     */
    public static RecordsFragment newInstance(int index)
    {
        RecordsFragment fragment = new RecordsFragment();
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        View root = inflater.inflate(R.layout.fragment_records, container, false);
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

        btnAll = (Button)view.findViewById(R.id.btn_all);
        btnToday = (Button)view.findViewById(R.id.btn_today);
        btnWeek = (Button)view.findViewById(R.id.btn_week);
        btnMonth = (Button)view.findViewById(R.id.btn_month);
        tvDisplay = (TextView)view.findViewById(R.id.tv_title);
        rvRecords = (RecyclerView)view.findViewById(R.id.rv_records);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = "All";
                tvDisplay.setText(R.string.all);
                displayEntries();
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = "Today";
                tvDisplay.setText(R.string.today);
                displayEntries();
            }
        });

        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = "Week";
                tvDisplay.setText(R.string.this_week);
                displayEntries();
            }
        });

        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = "Month";
                tvDisplay.setText(R.string.this_month);
                displayEntries();
            }
        });
    }

    /**
     * dataReceived() method
     * @param u
     */
    public void dataReceived(User u, Date t, Date w, Date m)
    {
        user = u;
        entries = user.getBlood_sugars();
        today = t;
        week = w;
        month = m;

        displayEntries();
    }

    public void displayEntries()
    {
        adapter = new RecordsRecyclerViewAdapter(this,entries,selected,today,week,month);
        rvRecords.setAdapter(adapter);
        rvRecords.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }
}
