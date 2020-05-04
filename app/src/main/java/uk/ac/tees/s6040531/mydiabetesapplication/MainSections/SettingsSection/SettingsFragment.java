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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.AccountSetupActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.LoginActivity;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * SettingsFragment
 */
public class SettingsFragment extends Fragment
{
    // Variables for alert dialogs
    private AlertDialog.Builder deleteAccount, checkDelete, changeEmail, changePass;

    // Variables for firebase access
    private FirebaseAuth auth;
    private FirebaseUser cUser;
    private AuthCredential cred;
    private FirebaseFirestore uDbRef;

    // Variable to store user's id
    private String id;

    // Variable for shared preference
    private SharedPreferences myPref;

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
     * @param inflater - layout inflator for fragment
     * @param container - view group for fragment
     * @param savedInstanceState - instance bundle
     * @return root
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Grabs the relevant layout file
        return inflater.inflate(R.layout.fragment_settings, container, false);
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

        // Initialise firebase variables
        auth = FirebaseAuth.getInstance();
        cUser = auth.getCurrentUser();
        uDbRef = FirebaseFirestore.getInstance();

        // Grabs the user's id
        id = auth.getUid();

        // Initialise shared preference
        myPref = Objects.requireNonNull(getActivity()).getSharedPreferences(getResources().getString(R.string.pref_key), Context.MODE_PRIVATE);

        // Variables for layout access
        Button btnEdit = view.findViewById(R.id.btn_edit);
        Button btnChEmail = view.findViewById(R.id.btn_change_email);
        Button btnChPass = view.findViewById(R.id.btn_change_password);
        Button btnDelete = view.findViewById(R.id.btn_delete);
        Button btnAbout = view.findViewById(R.id.btn_about);
        Button btnHelp = view.findViewById(R.id.btn_help);
        Button btnLogOut = view.findViewById(R.id.btn_log_out);

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

        btnChEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail.create().show();
            }
        });

        btnChPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass.create().show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount.create().show();
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

        changeEmail = new AlertDialog.Builder(getActivity());
        changeEmail.setTitle("Change Email");
        final EditText email = new EditText(getActivity());
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.setHint("Email Address");
        changeEmail.setView(email);
        changeEmail.setPositiveButton("Change", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String em = email.getText().toString();
                cUser.updateEmail(em);
            }
        });
        changeEmail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        changePass = new AlertDialog.Builder(getActivity());
        changePass.setTitle("Change Password");
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText oldPass = new EditText(getActivity());
        oldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldPass.setHint("Current Password");
        final EditText newPass = new EditText(getActivity());
        newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPass.setHint("New Password");
        final EditText conPass = new EditText(getActivity());
        conPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        conPass.setHint("Confirm New Password");
        layout.addView(oldPass);
        layout.addView(newPass);
        layout.addView(conPass);
        changePass.setView(layout);
        changePass.setPositiveButton("Change", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(final DialogInterface dialog, int which)
            {
                cred = EmailAuthProvider.getCredential(cUser.getEmail(), oldPass.getText().toString());
                cUser.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(newPass.getText().toString().equals("") || conPass.toString().equals(""))
                            {
                                Toast.makeText(getActivity(),"All fields must be filled in", Toast.LENGTH_SHORT).show();
                            }
                            else if(newPass.getText().toString().equals(conPass.getText().toString()))
                            {
                                cUser.updatePassword(newPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getActivity(),"Password Updated", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(),"Failed to Update Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Passwords do not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        changePass.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        deleteAccount = new AlertDialog.Builder(getActivity());
        deleteAccount.setTitle("Delete Account");
        final EditText dPassword = new EditText(getActivity());
        dPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dPassword.setHint("Password");
        deleteAccount.setView(dPassword);
        deleteAccount.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                cred = EmailAuthProvider.getCredential(cUser.getEmail(), dPassword.getText().toString());
                cUser.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            checkDelete.create().show();
                        }
                    }
                });
            }
        });
        deleteAccount.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             dialog.dismiss();
            }
        });

        checkDelete = new AlertDialog.Builder(getActivity());
        checkDelete.setTitle("Confirm Delete Account");
        final TextView check = new TextView(getActivity());
        check.setText("Are you sure you want to delete your account?");
        checkDelete.setView(check);
        checkDelete.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                uDbRef.collection("users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        cUser.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                SharedPreferences.Editor prefEd = myPref.edit();
                                prefEd.remove(getResources().getString(R.string.user_key));

                                Toast.makeText(getActivity(),"Account Deleted", Toast.LENGTH_SHORT).show();

                                auth.signOut();
                                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                getActivity().finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error deleteing account, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        checkDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
