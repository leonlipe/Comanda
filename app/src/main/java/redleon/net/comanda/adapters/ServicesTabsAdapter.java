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

    private Integer serviceId;

    public ServicesTabsAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DinersFragment dinersFragment= DinersFragment.newInstance(getServiceId());
                System.out.println(">>>>>>>>");
                System.out.println(dinersFragment);

                return dinersFragment;
            case 1:
                return ComandasFragment.newInstance(getServiceId());
            case 2:
                return PaymentsFragment.newInstance(getServiceId());
            case 3:
                return InvoicesFragment.newInstance(getServiceId());
        }


        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }


    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
