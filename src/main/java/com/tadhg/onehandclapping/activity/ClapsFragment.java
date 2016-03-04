package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.adapter.GridAdapter;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tadhg on 22/09/2015.
 */
public class ClapsFragment extends Fragment implements View.OnClickListener {


    Activity mActivity, activity;
    private List<ClapItem> myClap;
    private GetClapTask task;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ClapItem> claps;
    GridAdapter clapGridAdapter;
    ClapDAO clapDAO;
    private static final int MY_REQUEST = 1001;

    public static final String ARG_ITEM_ID = "employee_list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        clapDAO = new ClapDAO(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_claps, container, false);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Context mContext = getActivity();
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, columns));
        clapGridAdapter = new GridAdapter(activity, myClap);
        mRecyclerView.setAdapter(clapGridAdapter);

        task = new GetClapTask(activity);
        task.execute((Void) null);

        mRecyclerView.setOnClickListener(this);

        return rootView;
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){

            case Activity.RESULT_OK:

                // ... Check for some data from the intent
                if(requestCode == MY_REQUEST){
                    // .. lets toast again
                    int position = -1;
                    if(data != null){
                        position = data.getIntExtra("Position", 0);
                        ClapItem updatedItem = (ClapItem)data.getExtras().get("passed_item");
                        //updatedItem.get
                        Toast.makeText(getActivity(), "what " + position, Toast.LENGTH_SHORT).show();
                    }

                    if(position != -1) {
                        Toast.makeText(getActivity(), "Handled the result successfully at position " + position, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Failed to get data from intent" , Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case Activity.RESULT_CANCELED:

                // ... Handle this situation
                break;
        }
    }*/
/*
    @Override
    public void onHandleSelection(int position, String text) {

        Toast.makeText(getActivity(), "Selected item in list "+ position + " with text "+ text, Toast.LENGTH_SHORT).show();

        // ... Start a new Activity here and pass the values
        Intent secondActivity = new Intent(MainActivity.this, DetailActivity.class);
        secondActivity.putExtra("Text",text);
        secondActivity.putExtra("Position", position);
        startActivityForResult(secondActivity, MY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ClapItem updatedItem = (ClapItem)data.getExtras().get("passed_item");
            // deal with the item yourself

        }
    }*/
//set up adapter and pass clicked listener this



    @Override
    public void onClick(View v) {

    }

    public class GetClapTask extends AsyncTask<Void, Void, ArrayList<ClapItem>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetClapTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<ClapItem> doInBackground(Void... arg0) {

            ArrayList<ClapItem> clapList = clapDAO.getClaps();

            return clapList;
        }

        @Override
        protected void onPostExecute(ArrayList<ClapItem> clapList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("messages", clapList.toString());
                claps = clapList;
                if (clapList != null) {
                    if (clapList.size() != 0) {
                        clapGridAdapter = new GridAdapter(activity,
                                clapList);
                        mRecyclerView.setAdapter(clapGridAdapter);
                    } else {
                        Toast.makeText(activity, "No Claps",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

