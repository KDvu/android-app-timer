package com.example.kdvu.timer;

public class Times {
    private int _id;
    private int _hour;
    private int _min;
    private int _sec;

    public Times(){}

    public Times(int hour, int min, int sec){
        _hour = hour;
        _min = min;
        _sec = sec;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_hour() {
        return _hour;
    }

    public void set_hour(int _hour) {
        this._hour = _hour;
    }

    public int get_min() {
        return _min;
    }

    public void set_min(int _min) {
        this._min = _min;
    }

    public int get_sec() {
        return _sec;
    }

    public void set_sec(int _sec) {
        this._sec = _sec;
    }
}
