package in.hedera.reku.capstone.otp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.hedera.reku.capstone.Main2Activity;
import in.hedera.reku.capstone.R;
import in.hedera.reku.capstone.SorterService;
import in.hedera.reku.capstone.Utils;


public class OtpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;

    // Cursor Adapter
    OTPAdapter adapter;



    public OtpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new OTPAdapter(getActivity());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_otp, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvMsg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(Utils.OTP_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
        ((Main2Activity) getActivity()).getSupportActionBar().setTitle("OTP");
        Intent intent = new Intent(getContext(), SorterService.class);
        getActivity().startService(intent);


    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return Utils.getOTPCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }



}
