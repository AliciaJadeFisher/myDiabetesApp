package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection.ForumFragments;

import android.content.Intent;
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

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.PostRecyclerViewAdapter;

public class ThreadFragment extends Fragment
{
    //Variables used for layout access
    TextView threadName;
    RecyclerView postRecycler;
    FloatingActionButton fabNewPost;

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
        postDbRef = FirebaseFirestore.getInstance();
        fabNewPost = (FloatingActionButton)view.findViewById(R.id.fab_add_post);

        //onClickListener for newPost
        fabNewPost.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Deals with the button being pressed
             * @param v
             */
            @Override
            public void onClick(View v)
            {
                //Passes the project, user and thread to the CreatePostActivity and loads it up
                Intent i = new Intent(getActivity(), CreatePostActivity.class);
                i.putExtra("user", user);
                i.putExtra("thread", thread);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        postList = getPosts();


        //Passes the current activity, thread list, project and user to the adapter
        adapter = new PostRecyclerViewAdapter(getActivity(), postList, thread, user);
        postRecycler = (RecyclerView)view.findViewById(R.id.recyclerViewThreads);
        postRecycler.setAdapter(adapter);
        postRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.notifyDataSetChanged();
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

    public List<ThreadPost> getPosts()
    {
        final List<ThreadPost> postList = new ArrayList<>();

        postDbRef.collection("threads_posts ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        ThreadPost  p = document.toObject(ThreadPost.class);
                        p.setPostID(document.getId());
                        postList.add(0,p);
                        adapter.notifyItemInserted(0);
                    }
                }
            }
        });

        return postList;

    }


}