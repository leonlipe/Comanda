package redleon.net.comanda.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
import redleon.net.comanda.model.ComandasResult;
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
public class ComandasFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_SERVICE_ID = "serviceid";
    private SwipeRefreshLayout swipeLayout;

    private ComandasListAdapter adapter;
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

        adapter = new ComandasListAdapter(getActivity());
        setListAdapter(adapter);

        ComandasListLoader comandasListLoader = new ComandasListLoader(adapter);

        comandasListLoader.setServiceId(serviceId);
        comandasListLoader.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comandas, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_comandas_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    @Override public void onRefresh() {
      //  new Handler().postDelayed(new Runnable() {
         //   @Override public void run() {
                ComandasListLoader comandasListLoader = new ComandasListLoader(adapter);

                comandasListLoader.setServiceId(serviceId);
                comandasListLoader.execute();
                swipeLayout.setRefreshing(false);
         //   }
        //}, 1000);
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

        final ComandasFragment me = this;

        final ComandasResult dr = (ComandasResult) getListAdapter().getItem(pos);

        HttpClient.get("/services/get_history/" + dr.getId(), null, new JsonHttpResponseHandler() {
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
                        intent.putExtra(ComandHistoryActivity.SERVICE_ID, sa.getServiceId());
                        intent.putExtra(ComandHistoryActivity.DINER_ID, dr.getId());
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me.getActivity(), "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getActivity().getBaseContext());


        //Toast.makeText(getActivity(), "Item " + pos + " was clicked", Toast.LENGTH_SHORT).show();
    }

}
