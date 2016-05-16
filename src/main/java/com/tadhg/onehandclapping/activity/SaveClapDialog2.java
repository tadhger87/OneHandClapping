package com.tadhg.onehandclapping.activity;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tadhg.onehandclapping.R;
import com.tadhg.onehandclapping.db.ClapDAO;
import com.tadhg.onehandclapping.model.ClapItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Tadhg on 01/03/2016.
 */
public class SaveClapDialog2  extends DialogFragment implements View.OnClickListener {
    // blah
    private EditText mEditText;
    private TextView dateText;
    private Button save, cancel, cameraButton;
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private ClapDAO clapDAO;
    ClapItem clapItem;
    private AddClapTask task;
    private Context context;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;


    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
    //Or use only MMM if you want only 3 characters in the month
    String formattedDate = df.format(c.getTime());




    public interface SaveClapDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public SaveClapDialog2() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SaveClapDialog2 newInstance(String title) {
        SaveClapDialog2 frag = new SaveClapDialog2();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        clapDAO = new ClapDAO (getActivity());

        return inflater.inflate(R.layout.save_clap_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // findViewsById(view);
        //setListeners();

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", 0);
        final String output = prefs.getString("recording", null);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field


        clapItem = new ClapItem();

        mEditText = (EditText) view.findViewById(R.id.etxt_clap_name);
        dateText = (TextView) view.findViewById(R.id.date_tv);
        save = (Button) view.findViewById(R.id.button_save);
        cancel = (Button) view.findViewById(R.id.button_cancel);
        cameraButton = (Button) view.findViewById(R.id.cameraButton);
        imageView = (ImageView) view.findViewById(R.id.camera_iv);

        mEditText.requestFocus();

        //some methods using lambdas
        cancel.setOnClickListener(v -> dismiss());

        save.setOnClickListener(v -> {
            clapItem.setClapName(mEditText.getText().toString());
            clapItem.setClapDate(formattedDate);
            clapItem.setAudioRef(output);
            task = new AddClapTask(getActivity());
            task.execute((Void) null);
            mEditText.setText("");
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        dateText.setText(formattedDate);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);



    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(thumbnail);

                Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED,
                        null, "date_added ASC");
                if(cursor != null && cursor.moveToFirst())
                {
                    do {
                        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                        String cameraPath = uri.toString();
                        dateText.setText(cameraPath);

                        /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("pref", 0).edit();
                        editor.putString("pic", "" + cameraPath);
                        editor.apply();*/

                        clapItem = new ClapItem();
                        clapItem.setPictureRef(cameraPath);
                    }while(cursor.moveToNext());
                    cursor.close();


                }


            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getContext(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imageView.setImageBitmap(bm);
                //dateText.setText(selectedImagePath);

                clapItem = new ClapItem();
                clapItem.setPictureRef(selectedImagePath);

            }
        }
    }



    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public class AddClapTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddClapTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {

            long result = clapDAO.save(clapItem);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Clap Saved",
                            Toast.LENGTH_LONG).show();
                dismiss();
            }
        }
    }
    /*private void findViewsById(View rootView){
        mEditText = (EditText) rootView.findViewById(R.id.etxt_clap_name);
        dateText = (TextView) rootView.findViewById(R.id.date_tv);
        save = (Button) rootView.findViewById(R.id.button_save);
        cancel = (Button) rootView.findViewById(R.id.button_cancel);
        cameraButton = (Button) rootView.findViewById(R.id.cameraButton);
        imageView = (ImageView) rootView.findViewById(R.id.camera_iv);
    }
    private void setListeners(){
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == cancel){
            dismiss();
        } else if (v == cameraButton){
            selectImage();
        }else if (v == save){
            setClap();
            task = new AddClapTask(getActivity());
            task.execute((Void) null);
            mEditText.setText("");
        }
    }
    private void setClap() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", 0);
        final String output = prefs.getString("recording", null);
        SharedPreferences pref = getActivity().getSharedPreferences("pref", 0);
        final String cameraPath = pref.getString("pic", null);
        SharedPreferences pref2 = getActivity().getSharedPreferences("pref2", 0);
        final String selectedPath = pref2.getString("selectedPic", null);
        clapItem = new ClapItem();
        if ("".equals(mEditText.getText().toString())) {
            Toast toast = Toast.makeText(this.getActivity(), "You have to give your clap a name!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            clapItem.setClapName(mEditText.getText().toString());
            clapItem.setClapDate(formattedDate);
            clapItem.setAudioRef(output);
            clapItem.setPictureRef(cameraPath);
        }
    }*/





    @Override
    public void onClick(View v) {

    }

}

