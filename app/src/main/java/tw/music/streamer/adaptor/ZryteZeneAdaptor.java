package tw.music.streamer.adaptor;

import android.content.Intent;

import tw.music.streamer.service.ZryteZenePlay;

public class ZryteZeneAdaptor { //line 769

    public void ZryteZeneAdaptor() {
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