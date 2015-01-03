package com.greenidea.templete;

import android.os.Bundle;
import com.greenidea.templete.base.BaseBarActivity;

public class MainActivity extends BaseBarActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
