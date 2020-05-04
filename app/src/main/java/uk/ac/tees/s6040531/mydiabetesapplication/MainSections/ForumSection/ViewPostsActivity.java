package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.PostRecyclerViewAdapter;

/**
 * ViewPostsActivity
 */
public class ViewPostsActivity extends AppCompatActivity
{
    //Variables used for layout access
    TextView threadName, threadDesc;
    RecyclerView postRecycler;
    FloatingActionButton fabNewPost;

    // Variables for user and thread access
    ForumThread thread;
    List<ThreadPost> adapterList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    PostRecyclerViewAdapter adapter;

    //Variable used for Firebase access
    FirebaseFirestore postDbRef;
    FirebaseAuth auth;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        // Initialize Firebase variables
        auth = FirebaseAuth.getInstance();
        postDbRef = FirebaseFirestore.getInstance();

        // Initialize widgets
        fabNewPost = findViewById(R.id.fab_add_post);
        threadName = findViewById(R.id.tv_title);
        threadDesc = findViewById(R.id.tv_desc);
        postRecycler = findViewById(R.id.recyclerViewPosts);

        // Call required methods
        getIncomingIntent();
        getPosts();

        //onClickListener for fabNewPost
        fabNewPost.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for fabNewPost
             * @param v - activity view
             */
            @Override
            public void onClick(View v)
            {
                //Passes the user and thread to the CreatePostActivity and loads it up
                Intent i = new Intent(ViewPostsActivity.this, CreatePostActivity.class);
                i.putExtra("thread", thread);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }

    /**
     * getPosts() method
     */
    public void getPosts()
    {
        // Retrieves the posts from the database
        postDbRef.collection("thread_posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    // Loops through each post
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    {
                        // Grabs the current post
                        ThreadPost  p = document.toObject(ThreadPost.class);

                        // Grabs the post id
                        p.setPostID(document.getId());

                        //Checks if the current post belongs to the current thread
                        if(p.getThreadID().equals(thread.getThreadID()))
                        {
                            // Adds the post to the adapter list
                            adapterList.add(p);
                        }

                    }
                }

                // Sorts the posts by date
                adapterList.sort(new Comparator<ThreadPost>()
                {
                    @Override
                    public int compare(ThreadPost o1, ThreadPost o2)
                    {
                        return o1.getPostDate().compareTo(o2.getPostDate());
                    }
                });

                //Passes the current activity, post list and current thread to the adapter
                adapter = new PostRecyclerViewAdapter(ViewPostsActivity.this, adapterList, thread);
                postRecycler.setAdapter(adapter);
                postRecycler.setHasFixedSize(true);
                postRecycler.setLayoutManager(new LinearLayoutManager(ViewPostsActivity.this));
            }
        });
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    private void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference thread
        if(this.getIntent().hasExtra("thread"))
        {
            //Grabs the data in the extra
            thread = (ForumThread) this.getIntent().getSerializableExtra("thread");

            //Sets the text of the EditText
            threadName.setText(Objects.requireNonNull(thread).getTitle());
            threadDesc.setText(thread.getDesc());
        }
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the HomeActivity
        Intent i = new Intent(ViewPostsActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
