package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.DialogActivity;
import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.adapter.ClapsAdapter;
import com.tadhg.onehandclapping.adapter.GridAdapter;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.Clap;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tadhg on 22/09/2015.
 */
public class MessagesFragment extends Fragment  {
    Activity activity;
    private List<ClapItem> myClap;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ClapItem> claps;
    ClapsAdapter clapsAdapter;
    Context context;
    ClapsAdapter.ItemTouchListener itlistener;
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
        List<Clap> clap = fillWithData();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        // recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
      /*  recyclerView.addOnItemTouchListener(ClapsAdapter.ItemTouchListener = new ClapsAdapter.ItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(List<Clap> list, View view, int position, MotionEvent me) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/
        ClapsAdapter adapter = new ClapsAdapter(clap, getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return rootView;
    }



        public List<Clap> fillWithData() {

        List<Clap> data = new ArrayList<>();

        data.add(new Clap("Hearty Clap", R.drawable.clapping1));
        data.add(new Clap("Business Clap", R.drawable.clapping2));
        data.add(new Clap("Green Clap", R.drawable.clap3));
        data.add(new Clap("Slow Clap", R.mipmap.slow_clap));
        data.add(new Clap("Emoji Clap", R.mipmap.clap_emoji));
        data.add(new Clap("Slow Clap", R.mipmap.slow_clap));
        data.add(new Clap("Emoji Clap", R.mipmap.clap_emoji));


        return data;
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

