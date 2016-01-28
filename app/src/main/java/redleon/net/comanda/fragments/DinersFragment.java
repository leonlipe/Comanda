package redleon.net.comanda.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.MenuActivity;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.adapters.DinersListAdapter;
import redleon.net.comanda.loaders.DinersListLoader;
import redleon.net.comanda.model.DinersResult;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DinersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DinersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DinersFragment extends ListFragment  implements SwipeRefreshLayout.OnRefreshListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERVICE_ID = "serviceid";
    private static final String ARG_WINDOW_TITLE = "windowtitle";

    private Integer serviceId;
    private String windowTitle;
    private SwipeRefreshLayout swipeLayout;

    private DinersListAdapter adapter;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DinersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DinersFragment newInstance(Integer param1, String param2) {
        DinersFragment fragment = new DinersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SERVICE_ID, param1);
        args.putString(ARG_WINDOW_TITLE, param2);
        fragment.setArguments(args);
        //fragment.setTag


        return fragment;
    }

    public DinersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setServiceId(getArguments().getInt(ARG_SERVICE_ID));
            setWindowTitle(getArguments().getString(ARG_WINDOW_TITLE));
        }
        System.out.println("DinersFragment:"+ getServiceId());

         adapter = new DinersListAdapter(getActivity(), this);
        setListAdapter(adapter);

        DinersListLoader dinersListLoader = new DinersListLoader(adapter);

        dinersListLoader.setServiceId(getServiceId());
        dinersListLoader.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diners, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_diners_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onDinersFragmentInteraction(string);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        DinersListLoader dinersListLoader = new DinersListLoader(adapter);

        dinersListLoader.setServiceId(getServiceId());
        dinersListLoader.execute();
        swipeLayout.setRefreshing(false);
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDinersFragmentInteraction(String string);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        DinersResult dr = (DinersResult) getListAdapter().getItem(pos);
        if (dr.getStatus() == 0) {
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            intent.putExtra(MenuActivity.SERVICE_ID, getServiceId());
            intent.putExtra(MenuActivity.DINER_ID, dr.getId());
            intent.putExtra(MenuActivity.TABLE_DESC, getWindowTitle());
            startActivity(intent);
        }else{
            Toast.makeText(this.getActivity(), "El cliente ya cerró su servicio." , Toast.LENGTH_LONG).show();

        }
        //Toast.makeText(getActivity(), "Item " + pos + " was clicked", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart(){
        super.onStart();

         System.out.println(">>>>>>>>>>>>>>>>>OnStart Diners Fragmnent");

    }

    @Override
    public void onResume(){
        super.onResume();
        onRefresh();
        System.out.println(">>>>>>>>>>>>>>>>>>OnResume Diners Fragmnent");




    }




}
