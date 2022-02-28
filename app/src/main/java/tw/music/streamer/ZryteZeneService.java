package tw.music.streamer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ZryteZeneService extends Service {

    private ZryteZeneServiceBinder zzServiceBinder = new ZryteZeneServiceBinder();

    public ZryteZeneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return zzServiceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        zzServiceBinder.stopService();
        return super.onUnbind(intent);
    }
}
