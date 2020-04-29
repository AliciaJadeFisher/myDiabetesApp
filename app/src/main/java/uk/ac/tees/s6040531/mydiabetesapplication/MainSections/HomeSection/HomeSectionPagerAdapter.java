package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.HomeSection;

import android.content.Context;
import android.icu.text.AlphabeticIndex;

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

    RecordsFragment today = RecordsFragment.newInstance("Today");
    RecordsFragment week = RecordsFragment.newInstance("Week");
    RecordsFragment month = RecordsFragment.newInstance("Month");
    RecordsFragment all = RecordsFragment.newInstance("All");

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
                return today;
            case 1:
                return week;
            case 2:
                return month;
            case 3:
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
        // Show 4 total pages.
        return 4;
    }
}
