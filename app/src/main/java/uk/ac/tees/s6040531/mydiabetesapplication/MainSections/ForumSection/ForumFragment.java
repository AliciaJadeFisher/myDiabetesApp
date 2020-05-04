package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.ThreadRecyclerViewAdapter;

/**
 * ForumFragment
 */
public class ForumFragment extends Fragment
{
    private BroadcastReceiver bRec;

    // Variables for layout access
    static TextView tvNetCon;
    static RecyclerView threadRecycler;
    static FloatingActionButton fabAddThread;

    // Variable for thread list
    List<ForumThread> adapterList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    ThreadRecyclerViewAdapter adapter;

    //Variable used for database access
    FirebaseFirestore threadDbRef;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * ForumFragment constructor
     * @param index
     * @return fragment
     */
    public static ForumFragment newInstance(int index)
    {
        ForumFragment fragment = new ForumFragment();
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

        // Checks for any arguments
        int index = 1;
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
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        View root = inflater.inflate(R.layout.fragment_forum, container, false);
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

        // Initializes the widgets
        tvNetCon = (TextView)view.findViewById(R.id.tv_net_con);
        fabAddThread = (FloatingActionButton) view.findViewById(R.id.fab_add_thread);
        threadRecycler = (RecyclerView)view.findViewById(R.id.recyclerViewThreads);

        // Calls checkCon()
        checkCon();

        // Calls setAdapter()
        setAdapter();

        // onClickListener() for fabAddThread
        fabAddThread.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for fabAddThread
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                // Loads the CreateThreadActivity
                Intent i = new Intent(getActivity(), CreateThreadActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });
    }

    public void checkCon()
    {
        bRec = new ForumNetworkChecker();
        getActivity().registerReceiver(bRec, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static void showForum(Boolean state)
    {
        if(state)
        {
            tvNetCon.setText("");
            tvNetCon.setVisibility(View.GONE);
            fabAddThread.setVisibility(View.VISIBLE);
            threadRecycler.setVisibility(View.VISIBLE);
        }
        else
        {
            fabAddThread.setVisibility(View.GONE);
            threadRecycler.setVisibility(View.GONE);
            tvNetCon.setVisibility(View.VISIBLE);
            tvNetCon.setText("Error connecting to network. Please connect to a network and try again.");

        }
    }

    /**
     * getThreads() method
     * @return threadList
     */
    public List<ForumThread> getThreads()
    {
        // Crates a temporary list
        final List<ForumThread> threadList = new ArrayList<>();

        // Initalizes the database
        threadDbRef = FirebaseFirestore.getInstance();

        // Grabs all the threads in the database
        threadDbRef.collection("threads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    // Loops through the results
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        // Saves the curretn thread
                        ForumThread  t = document.toObject(ForumThread.class);

                        // Updates the thread id in the database
                        t.setThreadID(document.getId());
                        threadDbRef.collection("threads").document(t.getThreadID()).update("threadID",document.getId())
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                        // Adds the current thread to the thread list and notifys the adapter of a data change
                        threadList.add(0,t);
                        adapter.notifyItemChanged(0);
                    }
                }
            }
        });

        // Returns the threadList
        return threadList;
    }

    @Override
    public void onPause()
    {
        super.onPause();

        try
        {
            getActivity().unregisterReceiver(bRec);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * setAdapter() method
     */
    public void setAdapter()
    {
        // Grabs a list of threads
        adapterList = getThreads();

        // Initializes the adapter
        adapter = new ThreadRecyclerViewAdapter(this, adapterList);
        threadRecycler.setAdapter(adapter);
        threadRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }
}
