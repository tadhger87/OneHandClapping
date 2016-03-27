package com.tadhg.onehandclapping.activity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.model.Clap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tadhg on 24/03/2016.
 */
public class ScrollFragment extends Fragment implements View.OnTouchListener {

    TextView hearty, laughing, lonesome, bApplause;
    Button button;
    MediaPlayer mp = new MediaPlayer();
    Context context;
    List<Clap> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scroll_fragment, container, false);

        findViewsById(rootView);
        setListeners();

        return rootView;

    }

    private void findViewsById(View rootView) {

        hearty = (TextView) rootView.findViewById(R.id.heartyClapTxt);
        lonesome = (TextView) rootView.findViewById(R.id.lonesomeClapTxt);
        laughing = (TextView) rootView.findViewById(R.id.laughClapTxt);
        button = (Button) rootView.findViewById(R.id.buttonclap);

    }

    private void setListeners() {

        hearty.setOnTouchListener(this);
        lonesome.setOnTouchListener(this);
        laughing.setOnTouchListener(this);
        button.setOnTouchListener(this);

    }


    public List<Clap> fillWithData() { //ItemTouchListener itl
        List<Clap> data = new ArrayList<>();


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

        return data;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == hearty) {
            hearty(v, event);
        } else if (v == lonesome) {
            lonesome(v, event);
        }
        return true;
    }

    public boolean hearty (View v, MotionEvent me){
        switch (me.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                }

                try {
                    mp.reset();
                    AssetFileDescriptor afd;
                    //afd = v.getContext().getAssets().openFd(mItems.get(position).getAudio());

                    afd = v.getContext().getAssets().openFd("applause-01.mp3");

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
                Toast.makeText(getActivity(), "Tits",
                        Toast.LENGTH_SHORT).show();

            }
            break;

        }


        return true;

    }

    public boolean lonesome (View v, MotionEvent me){
        switch (me.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                }

                try {
                    mp.reset();
                    AssetFileDescriptor afd;
                    //afd = v.getContext().getAssets().openFd(mItems.get(position).getAudio());

                    afd = v.getContext().getAssets().openFd("fake_applause.mp3");

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
                Toast.makeText(getActivity(), "Tits",
                        Toast.LENGTH_SHORT).show();

            }
            break;

        }


        return true;

    }
}


