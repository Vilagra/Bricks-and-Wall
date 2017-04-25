package com.example.wallandbricks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.example.wallandbricks.entity.Brick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vilagra on 21.04.2017.
 */

public class VerificationLoader extends AsyncTaskLoader<Boolean> {
    private Verification verification;

    public VerificationLoader(Context context, Bundle bundle) {
        super(context);
        if (bundle != null) {
            verification= getVerificationFromBundle(bundle);
        }

    }

    @Override
    public Boolean loadInBackground() {
        return verification.check();
    }

    public Verification getVerificationFromBundle(Bundle bundle){  //parse the bundle and get instance of verification class
        int h = bundle.getInt(Constants.HEIGHT);
        int w = bundle.getInt(Constants.WIDTH);
        List<Brick> list = bundle.getParcelableArrayList(Constants.LIST_OF_WIDTH_BRICKS);
        Map<Brick,Integer> map = (Map<Brick, Integer>) bundle.getSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS);
        return new Verification(w,h,map,list);
    }

}
