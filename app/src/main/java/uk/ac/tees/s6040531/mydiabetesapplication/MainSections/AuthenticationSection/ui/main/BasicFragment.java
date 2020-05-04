package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * BasicFragment
 */
public class BasicFragment extends Fragment
{
    // Variables for layout access
    private EditText etName;
    private Spinner spnBs, spnCarb;
    private ViewPager viewPager;

    // Variable for firebase access
    private FirebaseAuth auth;

    // Variables for user data
    private User user, cUser;
    private String bs, carb, id;
    private sendDataMedical sd;

    // Varriables for array adapters for spinners
    private ArrayAdapter<CharSequence> bsAdapter;
    private ArrayAdapter<CharSequence> carbAdapter;


    /**
     * BasicFragment constructor
     * @param u - current user
     * @return fragment
     */
    static BasicFragment newInstance(User u)
    {
        BasicFragment fragment = new BasicFragment();
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
     * @param inflater - layout inflater for fragment
     * @param container - view group for fragment
     * @param savedInstanceState - instance bundle for fragment
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    /**
     * onViewCreated() method
     * @param view - fragmetn view
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        /* Initializes the widgets */
        viewPager = Objects.requireNonNull(getActivity()).findViewById(R.id.view_pager);
        etName = view.findViewById(R.id.et_name);
        spnBs = view.findViewById(R.id.spn_bs);
        spnCarb = view.findViewById(R.id.spn_carb);
        Button btnNext = view.findViewById(R.id.btn_next);

        // OnClickListener for btnNext
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick for btnNext
             * @param v - fragment view
             */
            @Override
            public void onClick(View v)
            {
                // Gets the current user's id
                auth = FirebaseAuth.getInstance();
                id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                // Grabs the spinner inputs
                bs = spnBs.getSelectedItem().toString();
                carb = spnCarb.getSelectedItem().toString();

                // Creates a new user object and sets some of the attributes
                user = new User();
                user.setId(id);
                user.setName(etName.getText().toString());
                user.setBs_m(bs);
                user.setCb_m(carb);

                // Passes the data to the next tab and then shows the next tab
                sd.sendDatatoMedical(user);
                viewPager.setCurrentItem(1);
            }
        });

        // ArrayAdapter for spnBs
        bsAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bs_types, android.R.layout.simple_spinner_item);
        bsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBs.setAdapter(bsAdapter);

        // ArrayAdapter for spnCarb
        carbAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.carb_types, android.R.layout.simple_spinner_item);
        carbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCarb.setAdapter(carbAdapter);

        // Checks if there is a current user
        if(cUser != null)
        {
            // Calls setUpDetails()
            setUpDetails();
        }
        else
        {
            // Sets default values
            bs = "mmol/L";
            carb = "g";
        }
    }

    /**
     * setUpDetails() method : displays the user's details to edit
     */
    private void setUpDetails()
    {
        // Displays the name
        etName.setText(cUser.getName());

        // Displays the blood sugar measurement
        int bsPos = bsAdapter.getPosition(cUser.getBs_m());
        spnBs.setSelection(bsPos);
        bs = cUser.getBs_m();

        // Displays the carbs measurement
        int cbPos = carbAdapter.getPosition(cUser.getCb_m());
        spnCarb.setSelection(cbPos);
        carb = cUser.getCb_m();
    }

    /**
     * Interface for SendMessage
     */
    public interface sendDataMedical
    {
        void sendDatatoMedical(User user);
    }

    /**
     * Called when fragment is first attached to its context
     * @param context - context fragment called from
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            sd = (sendDataMedical) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }
}


