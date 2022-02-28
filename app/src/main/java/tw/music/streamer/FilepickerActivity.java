package tw.music.streamer;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

public class FilepickerActivity extends AppCompatActivity {

    public final int REQ_CD_FP = 101;

    private Toolbar _toolbar;

    private ArrayList<String> vd = new ArrayList<>();

    private TextView textview1;

    private Intent intent = new Intent();
    private SharedPreferences data;
    private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.filepicker);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        _toolbar = findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        textview1 = findViewById(R.id.textview1);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        fp.setType("*/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    }

    private void initializeLogic() {
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        setTitle("zzfilepicker://");
        if (getIntent().getStringExtra("fileType").contains("/")) {
            fp.setType(getIntent().getStringExtra("fileType"));
        } else {
            fp.setType("*/*");
        }
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(fp, REQ_CD_FP);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_FP:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    if (getIntent().getStringExtra("fileType").contains("/")) {
                        data.edit().putString("tmpPath", _filePath.get(0)).commit();
                        finish();
                    } else {
                        if (_filePath.get(0).endsWith(getIntent().getStringExtra("fileType"))) {
                            data.edit().putString("tmpPath", _filePath.get(0)).commit();
                            finish();
                        } else {
                            finish();
                        }
                    }
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
		/*
}

private String queryName(Uri uri) {
    java.io.File file = new java.io.File(uri.getPath());
    return file.getName();
}

@Override
protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
  super.onActivityResult(_requestCode, _resultCode, _data);

  switch(_requestCode) {
    case REQ_CD_FP:
      if (_resultCode == Activity.RESULT_OK) {
        if (_data != null) {
          textview1.setText(queryName(_data.getData()));
        }
      }
      else {
        finish();
      }
      break;
    default:
      break;
  }
}

@Override
public void onBackPressed() {
*/
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        _DarkMode();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }


    private void _DarkMode() {
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            textview1.setBackgroundColor(0xFF252525);
            textview1.setTextColor(0xFFFFFFFF);
            _customNav("#252525");
        } else {
            textview1.setBackgroundColor(0xFFFFFFFF);
            textview1.setTextColor(0xFF000000);
            _customNav("#FFFFFF");
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
