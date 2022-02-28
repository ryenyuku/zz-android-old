package tw.music.streamer;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class ColpickActivity extends AppCompatActivity {


    private ScrollView vscroll1;
    private LinearLayout linear_colpick;
    private LinearLayout bg_result;
    private LinearLayout linear3;
    private LinearLayout linear9;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private LinearLayout linear10;
    private TextView text_hastag;
    private EditText edit_hex;
    private TextView text_a;
    private TextView textview2;
    private SeekBar seek_a;
    private TextView textn_a;
    private TextView text_r;
    private TextView red_swatch;
    private SeekBar seek_r;
    private TextView textn_r;
    private TextView text_g;
    private TextView green_swatch;
    private SeekBar seek_g;
    private TextView textn_g;
    private TextView text_b;
    private TextView blue_swatch;
    private SeekBar seek_b;
    private TextView textn_b;
    private TextView text_plhwarna;
    private TextView text_axhyre;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.colpick);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        vscroll1 = findViewById(R.id.vscroll1);
        linear_colpick = findViewById(R.id.linear_colpick);
        bg_result = findViewById(R.id.bg_result);
        linear3 = findViewById(R.id.linear3);
        linear9 = findViewById(R.id.linear9);
        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear8 = findViewById(R.id.linear8);
        linear10 = findViewById(R.id.linear10);
        text_hastag = findViewById(R.id.text_hastag);
        edit_hex = findViewById(R.id.edit_hex);
        text_a = findViewById(R.id.text_a);
        textview2 = findViewById(R.id.textview2);
        seek_a = findViewById(R.id.seek_a);
        textn_a = findViewById(R.id.textn_a);
        text_r = findViewById(R.id.text_r);
        red_swatch = findViewById(R.id.red_swatch);
        seek_r = findViewById(R.id.seek_r);
        textn_r = findViewById(R.id.textn_r);
        text_g = findViewById(R.id.text_g);
        green_swatch = findViewById(R.id.green_swatch);
        seek_g = findViewById(R.id.seek_g);
        textn_g = findViewById(R.id.textn_g);
        text_b = findViewById(R.id.text_b);
        blue_swatch = findViewById(R.id.blue_swatch);
        seek_b = findViewById(R.id.seek_b);
        textn_b = findViewById(R.id.textn_b);
        text_plhwarna = findViewById(R.id.text_plhwarna);
        text_axhyre = findViewById(R.id.text_axhyre);
    }

    private void initializeLogic() {
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
