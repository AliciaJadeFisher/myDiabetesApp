package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * BasicFragment
 */
public class BasicFragment extends Fragment
{
    // Variables for layout access
    EditText etName;
    Spinner spnBs, spnCarb;
    Button btnNext;
    ViewPager viewPager;

    // Variable for firebase access
    FirebaseAuth auth;

    // Variables for user data
    User user;
    String bs, carb, id;
    sendDataMedical sd;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * BasicFragment constructor
     * @param index
     * @return fragment
     */
    public static BasicFragment newInstance(int index)
    {
        BasicFragment fragment = new BasicFragment();
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
        View root = inflater.inflate(R.layout.fragment_basic, container, false);
        return root;
    }

    /**
     * onViewCreated() method
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Initializes the widgets
        viewPager = (ViewPager)getActivity().findViewById(R.id.view_pager);
        etName = (EditText)view.findViewById(R.id.et_name);
        spnBs = (Spinner)view.findViewById(R.id.spn_bs);
        spnCarb = (Spinner)view.findViewById(R.id.spn_carb);
        btnNext = (Button)view.findViewById(R.id.btn_next);

        // Sets default values
        bs = "mmol/L";
        carb = "g";

        // OnClickListener for btnNext
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick for btnNext
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Gets the current user's id
                auth = FirebaseAuth.getInstance();
                id = auth.getCurrentUser().getUid();

                // Grabs the spinner inputs
                bs = spnBs.getSelectedItem().toString();
                carb = spnCarb.getSelectedItem().toString();

                // Creates a new user object and sets some of the attributes
                user = new User();
                user.setId(id);
                user.setName(etName.getText().toString());
                user.setBs_m(bs);
                user.setCb_m(carb);

                // Passes the data to the next tab and shows the next tab
                sd.sendDataMedical(user);
                viewPager.setCurrentItem(1);
            }
        });

        // ArrayAdapter for spnBs
        ArrayAdapter<CharSequence> bsAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bs_types, android.R.layout.simple_spinner_item);
        bsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBs.setAdapter(bsAdapter);

        // ArrayAdapter for spnCarb
        ArrayAdapter<CharSequence> carbAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.carb_types, android.R.layout.simple_spinner_item);
        carbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCarb.setAdapter(carbAdapter);
    }

    /**
     * Interface for SendMessage
     */
    public interface sendDataMedical
    {
        void sendDataMedical(User user);
    }

    /**
     * Called when fragment is first attached to its context
     * @param context
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


