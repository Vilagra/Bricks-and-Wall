package com.example.wallandbricks;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Boolean>,AdapterOfBricks.DataIsEmptyListener {

    private EditText etWidthOfWall;
    private EditText etHeigthOfWall;
    private EditText etAmountOfBricks;
    private EditText etWidthOfBricks;
    private Button bAdd;
    private Button bVerification;
    private Button bClear;

    boolean isNewLoad =false;

    private AdapterOfBricks adapterOfBricks;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWidthOfWall = (EditText) findViewById(R.id.widthWall);
        etHeigthOfWall = (EditText) findViewById(R.id.heightWall);
        etAmountOfBricks = (EditText) findViewById(R.id.amountOfBricks);
        etWidthOfBricks = (EditText) findViewById(R.id.sizeOfBricks);
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateStateOfButton();
            }
        };
        etWidthOfBricks.addTextChangedListener(textWatcher);
        etHeigthOfWall.addTextChangedListener(textWatcher);
        etWidthOfWall.addTextChangedListener(textWatcher);
        etAmountOfBricks.addTextChangedListener(textWatcher);


        bAdd = (Button) findViewById(R.id.add);
        bClear = (Button) findViewById(R.id.clear);
        bVerification = (Button) findViewById(R.id.verification);
        bAdd.setOnClickListener(this);
        bClear.setOnClickListener(this);
        bVerification.setOnClickListener(this);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfBricks);
        adapterOfBricks = new AdapterOfBricks();
        adapterOfBricks.setDataIsEmptyListener(this);
        if(savedInstanceState!=null){
            recoverState(savedInstanceState);
        }
        updateStateOfButton();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterOfBricks);

        getLoaderManager().initLoader(Constants.LOADER_VER_ID, null, this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.verification));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                adapterOfBricks.updateData(Integer.valueOf(etWidthOfBricks.getText().toString()), Integer.valueOf(etAmountOfBricks.getText().toString()));
                updateStateOfButton();
                break;
            case R.id.clear:
                break;
            case R.id.verification:
                Bundle bundle= getBundleWithData();
                getLoaderManager().restartLoader(Constants.LOADER_VER_ID,bundle,this).forceLoad();
                progressDialog.show();
                isNewLoad=true;
                break;
        }
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        if (id == Constants.LOADER_VER_ID) {
            return new VerificationLoader(this, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        if(isNewLoad) {
            progressDialog.dismiss();
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            String msg;
            if (data) {
                msg = "YES";
            } else {
                msg = "NO";
            }
            alert.setTitle("Result").
                    setMessage(msg).
                    setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = alert.show();
            TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
            messageText.setTextSize(25);
            dialog.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(Constants.LIST_OF_WIDTH_BRICKS, (ArrayList<Integer>) adapterOfBricks.getKeys());
        outState.putSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS, (Serializable) adapterOfBricks.getMapOfBricks());
    }



    public void recoverState(Bundle saved){
        adapterOfBricks.setKeys(saved.getIntegerArrayList(Constants.LIST_OF_WIDTH_BRICKS));
        adapterOfBricks.setMapOfBricks((Map<Integer, Integer>) saved.getSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS));
    }

    public Bundle getBundleWithData(){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.WIDTH,Integer.valueOf(etWidthOfWall.getText().toString()));
        bundle.putInt(Constants.HEIGTH,Integer.valueOf(etHeigthOfWall.getText().toString()));
        bundle.putIntegerArrayList(Constants.LIST_OF_WIDTH_BRICKS, (ArrayList<Integer>) adapterOfBricks.getKeys());
        bundle.putSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS, (Serializable) adapterOfBricks.getMapOfBricks());
        return bundle;
    }

    private void updateStateOfButton() {
        boolean isEmptyWidthWall = etWidthOfWall.getText().toString().equals("");
        boolean isEmptyHeigthWall = etHeigthOfWall.getText().toString().equals("");
        boolean isEmptyAmount = etAmountOfBricks.getText().toString().equals("");
        boolean isEmptyWidtOfBricks = etWidthOfBricks.getText().toString().equals("");
        boolean isMapOfBricksEmpty = adapterOfBricks.getMapOfBricks().isEmpty();
        bVerification.setEnabled(!isEmptyWidthWall&&!isEmptyHeigthWall&&!isMapOfBricksEmpty);
        bClear.setEnabled(!isEmptyWidthWall||!isEmptyHeigthWall||!isMapOfBricksEmpty||!isEmptyAmount||!isEmptyWidtOfBricks);
        bAdd.setEnabled(!isEmptyAmount&&!isEmptyWidtOfBricks);

    }

    @Override
    public void notifyDataIsEmpty(boolean b) {
        if(b) {
            updateStateOfButton();
        }
    }
}
