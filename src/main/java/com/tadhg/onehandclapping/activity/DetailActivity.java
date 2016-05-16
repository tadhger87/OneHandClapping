package com.tadhg.onehandclapping.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Tadhg on 06/05/2016.
 */
public class DetailActivity extends ActionBarActivity implements View.OnClickListener {

    private Context context;
    MediaPlayer mp = null;
    AssetFileDescriptor descriptor;
    TextView textDate, textAudio, textPicture, textName;
    ClapDAO clapDAO;
    ClapItem updatedClap;
    String audio;
    ImageView picture;
    private UpdateClapTask task;
    private LinearLayout mRevealView;
    private EditText mEditTextTodo;
    private boolean isEditTextVisible;
    private InputMethodManager mInputManager;
    int defaultColor;
    private LinearLayout mTitleHolder;
    private ListView mList;
    private TextView mTitle;

    public static final String TAG = "Flange";
    private Boolean isFabOpen = false;
    private FloatingActionButton audioFAB, editFAB, mAddButton;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        clapDAO = new ClapDAO(this);

        String image = "test";
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        mEditTextTodo = (EditText) findViewById(R.id.etTodo);
        mList = (ListView) findViewById(R.id.list);
        mTitleHolder = (LinearLayout) findViewById(R.id.cardNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        mAddButton = (FloatingActionButton) findViewById(R.id.btn_add);
        mEditTextTodo = (EditText) findViewById(R.id.etTodo);
        audioFAB = (FloatingActionButton) findViewById(R.id.fab1);
        editFAB = (FloatingActionButton) findViewById(R.id.fab2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);


        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;

        try {
            Intent i = getIntent();
            Log.d(TAG, "intent: " + i.toString());
            Log.d(TAG, "extras: " + i.getExtras());

            ClapItem ci = (ClapItem) i.getParcelableExtra(ClapItem.EXTRA_CONTENT_DETAIL);

            Log.d(TAG, "content-item: " + ci.toString());
            Toast.makeText(this, ci.getPictureRef(),
                    Toast.LENGTH_SHORT).show();

            (textName = (TextView) findViewById(R.id.textView)).setText(ci.getClapName());
            (textDate = (TextView) findViewById(R.id.date_tv)).setText(ci.getClapDate());

            showPic();

            SharedPreferences prefs = this.getSharedPreferences("MyPref", 0);
            audio = prefs.getString("recording", null);

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        defaultColor = getResources().getColor(R.color.colorPrimaryDark);
        mAddButton.setOnClickListener(this);
        audioFAB.setOnClickListener(this);
        editFAB.setOnClickListener(this);
    }

    public void animateFAB() {

        if (isFabOpen) {

            mAddButton.startAnimation(rotate_backward);
            audioFAB.startAnimation(fab_close);
            editFAB.startAnimation(fab_close);
            audioFAB.setClickable(false);
            editFAB.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            mAddButton.startAnimation(rotate_forward);
            audioFAB.startAnimation(fab_open);
            editFAB.startAnimation(fab_open);
            audioFAB.setClickable(true);
            editFAB.setClickable(true);
            isFabOpen = true;
            editFAB.setImageDrawable(getResources().getDrawable (R.mipmap.icn_edit));
            editFAB.setBackgroundTintList(new ColorStateList(new int[][]{
                    new int[]{0}}
                    , new int[]{getResources().getColor(R.color.colorFAB2)
            }));
            Log.d("Raj", "open");

        }
    }
/*
    private void setUpAdapter() {
        mTodoList = new ArrayList<>();
        mToDoAdapter = new ArrayAdapter(this, R.layout.row_todo, mTodoList);
        mList.setAdapter(mToDoAdapter);
    }*/



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add:
                animateFAB();
                if (isEditTextVisible = true) {
                    hideEditText(mRevealView);

                }

                break;

            case R.id.fab1:
                playAudio();
                break;

            case R.id.fab2:

                if (!isEditTextVisible) {
                    revealEditText(mRevealView);
                    mEditTextTodo.requestFocus();
                    v.setBackgroundTintList(new ColorStateList(new int[][]
                            {new int[]{0}}, new int[]{getResources().getColor(R.color.colorTick)}));
                    editFAB.setImageDrawable(getResources().getDrawable(R.drawable.icn_done));
                    mInputManager.showSoftInput(mEditTextTodo, InputMethodManager.SHOW_IMPLICIT);
                }
                else {

                    updateClap();
                    mInputManager.hideSoftInputFromWindow(mEditTextTodo.getWindowToken(), 0);
                    hideEditText(mRevealView);
                    v.setBackgroundTintList(new ColorStateList(new int[][]{
                            new int[]{0}}
                            , new int[]{getResources().getColor(R.color.colorFAB2)
                    }));
                    editFAB.setImageDrawable(getResources().getDrawable(R.mipmap.icn_edit));
                }
                break;
        }
    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealEditText(LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        isEditTextVisible = true;
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideEditText(final LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int initialRadius = view.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        isEditTextVisible = false;
        anim.start();
    }
    public void updateClap(){
        updatedClap = new ClapItem();

        textName.setText(mEditTextTodo.getText().toString());
        updatedClap.setClapName(mEditTextTodo.getText().toString());
        updatedClap.setClapDate(textDate.toString());

        long result = clapDAO.update(updatedClap);
        if (result > 0) {
            finish();
        task = new UpdateClapTask(this);
        task.execute((Void) null);
        }

    }

    @Override
    public void onBackPressed() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(100);
        mAddButton.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animation animation) {
                mAddButton.setVisibility(View.GONE);
                finishAfterTransition();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void finish() {
        Intent returnIntent = getIntent(); //new Intent
        returnIntent.putExtra("passed_item", updatedClap);
        setResult(RESULT_OK, returnIntent);
        super.finish();
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
            picture = (ImageView) findViewById(R.id.cardPicture);
            picture.setImageBitmap(bm);
        }else{
            picture.setImageResource(R.drawable.clapping1);
        }
        Log.d(TAG, "show pic");
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


}

