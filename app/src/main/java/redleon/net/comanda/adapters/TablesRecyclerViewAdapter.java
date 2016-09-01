package redleon.net.comanda.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.model.TablesResult;

/**
 * Created by leon on 24/08/16.
 */
public class TablesRecyclerViewAdapter extends RecyclerView.Adapter<TablesRecyclerViewAdapter.ViewHolder>  {
    private ArrayList<TablesResult> mDataset = new ArrayList<TablesResult>();
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tableName;
        private TextView tableDescription;
        private ImageView tablePicture;
        private CardView cv;

        // each data item is just a string in this case
        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tableName = (TextView)itemView.findViewById(R.id.table_name);
            tableDescription = (TextView)itemView.findViewById(R.id.table_description);
            tablePicture = (ImageView) itemView.findViewById(R.id.table_picture);


        }

        @Override
        public void onClick(View view) {

        }
    }

    public TablesRecyclerViewAdapter(Context context) {
        setmContext(context);

    }

    @Override
    public TablesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_tables_detail_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tableName.setText(mDataset.get(position).getDescription());
        holder.tableDescription.setText(mDataset.get(position).getStatus_desc() + " "+mDataset.get(position).getMesero());
        if (mDataset.get(position).getStatus_desc().equals("Ocupada")){
            holder.tablePicture.setImageResource(R.drawable.dish_and_fork_no);
        }else{
            holder.tablePicture.setImageResource(R.drawable.dish_and_fork_ok);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public void upDateEntries(ArrayList<TablesResult> list) {
        mDataset.clear();
        mDataset.addAll(list);
        notifyDataSetChanged();
    }


}
