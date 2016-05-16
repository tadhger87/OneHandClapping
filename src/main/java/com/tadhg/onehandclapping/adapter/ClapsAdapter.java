package com.tadhg.onehandclapping.adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.model.Clap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tadhg on 09/03/2016.
 */
public class ClapsAdapter extends RecyclerView.Adapter<ClapsAdapter.ViewHolder>{

    RecyclerView recyclerview;
    List<Clap> mItems;
    private Context context;
    ItemTouchListener itlistener;
    private static final String TAG = "Fiddlesticks";
    private boolean mIsScrolling;


   /* @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }*/

    public interface ItemTouchListener {
        boolean onInterceptTouchEvent(List<Clap> list, View view, int position, MotionEvent me); //RecyclerView rv,
        void onTouchEvent(List<Clap> list, View view, int position, MotionEvent me);
        void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }





    public ClapsAdapter(Context mContext, List<Clap> myClap) { //ItemTouchListener itl
        super();
        this.mItems = myClap;
        this.context = mContext;
        setHasStableIds(true);


        // AssetFileDescriptor afd;
        // afd = view.getContext().getAssets().openFd("applause-01.mp3");
        mItems = new ArrayList<Clap>();
        Clap mClaps = new Clap();
        mClaps.setCName("Hearty Clap");
        mClaps.setImageId(R.drawable.clapping1);
        mClaps.setAudio("applause-01.mp3");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Business Clap");
        mClaps.setImageId(R.drawable.clapping2);
        mClaps.setAudio("fake_applause.mp3");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Green Clap");
        mClaps.setImageId(R.drawable.clap3);
        mClaps.setAudio("laugh_and_applause.mp3");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Slow Clap");
        mClaps.setImageId(R.mipmap.slow_clap);
        mClaps.setAudio("fake_applause.mp3");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Emoji Clap");
        mClaps.setImageId(R.mipmap.clap_emoji);
        mClaps.setAudio("light_applause.mp3");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Slow Clap");
        mClaps.setImageId(R.mipmap.slow_clap);
        mClaps.setAudio("laughter-1.wav");
        mItems.add(mClaps);

        mClaps = new Clap();
        mClaps.setCName("Emoji Clap");
        mClaps.setImageId(R.mipmap.clap_emoji);
        mClaps.setAudio("laughter-2.mp3");
        mItems.add(mClaps);
        // this.itlistener = itl;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{//implements View.OnTouchListener{


        public  TextView title;
        public ImageView imageView;
        private ItemTouchListener touchListener;
        //  private RecyclerView.OnItemTouchListener touchListener;
        final MediaPlayer mp = new MediaPlayer();
        private Context mContext;
        List<Clap> mItems;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.description);
            itemView.setTag(itemView);
            itemView.setClickable(true);
            itemView.setOnTouchListener(this);


        }

        public void setTouchListener (ItemTouchListener itemTouchListener){
            this.touchListener = itemTouchListener;
        }


        @Override
        public boolean onTouch( View v, MotionEvent event) {
            if (touchListener != null) {
                Log.d(TAG, "Touch");
                touchListener.onInterceptTouchEvent(mItems, v, getAdapterPosition(), event);
                touchListener.onTouchEvent(mItems, v, getAdapterPosition(), event);
            } return true;
        }



    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //Inflate the layout, initialize the View Holder

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clap, parent, false);


        return new ViewHolder(v);

    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MediaPlayer mp = new MediaPlayer();
        Clap clap = mItems.get(position);
        holder.title.setText(mItems.get(position).getCName());
        holder.imageView.setImageResource(mItems.get(position).getImageId());




        holder.setTouchListener(new ItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(List<Clap> list, View view, int position, MotionEvent me) {

                Log.d(TAG, "Intercept");
/* View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && this != null && mGestureDetector.onTouchEvent(e)) {
                    this.onTouchEvent(childView, rv.getChildAdapterPosition(childView), e, rv);
                }*//*


                 int mTouchSlop;



                ViewConfiguration vc = ViewConfiguration.get(v.getContext());
                mTouchSlop = vc.getScaledTouchSlop();


                float mLastX = 0, mStartX, mLastY = 0, mStartY = 0;
                boolean mIsBeingDragged;
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = e.getX();
                        mLastY = e.getY();
                        mStartY = mLastY;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mIsBeingDragged = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = e.getX();
                        float y = e.getY();
                        float xDelta = Math.abs(x - mLastX);
                        float yDelta = Math.abs(y - mLastY);

                        float yDeltaTotal = y - mStartY;
                        if (yDelta > xDelta && Math.abs(yDeltaTotal) > mTouchSlop) {
                            mIsBeingDragged = true;
                            mStartY = y;
                            return true;
                        }
                        break;
                }
*/

                return false;


            }

            @Override
            public void onTouchEvent(List<Clap> list, View view, int position, MotionEvent me) {
                // Toast.makeText(context, "TOUCH ME!!!",
                //       Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        //return items.size();
        return (null != mItems ? mItems.size() : 0);
    }

    public void insert(int position, Clap clap) {
        mItems.add(position, clap);
        notifyItemInserted(position);
    }


    public void remove(Clap clap) {
        int position = mItems.indexOf(clap);
        mItems.remove(position);
        notifyItemRemoved(position);
    }



    /*switch (me.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                        }

                        try {
                            mp.reset();
                            AssetFileDescriptor afd;
                            afd = view.getContext().getAssets().openFd(mItems.get(position).getAudio());
                            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                            mp.prepare();

                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.setLooping(true);

                        mp.start();
                    }

                    break;
                    case MotionEvent.ACTION_UP: {
                        mp.pause();
                        *//*mp.stop();
                        mp.release();*//*
                        Toast.makeText(context, mItems.get(position).getCName(),
                                Toast.LENGTH_SHORT).show();

                    }
                    break;

                }*/

}