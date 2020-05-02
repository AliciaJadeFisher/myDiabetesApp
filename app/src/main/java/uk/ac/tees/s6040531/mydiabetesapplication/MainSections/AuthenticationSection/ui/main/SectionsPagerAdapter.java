package uk.ac.tees.s6040531.mydiabetesapplication.MainSections.AuthenticationSection.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses.User;
import uk.ac.tees.s6040531.mydiabetesapplication.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    User cUser;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_basic, R.string.tab_medical, R.string.tab_time_blocks};
    private final Context mContext;

    /**
     * Main constructor
     * @param context
     * @param fm
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm, User u)
    {
        super(fm);
        mContext = context;
        cUser = u;
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
                BasicFragment basic = BasicFragment.newInstance(cUser);
                return basic;
            case 1:
                MedicalFragment medical = MedicalFragment.newInstance(cUser);
                return medical;
            case 2:
                TimeBlockFragment time = TimeBlockFragment.newInstance(cUser);
                return time;
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
        // Show 3 total pages.
        return 3;
    }
}