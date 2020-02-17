package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class MedicalFragment  extends Fragment
{
    sendDataTime sd;
    EditText etHypo, etBottom, etTop, etHyper, etDuration, etPrecision, etPortion, etCorrection;
    Button btnBack,btnNext;
    ViewPager viewPager;
    User user;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static MedicalFragment newInstance(int index)
    {
        MedicalFragment fragment = new MedicalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_medical, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager)getActivity().findViewById(R.id.view_pager);
        etHypo = (EditText)view.findViewById(R.id.et_hypo);
        etBottom = (EditText)view.findViewById(R.id.et_bottom);
        etTop = (EditText)view.findViewById(R.id.et_top);
        etHyper = (EditText)view.findViewById(R.id.et_hyper);
        etDuration = (EditText)view.findViewById(R.id.et_duration);
        etPrecision = (EditText)view.findViewById(R.id.et_precision);
        etPortion = (EditText)view.findViewById(R.id.et_carb_portion);
        etCorrection = (EditText)view.findViewById(R.id.et_correction);
        btnBack = (Button)view.findViewById(R.id.btn_go_back);
        btnNext = (Button)view.findViewById(R.id.btn_next);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user.setHypo(etHypo.getText().toString());
                user.setBottom(etBottom.getText().toString());
                user.setTop(etTop.getText().toString());
                user.setHyper(etHyper.getText().toString());
                user.setDuration(etDuration.getText().toString());
                user.setPrecision(etPrecision.getText().toString());
                user.setPortion(etPortion.getText().toString());
                user.setCorrection(etCorrection.getText().toString());

                sd.sendDataTime(user);
                viewPager.setCurrentItem(2);
            }
        });
    }

    /**
     * Interface for SendMessage
     */
    public interface sendDataTime
    {
        void sendDataTime(User user);
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
            sd = (sendDataTime) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }

    public void dataReceived(User u)
    {
       user = u;
    }
}
