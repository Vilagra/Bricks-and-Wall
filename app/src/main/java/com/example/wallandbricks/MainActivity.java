package com.example.wallandbricks;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Boolean> {

    EditText widthOfWall;
    EditText heigthOfWall;
    EditText amountOfBricks;
    EditText widthOFBricks;
    Button bAdd;
    Button bVerification;
    Button bClear;

    AdapterOfBricks adapterOfBricks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widthOfWall = (EditText) findViewById(R.id.widthWall);
        heigthOfWall = (EditText) findViewById(R.id.heightWall);
        amountOfBricks = (EditText) findViewById(R.id.amountOfBricks);
        widthOFBricks = (EditText) findViewById(R.id.sizeOfBricks);

        bAdd = (Button) findViewById(R.id.add);
        bClear = (Button) findViewById(R.id.clear);
        bVerification = (Button) findViewById(R.id.verification);
        bAdd.setOnClickListener(this);
        bClear.setOnClickListener(this);
        bVerification.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfBricks);
        adapterOfBricks = new AdapterOfBricks(new TreeMap<Integer, Integer>(),new ArrayList<Integer>());
        RecyclerView.LayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterOfBricks);

        getLoaderManager().initLoader(Constants.LOADER_VER_ID,null,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        adapterOfBricks.updateData(Integer.valueOf(widthOFBricks.getText().toString()),Integer.valueOf(amountOfBricks.getText().toString()));
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        if(id==Constants.LOADER_VER_ID) {
            return new VerificationLoader(this, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }


}
