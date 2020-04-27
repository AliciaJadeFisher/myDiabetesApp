package uk.ac.tees.s6040531.mydiabetesapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static uk.ac.tees.s6040531.mydiabetesapplication.MainSections.ForumSection.ForumFragment.showState;

public class NetworkChecker extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if(hasConnection(context))
            {
                showState(true);
                System.out.println("====== Network state : connected ======");
            }
            else
            {
                showState(false);
                System.out.println("====== Network state : not connected ======");
            }
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private boolean hasConnection(Context context)
    {
        try
        {
            ConnectivityManager conMan =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInf = conMan.getActiveNetworkInfo();

            return(netInf != null && netInf.isConnected());
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
