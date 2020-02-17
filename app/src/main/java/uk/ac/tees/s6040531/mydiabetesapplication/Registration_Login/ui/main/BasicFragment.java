package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class BasicFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    SendData sd;
    EditText etName;
    Spinner spnBs, spnCarb;
    Button btnNext;
    User user;
    FirebaseAuth auth;

    // Variables for user data
    String bs, carb, id;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static BasicFragment newInstance(int index)
    {
        BasicFragment fragment = new BasicFragment();
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

        etName = (EditText)view.findViewById(R.id.et_name);
        spnBs = (Spinner)view.findViewById(R.id.spn_bs);
        spnCarb = (Spinner)view.findViewById(R.id.spn_carb);
        btnNext = (Button)view.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                auth = FirebaseAuth.getInstance();
                id = auth.getCurrentUser().getUid();

                user = new User();
                user.setId(id);
                user.setName(etName.getText().toString());
                user.setBs_m(bs);
                user.setCb_m(carb);

                sd.sendData1Medical(user);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.spn_bs:
                bs = parent.getItemAtPosition(position).toString();
                System.out.println("Blood sugar type : " + bs);
                break;
            case R.id.spn_carb:
                carb = parent.getItemAtPosition(position).toString();
                System.out.println("Carbohydrate type : " + carb);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * Interface for SendMessage
     */
    interface SendData
    {
        void sendData1Medical(User user);
    }
}


