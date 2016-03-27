package com.tadhg.onehandclapping.activity;

/**
 * Created by Tadhg on 15/03/2016.
 */
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.tadhg.onehandclapping.model.Clap;

import java.io.IOException;
import java.util.List;

public class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
       // public void onItemClick(View view, int position);
        void onTouchEvent( View view, int position, MotionEvent me, RecyclerView v);
    }
//List<Clap> list,, int position,
    GestureDetector mGestureDetector;

    public RecyclerItemTouchListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onTouchEvent(childView, view.getChildAdapterPosition(childView), e, view);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {



        }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
