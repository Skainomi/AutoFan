package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.DataDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DataDao dataDao;
    FragmentManager fragmentManager;
    private static PowerFragment powerFragment;
    private static TimerFragment timerFragment;
    private static ScheduleFragment scheduleFragment;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataDao = new DataDao();
        dataDao.dataCheck();
        dataDao.getDeviceStatus();
        this.dataDao = new DataDao();
        this.fragmentManager = getSupportFragmentManager();
        MainActivity.powerFragment = new PowerFragment();
        MainActivity.timerFragment = new TimerFragment();
        MainActivity.scheduleFragment = new ScheduleFragment();

        FragmentManager fragment = getSupportFragmentManager();
        fragment.beginTransaction()
                .add(R.id.fl_control, powerFragment, PowerFragment.class.getSimpleName())
                .commit();

        binding.btnPower.setOnClickListener(this);
        binding.btnTimer.setOnClickListener(this);
        binding.btnSchedule.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Fragment fragment = powerFragment;
        String tag = "";
        switch (view.getId()) {
            case R.id.btn_power:
                fragment = powerFragment;
                tag = PowerFragment.class.getSimpleName();
                backgroundChanger(binding.btnPower);
                break;
            case R.id.btn_timer:
                fragment = timerFragment;
                tag = TimerFragment.class.getSimpleName();
                backgroundChanger(binding.btnTimer);
                break;
            case R.id.btn_schedule:
                fragment = scheduleFragment;
                tag = ScheduleFragment.class.getSimpleName();
                backgroundChanger(binding.btnSchedule);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_control, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public void backgroundChanger(AppCompatButton button){
        binding.btnPower.setBackgroundResource(R.drawable.white_all_corner);
        binding.btnPower.setTextColor(this.getResources().getColor(R.color.black));
        binding.btnSchedule.setBackgroundResource(R.drawable.white_all_corner);
        binding.btnSchedule.setTextColor(this.getResources().getColor(R.color.black));
        binding.btnTimer.setBackgroundResource(R.drawable.white_all_corner);
        binding.btnTimer.setTextColor(this.getResources().getColor(R.color.black));
        button.setBackgroundResource(R.drawable.btn_green_background);
        button.setTextColor(this.getResources().getColor(R.color.white));
    }

}