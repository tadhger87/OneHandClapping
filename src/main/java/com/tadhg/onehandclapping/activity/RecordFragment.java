package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.DialogActivity;
import com.tadhg.onehandclapping.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tadhg on 22/09/2015.
 */
public class RecordFragment extends Fragment implements SaveClapDialog.SaveClapDialogListener, MediaRecorder.OnInfoListener {
//http://stackoverflow.com/questions/23194192/progress-while-recording-audio
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;
    private Button startBtn;
    private Button stopBtn;
    private Button playBtn;
    private Button stopPlayBtn;
    private TextView text;
    private Button saveButton;
    private ImageView there, gone, play, pause;
    private ProgressBar mProgress;
    private LinearLayout buttonsLayout;

    protected boolean hasMicrophone() {
        PackageManager pmanager = getActivity().getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);

        mProgress          =(ProgressBar) rootView.findViewById(R.id.progress_rec);
        text = (TextView) rootView.findViewById(R.id.text1);
        there = (ImageView)rootView.findViewById(R.id.micImage);
        gone = (ImageView)rootView.findViewById(R.id.micImageStop);
        play = (ImageView)rootView.findViewById(R.id.playImage);
        pause = (ImageView)rootView.findViewById(R.id.pauseImage);

        buttonsLayout = (LinearLayout)rootView.findViewById(R.id.linearbuttons);
        // store it to sd card
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/javacodegeeksRecording.3gpp";

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outputFile);




        startBtn = (Button)rootView.findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                there.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.linear_top);

                buttonsLayout.setLayoutParams(params);
                start(v);
            }
        });

        stopBtn = (Button)rootView.findViewById(R.id.stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                mProgress.setVisibility(View.GONE);
                gone.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.linear_top);

                buttonsLayout.setLayoutParams(params);
                stop(v);
            }
        });

        playBtn = (Button)rootView.findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                gone.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.linear_top);

                buttonsLayout.setLayoutParams(params);
                play(v);
            }
        });

        stopPlayBtn = (Button)rootView.findViewById(R.id.stopPlay);
        stopPlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopPlay(v);

                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.linear_top);

        buttonsLayout.setLayoutParams(params);
            }
        });

        saveButton = (Button)rootView.findViewById(R.id.saveb);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showSaveDialog();
            }
        });

        return rootView;
    }

    private void showSaveDialog() {
        FragmentManager fm = getFragmentManager();
        SaveClapDialog saveClapDialog = SaveClapDialog.newInstance("Save Clap");
        saveClapDialog.show(fm, "fragment_save_clap");
    }
  /*  public void save (View view){

        Intent myIntent = new Intent(getActivity(), DialogActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        RecordFragment.this.startActivity(myIntent);
    }
*/
    public void start(View view){
        try {
            myRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener(){
            public void onInfo(MediaRecorder mr, int what, int extra){
                    if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        Log.v("AUDIOCAPTURE", "Maximum Duration Reached");
                        mr.stop();
                    }
                }
            });
            myRecorder.prepare();
            myRecorder.start();
            myRecorder.setMaxDuration(10000); // 10 seconds

        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

        text.setText("One Hand Clapping: Recording");
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);

        Toast.makeText(getActivity(), "Start recording...",
                Toast.LENGTH_SHORT).show();

    }

public void onInfo(MediaRecorder mr, int what, int extra) {
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
        Log.v("AUDIOCAPTURE","Maximum Duration Reached");
        mr.stop();
        }
        }

    public void stop(View view){
        try {
            myRecorder.stop();
            myRecorder.release();
            myRecorder  = null;

            stopBtn.setEnabled(false);
            playBtn.setEnabled(true);
            startBtn.setEnabled(false);



            text.setText(R.string.stop_top_tv);

            Toast.makeText(getActivity(), "Stop recording...",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }

    public void play(View view) {
        try{
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(outputFile);
            myPlayer.prepare();
            myPlayer.start();

            playBtn.setEnabled(false);
            stopPlayBtn.setEnabled(true);
            startBtn.setEnabled(false);
            text.setText(R.string.play_top_tv);

            Toast.makeText(getActivity(), "Start play the recording...",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stopPlay(View view) {
        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;
                startBtn.setEnabled(true);
                playBtn.setEnabled(true);
                stopPlayBtn.setEnabled(false);
                text.setText("Recording Point: Stop playing");

                Toast.makeText(getActivity(), "Stop playing the recording...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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


    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(getActivity(), "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }
}
