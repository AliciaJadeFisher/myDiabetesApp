package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Date;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ForumThread;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.ThreadPost;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * CreatePostActivity
 */
public class CreatePostActivity extends AppCompatActivity
{
    //Variables used for layout access
    EditText postContent;
    Button btnCreate, btnDelete;

    //Variables used for thread and post access
    ForumThread thread;
    ThreadPost post;

    //Variables used for Firebase access
    FirebaseFirestore threadDbRef, postDbRef;
    FirebaseAuth auth;
    User user;

    /**
     * onCreate() method
     * @param savedInstanceState - instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Grabs the current user
        SharedPreferences myPref = getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        //Initialises the widgets
        postContent = findViewById(R.id.et_post);
        btnCreate = findViewById(R.id.btn_create);
        btnDelete = findViewById(R.id.btn_delete);

        // Calls getIncomingIntent()
        getIncomingIntent();

        //Retrieves the current user
        auth = FirebaseAuth.getInstance();

        // Intialises database references
        threadDbRef = FirebaseFirestore.getInstance();
        postDbRef = FirebaseFirestore.getInstance();

        // onClickListener for btnCreate
        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnCreate
             * @param v - activity view
             */
            @Override
            public void onClick(View v)
            {
                // Retrieves the user's input
                String p = postContent.getText().toString();

                // Retrieves the new id and the current date, and creates a new ThreadPost
                Date created = new Date();
                post = new ThreadPost(thread.getThreadID(),"update",user.getId(), user.getName(), p, created);

                // Saves the post to the database
                postDbRef.collection("thread_posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        // Increments the number of posts
                        int posts = Integer.parseInt(thread.getPosts());
                        posts ++;
                        thread.setPosts(Integer.toString(posts));

                        // Updates the thread in the database, to reflect the post number change
                        threadDbRef.collection("threads").document(thread.getThreadID()).update("posts",Integer.toString(posts))
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    /**
                                     * onSuccess() method
                                     * @param aVoid - method parameter
                                     */
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        // Informs the user that the post was successful
                                        Toast.makeText(CreatePostActivity.this,"Post created." ,Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener()
                                {
                                    /**
                                     * onFailure() method
                                     * @param e - error
                                     */
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    /**
                     * onFailure() method
                     * @param e - error
                     */
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(CreatePostActivity.this, "Post failed, please try again.",Toast.LENGTH_SHORT).show();
                    }
                });

                // Loads the ViewPostsActivity
                onBackPressed();
            }
        });
    }

    /**
     * Retrieves data sent with the intent in the extra field
     */
    private void getIncomingIntent()
    {
        //Checks if the intent has an extra with the reference thread
        if (this.getIntent().hasExtra("thread")) {
            //Grabs the data in the extra
            thread = (ForumThread) this.getIntent().getSerializableExtra("thread");
        }
    }

    /**
     * onBackPressed() method
     */
    @Override
    public void onBackPressed()
    {
        // Loads the ViewPostsActivity
        Intent i = new Intent(CreatePostActivity.this, ViewPostsActivity.class);
        i.putExtra("thread", thread);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}