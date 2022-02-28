package tw.music.streamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DaeditActivity extends AppCompatActivity {


    private FloatingActionButton _fab;

    private ArrayList<HashMap<String, Object>> dataMap = new ArrayList<>();
    private ArrayList<String> dialog_list = new ArrayList<>();
    private ArrayList<String> childkey = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView imageview_back;
    private TextView textview_title;
    private ListView listview1;

    private SharedPreferences data;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.daedit);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        _fab = findViewById(R.id._fab);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        imageview_back = findViewById(R.id.imageview_back);
        textview_title = findViewById(R.id.textview_title);
        listview1 = findViewById(R.id.listview1);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        dialog = new AlertDialog.Builder(this);

        imageview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                double _tmpNum = 0;
                childkey.clear();
                for (int _repeat11 = 0; _repeat11 < dataMap.size(); _repeat11++) {
                    if (dataMap.get((int) _tmpNum).get("type").toString().equals("key")) {
                        childkey.add(dataMap.get((int) _tmpNum).get("key").toString());
                    }
                    _tmpNum++;
                }
                childkey.add("Add new childkey");
                dialog.setCancelable(false);
                dialog.setAdapter(new ArrayAdapter(DaeditActivity.this, android.R.layout.simple_list_item_1, childkey), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int _pos_dialog) {
                        _resetDialog();
                        if (_pos_dialog == (childkey.size() - 1)) {
                            _customSnack("Not implemented yet.", 2);
                        } else {
                            _add(childkey.get(_pos_dialog));
                        }
                    }
                });
                dialog.create().show();
            }
        });
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        _loadTheme();
        textview_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        dialog_list.add("Edit");
        dialog_list.add("Delete");
        dialog_list.add("Cancel");
        _retrieveData();
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

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

    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }


    private void _retrieveData() {
        dataMap.clear();
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("key", "teamdata");
            dataMap.add(_item);
        }

        dataMap.get(dataMap.size() - 1).put("type", "key");
        Map<String, ?> _allEntries = data.getAll();
        for (Map.Entry<String, ?> _entry : _allEntries.entrySet()) {
            {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put("key", _entry.getKey());
                dataMap.add(_item);
            }

            dataMap.get(dataMap.size() - 1).put("value", _entry.getValue());
            dataMap.get(dataMap.size() - 1).put("type", "subkey");
            dataMap.get(dataMap.size() - 1).put("childkey", "teamdata");
        }
        int _index = listview1.getFirstVisiblePosition();
        View _view = listview1.getChildAt(0);
        int _top = (_view == null) ? 0 : _view.getTop();
        listview1.setAdapter(new Listview1Adapter(dataMap));
        listview1.setSelectionFromTop(_index, _top);
    }


    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }


    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }


    private void _customSnack(final String _txt, final double _icon) {
        // Create the Snackbar
        ViewGroup containerLayout = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(containerLayout, "", com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        com.google.android.material.snackbar.Snackbar.SnackbarLayout layout = (com.google.android.material.snackbar.Snackbar.SnackbarLayout) snackbar.getView();
        // Inflate our custom view
        View snackview = getLayoutInflater().inflate(R.layout.custom_snack, null);
        // Configure the view
        ImageView image = snackview.findViewById(R.id.imageview);
        if (_icon == 0) {
            image.setImageResource(R.drawable.ic_info_outline_white);
            layout.setBackgroundColor(Color.parseColor("#2090F0"));
        } else {
            if (_icon == 1) {
                image.setImageResource(R.drawable.ic_done_white);
                layout.setBackgroundColor(Color.parseColor("#48B048"));
            } else {
                if (_icon == 2) {
                    image.setImageResource(R.drawable.ic_exit_white);
                    layout.setBackgroundColor(Color.parseColor("#E03830"));
                }
            }
        }
        TextView text = snackview.findViewById(R.id.textview);
        text.setText(_txt);
        text.setTextColor(Color.WHITE);
        text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        layout.setPadding(0, 0, 0, 0);
        // Add the view to the Snackbar's layout
        layout.addView(snackview, 0);
        // Show the Snackbar
        snackbar.show();
    }


    private void _resetDialog() {
        dialog = null;
        dialog = new AlertDialog.Builder(this);
    }


    private void _add(final String _childKey) {
        LinearLayout mylayout = new LinearLayout(DaeditActivity.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mylayout.setLayoutParams(params);
        mylayout.setOrientation(LinearLayout.VERTICAL);

        final EditText myedittext = new EditText(DaeditActivity.this);
        myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        myedittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        mylayout.addView(myedittext);
        dialog.setView(mylayout);
        myedittext.setHint("Key");
        dialog.setCancelable(false);
        dialog.setTitle("Add subkey");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
                _resetDialog();
                _add2(_childKey, myedittext.getText().toString());
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
                _resetDialog();
            }
        });
        dialog.create().show();
    }


    private void _add2(final String _childKey, final String _key) {
        LinearLayout mylayout = new LinearLayout(DaeditActivity.this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mylayout.setLayoutParams(params);
        mylayout.setOrientation(LinearLayout.VERTICAL);

        final EditText myedittext = new EditText(DaeditActivity.this);
        myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

        mylayout.addView(myedittext);
        dialog.setView(mylayout);
        myedittext.setHint("Value");
        dialog.setCancelable(false);
        dialog.setTitle("Add subkey");
        dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
                _resetDialog();
                data = getSharedPreferences(_childKey, Activity.MODE_PRIVATE);
                data.edit().putString(_key, myedittext.getText().toString()).commit();
                _retrieveData();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                _abandonFocus();
                _resetDialog();
            }
        });
        dialog.create().show();
    }


    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview_back);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
        }
        _fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor(theme_map.get(0).get("colorButton").toString())));
        _fab.setRippleColor(Color.parseColor(theme_map.get(0).get("colorRipple").toString()));
        _fab.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()));
        textview_title.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        linear2.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        imageview_back.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
    }


    private void _BlackStatusBarIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private void _rippleLib() {
    }

    public void drawableclass() {
    }

    private void _abandonFocus() {
        View _tmpView = this.getCurrentFocus();
        if (_tmpView != null) {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(_tmpView.getWindowToken(), 0);
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

    public static class Drawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[8];
            Arrays.fill(outerRadii, 0);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

    public static class CircleDrawables {
        public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
                );
                return stateListDrawable;
            } else {
                android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
                android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));

                android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
                return new android.graphics.drawable.RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        private static android.graphics.drawable.Drawable getRippleColor(int color) {
            float[] outerRadii = new float[180];
            Arrays.fill(outerRadii, 80);
            android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);

            android.graphics.drawable.ShapeDrawable shapeDrawable = new
                    android.graphics.drawable.ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }
    }

    public class Listview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _view, ViewGroup _viewGroup) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.daeditlayout, null);
            }

            final LinearLayout linear1 = _v.findViewById(R.id.linear1);
            final ImageView imageview2 = _v.findViewById(R.id.imageview2);
            final ImageView imageview1 = _v.findViewById(R.id.imageview1);
            final TextView textview1 = _v.findViewById(R.id.textview1);

            if (_data.get(_position).get("type").toString().equals("key")) {
                textview1.setText(_data.get(_position).get("key").toString());
                imageview1.setImageResource(R.drawable.ic_folder);
            }
            if (_data.get(_position).get("type").toString().equals("subkey")) {
                textview1.setText(_data.get(_position).get("key").toString().concat(": ".concat(_data.get(_position).get("value").toString())));
                imageview1.setImageResource(R.drawable.ic_file);
            }
            if (_data.get(_position).containsKey("childkey")) {
                imageview2.setVisibility(View.VISIBLE);
            } else {
                imageview2.setVisibility(View.GONE);
            }
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
            textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
            imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
            imageview2.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
            linear1.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(theme_map.get(0).get("colorRipple").toString())));
            linear1.setClickable(true);
            linear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    dialog.setCancelable(false);
                    dialog.setTitle(dataMap.get(_position).get("key").toString());
                    if (dataMap.get(_position).get("type").toString().equals("key")) {
                        String _tmpKeys = "";
                        double _tmpNum = 0;
                        for (int _repeat73 = 0; _repeat73 < dataMap.size(); _repeat73++) {
                            if (dataMap.get((int) _tmpNum).get("type").toString().equals("subkey")) {
                                if (dataMap.get((int) _tmpNum).get("childkey").toString().equals(dataMap.get(_position).get("key").toString())) {
                                    _tmpKeys = _tmpKeys.concat(dataMap.get((int) _tmpNum).get("key").toString().concat("\n"));
                                }
                            }
                            if (_tmpNum == (dataMap.size() - 1)) {
                                dialog.setMessage(_tmpKeys);
                            }
                            _tmpNum++;
                        }
                    } else {
                        if (dataMap.get(_position).get("type").toString().equals("subkey")) {
                            dialog.setMessage(dataMap.get(_position).get("value").toString());
                        }
                    }
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            _resetDialog();
                        }
                    });
                    dialog.create().show();
                }
            });
            linear1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View _vlol) {
                    dialog.setCancelable(false);
                    dialog.setAdapter(new ArrayAdapter(DaeditActivity.this, android.R.layout.simple_list_item_1, dialog_list), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dia, int _pos_dialog) {
                            _resetDialog();
                            if (_pos_dialog == 0) {
                                if (dataMap.get(_position).get("type").toString().equals("key")) {
                                    _customSnack("Not implemented yet.", 2);
                                } else {
                                    if (dataMap.get(_position).get("type").toString().equals("subkey")) {
                                        LinearLayout mylayout = new LinearLayout(DaeditActivity.this);

                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        mylayout.setLayoutParams(params);
                                        mylayout.setOrientation(LinearLayout.VERTICAL);

                                        final EditText myedittext = new EditText(DaeditActivity.this);
                                        myedittext.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                                        myedittext.setText(dataMap.get(_position).get("value").toString());

                                        mylayout.addView(myedittext);
                                        dialog.setView(mylayout);
                                        myedittext.setHint("Value");
                                        dialog.setCancelable(false);
                                        dialog.setTitle("Change value");
                                        dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface _dialog, int _which) {
                                                _abandonFocus();
                                                _resetDialog();
                                                data = getSharedPreferences(dataMap.get(_position).get("childkey").toString(), Activity.MODE_PRIVATE);
                                                data.edit().putString(dataMap.get(_position).get("key").toString(), myedittext.getText().toString()).commit();
                                                _retrieveData();
                                            }
                                        });
                                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface _dialog, int _which) {
                                                _abandonFocus();
                                                _resetDialog();
                                            }
                                        });
                                        dialog.create().show();
                                    }
                                }
                            } else {
                                if (_pos_dialog == 1) {
                                    if (dataMap.get(_position).get("type").toString().equals("key")) {
                                        data = getSharedPreferences(dataMap.get(_position).get("key").toString(), Activity.MODE_PRIVATE);
                                        data.edit().clear().commit();
                                        _retrieveData();
                                    } else {
                                        if (dataMap.get(_position).get("type").toString().equals("subkey")) {
                                            data = getSharedPreferences(dataMap.get(_position).get("childkey").toString(), Activity.MODE_PRIVATE);
                                            data.edit().remove(dataMap.get(_position).get("key").toString()).commit();
                                            _retrieveData();
                                        }
                                    }
                                } else {
                                    if (_pos_dialog == 2) {

                                    }
                                }
                            }
                        }
                    });
                    dialog.create().show();
                    return true;
                }
            });

            return _v;
        }
    }

}
