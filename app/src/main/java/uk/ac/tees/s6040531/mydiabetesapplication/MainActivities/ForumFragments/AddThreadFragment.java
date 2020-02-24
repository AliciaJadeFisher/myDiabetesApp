package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class AddThreadFragment extends Fragment
{
    //Variables used for layout access
    EditText etTitle, etDescription;
    Button btnCreate, btnDelete;

    //Variables used for project and thread access
    ForumThread thread;
    String tID;

    //Variable used tro store create/edit status
    String status = "New";

    //Variables used for database access
    FirebaseFirestore threadDbRef, postDbRef;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static AddThreadFragment newInstance(int index)
    {
        AddThreadFragment fragment = new AddThreadFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_thread, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
    }
}
