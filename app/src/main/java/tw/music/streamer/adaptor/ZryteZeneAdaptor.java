package tw.music.streamer.adaptor;

import android.content.Intent;

import tw.music.streamer.service.ZryteZenePlay;

public class ZryteZeneAdaptor {

    private boolean isr, isp;
    private int cd;

    public void ZryteZeneAdaptor() {
        isr = false;
        isp = false;
        cd = 0;
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

    public void clear() {
        requestAction("stop","-");
    }

    public void requestAction(String a) {
        Intent jof = new Intent(getApplicationContext(), ZryteZenePlay.class);
        jof.putExtra("action", a);
        sendBroadcast(jof);
    }

    public void requestAction(String a, int b) {
        Intent jof = new Intent(getApplicationContext(), ZryteZenePlay.class);
        jof.putExtra("action", a);
        jof.putExtra("req-data", b);
        sendBroadcast(jof);
    }

    public void requestAction(String a, String b) {
        Intent jof = new Intent(getApplicationContext(), ZryteZenePlay.class);
        jof.putExtra("action", a);
        jof.putExtra("req-data", b);
        sendBroadcast(jof);
    }
}