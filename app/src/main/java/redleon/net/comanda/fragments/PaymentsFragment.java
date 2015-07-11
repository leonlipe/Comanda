package redleon.net.comanda.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.PaymentsListAdapter;
import redleon.net.comanda.loaders.PaymentsListLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentsFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERVICE_ID = "serviceid";

    private Integer serviceId;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PaymentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentsFragment newInstance(Integer param1) {
        PaymentsFragment fragment = new PaymentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SERVICE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public PaymentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serviceId = getArguments().getInt(ARG_SERVICE_ID);
        }
        System.out.println("PaymentsFragment:"+serviceId);

        PaymentsListAdapter adapter = new PaymentsListAdapter(savedInstanceState);
        setListAdapter(adapter);

        PaymentsListLoader PaymentsListLoader = new PaymentsListLoader(adapter);

        PaymentsListLoader.setServiceId(serviceId);
        PaymentsListLoader.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payments, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        //listView.setItemsCanFocus(false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onPaymentsFragmentInteraction(string);
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
        public void onPaymentsFragmentInteraction(String string);
    }

}