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

        // onClickListener for btnEdit
        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnEdit
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Loads up the AccountSetupActivity and passes the previous activity to it
                Intent i = new Intent(getActivity(), AccountSetupActivity.class);
                i.putExtra("previous", "settings");
                Objects.requireNonNull(getActivity()).startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                getActivity().finish();
            }
        });

        // onClickListener for btnChEmail
        btnChEmail.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnChEmail
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                // Shows the changeEmail alert dialog
                changeEmail.create().show();
            }
        });

        // onClickListener for btnChPass
        btnChPass.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnChPass
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                // Shows the changePass alert dialog
                changePass.create().show();
            }
        });

        // onClickListener for btnDelete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            /**
             * onCick() for btnDelete
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                // Shows the deleteAccount alert dialog
                deleteAccount.create().show();
            }
        });

        // onClickListener for btnAbout
        btnAbout.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick for btnAbout
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                // Loads up the SettingsAboutActivity
                Intent i = new Intent(getActivity(), SettingsAboutActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        // onClickListener for btnHelp
        btnHelp.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() for btnHelp
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v) {
                // Loads up the SettingsHelpActivity
                Intent i = new Intent(getActivity(), SettingsHelpActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        // onClickListener for btnLogOut
        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            /**
             * onClick() for btnLogOut
             * @param v - view for fragment
             */
            @Override
            public void onClick(View v)
            {
                // Signs the user out
                auth.signOut();

                // Loads up the LoginActivity
                Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                getActivity().finish();
            }
        });

        // Sets up the changeEmail alert dialog
        changeEmail = new AlertDialog.Builder(getActivity());
        changeEmail.setTitle("Change Email");
        final EditText email = new EditText(getActivity());
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.setHint("Email Address");
        changeEmail.setView(email);

        // setPositiveButton for changeEmail
        changeEmail.setPositiveButton("Change", new DialogInterface.OnClickListener()
        {
            /**
             * onClick() for positive button for changeEmail
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Grabs the entered email and updates the login information
                String em = email.getText().toString();
                cUser.updateEmail(em);
            }
        });

        // setNegativeButton for changeEmail
        changeEmail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * onClick() for negative button for changeEmai
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismisses the dialog
                dialog.dismiss();
            }
        });

        // Sets up the changePass alert dialog
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

        // setPositiveButton for changePass
        changePass.setPositiveButton("Change", new DialogInterface.OnClickListener()
        {
            /**
             * onClick() for positive button for changePass
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(final DialogInterface dialog, int which)
            {
                // Gets the authentication credentials from the users email and entered password
                cred = EmailAuthProvider.getCredential(Objects.requireNonNull(cUser.getEmail()), oldPass.getText().toString());

                // Checks the authentication credentials
                cUser.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    /**
                     * onComplete() method
                     * @param task - results of task
                     */
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        // Checks if the task was successful
                        if(!task.isSuccessful())
                        {
                            // Informs the user that their password was wrong
                            Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Checks if the new password and confirmation passwords are filled in
                            if(newPass.getText().toString().equals("") || conPass.toString().equals(""))
                            {
                                // Informs the user that the fields need to be filled in
                                Toast.makeText(getActivity(),"All fields must be filled in", Toast.LENGTH_SHORT).show();
                            }
                            // Checks if the new password and confirmation passwords match
                            else if(newPass.getText().toString().equals(conPass.getText().toString()))
                            {
                                // Updates the user's password
                                cUser.updatePassword(newPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    /**
                                     * onComplete() method
                                     * @param task - results of task
                                     */
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Checks if the task was successful
                                        if(task.isSuccessful())
                                        {
                                            // Informs the user that the password was updated and dismisses the dialog
                                            Toast.makeText(getActivity(),"Password Updated", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else
                                        {
                                            // Informs the user that the password failed to update
                                            Toast.makeText(getActivity(),"Failed to Update Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                // Informs the user that the entered passwords do not match
                                Toast.makeText(getActivity(),"Passwords do not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        // setNegativeButton for changePass
        changePass.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * onClick() for negative button for changePass
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismisses the dialog
                dialog.dismiss();
            }
        });

        // Sets up the deleteAccount alert dialog
        deleteAccount = new AlertDialog.Builder(getActivity());
        deleteAccount.setTitle("Delete Account");
        final EditText dPassword = new EditText(getActivity());
        dPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dPassword.setHint("Password");
        deleteAccount.setView(dPassword);

        // setPositiveButton for deleteAccount
        deleteAccount.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            /**
             * onClick() for postivie button for deleteAccount
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Gets the authentication credentials from the users email and entered password
                cred = EmailAuthProvider.getCredential(Objects.requireNonNull(cUser.getEmail()), dPassword.getText().toString());

                // Checks the authentication credentials
                cUser.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                    /**
                     * onComplete() method
                     * @param task - results of task
                     */
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        // Checks if the task was successful
                        if(!task.isSuccessful())
                        {
                            // Informs the user that the password was incorrect
                            Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Shows the checkDelete alert dialog
                            checkDelete.create().show();
                        }
                    }
                });
            }
        });

        // setNegativeButton for deleteAccount
        deleteAccount.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * onClick() for negative button for deleteAccount
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismisses the dialog
                dialog.dismiss();
            }
        });

        // Sets up the checkDelete alert dialog
        checkDelete = new AlertDialog.Builder(getActivity());
        checkDelete.setTitle("Confirm Delete Account");
        final TextView check = new TextView(getActivity());
        String text = "Are you sure you want to delete your account?";
        check.setText(text);
        checkDelete.setView(check);

        // setPositiveButton for checkDelete
        checkDelete.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener()
        {
            /**
             * onClick() for positive button for checkDelete
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Removes the user from the database
                uDbRef.collection("users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * onSuccess() method
                     * @param aVoid - method parameter
                     */
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        // Removes the user's authentication details
                        cUser.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            /**
                             * onComplete() method
                             * @param task - results of task
                             */
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                // Removes the user's data from the local cache
                                SharedPreferences.Editor prefEd = myPref.edit();
                                prefEd.remove(getResources().getString(R.string.user_key));
                                prefEd.apply();

                                // Informs the user that the account was deleted
                                Toast.makeText(getActivity(),"Account Deleted", Toast.LENGTH_SHORT).show();

                                // Signs the user out
                                auth.signOut();

                                // Loads up the LoginActivity
                                Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                getActivity().finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    /**
                     * onFailure() method
                     * @param e - error
                     */
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Informs the user that there was an error deleting the account
                        Toast.makeText(getActivity(),"Error deleting account, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // setNegativeButton for checkDelete
        checkDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * onClick() for negative button for checkDelete
             * @param dialog - dialog chosen
             * @param which - button chosen
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismisses the dialog
                dialog.dismiss();
            }
        });
    }
}
