package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.adapter.ClapsAdapter;
import com.tadhg.onehandclapping.model.Clap;

import java.util.List;


/**
 * Created by Tadhg on 22/09/2015.
 */
public class MessagesFragment extends Fragment  {
//implements RecyclerView.OnItemTouchListener
    private List<Clap> myClap;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    //ArrayList<ClapItem> claps;
    ClapsAdapter clapsAdapter;
   // RecyclerView.Adapter clapsAdapter;
    Context context;
   // ClapsAdapter.ItemTouchListener itlistener;
    GestureDetectorCompat gdc;

    MediaPlayer mp = new MediaPlayer();

    private static final int VERTICAL_ITEM_SPACE = 38;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

       // gdc = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener());


        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        // recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       /* recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());



                if(child!=null && gdc.onTouchEvent(e)) {
                   // Drawer.closeDrawers();
                }return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/
        clapsAdapter = new ClapsAdapter(activity, myClap);
        recyclerView.setAdapter(clapsAdapter);


        return rootView;
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

/*
class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
    RecyclerView recyclerView;
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        int position = recyclerView.getChildAdapterPosition(view);

        // handle single tap

        return super.onSingleTapConfirmed(e);
    }

    public void onLongPress(MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        int position = recyclerView.getChildAdapterPosition(view);

        // handle long press

        super.onLongPress(e);
    }
}*/
