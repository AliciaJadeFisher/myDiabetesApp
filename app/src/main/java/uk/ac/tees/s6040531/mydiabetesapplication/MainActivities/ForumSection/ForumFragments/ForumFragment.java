package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.ForumFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.CreateThreadActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.ThreadRecyclerViewAdapter;

public class ForumFragment extends Fragment
{
    RecyclerView threadRecycler;
    Button btnAddThread;

    User user;

    List<ForumThread> adapterList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    ThreadRecyclerViewAdapter adapter;

    //Variable used for database access
    FirebaseFirestore threadDbRef;

    private static final String TAG = "ForumFragment";
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

    @Override
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
        threadRecycler = (RecyclerView)view.findViewById(R.id.recyclerViewThreads);

        btnAddThread.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Loads the HomeActivity
                Intent i = new Intent(getActivity(), CreateThreadActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        setAdapter();
    }

    public List<ForumThread> getThreads()
    {
        final List<ForumThread> threadList = new ArrayList<>();

        threadDbRef = FirebaseFirestore.getInstance();

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
                        threadDbRef.collection("threads").document(t.getThreadID()).update("threadID",document.getId())
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });
                        threadList.add(0,t);
                        adapter.notifyItemInserted(0);
                    }
                }
            }
        });

        return threadList;

    }

    public void setUser(User u)
    {
        user = u;
    }

    public void setAdapter()
    {
        adapterList = getThreads();

        adapter = new ThreadRecyclerViewAdapter(this, adapterList, user);
        threadRecycler.setAdapter(adapter);
        threadRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
    }
}