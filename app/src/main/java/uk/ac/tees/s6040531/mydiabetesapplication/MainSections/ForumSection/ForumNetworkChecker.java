package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

import static uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection.ForumFragment.showForum;

/**
 * ForumNetworkChecker
 */
public class ForumNetworkChecker extends BroadcastReceiver
{
    /**
     * onReceive() method
     * @param context - activity called from
     * @param intent - intent passed
     */
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            // Checks if there is a connection and passes the result back to the fragment
            if(hasConnection(context))
            {
                showForum(true);
            }
            else
            {
                showForum(false);
            }
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * hasConnection() method
     * @param context - activity called from
     * @return connection
     */
    private boolean hasConnection(Context context)
    {
        // Gets network information
        try
        {
            ConnectivityManager conMan =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInf = Objects.requireNonNull(conMan).getActiveNetworkInfo();

            // Returns if there is a connection or not
            return(netInf != null && netInf.isConnected());
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
