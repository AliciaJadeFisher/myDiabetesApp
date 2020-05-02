package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.SettingsSection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.AccountSetupActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.LoginActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class SettingsFragment extends Fragment
{
    Button btnEdit, btnDelete, btnAbout, btnHelp, btnLogOut;

    AlertDialog.Builder deleteAccount, checkDelete;

    FirebaseAuth auth;
    FirebaseUser cUser;
    AuthCredential cred;
    FirebaseFirestore uDbRef;

    User user;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SettingsFragment newInstance(int index)
    {
        SettingsFragment fragment = new SettingsFragment();
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
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences myPref = getActivity().getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString(getResources().getString(R.string.user_key),"");
        user = gson.fromJson(json,User.class);

        btnEdit = (Button)view.findViewById(R.id.btn_edit);
        btnDelete = (Button)view.findViewById(R.id.btn_delete);
        btnAbout = (Button)view.findViewById(R.id.btn_about);
        btnHelp = (Button)view.findViewById(R.id.btn_help);
        btnLogOut = (Button)view.findViewById(R.id.btn_log_out);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), AccountSetupActivity.class);
                i.putExtra("previous", "settings");
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount.show();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SettingsAboutActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), SettingsHelpActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                auth.signOut();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                getActivity().finish();
            }
        });

        deleteAccount = new AlertDialog.Builder(getActivity());
        deleteAccount.setTitle("Delete Account");
        final EditText dPassword = new EditText(getActivity());
        dPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        deleteAccount.setView(dPassword);
        deleteAccount.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                cred = EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(), dPassword.getText().toString());
                cUser.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            checkDelete.show();
                        }
                    }
                });
            }
        });
        deleteAccount.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        checkDelete = new AlertDialog.Builder(getActivity());

    }
}
