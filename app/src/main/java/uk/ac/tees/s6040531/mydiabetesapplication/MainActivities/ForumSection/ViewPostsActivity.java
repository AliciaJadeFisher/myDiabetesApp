package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumSection;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.PostRecyclerViewAdapter;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.LoginActivity;

public class ViewPostsActivity extends AppCompatActivity
{
    //Variables used for layout access
    TextView threadName, threadDesc;
    RecyclerView postRecycler;
    FloatingActionButton fabNewPost;

    User user;
    ForumThread thread;
    List<ThreadPost> adapterList = new ArrayList<>();

    //Variable used for the recyclerView adapter
    PostRecyclerViewAdapter adapter;

    //Variable used for database access
    FirebaseFirestore postDbRef;
    FirebaseFirestore udbRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        postDbRef = FirebaseFirestore.getInstance();
        fabNewPost = (FloatingActionButton)findViewById(R.id.fab_add_post);
        threadName = (TextView)findViewById(R.id.tv_title);
        threadDesc = (TextView)findViewById(R.id.tv_desc);
        postRecycler = (RecyclerView)findViewById(R.id.recyclerViewPosts);

        getIncomingIntent();
        getUser();
        getPosts();

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
                //Passes the user and thread to the CreatePostActivity and loads it up
                Intent i = new Intent(ViewPostsActivity.this, CreatePostActivity.class);
                i.putExtra("user", user);
                i.putExtra("thread", thread);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }

    public void getUser()
    {
        udbRef.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                user =  documentSnapshot.toObject(User.class);
            }
        });
    }

    public void getPosts()
    {
        postDbRef.collection("thread_posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        ThreadPost  p = document.toObject(ThreadPost.class);
                        p.setPostID(document.getId());

                        //Checks if the current post belongs to the current thread
                        if(p.getThreadID().equals(thread.getThreadID()))
                        {
                            System.out.println("================================== " + p.getThreadID() + " ===================================");
                            adapterList.add(p);
                            System.out.println("================================== List Size : " + adapterList.size() + " ======================");
                        }

                    }
                }

                System.out.println("================================== Final List Size : " + adapterList.size() + " ======================");

                adapterList.sort(new Comparator<ThreadPost>()
                {
                    @Override
                    public int compare(ThreadPost o1, ThreadPost o2)
                    {
                        return o1.getPostDate().compareTo(o2.getPostDate());
                    }
                });
                //Passes the current activity, thread list, project and user to the adapter
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
            threadName.setText(thread.getTitle());
            threadDesc.setText(thread.getDesc());
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ViewPostsActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
