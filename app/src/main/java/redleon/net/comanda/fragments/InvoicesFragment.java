package redleon.net.comanda.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import redleon.net.comanda.R;
import redleon.net.comanda.model.Invoice;
import redleon.net.comanda.adapters.InvoicesListAdapter;
import redleon.net.comanda.loaders.InvoicesListLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InvoicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InvoicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoicesFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERVICE_ID = "serviceid";
    private Integer serviceId;
    private SwipeRefreshLayout swipeLayout;



    private OnFragmentInteractionListener mListener;
    private InvoicesListAdapter invoicesListAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment InvoicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoicesFragment newInstance(Integer param1) {
        InvoicesFragment fragment = new InvoicesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SERVICE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public InvoicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serviceId = getArguments().getInt(ARG_SERVICE_ID);
        }

        invoicesListAdapter = new InvoicesListAdapter(getActivity());

        setListAdapter(invoicesListAdapter);
        InvoicesListLoader invoicesListLoader = new InvoicesListLoader(invoicesListAdapter);
        invoicesListLoader.setServiceId(serviceId);
        invoicesListLoader.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoices, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_invoices_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onInvoicesFragmentInteraction(string);
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
        InvoicesListLoader invoicesListLoader = new InvoicesListLoader(invoicesListAdapter);
        invoicesListLoader.setServiceId(serviceId);
        invoicesListLoader.execute();
        swipeLayout.setRefreshing(false);

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
        public void onInvoicesFragmentInteraction(String string);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        final Invoice item = (Invoice) getListAdapter().getItem(pos);


    }
    @Override
    public void onStart(){
        super.onStart();

         System.out.println(">>>>>>>>>>>>>>>>>OnStart Invoices Fragment");

    }

    @Override
    public void onResume(){
        super.onResume();
        onRefresh();
        System.out.println(">>>>>>>>>>>>>>>>>>OnResume Invoices Fragment");


    }

}
