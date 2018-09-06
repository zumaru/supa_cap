package e.user.supa_cap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return tabFragment.newInstance(R.color.colorWhite);
            case 1:
                return tabFragment.newInstance(R.color.colorWhite);
            case 2:
                return tabFragment.newInstance(R.color.colorWhite);

        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "家";
            case 1:
                return "ろーかる";
            case 2:
                return  "れんごー";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
