package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SecondPage extends ActionBarActivity implements View.OnClickListener {

    private  Context context;
    Button nb, updateButton;
    MediaPlayer mp = null;
    AssetFileDescriptor descriptor;
    TextView  textDate, textAudio, textPicture;
    EditText textName;
    ClapDAO clapDAO;
    ClapItem updatedClap;
    String name, audio;
    ImageView picture;
    private UpdateClapTask task;

    public static final String TAG = "Falafel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        clapDAO = new ClapDAO(this);
        //clapItem = new ClapItem();

        String image = "test";

        try {
            Intent i = getIntent();
            Log.d(TAG, "intent: " + i.toString());
            Log.d(TAG, "extras: " + i.getExtras());

            ClapItem ci = (ClapItem) i.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);

            Log.d(TAG, "content-item: " + ci.toString());


           (textName = (EditText) findViewById(R.id.textName)).setText(ci.getClapName());
            (textDate = (TextView) findViewById(R.id.textDate)).setText(ci.getClapDate());
            (textAudio = (TextView) findViewById(R.id.textAudioRef)).setText(ci.getAudioRef());
            (textPicture = (TextView) findViewById(R.id.textPictureRef)).setText(ci.getPictureRef());

            textName.requestFocus();
            showPic();

            SharedPreferences prefs = this.getSharedPreferences("MyPref", 0);
             audio = prefs.getString("recording", null);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(v ->

            updateClap()
            /*clapItem.setClapName(textName.getText().toString());
            clapItem.setClapDate(textDate.toString());
            clapItem.setAudioRef(textAudio.toString());
            clapItem.setPictureRef(textPicture.toString());
            task = new UpdateClapTask(this);
            task.execute((Void) null);*/

        );

            nb = (Button) findViewById(R.id.noise_button);
        nb.setOnClickListener(v ->
                playAudio());

        }



    public void updateClap(){
        updatedClap = new ClapItem();

        updatedClap.setClapName(textName.getText().toString());
        updatedClap.setClapDate(textDate.toString());
        updatedClap.setAudioRef(textAudio.toString());
        updatedClap.setPictureRef(textPicture.toString());
        long result = clapDAO.update(updatedClap);
        if (result > 0) {
            finish();
//        task = new UpdateClapTask(this);
//        task.execute((Void) null);
        }
    }

    @Override
    public void finish() {
        Intent returnIntent = getIntent(); //new Intent
        returnIntent.putExtra("passed_item", updatedClap);
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }
    private void getParcelables(){
        Intent i = getIntent();
        Log.d(TAG, "intent: " + i.toString());
        Log.d(TAG, "extras: " + i.getExtras());
        ClapItem ci = (ClapItem) i.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);
    }

    private void showPic(){

        Intent i = getIntent();
        Log.d(TAG, "intent: " + i.toString());
        Log.d(TAG, "extras: " + i.getExtras());
        ClapItem ci = (ClapItem) i.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);
        Uri selectedImageUri = Uri.parse(ci.getPictureRef());
        if (ci.getPictureRef() != null) {
            String stringUri = selectedImageUri.toString();

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(stringUri, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(stringUri, options);
            picture = (ImageView) findViewById(R.id.image_iv);
            picture.setImageBitmap(bm);
        }else{
            picture.setImageResource(R.drawable.clapping1);
        }
    }

    public void playAudio() {

        try {

            Intent i = getIntent();
            Log.d(TAG, "intent: " + i.toString());
            Log.d(TAG, "extras: " + i.getExtras());
            ClapItem ci = (ClapItem) i.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);
            Uri myUri = Uri.parse(ci.getAudioRef());


            Toast.makeText(this, audio, Toast.LENGTH_LONG).show();
           mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(getApplicationContext(), myUri);
            mp.prepare();
            //mp.setLooping(true);
            mp.start();
            mp.setVolume(3, 3);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
      } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Stop Audio
    public void stop() {
        try {
            if (mp != null) {
                mp.stop();
                mp.release();
                mp = null;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class UpdateClapTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public UpdateClapTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {

            long result = clapDAO.update(updatedClap);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Clap Updated",
                            Toast.LENGTH_LONG).show();
                //dismiss();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}


