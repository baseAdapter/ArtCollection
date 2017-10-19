package com.tsutsuku.artcollection.live.stream;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hyphenate.chat.EMMessage;
import com.netease.LSMediaCapture.Statistics;
import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMediaCapture.LSLiveStreamingParaCtx;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.LSMediaCapture.view.NeteaseSurfaceView;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.presenter.ChatRoomRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

//由于直播推流的URL地址较长，可以直接在代码中的mliveStreamingURL设置直播推流的URL
public class MediaPreviewActivity extends Activity implements OnSeekBarChangeListener, lsMessageHandler {
    private static final String ACTIVITY_ID = "activityId";
    private static final String PUSH_USERID = "pushUserId";

    /**
     * @param context
     * @param urlMedia 推流地址Url
     * @param videoResolution 推流清晰度 "HD"高清, "SD"标清, "Fluency"流畅
     */
    public static void launch(Context context, String urlMedia, String videoResolution, String activityId, String pushUserId) {
        context.startActivity(new Intent(context, MediaPreviewActivity.class)
                .putExtra("mediaPath", urlMedia)
                .putExtra("videoResolution", videoResolution)
                .putExtra(ACTIVITY_ID, activityId)
                .putExtra(PUSH_USERID, pushUserId)
                .putExtra("filter", false));
    }

    private Handler handler;
    private Context context;

    //Demo控件
    private ImageButton startPauseResumeBtn;
    private ImageButton switchBtn;

    private lsMediaCapture mLSMediaCapture = null;
    private String mliveStreamingURL = null;
    private String mVideoResolution = null;
    private boolean mUseFilter = false;

    private Handler mHandler;

    //SDK统计相关变量
    private LSLiveStreamingParaCtx mLSLiveStreamingParaCtx = null;
    private Statistics mStatistics = null;

    //普通模式的view
    private NeteaseSurfaceView mVideoView;

    //状态变量
    private boolean m_liveStreamingOn = false;
    private boolean m_liveStreamingInitFinished = false;
    private boolean m_tryToStopLivestreaming = false;
    private boolean m_startVideoCamera = false;

    private Intent mIntentLiveStreamingStopFinished = new Intent("LiveStreamingStopFinished");

    private int mVideoPreviewWidth, mVideoPreviewHeight;

    private long mLastVideoProcessErrorAlertTime = 0;
    private long mLastAudioProcessErrorAlertTime = 0;
    private boolean mHardWareEncEnable = false;

    //视频涂鸦相关变量
    private boolean mGraffitiOn = false;//视频涂鸦开关，默认关闭，需要视频涂鸦的用户可以开启此开关
    private String mGraffitiFilePath;//视频涂鸦文件路径
    private String mGraffitiFileName = "vcloud1.bmp";//视频涂鸦文件名
    private File mGraffitiAppFileDirectory = null;
    private int mGraffitiPosX = 0;
    private int mGraffitiPosY = 0;

    //视频水印相关变量
    private boolean mWaterMarkOn = false;//视频水印开关，默认关闭，需要视频水印的用户可以开启此开关
    private String mWaterMarkFilePath;//视频水印文件路径
    private String mWaterMarkFileName = "logo.png";//视频水印文件名
    private File mWaterMarkAppFileDirectory = null;
    private int mWaterMarkPosX = 20;//视频水印坐标(X)
    private int mWaterMarkPosY = 10;//视频水印坐标(Y)

    //视频动态水印相关变量
    private boolean mDynamicWaterMarkOn = false;//视频动态水印开关，默认关闭，需要视频水印的用户可以开启此开关
    private int mDynamicWaterMarkFps = 20;
    private boolean mDynamicWaterMarkLooped = true;
    private int mDynamicWaterMarkPosX = 40;//视频动态水印坐标(X)
    private int mDynamicWaterMarkPosY = 10;//视频动态水印坐标(Y)

    //视频截图相关变量
    private String mScreenShotFilePath = "/sdcard/";//视频截图文件路径
    private String mScreenShotFileName = "test.jpg";//视频截图文件名

    //查询摄像头支持的采集分辨率信息相关变量
    private Thread mCameraThread;
    private Looper mCameraLooper;
    private int mCameraID = CAMERA_POSITION_BACK;//默认查询的是后置摄像头
    private Camera mCamera;

    private float mCurrentDistance;
    private float mLastDistance = -1;

    public static final int CAMERA_POSITION_BACK = 0;
    public static final int CAMERA_POSITION_FRONT = 1;

    public static final int LS_VIDEO_CODEC_AVC = 0;
    public static final int LS_VIDEO_CODEC_VP9 = 1;
    public static final int LS_VIDEO_CODEC_H265 = 2;

    public static final int LS_AUDIO_STREAMING_LOW_QUALITY = 0;
    public static final int LS_AUDIO_STREAMING_HIGH_QUALITY = 1;

    public static final int LS_AUDIO_CODEC_AAC = 0;
    public static final int LS_AUDIO_CODEC_SPEEX = 1;
    public static final int LS_AUDIO_CODEC_MP3 = 2;
    public static final int LS_AUDIO_CODEC_G711A = 3;
    public static final int LS_AUDIO_CODEC_G711U = 4;

