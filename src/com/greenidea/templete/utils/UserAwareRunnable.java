package com.greenidea.templete.utils;

import android.app.ProgressDialog;
import android.os.Message;
import com.greenidea.templete.base.BaseBarActivity;

import java.lang.ref.WeakReference;

/**
 * 用户明确知道的、并要求用户等待的工作线程，通常通过一个弹出框提示加载中。
 * 用户可以按返回键中断线程的操作
 *
 * Created by Green on 2014/12/26.
 */
public class UserAwareRunnable implements Runnable {

    private WeakReference<Thread> runningThread = null;

    private Runnable runnable;

    private  Handler handler;

    public UserAwareRunnable(Runnable runnable, Handler handler)
    {
        this.runnable = runnable;
        this.handler = handler;
    }

    @Override
    public void run()
    {
        runningThread = new WeakReference<Thread>(Thread.currentThread());
        //显示提示框
        handler.informBusy();

        runnable.run();
    }


    /**
     * 中断当前run方法所在线程
    */
    public void interrupt()
    {
        handler.hideDialog();
        if(null != runningThread.get())
        {
            runningThread.get().interrupt();
        }
    }

    /**
     * 与@UserAwareRunnable一起使用的Handler，可以使用本类自动管理弹出框的弹出和关闭
     * <br>有多个UserAwareThread弹出提示框遮挡时，弹出框隐藏也不会显示混乱
     * Created by Green on 2014/12/26.
     */
    public static class Handler extends android.os.Handler {
        private BaseBarActivity hostActivity;
        private String message;
        private ProgressDialog dialog;

        public Handler(BaseBarActivity hostActivity, String message) {
            this.hostActivity = hostActivity;
            this.message = message;
        }

        void informBusy()
        {
            dialog = new ProgressDialog(hostActivity);

            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.show();
        }

        void hideDialog()
        {
            if(dialog != null && dialog.isShowing())
            {
                dialog.dismiss();
            }
        }

        @Override
        public void handleMessage(Message msg)
        {
            dialog.dismiss();
        }
    }
}
