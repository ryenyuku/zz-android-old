package tw.music.streamer.adaptor;

import android.content.Intent;
import android.content.Context;

import java.util.ArrayList;

import tw.music.streamer.service.ZryteZenePlay;

public class ZryteZeneAdaptor {

    private boolean isr, isp;
    private int cd, bu;
    private ArrayList<String> e;
    private Context ctx;

    public ZryteZeneAdaptor(Context a) {
        isr = false;
        isp = false;
        cd = 0;
        e = new ArrayList<>();
        ctx = a;
    }

    public void setRunning(boolean a) {
        isr = a;
    }

    public boolean isRunning() {
        return isr;
    }

    public void setPlaying(boolean a) {
        isp = a;
    }

    public boolean isPlaying() {
        return isp;
    }

    public void setCurrentDuration(int a) {
        cd = a;
    }

    public int getCurrentDuration() {
        return cd;
    }

    public void setBufferingUpdate(int a) {
        bu = a;
    }

    public int getBufferingUpdate() {
        return bu;
    }

    public void clear() {
        requestAction("stop","-");
    }

    public void addError(String a) {
        e.add(a);
    }

    public void requestAction(String a) {
        Intent jof = new Intent(ctx, ZryteZenePlay.class);
        jof.putExtra("action", a);
        ctx.sendBroadcast(jof);
    }

    public void requestAction(String a, int b) {
        Intent jof = new Intent(ctx, ZryteZenePlay.class);
        jof.putExtra("action", a);
        jof.putExtra("req-data", b);
        ctx.sendBroadcast(jof);
    }

    public void requestAction(String a, String b) {
        Intent jof = new Intent(ctx, ZryteZenePlay.class);
        jof.putExtra("action", a);
        jof.putExtra("req-data", b);
        ctx.sendBroadcast(jof);
    }
}