package com.tadhg.onehandclapping.adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.model.Clap;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tadhg on 09/03/2016.
 */
public class ClapsAdapter extends RecyclerView.Adapter<ClapsAdapter.ViewHolder>implements View.OnTouchListener {


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public interface ItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e);
        void onTouchEvent(List<Clap> list, View view, int position, MotionEvent me);
        void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    List<Clap> items;
   private Context mContext;
    ItemTouchListener itlistener;

    public ClapsAdapter(List<Clap> myClaps, Context mContext) { //ItemTouchListener itl
        this.items = myClaps;
        this.mContext = mContext;
       // this.itlistener = itl;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {//implements View.OnTouchListener{

        List<Clap> mItems;
        public  TextView title;
        public ImageView imageView;
        private ItemTouchListener touchListener;
        final MediaPlayer mp = new MediaPlayer();
        private Context mContext;

       public ViewHolder(View itemView) {
            super(itemView);

           imageView = (ImageView) itemView.findViewById(R.id.icon);
           title = (TextView) itemView.findViewById(R.id.description);
           itemView.setTag(itemView);
           itemView.setOnTouchListener(this);

       }

        public void setTouchListener (ItemTouchListener itemTouchListener){
            this.touchListener = itemTouchListener;
        }



        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (touchListener != null){
                touchListener.onTouchEvent(mItems, v, getAdapterPosition(), event);
            }

            return false;
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clap, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Clap clap = items.get(position);
        holder.title.setText(items.get(position).getCName());
        holder.imageView.setImageResource(items.get(position).getImageId());

        final MediaPlayer mp = new MediaPlayer();

        holder.setTouchListener(new ItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(List<Clap> list, View view, int position, MotionEvent me) {
                Toast.makeText(mContext, "TOUCH ME!!!",
                        Toast.LENGTH_SHORT).show();
               /* switch (me.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        if (mp.isPlaying()) {
                            mp.stop();
                        }

                        try {
                            mp.reset();
                            AssetFileDescriptor afd;
                            afd = view.getContext().getAssets().openFd("applause-01.mp3");
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
                    }
                    break;

                }*/
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
        return (null != items ? items.size() : 0);
    }

    public void insert(int position, Clap clap) {
        items.add(position, clap);
        notifyItemInserted(position);
    }


    public void remove(Clap clap) {
        int position = items.indexOf(clap);
        items.remove(position);
        notifyItemRemoved(position);
    }



}