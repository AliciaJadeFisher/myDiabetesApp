package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SettingsFragment extends Fragment
{
    Button btnAbout;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SettingsFragment newInstance(int index)
    {
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        btnAbout = (Button)view.findViewById(R.id.btn_about);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SettingsAboutActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
}
