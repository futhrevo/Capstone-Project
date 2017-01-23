package in.hedera.reku.capstone.otp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.hedera.reku.capstone.R;

/**
 * Created by rakeshkalyankar on 21/01/17.
 */

public class OTPAdapter extends RecyclerView.Adapter<OTPAdapter.OTPAdapterViewHolder>{

    private Cursor cursor;
    final private Context context;

    public OTPAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OTPAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment_otp_item, parent, false);
            view.setFocusable(true);
            return new OTPAdapterViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }

    }

    @Override
    public void onBindViewHolder(OTPAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String body = cursor.getString(2);
        holder.mbodyView.setText(body);
    }

    @Override
    public int getItemCount() {
        if ( null == cursor ) return 0;
        return cursor.getCount();
    }

    public class OTPAdapterViewHolder extends RecyclerView.ViewHolder {
        public  final TextView mbodyView;

        public OTPAdapterViewHolder(View itemView) {
            super(itemView);
            mbodyView = (TextView) itemView.findViewById(R.id.otpbody);
        }
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

}
