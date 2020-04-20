package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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
    EditText etStart, etEnd, etRatio;
    RecyclerView rvTime;
    Button btnAdd,btnBack,btnSave;
    ViewPager viewPager;

    // Variable for recyclerView adapter
    TimeBlockRecyclerViewAdapter adapter;

    // Variables for user data
    User user;
    List<TimeBlock> time_blocks = new ArrayList<>();

    // Variables for firebase access
    FirebaseFirestore udbRef;
    FirebaseAuth auth;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * TimeBlockFragment constructor
     * @param index
     * @return fragment
     */
    public static TimeBlockFragment newInstance(int index)
    {
        TimeBlockFragment fragment = new TimeBlockFragment();
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
        View root = inflater.inflate(R.layout.fragment_time_blocks, container, false);
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

        // Initalize firebase variables
        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        // Intializes the widgets
        viewPager = (ViewPager)getActivity().findViewById(R.id.view_pager);
        etStart = (EditText)view.findViewById(R.id.et_start);
        etEnd = (EditText)view.findViewById(R.id.et_end);
        etRatio = (EditText)view.findViewById(R.id.et_carb_ratio);
        btnAdd = (Button)view.findViewById(R.id.btn_add);
        btnBack = (Button)view.findViewById(R.id.btn_back);
        btnSave = (Button)view.findViewById(R.id.btn_save);
        rvTime = (RecyclerView)view.findViewById(R.id.rv_time);
        rvTime.setHasFixedSize(true);
        rvTime.setLayoutManager(new LinearLayoutManager(getContext()));

        // OnClickListener for btnAdd
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnAdd
             * @param v
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

        // OnClickListener for btnBack
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnBack
             * @param v
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
             * @param v
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
                     * @param aVoid
                     */
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        // Informs the user that the save was successful
                        Toast.makeText(getActivity(), "Details saved", Toast.LENGTH_SHORT).show();

                        // Loads the HomeActivity
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.putExtra("user",user);
                        getActivity().startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        getActivity().finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    /**
                     * onFailure() method
                     * @param e
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
     * dataReceived() method
     * @param u
     */
    public void dataReceived(User u)
    {
        user = u;
    }
}