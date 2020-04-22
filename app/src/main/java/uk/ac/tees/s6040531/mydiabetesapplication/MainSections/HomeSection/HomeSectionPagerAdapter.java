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
    private static final int[] TAB_TITLES = new int[]{R.string.today, R.string.week,R.string.month,R.string.all};
    private final Context mContext;

    /**
     * Main constructor
     * @param context
     * @param fm
     */
    public HomeSectionPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    /**
     * getItem() method
     * @param position
     * @return selected fragment
     */
    @Override
    public Fragment getItem(int position)
    {
        // Switches between the fragments, depending on button clicks
        switch (position)
        {
            case 0:
                RecordsFragment today = new RecordsFragment();
                today.dataReceived("Today");
                return today;
            case 1:
                RecordsFragment week = new RecordsFragment();
                week.dataReceived("Week");
                return week;
            case 2:
                RecordsFragment month = new RecordsFragment();
                month.dataReceived("Month");
                return month;
            case 3:
                RecordsFragment all = new RecordsFragment();
                all.dataReceived("All");
                return all;
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
        return 4;
    }
}
