package br.org.cesar.knot.beamsensor.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;

import br.org.cesar.knot.beamsensor.R;

public final class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context, R.style.AppProgressDialogTheme);
        ProgressBar progressBar = new ProgressBar(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);
        setContentView(progressBar);
    }

    public void showProgressDialog() {
        if (!isShowing()) {
            setCanceledOnTouchOutside(false);
            show();
        }
    }

    public void hideProgressDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //do not cancel if back press hard button
    }
}