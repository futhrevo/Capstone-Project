package in.hedera.reku.capstone.bills;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.hedera.reku.capstone.Main2Activity;
import in.hedera.reku.capstone.R;
import in.hedera.reku.capstone.Utils;
import in.hedera.reku.capstone.helper.ItemTouchHelperSimpleCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private Paint p = new Paint();
    // Cursor Adapter
    BillsAdapter adapter;

    public BillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bills, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvMsg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        adapter = new BillsAdapter(getActivity(), new BillsAdapter.BillsAdapterOnClickHandler() {
            @Override
            public void onClick(String body, BillsAdapter.BillsAdapterViewHolder vh) {
                        Toast.makeText(getActivity(), body, Toast.LENGTH_SHORT).show();
                mPosition = vh.getAdapterPosition();
            }
        }, null);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        initSwipe();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(Utils.BILLS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
        ((Main2Activity) getActivity()).getSupportActionBar().setTitle("Bills");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return Utils.getBillsCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelperSimpleCallback(recyclerView, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
