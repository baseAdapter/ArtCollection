package com.tsutsuku.artcollection.other.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.BuildConfig;
import com.tsutsuku.artcollection.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DengChao on 2017/6/5.
 */

public class CheckUpdate {

    public static void showUpdataDialog(final Activity context, final Handler handler, final CheckUpModel info) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        RelativeLayout rl_close = (RelativeLayout) view.findViewById(R.id.rl_close);
        ImageView btn_enter = (ImageView) view.findViewById(R.id.btn_enter);
        Button btn_update = (Button) view.findViewById(R.id.btn_update);
        dialog.setCancelable(false);
        tv_version.setText(info.getInfo().getTerminal_version());
        tv_content.setText(info.getInfo().getContent());
        if ("0".equals(info.getInfo().getStatus())) {
            rl_close.setVisibility(View.VISIBLE);
        } else if ("1".equals(info.getInfo().getStatus())) {
            rl_close.setVisibility(View.GONE);
        } else {
            return;
        }
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(info.getInfo().getStatus())) {
                    context.finish();
                    System.exit(0);
                    return;
                }
                handler.sendEmptyMessage(2);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadApk(context, handler, info);
            }
        });

        dialog.show();
    }

    static ProgressDialog pd; // 进度条对话框

    public static void dismiss() {
        if (pd != null && pd.isShowing())
            pd.dismiss();
    }

    protected static void downLoadApk(final Activity context, final Handler handler, final CheckUpModel info) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("下载更新中");
        pd.show();
        new Thread() {

            @Override
            public void run() {
                try {
                    // File file = CheckUpdate.getFileFromServer(info.getUrl(),
                    // pd);
                    // sleep(3000);
                    // pd.dismiss(); // 结束掉进度条对话框
                    // installApk(context, file);
                    File file = CheckUpdate.getFileFromServer(info.getInfo().getAddress());
                    // sleep(3000);
                    pd.dismiss(); // 结束掉进度条对话框
                    installApk(context, file);
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = -2;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected static void installApk(Activity context, File file) {
        Intent intent = new Intent();

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static File getFileFromServer(String path) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        int length = 0;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            length = conn.getContentLength();
            pd.setMax(100);
            InputStream is = conn.getInputStream();
            new File(Environment.getExternalStorageDirectory() + "/7PeiShang/").mkdirs();
            File file = new File(Environment.getExternalStorageDirectory() + "/7PeiShang", "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                float progress = ((float) total) / ((float) length);
                pd.setProgress((int) (progress * 100));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }
}
