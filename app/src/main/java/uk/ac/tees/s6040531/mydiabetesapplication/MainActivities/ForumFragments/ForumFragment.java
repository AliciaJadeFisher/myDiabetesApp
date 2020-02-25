package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.ThreadRecyclerViewAdapter;

public class ForumFragment extends Fragment
{
    RecyclerView threadRecycler;
    Button btnAddThread;

    ThreadFragGo tm;
    AddThreadFragGo atm;
    User u;

    List<ForumThread> adapterList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    ThreadRecyclerViewAdapter adapter;

    //Variable used for database access
    FirebaseFirestore threadDbRef;

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
        threadDbRef = FirebaseFirestore.getInstance();
        btnAddThread = (Button)view.findViewById(R.id.btn_addThread);

        btnAddThread.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                atm.goToAddThreadFragment();
            }
        });

        adapterList = getThreads();


        //Passes the current activity, thread list, project and user to the adapter
        adapter = new ThreadRecyclerViewAdapter(this, adapterList, u, tm);
        threadRecycler = (RecyclerView)view.findViewById(R.id.recyclerViewThreads);
        threadRecycler.setAdapter(adapter);
        threadRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public List<ForumThread> getThreads()
    {
        final List<ForumThread> threadList = new ArrayList<>();

        threadDbRef.collection("threads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        ForumThread  t = document.toObject(ForumThread.class);
                        t.setThreadID(document.getId());
                        threadList.add(t);
                    }
                }
            }
        });

        return threadList;

    }

    public void updateThreads()
    {
        adapterList = getThreads();
        adapter.updateList(adapterList);
    }

    /**
     * Interface for goToThreadFragment
     */
    public interface ThreadFragGo
    {
        void goToThreadFragment(User user, ForumThread thread);
    }

    /**
     * Interface for goToThreadFragment
     */
    public interface AddThreadFragGo
    {
        void goToAddThreadFragment();
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
            atm = (AddThreadFragGo) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }
}