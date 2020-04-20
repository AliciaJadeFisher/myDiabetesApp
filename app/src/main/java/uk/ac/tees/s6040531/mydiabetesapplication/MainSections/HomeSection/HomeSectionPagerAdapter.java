package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Context;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

public class HomeSectionPagerAdapter extends FragmentPagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.records, R.string.graph};
    private final Context mContext;
    User user;
    Date today;
    Date week;
    Date month;

    /**
     * Main constructor
     * @param context
     * @param fm
     */
    public HomeSectionPagerAdapter(Context context, FragmentManager fm, User u, Date t, Date w, Date m)
    {
        super(fm);
        mContext = context;
        user = u;
        today = t;
        week = w;
        month = m;
    }

    /**
     * getItem() method
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position)
    {
        // Switches between the fragments, depending on button clicks
        switch (position)
        {
            case 0:
                RecordsFragment records = new RecordsFragment();
                records.dataReceived(user, today, week , month);
                return records;
            case 1:
                GraphFragment graph = new GraphFragment();
                return graph;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount()
    {
        // Show 2 total pages.
        return 2;
    }
}