package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @SuppressLint("StaticFieldLeak")
    private static TextView tvNetCon;
    private static RecyclerView threadRecycler;
    private static FloatingActionButton fabAddThread;

    //Variable used for the recyclerView adapter
    private ThreadRecyclerViewAdapter adapter;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView() method
     * @param inflater - layout inflater for fragment
     * @param container - view group for fragment
     * @param savedInstanceState - isntancce bundle
     * @return root
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    /**
     * onViewCreated() method
     * @param view - view for fragment
     * @param savedInstanceState - instance bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Initializes the widgets
        tvNetCon = view.findViewById(R.id.tv_net_con);
        fabAddThread = view.findViewById(R.id.fab_add_thread);
        threadRecycler = view.findViewById(R.id.recyclerViewThreads);

        // Calls checkCon()
        checkCon();

        // Calls setAdapter()
        setAdapter();

        // onClickListener() for fabAddThread
        fabAddThread.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for fabAddThread
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Loads the CreateThreadActivity
                Intent i = new Intent(getActivity(), CreateThreadActivity.class);
                startActivity(i);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });
    }

    /**
     * checkCon() method
     */
    private void checkCon()
    {
        // Creates and registers the broadcast receiver
        bRec = new ForumNetworkChecker();
        Objects.requireNonNull(getActivity()).registerReceiver(bRec, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * showForum() method
     * @param state - network connection
     */
    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    static void showForum(Boolean state)
    {
        // Checks if there is a network connect
        if(state)
        {
            // Hides the network TextView
            tvNetCon.setText("");
            tvNetCon.setVisibility(View.GONE);

            // Shows the widgets
            fabAddThread.setVisibility(View.VISIBLE);
            threadRecycler.setVisibility(View.VISIBLE);
        }
        else
        {
            // Hides the widgets
            fabAddThread.setVisibility(View.GONE);
            threadRecycler.setVisibility(View.GONE);

            // Shows the network TextView
            tvNetCon.setVisibility(View.VISIBLE);
            tvNetCon.setText("Error connecting to network. Please connect to a network and try again.");
        }
    }

    /**
     * getThreads() method
     * @return threadList
     */
    private List<ForumThread> getThreads()
    {
        // Crates a temporary list
        final List<ForumThread> threadList = new ArrayList<>();

        // Initalizes the database
        //Variable used for database access
        FirebaseFirestore threadDbRef = FirebaseFirestore.getInstance();

        // Grabs all the threads in the database
        threadDbRef.collection("threads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    // Loops through the results
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    {
                        // Saves the curretn thread
                        ForumThread  t = document.toObject(ForumThread.class);

                        // Updates the thread id in the database
                        t.setThreadID(document.getId());

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

    /**
     * onPause() method
     */
    @Override
    public void onPause()
    {
        super.onPause();

        // Unregisters the broadcast receiver
        try
        {
            Objects.requireNonNull(getActivity()).unregisterReceiver(bRec);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * setAdapter() method
     */
    private void setAdapter()
    {
        // Grabs a list of threads
        // Variable for thread list
        List<ForumThread> adapterList = getThreads();

        // Initializes the adapter
        adapter = new ThreadRecyclerViewAdapter(this, adapterList);
        threadRecycler.setAdapter(adapter);
        threadRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }
}
