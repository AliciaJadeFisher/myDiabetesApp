package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.HomeActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.TimeBlock;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;
import uk.ac.tees.s6040531.mydiabetesapplication.RecyclerAdapters.TimeBlockRecyclerViewAdapter;
import uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.RegistrationActivity;

public class TimeBlockFragment extends Fragment
{
    EditText etStart, etEnd, etRatio;
    RecyclerView rvTime;
    Button btnAdd,btnBack,btnSave;
    ViewPager viewPager;
    User user;
    TimeBlockRecyclerViewAdapter adapter;
    List<TimeBlock> time_blocks = new ArrayList<>();

    FirebaseFirestore udbRef;
    FirebaseAuth auth;

    private static final String TAG = "TBFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static TimeBlockFragment newInstance(int index)
    {
        TimeBlockFragment fragment = new TimeBlockFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_time_blocks, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        udbRef = FirebaseFirestore.getInstance();

        viewPager = (ViewPager)getActivity().findViewById(R.id.view_pager);
        etStart = (EditText)view.findViewById(R.id.et_start);
        etEnd = (EditText)view.findViewById(R.id.et_end);
        etRatio = (EditText)view.findViewById(R.id.et_carb_ratio);
        btnAdd = (Button)view.findViewById(R.id.btn_add);
        btnBack = (Button)view.findViewById(R.id.btn_back);
        btnSave = (Button)view.findViewById(R.id.btn_save);

        rvTime = (RecyclerView)view.findViewById(R.id.rv_time);
        rvTime.setHasFixedSize(true);
        rvTime.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String start = etStart.getText().toString();
                String end = etEnd.getText().toString();
                String ratio = etRatio.getText().toString();

                TimeBlock tb = new TimeBlock(start, end , ratio);
                time_blocks.add(tb);

                adapter = new TimeBlockRecyclerViewAdapter(getActivity(),time_blocks);
                rvTime.setAdapter(adapter);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user.setTime_blocks(time_blocks);

                udbRef.document("users/"+ auth.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(getActivity(), "Details Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getActivity(), "Error saving details", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(getActivity(), HomeActivity.class);
                i.putExtra("user",user);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                getActivity().finish();

            }
        });
    }

    public void dataReceived(User u)
    {
        user = u;
    }
}
