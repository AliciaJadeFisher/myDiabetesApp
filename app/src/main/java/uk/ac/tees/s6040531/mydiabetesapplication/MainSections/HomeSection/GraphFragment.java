package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class GraphFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * GraphFragment constructor
     * @param index
     * @return fragment
     */
    public static GraphFragment newInstance(int index)
    {
        GraphFragment fragment = new GraphFragment();
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
        View root = inflater.inflate(R.layout.fragment_graph, container, false);
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
    }
}
