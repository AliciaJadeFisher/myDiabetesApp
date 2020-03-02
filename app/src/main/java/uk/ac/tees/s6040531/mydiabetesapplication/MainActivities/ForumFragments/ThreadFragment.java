package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class ThreadFragment extends Fragment
{
    //Variables used for layout access
    TextView threadName;
    RecyclerView recyclerView;
    FloatingActionButton newPost;

    User user;
    ForumThread thread;
    List<ThreadPost> postList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    PostRecyclerViewAdapter adapter;

    //Variable used for database access
    FirebaseFirestore postDbRef;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ThreadFragment newInstance(int index)
    {
        ThreadFragment fragment = new ThreadFragment();
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
        View root = inflater.inflate(R.layout.fragment_thread, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
    }

    public void setDetails(User u, ForumThread t)
    {
        if(u != null)
        {
            user = u;
        }

        if(t != null)
        {
            thread = t;
        }
    }
}