package in.hedera.reku.capstone.bills;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.hedera.reku.capstone.R;

/**
 * Created by rakeshkalyankar on 23/01/17.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsAdapterViewHolder>{

    private Cursor cursor;
    final private Context context;
    final private BillsAdapterOnClickHandler mClickHandler;
    final private View mEmptyView;

    private List<Integer> itemsPendingRemoval;
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<Integer, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

    public BillsAdapter(Context context, BillsAdapterOnClickHandler mClickHandler, View mEmptyView) {
        this.context = context;
        this.mClickHandler = mClickHandler;
        this.mEmptyView = mEmptyView;
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public BillsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item_row, parent, false);
            view.setFocusable(true);
            return new BillsAdapterViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(BillsAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final int index = cursor.getInt(0);

        if (itemsPendingRemoval.contains(index)) {
            holder.leftSwipeLayout.setVisibility(View.VISIBLE);
            holder.subFragmentBillLayout.setVisibility(View.GONE);
            holder.undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    undoOpt(index);
                }
            });
        } else {
            holder.subFragmentBillLayout.setVisibility(View.VISIBLE);
            holder.leftSwipeLayout.setVisibility(View.GONE);
            String body = cursor.getString(2);
            holder.mbodyView.setText(body);
            holder.mSenderView.setText(cursor.getString(1));
        }
    }

    private void undoOpt(int customer) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(customer);
        pendingRunnables.remove(customer);
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(Integer.valueOf(customer) );
        // this will rebind the row in "normal" state
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if ( null == cursor ) return 0;
        return cursor.getCount();
    }

    public class BillsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  final TextView mbodyView;
        public final TextView mSenderView;
        public final LinearLayout subFragmentBillLayout;
        public final LinearLayout leftSwipeLayout;
        public final TextView undo;

        public BillsAdapterViewHolder(View itemView) {
            super(itemView);
            mbodyView = (TextView) itemView.findViewById(R.id.billbody);
            mSenderView = (TextView) itemView.findViewById(R.id.billsender);
            subFragmentBillLayout = (LinearLayout) itemView.findViewById(R.id.subFragmentBill);
            leftSwipeLayout = (LinearLayout) itemView.findViewById(R.id.leftSwipeLayout);
            undo = (TextView) itemView.findViewById(R.id.undo);
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

    public void pendingRemoval(final int position){
        cursor.moveToPosition(position);
        final int index = cursor.getInt(0);
        if (!itemsPendingRemoval.contains(index)) {
            itemsPendingRemoval.add(index);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(position);
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(index, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        cursor.moveToPosition(position);
        final int index = cursor.getInt(0);

        if (itemsPendingRemoval.contains(index)) {
            itemsPendingRemoval.remove( Integer.valueOf(index) );
        }
        if (true) { //check for condition for removal
            Log.d("BillsAdapter", "remove data from cursor");
//            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        cursor.moveToPosition(position);
        final int index = cursor.getInt(0);
        return itemsPendingRemoval.contains(index);
    }

}
