package redleon.net.comanda.adapters;

/**
 * Created by leon on 15/05/15.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.fragments.DinersFragment;
import redleon.net.comanda.fragments.InvoicesFragment;
import redleon.net.comanda.fragments.PaymentsFragment;

public class ServicesTabsAdapter extends FragmentPagerAdapter{

    public ServicesTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DinersFragment();
            case 1:
                return new ComandasFragment();
            case 2:
                return new PaymentsFragment();
            case 3:
                return new InvoicesFragment();
        }


        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }


}
