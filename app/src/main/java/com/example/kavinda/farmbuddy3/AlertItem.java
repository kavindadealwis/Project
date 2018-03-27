package com.example.kavinda.farmbuddy3;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Kavinda on 3/27/2018.
 */

public class AlertItem extends RelativeLayout {
    View rootView;
    TextView msg;
    TextView date;

    public AlertItem(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        rootView = inflate(context, R.layout.vitem, this);
        msg = (TextView) rootView.findViewById(R.id.errorText);
        date = (TextView) rootView.findViewById(R.id.errorText2);
    }

}
