package com.mindyourelders.MyHealthCareWishes.Connections;

import android.widget.BaseAdapter;

/**
 * Created by welcome on 9/13/2017.
 */

public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position) {
        return true;
    }
}