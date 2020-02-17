package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

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
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class MedicalFragment  extends Fragment
{
    SendData sd;
    EditText etHypo, etBottom, etTop, etHyper, etDuration, etPrecision, etPortion, etCorrection;
    Button btnBack,btnNext;
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
        View root = inflater.inflate(R.layout.fragment_basic, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        etHypo = (EditText)view.findViewById(R.id.et_hypo);
        etBottom = (EditText)view.findViewById(R.id.et_bottom);
        etTop = (EditText)view.findViewById(R.id.et_top);
        etHyper = (EditText)view.findViewById(R.id.et_hyper);
        etDuration = (EditText)view.findViewById(R.id.et_duration);
        etPrecision = (EditText)view.findViewById(R.id.et_precision);
        etPortion = (EditText)view.findViewById(R.id.et_carb_portion);
        etCorrection = (EditText)view.findViewById(R.id.et_correction);
        btnBack = (Button)view.findViewById(R.id.btn_back);
        btnNext = (Button)view.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user.setHypo(Double.parseDouble(etHypo.getText().toString()));
                user.setBottom(Double.parseDouble(etBottom.getText().toString()));
                user.setTop(Double.parseDouble(etTop.getText().toString()));
                user.setHyper(Double.parseDouble(etHyper.getText().toString()));
                user.setDuration(Double.parseDouble(etDuration.getText().toString()));
                user.setPrecision(Double.parseDouble(etPrecision.getText().toString()));
                user.setPortion(Integer.parseInt(etPortion.getText().toString()));
                user.setCorrection(Double.parseDouble(etCorrection.getText().toString()));

                sd.sendData1Medical(user);
            }
        });
    }

    /**
     * Interface for SendMessage
     */
    interface SendData
    {
        void sendData1Medical(User user);
    }

    public void dataReceived(User u)
    {
       user = u;
    }
}
