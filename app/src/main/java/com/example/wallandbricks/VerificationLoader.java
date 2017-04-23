package com.example.wallandbricks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

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
        List<Integer> list = bundle.getIntegerArrayList(Constants.LIST_OF_WIDTH_BRICKS);
        Map<Integer,Integer> map = (Map<Integer, Integer>) bundle.getSerializable(Constants.WIDTH_AND_AMOUNT_OF_BRICKS);
        return new Verification(w,h,map,list);
    }

}
