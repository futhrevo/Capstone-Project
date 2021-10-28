package in.hedera.reku.capstone.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import in.hedera.reku.capstone.R;
import in.hedera.reku.capstone.bills.BillsAdapter;

/**
 * Created by rakeshkalyankar on 06/02/17.
 */

public class ItemTouchHelperSimpleCallback extends ItemTouchHelper.SimpleCallback{
    private Paint p = new Paint();
    private final RecyclerView  recyclerView;
    private final Context context;
    private Drawable archiveIcon;
    private Drawable shareIcon;
    private boolean initiated;
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * @param recyclerView
     * @param context
     */
    public ItemTouchHelperSimpleCallback(RecyclerView recyclerView, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.recyclerView = recyclerView;
        this.context = context;
    }

    private void init() {
        archiveIcon = ContextCompat.getDrawable(context, R.drawable.ic_archive);
        shareIcon = ContextCompat.getDrawable(context, R.drawable.ic_share_variant);
        initiated = true;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT){
            Toast.makeText(context, "Left Swipe", Toast.LENGTH_SHORT).show();
            BillsAdapter billsAdapter = (BillsAdapter) recyclerView.getAdapter();
            billsAdapter.pendingRemoval(position);
        } else {
            Toast.makeText(context, "Right Swipe", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Bitmap icon;
        if (!initiated) {
            init();
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if(dX > 0){
                p.setColor(Color.parseColor("#388E3C"));
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                c.drawRect(background,p);
//                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_mode_edit_white_24dp);
//                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                c.drawBitmap(icon,null,icon_dest,p);
                shareIcon.setBounds(itemView.getLeft() + (int) width , itemView.getTop() + (int) width, itemView.getLeft()+  2*(int)width,itemView.getBottom() - (int) width);
                shareIcon.draw(c);
                final float alpha = (float) (1.0 - Math.abs(dX) / (float) viewHolder.itemView.getWidth());
                itemView.setAlpha(alpha);
            } else if(dX < 0){
                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background,p);
//                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_white_24dp);
//
//                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
//                c.drawBitmap(icon,null,icon_dest,p);
                archiveIcon.setBounds( itemView.getRight() - 2*(int)width ,itemView.getTop() + (int)width, itemView.getRight() - (int)width,itemView.getBottom() - (int)width);
                archiveIcon.draw(c);
                final float alpha = (float) (1.0 - Math.abs(dX) / (float) viewHolder.itemView.getWidth());
                itemView.setAlpha(alpha);

            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
