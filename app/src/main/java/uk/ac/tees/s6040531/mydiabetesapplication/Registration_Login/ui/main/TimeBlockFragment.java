package uk.ac.tees.s6040531.mydiabetesapplication.Registration_Login.ui.main;

import androidx.fragment.app.Fragment;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;

public class TimeBlockFragment extends Fragment
{
    User user;

    public void dataReceived(User u)
    {
        user = u;
    }
}
