package com.niu.user.entrty;

/**
 * Created by niu on 2017/7/1.
 *
 * 本地通话记录信息
 */

public class CallLogInfoBean {
    private int id;
    private String name; // 名称
    private String number; // 号码
    private String date; // 日期
    private int type; // 来电:1，拨出:2,未接:3
    private int count; // 通话次数
    private long duration;//通话时长

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CallLogInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", count=" + count +
                ", duration=" + duration +
                '}';
    }
}
