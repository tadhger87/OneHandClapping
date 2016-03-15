package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tadhg.onehandclapping.DialogActivity;
import com.tadhg.onehandclapping.R;

/**
 * Created by Tadhg on 26/02/2016.
 */
public class TransitionActivity extends Activity {
    //blah
    private Button saveButton;
    public TransitionActivity() {
        // Required empty public constructor
    }



    /*@Bind(R.id.activity_contact_rl_container) RelativeLayout mRlContainer;
    @Bind(R.id.activity_contact_fab) FloatingActionButton mFab;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_activity);
     //   Button view = (Button) findViewById(R.id.button);
      //  view.setOnClickListener(e -> Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show());
       // setupEnterAnimation();

    }
/*
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.change_bound_with_arc);
        transition.setDuration(300);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealShow(mRlContainer);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }*/
    public void save (View view){


    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transition_activity, container, false);

        saveButton = (Button)rootView.findViewById(R.id.saveb);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                save(v);
            }
        });
        // Inflate the layout for this fragment
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
    */
}

