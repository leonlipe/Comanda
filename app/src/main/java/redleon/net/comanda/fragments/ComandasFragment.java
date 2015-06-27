package redleon.net.comanda.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.ComandHistoryActivity;
import redleon.net.comanda.activities.MenuActivity;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.adapters.ComandasListAdapter;
import redleon.net.comanda.loaders.ComandasListLoader;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.network.HttpClient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComandasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComandasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComandasFragment extends ListFragment {
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
     * @return A new instance of fragment ComandasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComandasFragment newInstance(Integer param1) {
        ComandasFragment fragment = new ComandasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SERVICE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ComandasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serviceId = getArguments().getInt(ARG_SERVICE_ID);
        }
        System.out.println("ComandasFragment:"+serviceId);

        ComandasListAdapter adapter = new ComandasListAdapter(getActivity());
        setListAdapter(adapter);

        ComandasListLoader ComandasListLoader = new ComandasListLoader(adapter);

        ComandasListLoader.setServiceId(serviceId);
        ComandasListLoader.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comandas, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onComandasFragmentInteraction(string);
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
        public void onComandasFragmentInteraction(String string);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        final DinersResult dr = (DinersResult) getListAdapter().getItem(pos);

        HttpClient.get("services/get_history/" + dr.getId(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    // Do something with the response
                    //System.out.println(response.getJSONObject("service").getInt("id"));
                    if (sResponse.equals("ok")) {
                        ServicesActivity sa = (ServicesActivity) getActivity();
                        Intent intent = new Intent(getActivity(), ComandHistoryActivity.class);
                        intent.putExtra(MenuActivity.SERVICE_ID, sa.getServiceId());
                        intent.putExtra(MenuActivity.DINER_ID, dr.getId());
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


            }
        });


        //Toast.makeText(getActivity(), "Item " + pos + " was clicked", Toast.LENGTH_SHORT).show();
    }

}
