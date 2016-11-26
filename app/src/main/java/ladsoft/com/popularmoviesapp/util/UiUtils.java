package ladsoft.com.popularmoviesapp.util;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class UiUtils {

    public static void hideStatusBar(Window window){
        if (Build.VERSION.SDK_INT < 16){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSnackbar(View view, String message, String actionLabel, int duration, View.OnClickListener listener) {
        final Snackbar snackbar = Snackbar.make(view, message, duration);

        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setMaxLines(999);

        if(actionLabel != null) {
            if(listener == null) {
                listener = new  View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                };
            }

            snackbar.setAction(actionLabel, listener);
        }

        snackbar.show();
    }

    public static void triggerSwipeRefreshLayout(final SwipeRefreshLayout view, final SwipeRefreshLayout.OnRefreshListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setRefreshing(true);
                if(listener != null) {
                    listener.onRefresh();
                }
            }
        });
    }

}

