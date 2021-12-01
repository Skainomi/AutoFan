package com.example.myapplication.model;

import java.util.Date;

public class Data {
    public static int fanSpeed;
    public static int online;
    public static int timer;
    public static Date timerDate;
    public static int deviceStatus = 0;


    public Data() {
    }

    public Data(int fanSpeed, int online, int timer, Date timerDate) {
        Data.fanSpeed = fanSpeed;
        Data.online = online;
        Data.timer = timer;
        Data.timerDate = timerDate;
    }
}
