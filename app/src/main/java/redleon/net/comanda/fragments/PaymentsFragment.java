package redleon.net.comanda.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import redleon.net.comanda.R;
import redleon.net.comanda.activities.PaymentActivity;
import redleon.net.comanda.adapters.PaymentsListAdapter;
import redleon.net.comanda.loaders.PaymentsListLoader;
import redleon.net.comanda.model.PaymentsResult;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentsFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERVICE_ID = "serviceid";

    private Integer serviceId;
    private SwipeRefreshLayout swipeLayout;

    private OnFragmentInteractionListener mListener;

    private PaymentsListAdapter adapter;
    private List<PaymentsResult> selectedItems = new ArrayList<PaymentsResult>();
    private BigDecimal total = new BigDecimal(0);
    private TextView totalText;

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

        adapter = new PaymentsListAdapter(getActivity());
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
       // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        totalText = (TextView) view.findViewById(R.id.payment_list_total);
        Button btnPagar = (Button) view.findViewById(R.id.btn_pay);
        btnPagar.setOnClickListener(this);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_comandas_swipe_container);
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

    @Override
    public void onRefresh() {
        PaymentsListLoader paymentsListLoader = new PaymentsListLoader(adapter);
        paymentsListLoader.setServiceId(serviceId);
        paymentsListLoader.execute();
        selectedItems.clear();
        totalText.setText("$0.00");
        total = new BigDecimal(0);
        swipeLayout.setRefreshing(false);

    }

    @Override
    public void onClick(View v) {
        if (selectedItems.size() == 0){
            Toast.makeText(getView().getContext(), "Debes de seleccionar al menos una persona.", Toast.LENGTH_LONG).show();
        }else {
            ArrayList<Integer> idsArray = new ArrayList<Integer>();
            for (PaymentsResult paymentsResult : selectedItems) {
                idsArray.add(paymentsResult.getId());
            }

            Log.v("Pay:", new Gson().toJson(idsArray));
            Intent intent = new Intent(v.getContext(), PaymentActivity.class);
            intent.putIntegerArrayListExtra(PaymentActivity.DINERS_ARRAY, idsArray);
            intent.putExtra(PaymentActivity.GRAN_TOTAL, totalText.getText());
            intent.putExtra(PaymentActivity.SERVICE_ID, serviceId);
            startActivity(intent);
        }
    }

   // @Override
//    public boolean onLongClick(View view) {
//        System.out.println("Pasa por el click sostenido");
//        return false;
//    }

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

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        final PaymentsResult item = (PaymentsResult) getListAdapter().getItem(pos);
        if (item.getStatus() == 0) {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item);
                total = total.subtract(item.getTotal());
            } else {
                selectedItems.add(item);
                total = total.add(item.getTotal());
            }
            NumberFormat format = NumberFormat.getCurrencyInstance();
            totalText.setText(format.format(total));
        }
    }


    @Override
    public void onStart(){
        super.onStart();

         System.out.println(">>>>>>>>>>>>>>>>>OnStart Payments Fragment");

    }

    @Override
    public void onResume(){
        super.onResume();
        onRefresh();
        System.out.println(">>>>>>>>>>>>>>>>>>OnResume Payments Fragment");


    }


}