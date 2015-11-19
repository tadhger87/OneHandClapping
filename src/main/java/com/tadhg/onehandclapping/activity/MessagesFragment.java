package com.tadhg.onehandclapping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tadhg.onehandclapping.DialogActivity;
import com.tadhg.onehandclapping.R;


/**
 * Created by Tadhg on 22/09/2015.
 */
public class MessagesFragment extends Fragment {

    private Button saveButton;
    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

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

    public void save (View view){

        Intent myIntent = new Intent(getActivity(), DialogActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        MessagesFragment.this.startActivity(myIntent);
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

