package redleon.net.comanda.adapters;

/**
 * Created by leon on 15/05/15.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.fragments.DinersFragment;
import redleon.net.comanda.fragments.InvoicesFragment;
import redleon.net.comanda.fragments.PaymentsFragment;

public class ServicesTabsAdapter extends FragmentPagerAdapter {

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private Integer serviceId;
    private String windowTitle;

    public ServicesTabsAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DinersFragment dinersFragment= DinersFragment.newInstance(getServiceId(), getWindowTitle());
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
    public Object instantiateItem(ViewGroup container, int position){
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment){
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }

        return obj;

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

    public Fragment getFragment(int position){
        String tag = mFragmentTags.get(position);
        if (tag == null){
            return null;
        }

        return mFragmentManager.findFragmentByTag(tag);
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }
}
