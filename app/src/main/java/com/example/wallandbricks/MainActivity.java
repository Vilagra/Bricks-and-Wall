package com.example.wallandbricks;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Boolean> {

    EditText etWidthOfWall;
    EditText etHeigthOfWall;
    EditText etAmountOfBricks;
    EditText etWidthOfBricks;
    Button bAdd;
    Button bVerification;
    Button bClear;

    AdapterOfBricks adapterOfBricks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWidthOfWall = (EditText) findViewById(R.id.widthWall);
        etHeigthOfWall = (EditText) findViewById(R.id.heightWall);
        etAmountOfBricks = (EditText) findViewById(R.id.amountOfBricks);
        etWidthOfBricks = (EditText) findViewById(R.id.sizeOfBricks);

        bAdd = (Button) findViewById(R.id.add);
        bClear = (Button) findViewById(R.id.clear);
        bVerification = (Button) findViewById(R.id.verification);
        bAdd.setOnClickListener(this);
        bClear.setOnClickListener(this);
        bVerification.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfBricks);
        adapterOfBricks = new AdapterOfBricks(new HashMap<Integer, Integer>(), new ArrayList<Integer>());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterOfBricks);

        getLoaderManager().initLoader(Constants.LOADER_VER_ID, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                adapterOfBricks.updateData(Integer.valueOf(etWidthOfBricks.getText().toString()), Integer.valueOf(etAmountOfBricks.getText().toString()));
                break;
            case R.id.clear:
                break;
            case R.id.verification:
                Bundle bundle= getBundleWithData();
                getLoaderManager().restartLoader(Constants.LOADER_VER_ID,bundle,this).forceLoad();
                break;
        }
    }

    public Bundle getBundleWithData(){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.WIDTH,Integer.valueOf(etWidthOfWall.getText().toString()));
        bundle.putInt(Constants.HEIGTH,Integer.valueOf(etHeigthOfWall.getText().toString()));
        bundle.putIntegerArrayList(Constants.LIST_OF_WIDTH_BRICKS,adapterOfBricks.getKeys());
        bundle.putSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS, (Serializable) adapterOfBricks.getMapOfBricks());
        return bundle;
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
        final AlertDialog.Builder alert= new AlertDialog.Builder(this);
        String msg;
        if(data){
            msg="YES";
        }else {
            msg="NO";
        }
        alert.setTitle("Result").
                setMessage(msg).
                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }


}
