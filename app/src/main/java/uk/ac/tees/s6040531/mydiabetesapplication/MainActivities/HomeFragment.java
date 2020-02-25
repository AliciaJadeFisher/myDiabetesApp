package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeFragment extends Fragment
{
    TextView tvTitle;
    User user;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static HomeFragment newInstance(int index)
    {
        HomeFragment fragment = new HomeFragment();
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = (TextView)view.findViewById(R.id.text_home);

        tvTitle.setText("Welcome!");
    }

    public void setUser(User u)
    {
        if(u != null)
        {
            user = u;

            tvTitle.setText("Welcome " + user.getName());

            System.out.println("======================== Current user : " + user.getName() + "====================");
        }
    }


}