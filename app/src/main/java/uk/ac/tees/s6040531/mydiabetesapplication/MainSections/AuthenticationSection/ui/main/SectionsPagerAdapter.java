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
    private User cUser;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_basic, R.string.tab_medical, R.string.tab_time_blocks};
    private final Context mContext;

    /**
     * Main constructor
     * @param context - activity called from
     * @param fm - activity fragment manager
     * @param u - current user
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm, User u)
    {
        super(fm);
        mContext = context;
        cUser = u;
    }

    /**
     * getItem() method
     * @param position - position of the selected tab
     * @return relevant tab
     */
    @Override
    public Fragment getItem(int position)
    {
        // Switches between the fragments, depending on button clicks
        switch (position)
        {
            case 0:
                return BasicFragment.newInstance(cUser);
            case 1:
                return MedicalFragment.newInstance(cUser);
            case 2:
                return TimeBlockFragment.newInstance(cUser);
            default:
                return null;
        }
    }

    /**
     * Returns the title of the tab based on the position
     * @param position - position of selected tab
     * @return tab position
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * Returns the number of tabs
     * @return 3
     */
    @Override
    public int getCount()
    {
        // Show 3 total pages.
        return 3;
    }
}