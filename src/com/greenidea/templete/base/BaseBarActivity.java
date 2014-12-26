package com.greenidea.templete.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.Toast;
import com.greenidea.templete.R;
import com.greenidea.templete.utils.UserAwareRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseBarActivity extends ActionBarActivity {

    private static ExecutorService pool;

    private Stack<UserAwareRunnable> userAwareRunnableList;

    /**
     * 在其他线程中执行任务，并在界面弹出提示框提示用户
     * @param runnable
     * @param handler
     */
    protected void doUserAwareJob(Runnable runnable, UserAwareRunnable.Handler handler)
    {
        if(null == userAwareRunnableList)
        {
            userAwareRunnableList = new Stack<UserAwareRunnable>();
        }
        if(null == pool)
        {
            pool = Executors.newCachedThreadPool();
        }

        UserAwareRunnable userAwareRunnable = new UserAwareRunnable(runnable, handler);
        userAwareRunnableList.add(userAwareRunnable);

        pool.execute(userAwareRunnable);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                return super.onKeyDown(keyCode, event);
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

}
