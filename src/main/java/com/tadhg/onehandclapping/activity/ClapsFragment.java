package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.adapter.GridAdapter;

import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

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
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private static final int MY_REQUEST = 1001;
    private Menu menu;
    private boolean isGridView;
    public static final String ARG_ITEM_ID = "employee_list";
    DetailFragment main;

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
        isGridView = true;

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


       // mLayoutManager = new GridLayoutManager(getActivity(), 2);

       // mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Context mContext = getActivity();
        // The number of Columns
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
       // final int columns = getResources().getInteger(R.integer.gallery_columns);
       // mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, columns));
        clapGridAdapter = new GridAdapter(activity, myClap, main);
        mRecyclerView.setAdapter(clapGridAdapter);

        task = new GetClapTask(activity);
        task.execute((Void) null);

        mRecyclerView.setOnClickListener(this);
        setHasOptionsMenu(true);
        return rootView;
    }


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.clap_menu, menu);

    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.clap_menu, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu,inflater);
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle) {
            toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (isGridView) {
            mStaggeredLayoutManager.setSpanCount(1);
            item.setIcon(R.mipmap.ic_action_grid);
            item.setTitle("Show as grid");
            isGridView = false;
        } else {
            mStaggeredLayoutManager.setSpanCount(2);
            item.setIcon(R.mipmap.ic_action_grid);
            item.setTitle("Show as list");
            isGridView = true;
        }
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
                                clapList, main);
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

