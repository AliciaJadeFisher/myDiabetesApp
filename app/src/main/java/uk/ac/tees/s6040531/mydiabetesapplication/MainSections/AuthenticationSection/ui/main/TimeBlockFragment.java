package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.TimeBlock;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.TimeBlockRecyclerViewAdapter;

/**
 * TimeBlockFragment
 */
public class TimeBlockFragment extends Fragment
{
    // Variables for layout access
    private EditText etStart, etEnd, etRatio;
    private RecyclerView rvTime;
    private ViewPager viewPager;

    // Variable for recyclerView adapter
    private TimeBlockRecyclerViewAdapter adapter;

    // Variables for user data
    private User user,cUser;
    private List<TimeBlock> time_blocks = new ArrayList<>();

    // Variables for firebase access
    private FirebaseFirestore udbRef;
    private FirebaseAuth auth;

    /**
     * TimeBlockFragment constructor
     * @param u - current user
     * @return fragment
     */
    static TimeBlockFragment newInstance(User u)
    {
        TimeBlockFragment fragment = new TimeBlockFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("current",u);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Checks for any arguments
        if (getArguments() != null)
        {
            // Grabs the current user
            cUser = (User)getArguments().getSerializable("current");
        }
    }

    /**
     * onCreateView() method
     * @param inflater - layout inflator for fragment
     * @param container - view group for fargment
     * @param savedInstanceState - instance bundle
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_time_blocks, container, false);
    }

    /**
     * onViewCreated() method
     * @param view - view for fragment
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Initalize firebase variables
        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        // Intializes the widgets
        viewPager = Objects.requireNonNull(getActivity()).findViewById(R.id.view_pager);
        etStart = view.findViewById(R.id.et_start);
        etEnd = view.findViewById(R.id.et_end);
        etRatio = view.findViewById(R.id.et_carb_ratio);
        Button btnAdd = view.findViewById(R.id.btn_add);
        Button btnClear = view.findViewById(R.id.btn_clear);
        Button btnBack = view.findViewById(R.id.btn_back);
        Button btnSave = view.findViewById(R.id.btn_save);
        rvTime = view.findViewById(R.id.rv_time);
        rvTime.setHasFixedSize(true);
        rvTime.setLayoutManager(new LinearLayoutManager(getContext()));

        // Checks if there is a current user
        if(cUser != null)
        {
            // Calls setUpDetails()
            setUpDetails();
        }

        // OnClickListener for btnAdd
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnAdd
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Grabs the user inputs
                String start = etStart.getText().toString();
                String end = etEnd.getText().toString();
                String ratio = etRatio.getText().toString();

                // Creates a new time block and saves it to the list
                TimeBlock tb = new TimeBlock(start, end , ratio);
                time_blocks.add(tb);

                // Passes the list to the adapter and then sets the adapter
                adapter = new TimeBlockRecyclerViewAdapter(getActivity(),time_blocks);
                rvTime.setAdapter(adapter);

                // Clears the text fields
                etStart.setText("");
                etEnd.setText("");
                etRatio.setText("");
            }
        });

        // onClickListener for btnClear
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnClear
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                time_blocks.clear();
                adapter.notifyItemRangeRemoved(0,0);
            }
        });

        // OnClickListener for btnBack
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnBack
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Returns to the previous tab
                viewPager.setCurrentItem(1);
            }
        });

        // OnClickListener for btnSave
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnSave
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Sets the time blocks attribute
                user.setTime_blocks(time_blocks);

                // Saves the user object to the database
                udbRef.document("users/"+ auth.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * onSuccess() method
                     * @param aVoid - method paramaeter
                     */
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        // Cache's the user's details locally
                        SharedPreferences myPref = Objects.requireNonNull(getActivity()).getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefEd = myPref.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        prefEd.putString(getResources().getString(R.string.user_key),json);
                        prefEd.apply();

                        // Informs the user that the save was successful
                        Toast.makeText(getActivity(), "Details saved", Toast.LENGTH_SHORT).show();

                        // Loads the HomeActivity
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        getActivity().startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        getActivity().finish();
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
                        Toast.makeText(getActivity(), "Error saving details, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * setUpDetails() method : displays the user's details to edit
     */
    private void setUpDetails()
    {
        // Displays the time blocks
        time_blocks = cUser.getTime_blocks();

        // Passes the list to the adapter and then sets the adapter
        adapter = new TimeBlockRecyclerViewAdapter(getActivity(),time_blocks);
        rvTime.setAdapter(adapter);
    }

    /**
     * dataReceived() method
     * @param u - current user
     */
    public void dataReceived(User u)
    {
        user = u;
    }
}