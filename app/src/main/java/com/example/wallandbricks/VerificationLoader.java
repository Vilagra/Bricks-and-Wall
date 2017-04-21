package com.example.wallandbricks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Vilagra on 21.04.2017.
 */

public class VerificationLoader extends AsyncTaskLoader<Boolean> {
    Verification verification;

    public VerificationLoader(Context context, Bundle bundle) {
        super(context);

    }

    @Override
    public Boolean loadInBackground() {
        return null;
    }

}
