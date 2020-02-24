package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class ForumFragment extends Fragment
{
    ThreadFragGo tm;
    Button btnAddThread;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ForumFragment newInstance(int index)
    {
        ForumFragment fragment = new ForumFragment();
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
        View root = inflater.inflate(R.layout.fragment_forum, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        btnAddThread = (Button)view.findViewById(R.id.btn_addThread);

        btnAddThread.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tm.goToThreadFragment();
            }
        });
    }

    /**
     * Interface for goToThreadFragment
     */
    public interface ThreadFragGo
    {
        void goToThreadFragment();
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
            tm = (ThreadFragGo) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }
}