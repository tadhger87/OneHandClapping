package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tadhg on 22/01/2016.
 */
public class RecordFragment extends Fragment implements View.OnClickListener, MediaRecorder.OnInfoListener{
    MediaRecorder recorder;
    MediaPlayer myPlayer;
    File audiofile = null;
    static final String TAG = "MediaRecording";
    Button startButton,stopButton, playButton, stopPlayButton, saveButton, discardButton, dicklips;
    private ImageView mic, gone, play, pause;
    private ProgressBar mProgress;
    private LinearLayout buttonsLayout;
    private TextView text, dialogText = null;

    Context context;

    protected boolean hasMicrophone() {
        PackageManager pmanager = getActivity().getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasMicrophone();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        findViewsById(rootView);
        setListeners();
        saveButton.setVisibility(View.GONE);





        return rootView;
    }

    private void findViewsById(View rootView){
        mProgress =(ProgressBar) rootView.findViewById(R.id.progress_rec);
        startButton = (Button) rootView.findViewById(R.id.start);
        stopButton = (Button) rootView.findViewById(R.id.stop);
        playButton = (Button) rootView.findViewById(R.id.play);
        stopPlayButton = (Button) rootView.findViewById(R.id.stopPlay);
        saveButton = (Button) rootView.findViewById(R.id.saveb);
        //discardButton = (Button) rootView.findViewById(R.id.discard_button);
        text = (TextView) rootView.findViewById(R.id.text1);
        mic = (ImageView)rootView.findViewById(R.id.micImage);
        gone = (ImageView)rootView.findViewById(R.id.micImageStop);
        play = (ImageView)rootView.findViewById(R.id.playImage);
        pause = (ImageView)rootView.findViewById(R.id.pauseImage);
        buttonsLayout = (LinearLayout)rootView.findViewById(R.id.linearbuttons);
        //dicklips = (Button)rootView.findViewById(R.id.transButton);

    }

    private void setListeners() {

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        stopPlayButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
       // dicklips.setOnClickListener(this);
//        discardButton.setOnClickListener(this);
    }

    public void startRecording(View view) throws IOException {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        //Creating file
        File dir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }
        pause.setVisibility(View.GONE);
        mic.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.linear_top);

        buttonsLayout.setLayoutParams(params);
        //Creating MediaRecorder and specifying audio source, output format, encoder & output format
        recorder = new MediaRecorder();

        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (recorder != null && what == 800) {
                    recorder.stop();
                }
            }
        });
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
       // recorder.setMaxDuration(5000);
        recorder.prepare();
        recorder.start();



        Toast.makeText(getActivity(), "Start recording...",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {

    }

    public void stopRecording(View view) {

        mProgress.setVisibility(View.GONE);
        gone.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.linear_top);

        buttonsLayout.setLayoutParams(params);
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
        //stopping recorder
        recorder.stop();
        recorder.reset();

        Toast.makeText(getActivity(), "Stop recording...",
                Toast.LENGTH_SHORT).show();
    }

    public void playAudio(View view){
        gone.setVisibility(View.GONE);
        pause.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.linear_top);
        buttonsLayout.setLayoutParams(params);

        try{
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(String.valueOf(audiofile));
            myPlayer.prepare();
            myPlayer.start();
            myPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {

                    playButton.setEnabled(true);
                    stopPlayButton.setEnabled(false);
                    startButton.setEnabled(true);
                    play.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
                }
            });

            playButton.setEnabled(false);
            stopPlayButton.setEnabled(true);
            startButton.setEnabled(false);


            Toast.makeText(getActivity(), "Start play the recording...",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void stopPlayAudio(View view){
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)buttonsLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.linear_top);
        buttonsLayout.setLayoutParams(params);

        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;
                startButton.setEnabled(true);
                playButton.setEnabled(true);
                stopPlayButton.setEnabled(false);


                Toast.makeText(getActivity(), "Stop playing the recording...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void showSaveDialog() {
        //after stopping the recorder, create the sound file and add it to media library.
        addRecordingToMediaLibrary();
        FragmentManager fm = getFragmentManager();
        SaveClapDialog2 saveClapDialog = SaveClapDialog2.newInstance("Save Clap");

        saveClapDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

    @Override
    public void onDismiss(DialogInterface dialog) {
        Fragment newFragment = new RecordFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, newFragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }
});
        saveClapDialog.show(fm, "fragment_save_clap");



    }

private void trans(){
   //Intent intent = new Intent(getActivity(), TransitionActivity.class);
    startActivity(new Intent(getActivity(), TransitionActivity.class));

}



    protected void addRecordingToMediaLibrary() {
        //creating content values of size 4
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

        //creating content resolver and storing it in the external content uri
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);


        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPref", 0).edit();
        editor.putString("recording", "" + newUri);

        editor.apply();

        //sending broadcast message to scan the media file so that it can be available
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(getActivity(), "Added File " + newUri, Toast.LENGTH_LONG).show();
    }

 /*   private void discardRecording(){
        recorder.reset();
        recorder.release();


    }*/

    @Override
    public void onClick(View view) {
        if (view == startButton) {
            try {
                startRecording(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view == stopButton) {
            stopRecording(view);
        }else if (view == playButton){
            playAudio(view);
        }else if (view == stopPlayButton){
            stopPlayAudio(view);
        }else if (view == saveButton) {
            showSaveDialog();
        }else if (view == dicklips){
            trans();
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
