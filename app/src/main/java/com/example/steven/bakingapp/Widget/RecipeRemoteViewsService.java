package com.example.steven.bakingapp.Widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by Steven on 12/05/2018.
 */

public class RecipeRemoteViewsService extends RemoteViewsService {

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("RemoteViewsService", "Inside RemoteViewsService");
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}