    public static final int FLV = 0;
    public static final int RTMP = 1;
    public static final int RTMP_AND_FLV = 2;

    public static final int HAVE_AUDIO = 0;
    public static final int HAVE_VIDEO = 1;
    public static final int HAVE_AV = 2;

    public static final int OpenQoS = 0;
    public static final int CloseQoS = 1;

    private static final String TAG = "NeteaseLiveStream";

    private Toast mToast;

    private void showToast(final String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        } else {
            mToast.setText(text);
            mToast.show();
        }
    }

    //查询Android摄像头支持的采样分辨率相关方法（1）
    public void openCamera(final int cameraID) {
        final Semaphore lock = new Semaphore(0);
        final RuntimeException[] exception = new RuntimeException[1];
        mCameraThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mCameraLooper = Looper.myLooper();
                try {
                    mCamera = Camera.open(cameraID);
                } catch (RuntimeException e) {
                    exception[0] = e;
                } finally {
                    lock.release();
                    Looper.loop();
                }
            }
        });
        mCameraThread.start();
        lock.acquireUninterruptibly();
    }

    //查询Android摄像头支持的采样分辨率相关方法（2）
    public void lockCamera() {
        try {
            mCamera.reconnect();
        } catch (Exception e) {
        }
    }

    //查询Android摄像头支持的采样分辨率相关方法（3）
    public void releaseCamera() {
        if (mCamera != null) {
            lockCamera();
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    //查询Android摄像头支持的采样分辨率相关方法（4）
    public List<Camera.Size> getCameraSupportSize(int cameraID) {
        openCamera(cameraID);
        if (mCamera != null) {
            Parameters param = mCamera.getParameters();
            List<Camera.Size> previewSizes = param.getSupportedPreviewSizes();
            releaseCamera();
            return previewSizes;
        }
        return null;
    }

    //视频水印相关方法(1)
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    //视频水印相关方法(2)
    public void waterMark() {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mWaterMarkAppFileDirectory = getExternalFilesDir(null);
        } else {
            mWaterMarkAppFileDirectory = getFilesDir();
        }

        AssetManager assetManager = getAssets();

        //拷贝水印文件到APP目录
        String[] files = null;
        File fileDirectory;

        try {
            files = assetManager.list("waterMark");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        if (mWaterMarkAppFileDirectory != null) {
            fileDirectory = mWaterMarkAppFileDirectory;
        } else {
            fileDirectory = Environment.getExternalStorageDirectory();
            mWaterMarkFilePath = fileDirectory + "/" + mWaterMarkFileName;
        }

        for (String filename : files) {
            try {
                InputStream in = assetManager.open("waterMark/" + filename);
                File outFile = new File(fileDirectory, filename);
                FileOutputStream out = new FileOutputStream(outFile);
                copyFile(in, out);
                mWaterMarkFilePath = outFile.toString();
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file", e);
            }
        }
    }

    //视频涂鸦相关方法
    public void Graffiti() {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mGraffitiAppFileDirectory = getExternalFilesDir(null);
        } else {
            mGraffitiAppFileDirectory = getFilesDir();
        }

        AssetManager assetManager = getAssets();

        //拷贝涂鸦文件到APP目录
        String[] files = null;
        File fileDirectory;

        try {
            files = assetManager.list("graffiti");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        if (mGraffitiAppFileDirectory != null) {
            fileDirectory = mGraffitiAppFileDirectory;
        } else {
            fileDirectory = Environment.getExternalStorageDirectory();
            mGraffitiFilePath = fileDirectory + "/" + mGraffitiFileName;
        }

        for (String filename : files) {
            try {
                InputStream in = assetManager.open("graffiti/" + filename);
                File outFile = new File(fileDirectory, filename);
                FileOutputStream out = new FileOutputStream(outFile);
                copyFile(in, out);
                mGraffitiFilePath = outFile.toString();
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file", e);
            }
        }
    }

    //音视频参数设置
    public void paraSet() {

        //创建参数实例
        mLSLiveStreamingParaCtx = mLSMediaCapture.new LSLiveStreamingParaCtx();
        mLSLiveStreamingParaCtx.eHaraWareEncType = mLSLiveStreamingParaCtx.new HardWareEncEnable();
        mLSLiveStreamingParaCtx.eOutFormatType = mLSLiveStreamingParaCtx.new OutputFormatType();
        mLSLiveStreamingParaCtx.eOutStreamType = mLSLiveStreamingParaCtx.new OutputStreamType();
        mLSLiveStreamingParaCtx.sLSAudioParaCtx = mLSLiveStreamingParaCtx.new LSAudioParaCtx();
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.codec = mLSLiveStreamingParaCtx.sLSAudioParaCtx.new LSAudioCodecType();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx = mLSLiveStreamingParaCtx.new LSVideoParaCtx();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.codec = mLSLiveStreamingParaCtx.sLSVideoParaCtx.new LSVideoCodecType();
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.cameraPosition = mLSLiveStreamingParaCtx.sLSVideoParaCtx.new CameraPosition();
        mLSLiveStreamingParaCtx.sLSQoSParaCtx = mLSLiveStreamingParaCtx.new LSQoSParaCtx();

        if (!mLSLiveStreamingParaCtx.eHaraWareEncType.hardWareEncEnable && mWaterMarkOn) {
            waterMark();
        }

        if (!mLSLiveStreamingParaCtx.eHaraWareEncType.hardWareEncEnable && mGraffitiOn) {
            Graffiti();
        }

        //设置摄像头信息，并开始本地视频预览
        mLSLiveStreamingParaCtx.sLSVideoParaCtx.cameraPosition.cameraPosition = CAMERA_POSITION_BACK;//默认后置摄像头，用户可以根据需要调整

        //输出格式：视频、音频和音视频
        mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType = HAVE_AV;//默认音视频推流

        //输出封装格式
        mLSLiveStreamingParaCtx.eOutFormatType.outputFormatType = RTMP;//默认RTMP推流

        //输出FLV文件的路径和名称
        mLSLiveStreamingParaCtx.eOutFormatType.outputFormatFileName = "/sdcard/media.flv";//当outputFormatType为FLV或者RTMP_AND_FLV时有效

        //音频编码参数配置
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.samplerate = 44100;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.bitrate = 64000;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.frameSize = 2048;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.channelConfig = AudioFormat.CHANNEL_IN_MONO;
        mLSLiveStreamingParaCtx.sLSAudioParaCtx.codec.audioCODECType = LS_AUDIO_CODEC_AAC;

        //硬件编码参数设置
        mLSLiveStreamingParaCtx.eHaraWareEncType.hardWareEncEnable = mHardWareEncEnable;//默认关闭硬件编码

        //网络QoS开关
        mLSLiveStreamingParaCtx.sLSQoSParaCtx.qosType = OpenQoS;//默认打开QoS

        //视频编码参数配置，视频码率可以由用户任意设置，视频分辨率按照如下表格设置
        //采集分辨率     编码分辨率     建议码率
        //1280x720     1280x720     1500kbps
        //1280x720     960x540      800kbps
        //960x720      960x720      1000kbps
        //960x720      960x540      800kbps
        //960x540      960x540      800kbps
        //640x480      640x480      600kbps
        //640x480      640x360      500kbps
        //320x240      320x240      250kbps
        //320x240      320x180      200kbps

        //如下是编码分辨率等信息的设置
        if (mVideoResolution.equals("HD")) {
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.fps = 20;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.bitrate = 1500000;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.codec.videoCODECType = LS_VIDEO_CODEC_AVC;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.width = 1280;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.height = 720;
        } else if (mVideoResolution.equals("SD")) {
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.fps = 20;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.bitrate = 600000;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.codec.videoCODECType = LS_VIDEO_CODEC_AVC;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.width = 640;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.height = 480;
        } else {
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.fps = 15;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.bitrate = 250000;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.codec.videoCODECType = LS_VIDEO_CODEC_AVC;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.width = 320;
            mLSLiveStreamingParaCtx.sLSVideoParaCtx.height = 240;
        }
    }

    long clickTime = 0l;
    private Thread mThread;

    //按钮初始化
    public void buttonInit() {

        handler = new Handler();
        //开始直播按钮初始化	
        startPauseResumeBtn = (ImageButton) findViewById(R.id.StartStopAVBtn);
        startPauseResumeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                if (time - clickTime < 1000) {
                    return;
                }

                startPauseResumeBtn.setClickable(false);
                if (!m_liveStreamingOn) {
                    //8、初始化直播推流
                    if (mThread != null) {
                        showToast("正在开启直播，请稍后。。。");
                        return;
                    }
                    showToast("初始化中。。。");
                    mThread = new Thread() {
                        public void run() { //正常网络下initLiveStream 1、2s就可完成，当网络很差时initLiveStream可能会消耗5-10s，因此另起线程防止UI卡住
                            m_liveStreamingInitFinished = mLSMediaCapture.initLiveStream(mLSLiveStreamingParaCtx);
                            if (m_liveStreamingInitFinished) {
                                startAV();
                            } else {
                                showToast("直播开启失败，请仔细检查推流地址, 正在退出当前界面。。。");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        MediaPreviewActivity.this.finish();
                                    }
                                }, 5000);
                            }
                            mThread = null;
                        }
                    };
                    mThread.start();
//					m_liveStreamingInitFinished = mLSMediaCapture.initLiveStream(mliveStreamingURL, mLSLiveStreamingParaCtx);
//					if(m_liveStreamingInitFinished){
//						startAV();
//					}else {
//						showToast("初始化直播失败,正在退出当前界面。。。");
//						mHandler.postDelayed(new Runnable() {
//							@Override
//							public void run() {
//								MediaPreviewActivity.this.finish();
//							}
//						},3000);
//					}
                    startPauseResumeBtn.setImageResource(R.drawable.pause);
                } else {
                    new MaterialDialog.Builder(context)
                            .title("提示")
                            .content("中途休息?")
                            .positiveText("中途休息")
                            .negativeText("结束活动")
                            .onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    StreamRepository.resetPush(getIntent().getStringExtra(ACTIVITY_ID),
                                            getIntent().getStringExtra(PUSH_USERID));
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    StreamRepository.endPush(getIntent().getStringExtra(ACTIVITY_ID),
                                            getIntent().getStringExtra(PUSH_USERID));
                                }
                            })
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    showToast("停止直播中，请稍等。。。");
                                    handler.removeCallbacks(runnable);
                                    mLSMediaCapture.stopLiveStreaming();
                                    startPauseResumeBtn.setImageResource(R.drawable.play);
                                }
                            }).show();
                }
            }
        });


        //切换前后摄像头按钮初始化
        switchBtn = (ImageButton) findViewById(R.id.switchBtn);
        switchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				if(pauseAudio){
//					mLSMediaCapture.backgroundAudioEncode();
//					showToast(getApplication(),"静音",Toast.LENGTH_SHORT).show();
//				}else {
//					showToast(getApplication(),"恢复",Toast.LENGTH_SHORT).show();
//					mLSMediaCapture.resumeAudioEncode();
//				}
//				pauseAudio = !pauseAudio;
                switchCamera();
            }
        });
    }

    boolean pauseAudio = true;

    //切换前后摄像头
    private void switchCamera() {
        if (mLSMediaCapture != null) {
            mLSMediaCapture.switchCamera();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            StreamRepository.keepPush(getIntent().getStringExtra(ACTIVITY_ID),
                    getIntent().getStringExtra(PUSH_USERID));
            handler.postDelayed(this, 10000);
        }
    };

    //开始直播
    private void startAV() {
        if (mLSMediaCapture != null && m_liveStreamingInitFinished) {


            StreamRepository.beginPush(getIntent().getStringExtra(ACTIVITY_ID),
                    getIntent().getStringExtra(PUSH_USERID));


            handler.postDelayed(runnable, 10000);

            //7、开始直播
            mLSMediaCapture.startLiveStreaming();
            m_liveStreamingOn = true;

            //8、设置视频水印参数（可选）
            if (!mHardWareEncEnable && mWaterMarkOn && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO)) {
                mLSMediaCapture.setWaterMarkPara(mWaterMarkFilePath, mWaterMarkPosX, mWaterMarkPosY);
            }

            //9、设置视频动态水印参数（可选）
            if (!mHardWareEncEnable && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO)) {
                try {
                    String[] waters = getAssets().list("dynamicWaterMark");
                    for (int i = 0; i < waters.length; i++) {
                        waters[i] = "dynamicWaterMark/" + waters[i];
                    }

                    if (mDynamicWaterMarkOn && waters != null) {
                        mLSMediaCapture.setDynamicWaterMarkPara(waters, mDynamicWaterMarkFps, mDynamicWaterMarkLooped, mDynamicWaterMarkPosX, mDynamicWaterMarkPosY);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //10、设置视频涂鸦参数（可选）
            if (!mHardWareEncEnable && mGraffitiOn && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO)) {
                FileInputStream is = null;
                try {
                    is = new FileInputStream(mGraffitiFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                int mWidth = bitmap.getWidth();
                int mHeight = bitmap.getHeight();
                int[] mIntArray = new int[mWidth * mHeight];

                // Copy pixel data from the Bitmap into the 'intArray' array
                bitmap.getPixels(mIntArray, 0, mWidth, 0, 0, mWidth, mHeight);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.e(TAG, "error : ", e);
                }

                mLSMediaCapture.setGraffitiPara(mIntArray, mWidth, mHeight, mGraffitiPosX, mGraffitiPosY, mGraffitiOn);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "activity onCreate");
        context = this;

        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //从直播设置页面获取推流URL和分辨率信息
        //alert1用于检测设备SDK版本是否符合开启滤镜要求，alert2用于检测设备的硬件编码模块（与滤镜相关）是否正常
        mliveStreamingURL = getIntent().getStringExtra("mediaPath");
        mVideoResolution = getIntent().getStringExtra("videoResolution");
        mUseFilter = getIntent().getBooleanExtra("filter", false);

        //获取摄像头支持的分辨率信息，根据这个信息，用户可以选择合适的视频preview size和encode size
        //!!!!注意，用户在设置视频采集分辨率和编码分辨率的时候必须预先获取摄像头支持的采集分辨率，并设置摄像头支持的采集分辨率，否则会造成不可预知的错误，导致直播推流失败和SDK崩溃
        //用户可以采用下面的方法(getCameraSupportSize)，获取摄像头支持的采集分辨率列表
        //获取分辨率之后，用户可以按照下表选择性设置采集分辨率和编码分辨率(注意：一定要Android设备支持该种采集分辨率才可以设置，否则会造成不可预知的错误，导致直播推流失败和SDK崩溃)

        //采集分辨率     编码分辨率     建议码率
        //1280x720     1280x720     1500kbps
        //1280x720     960x540      800kbps
        //960x720      960x720      1000kbps
        //960x720      960x540      800kbps
        //960x540      960x540      800kbps
        //640x480      640x480      600kbps
        //640x480      640x360      500kbps
        //320x240      320x240      250kbps
        //320x240      320x180      200kbps


        //用户需要分别检查前后摄像头支持的分辨率，设置前后摄像头都支持的分辨率，保障在切换摄像头的过程中不会出错
//        List<Camera.Size> backCameraSupportSize = getCameraSupportSize(CAMERA_POSITION_BACK);
//        List<Camera.Size> frontCameraSupportSize = getCameraSupportSize(CAMERA_POSITION_FRONT);
//
//        if (backCameraSupportSize != null && backCameraSupportSize.size() != 0) {
//			for (Camera.Size size : backCameraSupportSize) {
//				Log.i(TAG, "backCameraSupportSize " + " is " + size.width + "x" + size.height);
//			}
//		}
//
//        if (frontCameraSupportSize != null && frontCameraSupportSize.size() != 0) {
//			for (Camera.Size size : frontCameraSupportSize) {
//				Log.i(TAG, "frontCameraSupportSize " + " is " + size.width + "x" + size.height);
//			}
//		}

        /** 注意 720p的分辨率属于超高清的分辨率，正常的手机3、4g网络无法达到这样的带宽，
         * 且性能一般的手机在这样的分辨率下也无法达到较高的帧率，这里只作为SDK性能测试使用。
         * 用户在接入正常直播过程中建议使用目前市场上最常用的 640x480 的分辨率*/
        if (mVideoResolution.equals("HD")) {
            mVideoPreviewWidth = 1280;
            mVideoPreviewHeight = 720;
        } else if (mVideoResolution.equals("SD")) {
            mVideoPreviewWidth = 640;
            mVideoPreviewHeight = 480;
        } else {
            mVideoPreviewWidth = 320;
            mVideoPreviewHeight = 240;
        }

        m_liveStreamingOn = false;
        m_tryToStopLivestreaming = false;

        //以下为SDK调用主要步骤，请用户参考使用
        //1、创建直播实例
        lsMediaCapture.LsMediaCapturePara lsMediaCapturePara = new lsMediaCapture.LsMediaCapturePara();
        lsMediaCapturePara.Context = getApplicationContext();
        lsMediaCapturePara.lsMessageHandler = this;
        lsMediaCapturePara.videoPreviewWidth = mVideoPreviewWidth;
        lsMediaCapturePara.videoPreviewHeight = mVideoPreviewHeight;
        lsMediaCapturePara.useFilter = mUseFilter;
        lsMediaCapturePara.logLevel = lsLogUtil.LogLevel.INFO;
        lsMediaCapturePara.uploadLog = false; //是否上传SDK日志
        mLSMediaCapture = new lsMediaCapture(lsMediaCapturePara, mliveStreamingURL);
        //2、设置直播参数
        paraSet();

        //4、根据是否使用滤镜模式和推流方式（音视频或者音频或者视频）选择surfaceview类型
        if ((!mUseFilter && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO))
                || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AUDIO) {
            setContentView(R.layout.activity_live_stream);
            mVideoView = (NeteaseSurfaceView) findViewById(R.id.videoview);
        }

        //6、设置视频预览参数
        if (!mUseFilter && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO)) {
            mVideoView.setPreviewSize(mVideoPreviewWidth, mVideoPreviewHeight);
        }

        if (mLSMediaCapture != null) {

            //7、开启视频预览
            if (!mUseFilter && (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO)) {
                mLSMediaCapture.startVideoPreview(mVideoView, mLSLiveStreamingParaCtx.sLSVideoParaCtx.cameraPosition.cameraPosition);
                m_startVideoCamera = true;
            }

            //【示例代码】设置自定义视频采集类型(如果是自定义YUV则不需要调用startVideoPreview接口)
//			mLSMediaCapture.setSourceType(lsMediaCapture.SourceType.CustomAV);
//			//自定义输入默认是横屏，正的yuv数据
            //【示例代码】结束


        }

        //9、Demo控件的初始化（Demo层实现，用户不需要添加该操作）
        buttonInit();

        //【示例代码 customVideo】设置自定义视频采集逻辑 （自定义视频采集逻辑不要调用startPreview，也不用初始化surfaceView）
//		new Thread() {  //视频采集线程
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						if(!m_liveStreamingOn){
//							continue;
//						}
//						int width = 352;
//						int height = 288;
//						FileInputStream in = new FileInputStream("/sdcard/dump_352_288.yuv");
//						int len = width * height * 3 / 2;
//						byte buffer[] = new byte[len];
//						int count;
//						while ((count = in.read(buffer)) != -1) {
//							if (len == count) {
//								mLSMediaCapture.sendCustomYUVData(buffer,width,height);
//							} else {
//								break;
//							}
//							sleep(50, 0);
//						}
//						in.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}.start();
        //【示例代码】结束

        //【示例代码2】设置自定义音频采集逻辑（音频采样位宽必须是16）
//		new Thread() {  //音频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//						if(!m_liveStreamingOn){
//							continue;
//						}
//                        FileInputStream in = new FileInputStream("/sdcard/dump.pcm");
//                        int len = 2048;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLSMediaCapture.sendCustomPCMData(buffer);
//                            } else {
//                                break;
//                            }
//                            sleep(20, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
        //【示例代码】结束


        //********** 摄像头采集原始数据回调（开发者可以修改该数据，美颜增加滤镜等，推出的流便随之发生变化） *************//
//		mLSMediaCapture.setCaptureRawDataCB(new lsVideoCaptureCallback() {
//			int i = 0;
//			@Override
//			public void onVideoCapture(byte[] data, int width, int height) {
//				// 这里将data直接修改，SDK根据修改后的data数据直接推流
//				if(i % 10 == 0){
//					for(int j = 0; j< 10000;j++){
//						data[j] = 0;
//					}
//				}
//				i++;
//			}
//		});

        //********** 麦克风采集原始数据回调（开发者可以修改该数据，进行降噪、回音消除等，推出的流便随之发生变化） *************//
//		mLSMediaCapture.setAudioRawDataCB(new lsAudioCaptureCallback() {
//			int i = 0;
//			@Override
//			public void onAudioCapture(byte[] data, int len) {
//				// 这里将data直接修改，SDK根据修改后的data数据直接推流
//				if(i % 10 == 0){
//					for(int j = 0; j< 1000;j++){
//						data[j] = 0;
//					}
//				}
//				i++;
//			}
//		});
    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, "activity onDestroy");

        if (mVideoView != null) {
            mVideoView.setVisibility(View.GONE);
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        //停止直播调用相关API接口
        if (mLSMediaCapture != null && m_liveStreamingOn) {

            //停止直播，释放资源
            mLSMediaCapture.stopLiveStreaming();

            //如果音视频或者单独视频直播，需要关闭视频预览
            if (m_startVideoCamera) {
                mLSMediaCapture.stopVideoPreview();
                mLSMediaCapture.destroyVideoPreview();
            }

            //反初始化推流实例，当它与stopLiveStreaming连续调用时，参数为false
            mLSMediaCapture.uninitLsMediaCapture(false);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 2);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        } else if (mLSMediaCapture != null && m_startVideoCamera) {
            mLSMediaCapture.stopVideoPreview();
            mLSMediaCapture.destroyVideoPreview();

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            mLSMediaCapture.uninitLsMediaCapture(true);
            mLSMediaCapture = null;

            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(mIntentLiveStreamingStopFinished);
        } else if (!m_liveStreamingInitFinished) {
            mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(mIntentLiveStreamingStopFinished);

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            mLSMediaCapture.uninitLsMediaCapture(true);
        }

        if (m_liveStreamingOn) {
            m_liveStreamingOn = false;
        }

        super.onDestroy();
    }

    protected void onPause() {
        Log.i(TAG, "Activity onPause");
        if (mLSMediaCapture != null) {
            if (!m_tryToStopLivestreaming && m_liveStreamingOn) {
                StreamRepository.sleepPush(getIntent().getStringExtra(ACTIVITY_ID),
                        getIntent().getStringExtra(PUSH_USERID));
                handler.removeCallbacks(runnable);
                if (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO) {
                    //推最后一帧图像
                    mLSMediaCapture.backgroundVideoEncode();
                } else if (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AUDIO) {
                    //释放音频采集资源
                    mLSMediaCapture.backgroundAudioEncode();
                }
            }
        }
        super.onPause();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        Log.i(TAG, "Activity onResume");
        super.onResume();
        if (mLSMediaCapture != null && m_liveStreamingOn) {
            handler.post(runnable);
            if (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AV || mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_VIDEO) {
                //关闭推流固定图像，正常推流
                mLSMediaCapture.resumeVideoEncode();
            } else if (mLSLiveStreamingParaCtx.eOutStreamType.outputStreamType == HAVE_AUDIO) {
                //关闭推流静音帧
                mLSMediaCapture.resumeAudioEncode();
            }
        }
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (mLSMediaCapture != null) {
            mLSMediaCapture.setFilterStrength(progress);
        }
    }

    //处理SDK抛上来的异常和事件，用户需要在这里监听各种消息，进行相应的处理。
    //例如监听断网消息，用户根据断网消息进行直播重连   
    //注意：直播重连请使用restartLiveStream，在网络带宽较低导致发送码率帧率降低时，可以调用这个接口重启直播，改善直播质量
    //在网络断掉的时候（用户可以监听 MSG_RTMP_ URL_ERROR 和 MSG_BAD_NETWORK_DETECT ），用户不可以立即调用改接口，而是应该在网络重新连接以后，主动调用这个接口。
    //如果在网络没有重新连接便调用这个接口，直播将不会重启
    @Override
    public void handleMessage(int msg, Object object) {
        switch (msg) {
            case MSG_INIT_LIVESTREAMING_OUTFILE_ERROR://初始化直播出错
            case MSG_INIT_LIVESTREAMING_VIDEO_ERROR:
            case MSG_INIT_LIVESTREAMING_AUDIO_ERROR: {
                showToast("初始化直播出错");
                break;
            }
            case MSG_START_LIVESTREAMING_ERROR://开始直播出错
            {
                showToast("开始直播出错：" + object);
                break;
            }
            case MSG_STOP_LIVESTREAMING_ERROR://停止直播出错
            {
                if (m_liveStreamingOn) {
                    showToast("MSG_STOP_LIVESTREAMING_ERROR  停止直播出错");
                }
                break;
            }
            case MSG_AUDIO_PROCESS_ERROR://音频处理出错
            {
                if (m_liveStreamingOn && System.currentTimeMillis() - mLastAudioProcessErrorAlertTime >= 10000) {
                    showToast("音频处理出错");
                    mLastAudioProcessErrorAlertTime = System.currentTimeMillis();
                }

                break;
            }
            case MSG_VIDEO_PROCESS_ERROR://视频处理出错
            {
                if (m_liveStreamingOn && System.currentTimeMillis() - mLastVideoProcessErrorAlertTime >= 10000) {
                    showToast("视频处理出错");
                    mLastVideoProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            }
            case MSG_START_PREVIEW_ERROR://视频预览出错，可能是获取不到camera的使用权限
            {
                Log.i(TAG, "test: in handleMessage, MSG_START_PREVIEW_ERROR");
                showToast("无法打开相机，可能没有相关的权限");
                break;
            }
            case MSG_AUDIO_RECORD_ERROR://音频采集出错，获取不到麦克风的使用权限
            {
                showToast("无法开启；录音，可能没有相关的权限");
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_RECORD_ERROR");
                break;
            }
            case MSG_RTMP_URL_ERROR://断网消息
            {
                Log.i(TAG, "test: in handleMessage, MSG_RTMP_URL_ERROR");
                showToast("MSG_RTMP_URL_ERROR，推流已停止,正在退出当前界面");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MediaPreviewActivity.this.finish();
                    }
                }, 3000);
                break;
            }
            case MSG_URL_NOT_AUTH://直播URL非法，URL格式不符合视频云要求
            {
                showToast("MSG_URL_NOT_AUTH  直播地址不合法");
                break;
            }
            case MSG_SEND_STATICS_LOG_ERROR://发送统计信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_STATICS_LOG_ERROR");
                break;
            }
            case MSG_SEND_HEARTBEAT_LOG_ERROR://发送心跳信息出错
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_HEARTBEAT_LOG_ERROR");
                break;
            }
            case MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR://音频采集参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR://音频参数不支持
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR");
                break;
            }
            case MSG_NEW_AUDIORECORD_INSTANCE_ERROR://音频实例初始化出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_NEW_AUDIORECORD_INSTANCE_ERROR");
                break;
            }
            case MSG_AUDIO_START_RECORDING_ERROR://音频采集出错
            {
                Log.i(TAG, "test: in handleMessage, MSG_AUDIO_START_RECORDING_ERROR");
                break;
            }
            case MSG_QOS_TO_STOP_LIVESTREAMING://网络QoS极差，视频码率档次降到最低
            {
                showToast("MSG_QOS_TO_STOP_LIVESTREAMING");
                Log.i(TAG, "test: in handleMessage, MSG_QOS_TO_STOP_LIVESTREAMING");
                break;
            }
            case MSG_HW_VIDEO_PACKET_ERROR://视频硬件编码出错反馈消息
            {
                break;
            }
            case MSG_WATERMARK_INIT_ERROR://视频水印操作初始化出错
            {
                break;
            }
            case MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR://视频水印图像超出原始视频出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PIC_OUT_OF_VIDEO_ERROR");
                break;
            }
            case MSG_WATERMARK_PARA_ERROR://视频水印参数设置出错
            {
                //Log.i(TAG, "test: in handleMessage: MSG_WATERMARK_PARA_ERROR");
                break;
            }
            case MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR://camera采集分辨率不支持
            {
                //Log.i(TAG, "test: in handleMessage: MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR");
                break;
            }
            case MSG_START_PREVIEW_FINISHED://camera采集预览完成
            {
                Log.i(TAG, "test: MSG_START_PREVIEW_FINISHED");

                break;
            }
            case MSG_START_LIVESTREAMING_FINISHED://开始直播完成
            {
                Log.i(TAG, "test: MSG_START_LIVESTREAMING_FINISHED");
                showToast("直播开始");
                m_liveStreamingOn = true;
                startPauseResumeBtn.setClickable(true);
                break;
            }
            case MSG_STOP_LIVESTREAMING_FINISHED://停止直播完成
            {
                Log.i(TAG, "test: MSG_STOP_LIVESTREAMING_FINISHED");
                showToast("停止直播已完成");
                m_liveStreamingOn = false;
                startPauseResumeBtn.setClickable(true);
                {
                    mIntentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
                    sendBroadcast(mIntentLiveStreamingStopFinished);
                }

//				  mIntentLiveStreamingStopFinished = null;
//				  mStatistics = null;
//				  mHandler.removeCallbacksAndMessages(null);
//				  mHandler = null;

                break;
            }
            case MSG_STOP_VIDEO_CAPTURE_FINISHED: {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_VIDEO_CAPTURE_FINISHED");
                break;
            }
            case MSG_STOP_AUDIO_CAPTURE_FINISHED: {
                Log.i(TAG, "test: in handleMessage: MSG_STOP_AUDIO_CAPTURE_FINISHED");
                break;
            }
            case MSG_SWITCH_CAMERA_FINISHED://切换摄像头完成
            {
                int cameraId = (Integer) object;//切换之后的camera id
                break;
            }
            case MSG_SEND_STATICS_LOG_FINISHED://发送统计信息完成
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SEND_STATICS_LOG_FINISHED");
                break;
            }
            case MSG_SERVER_COMMAND_STOP_LIVESTREAMING://服务器下发停止直播的消息反馈，暂时不使用
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SERVER_COMMAND_STOP_LIVESTREAMING");
                break;
            }
            case MSG_GET_STATICS_INFO://获取统计信息的反馈消息
            {
                //Log.i(TAG, "test: in handleMessage, MSG_GET_STATICS_INFO");

                Message message = Message.obtain(mHandler, MSG_GET_STATICS_INFO);
                mStatistics = (Statistics) object;

                Bundle bundle = new Bundle();
                bundle.putInt("FR", mStatistics.videoEncodeFrameRate);
                bundle.putInt("VBR", mStatistics.videoEncodeBitRate);
                bundle.putInt("ABR", mStatistics.audioEncodeBitRate);
                bundle.putInt("TBR", mStatistics.totalRealSendBitRate);
                message.setData(bundle);

                if (mHandler != null) {
                    mHandler.sendMessage(message);
                }
                break;
            }
            case MSG_BAD_NETWORK_DETECT://如果连续一段时间（10s）实际推流数据为0，会反馈这个错误消息
            {
                showToast("MSG_BAD_NETWORK_DETECT");
                //Log.i(TAG, "test: in handleMessage, MSG_BAD_NETWORK_DETECT");
                break;
            }
            case MSG_SCREENSHOT_FINISHED://视频截图完成后的消息反馈
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SCREENSHOT_FINISHED, buffer is " + (byte[]) object);
                getScreenShotByteBuffer((byte[]) object);

                break;
            }
            case MSG_SET_CAMERA_ID_ERROR://设置camera出错（对于只有一个摄像头的设备，如果调用了不存在的摄像头，会反馈这个错误消息）
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SET_CAMERA_ID_ERROR");
                break;
            }
            case MSG_SET_GRAFFITI_ERROR://设置涂鸦出错消息反馈
            {
                //Log.i(TAG, "test: in handleMessage, MSG_SET_GRAFFITI_ERROR");
                break;
            }
            case MSG_MIX_AUDIO_FINISHED://伴音一首MP3歌曲结束后的反馈
            {
                //Log.i(TAG, "test: in handleMessage, MSG_MIX_AUDIO_FINISHED");
                break;
            }
            case MSG_URL_FORMAT_NOT_RIGHT://推流url格式不正确
            {
                //Log.i(TAG, "test: in handleMessage, MSG_URL_FORMAT_NOT_RIGHT");
                showToast("MSG_URL_FORMAT_NOT_RIGHT");
                break;
            }
            case MSG_URL_IS_EMPTY://推流url为空
            {
                //Log.i(TAG, "test: in handleMessage, MSG_URL_IS_EMPTY");
                break;
            }

            default:
                break;

        }
    }

    //获取截屏图像的数据
    public void getScreenShotByteBuffer(byte[] screenShotByteBuffer) {
        FileOutputStream outStream = null;
        String screenShotFilePath = mScreenShotFilePath + mScreenShotFileName;
        try {
            outStream = new FileOutputStream(String.format(screenShotFilePath));
            outStream.write(screenShotByteBuffer);
            showToast("截图已保存到SD下的test.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Demo层视频缩放和摄像头对焦操作相关方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.i(TAG, "test: down!!!");
                //调用摄像头对焦操作相关API
                if (mLSMediaCapture != null) {
                    mLSMediaCapture.setCameraFocus();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.i(TAG, "test: move!!!");
                /**
                 * 首先判断按下手指的个数是不是大于两个。 
                 * 如果大于两个则执行以下操作（即图片的缩放操作）。 
                 */
                if (event.getPointerCount() >= 2) {

                    float offsetX = event.getX(0) - event.getX(1);
                    float offsetY = event.getY(0) - event.getY(1);
                    /**
                     * 原点和滑动后点的距离差 
                     */
                    mCurrentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);
                    if (mLastDistance < 0) {
                        mLastDistance = mCurrentDistance;
                    } else {
                        /**
                         * 如果当前滑动的距离（currentDistance）比最后一次记录的距离（lastDistance）相比大于5英寸（也可以为其他尺寸），
                         * 那么现实图片放大
                         */
                        if (mCurrentDistance - mLastDistance > 5) {
                            //Log.i(TAG, "test: 放大！！！");  
                            if (mLSMediaCapture != null) {
                                mLSMediaCapture.setCameraZoomPara(true);
                            }

                            mLastDistance = mCurrentDistance;
                            /**
                             * 如果最后的一次记录的距离（lastDistance）与当前的滑动距离（currentDistance）相比小于5英寸， 
                             * 那么图片缩小。 
                             */
                        } else if (mLastDistance - mCurrentDistance > 5) {
                            //Log.i(TAG, "test: 缩小！！！");
                            if (mLSMediaCapture != null) {
                                mLSMediaCapture.setCameraZoomPara(false);
                            }

                            mLastDistance = mCurrentDistance;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //Log.i(TAG, "test: up!!!");
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        m_tryToStopLivestreaming = true;
    }

}
