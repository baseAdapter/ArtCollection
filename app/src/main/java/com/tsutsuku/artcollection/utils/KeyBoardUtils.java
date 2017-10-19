package com.tsutsuku.artcollection.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Author Sun Renwei
 * @Create 2017/2/16
 * @Description 键盘和输入法相关工具类
 */

public class KeyBoardUtils {
    private static final int MAX_CASH_NUM = 10;

    public static InputFilter getCashInputFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if ((source.equals(".") || source.equals("0")) && dest.toString().length() == 0) {
                    return "0.";
                }

                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mLength = dest.toString().substring(index).length();
                    if (mLength == 3) {
                        return "";
                    }
                }

                if (dest.length() > MAX_CASH_NUM || dest.toString().indexOf(".") > MAX_CASH_NUM) {
                    return "";
                }

                return null;
            }
        };
    }

    /**
     * 关闭软键盘
     * @param activity 上下文Activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 打开软键盘
     * @param context 上下文
     * @param editText 聚焦的editText
     */
    public static void openKeyboard(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText, 0);
        }
    }

    /**
     * 判断软键盘是否显示
     *
     * @param rootView 根View
     * @return
     */
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
