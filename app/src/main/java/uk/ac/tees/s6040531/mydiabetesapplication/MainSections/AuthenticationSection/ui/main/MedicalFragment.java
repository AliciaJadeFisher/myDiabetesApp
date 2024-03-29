package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * MedicalFragment
 */
public class MedicalFragment  extends Fragment
{
    // Variables for layout access
    private EditText etHypo, etBottom, etTop, etHyper, etDuration, etPrecision, etPortion, etCorrection;
    private ViewPager viewPager;

    // Variables for user data
    private User user, cUser;
    private sendDataTime sd;

    /**
     * MedicalFragment constructor
     * @param u - current user
     * @return fragment
     */
    static MedicalFragment newInstance(User u)
    {
        MedicalFragment fragment = new MedicalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("current", u);
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
            // Gets the current user
            cUser = (User)getArguments().getSerializable("current");
        }
    }

    /**
     * onCreateView() method
     * @param inflater - layout inflater for fragment
     * @param container - view group for fragment
     * @param savedInstanceState - instance bundle
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_medical, container, false);
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

        // Initializes the widgets
        viewPager = Objects.requireNonNull(getActivity()).findViewById(R.id.view_pager);
        etHypo = view.findViewById(R.id.et_hypo);
        etBottom = view.findViewById(R.id.et_bottom);
        etTop = view.findViewById(R.id.et_top);
        etHyper = view.findViewById(R.id.et_hyper);
        etDuration = view.findViewById(R.id.et_duration);
        etPrecision = view.findViewById(R.id.et_precision);
        etPortion = view.findViewById(R.id.et_carb_portion);
        etCorrection = view.findViewById(R.id.et_correction);
        Button btnBack = view.findViewById(R.id.btn_go_back);
        Button btnNext = view.findViewById(R.id.btn_next);

        // Checks if there is a current user
        if(cUser != null)
        {
            // Call setUpDetails()
            setUpDetails();
        }

        // OnClickListener for btnBack
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() method for btnBack
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Returns to the previous tab
                viewPager.setCurrentItem(0);
            }
        });

        // OnClickListener for btnNext
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnNext
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Sets some of the user object's attributes
                user.setHypo(etHypo.getText().toString());
                user.setBottom(etBottom.getText().toString());
                user.setTop(etTop.getText().toString());
                user.setHyper(etHyper.getText().toString());
                user.setDuration(etDuration.getText().toString());
                user.setPrecision(etPrecision.getText().toString());
                user.setPortion(etPortion.getText().toString());
                user.setCorrection(etCorrection.getText().toString());

                // Passes the data to the next tab and then shows the next tab
                sd.sendDataToTime(user);
                viewPager.setCurrentItem(2);
            }
        });
    }

    /**
     * setUpDetails() method : displays the user's details to edit
     */
    private void setUpDetails()
    {
        // Displays the medical details
        etHypo.setText(cUser.getHypo());
        etBottom.setText(cUser.getBottom());
        etTop.setText(cUser.getTop());
        etHyper.setText(cUser.getHyper());
        etDuration.setText(cUser.getDuration());
        etPrecision.setText(cUser.getPrecision());
        etPortion.setText(cUser.getPortion());
        etCorrection.setText(cUser.getCorrection());
    }

    /**
     * Interface for SendMessage
     */
    public interface sendDataTime
    {
        void sendDataToTime(User user);
    }

    /**
     * Called when fragment is first attached to its context
     * @param context - activity fragment called from
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            sd = (sendDataTime) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
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
