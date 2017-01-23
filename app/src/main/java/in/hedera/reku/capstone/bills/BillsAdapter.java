package in.hedera.reku.capstone.bills;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.hedera.reku.capstone.R;

/**
 * Created by rakeshkalyankar on 23/01/17.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsAdapterViewHolder>{

    private Cursor cursor;
    final private Context context;
    final private BillsAdapterOnClickHandler mClickHandler;
    final private View mEmptyView;

    public BillsAdapter(Context context, BillsAdapterOnClickHandler mClickHandler, View mEmptyView) {
        this.context = context;
        this.mClickHandler = mClickHandler;
        this.mEmptyView = mEmptyView;
    }

    @Override
    public BillsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment_bill, parent, false);
            view.setFocusable(true);
            return new BillsAdapterViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(BillsAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String body = cursor.getString(2);
        holder.mbodyView.setText(body);
        holder.mSenderView.setText(cursor.getString(1));
    }

    @Override
    public int getItemCount() {
        if ( null == cursor ) return 0;
        return cursor.getCount();
    }

    public class BillsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  final TextView mbodyView;
        public final TextView mSenderView;

        public BillsAdapterViewHolder(View itemView) {
            super(itemView);
            mbodyView = (TextView) itemView.findViewById(R.id.billbody);
            mSenderView = (TextView) itemView.findViewById(R.id.billsender);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            cursor.moveToPosition(adapterPosition);
            mClickHandler.onClick(cursor.getString(2), this);

        }
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

    public static interface BillsAdapterOnClickHandler {
        void onClick(String body, BillsAdapterViewHolder vh);
    }
}
