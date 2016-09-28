package com.klm.testcase.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.klm.testcase.R;


/**
 * Created by heitorzc on 08/03/16.
 */
public class CustomProgressDialog extends ProgressDialog {

    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
    }

    @Override
    public void show() {
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
