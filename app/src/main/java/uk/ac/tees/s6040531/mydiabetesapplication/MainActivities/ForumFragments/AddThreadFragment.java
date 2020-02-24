package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ForumFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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
    ForumFragGo fm;

    //Variable used tro store create/edit status
    String status = "New";

    //Variables used for database access
    FirebaseFirestore threadDbRef, postDbRef;

    private static final String TAG = "AddThreadFragment";
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

        //Initialises the widgets
        etTitle = (EditText)view.findViewById(R.id.et_title);
        etDescription = (EditText)view.findViewById(R.id.et_description);
        btnCreate = (Button)view.findViewById(R.id.btn_create_thread);
        btnDelete = (Button)view.findViewById(R.id.btn_delete_thread);

        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String id = "Update me please!";
                String title = etTitle.getText().toString();
                String desc = etDescription.getText().toString();
                String posts = "0";

                ForumThread newThread = new ForumThread(id, title, desc, posts);

                threadDbRef.collection("threads").add(newThread).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(getActivity(), "Thread created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "========================== Error adding event document =====================", e);
                        Toast.makeText(getActivity(), "Error, details could not be saved", Toast.LENGTH_SHORT).show();
                    }
                });

                fm.goToForumFragment();
            }
        });
    }

    /**
     * Interface for goToForumFragment
     */
    public interface ForumFragGo
    {
        void goToForumFragment();
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
            fm = (ForumFragGo) getActivity();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }
}
