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
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ThemesstrActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private FloatingActionButton _fab;
    private boolean isOpened = false;
    private HashMap<String, Object> map_var = new HashMap<>();
    private boolean isAdmin = false;
    private double num = 0;
    private HashMap<String, Object> tmpMap = new HashMap<>();

    private ArrayList<HashMap<String, Object>> fb_listmap = new ArrayList<>();
    private ArrayList<String> childKey = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> themes_map = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> gridMap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> gridtmp = new ArrayList<>();
    private ArrayList<String> adminsList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> profile_map = new ArrayList<>();
    private ArrayList<String> proChildKeys = new ArrayList<>();
    private ArrayList<String> indexTheme = new ArrayList<>();
    private ArrayList<String> indexThmStr = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private ImageView imageview1;
    private TextView textview1;
    private ListView listview1;
    private LinearLayout linear_grid;

    private DatabaseReference fb_themes = _firebase.getReference("upload/themes");
    private ChildEventListener _fb_themes_child_listener;
    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private DatabaseReference profile = _firebase.getReference("profile/text");
    private ChildEventListener _profile_child_listener;
    private DatabaseReference profile_admins = _firebase.getReference("profile/admins");
    private ChildEventListener _profile_admins_child_listener;
    private AlertDialog.Builder d;
    private SharedPreferences data;
    private GridView grid;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.themesstr);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        _fab = findViewById(R.id._fab);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        imageview1 = findViewById(R.id.imageview1);
        textview1 = findViewById(R.id.textview1);
        listview1 = findViewById(R.id.listview1);
        linear_grid = findViewById(R.id.linear_grid);
        Auth = FirebaseAuth.getInstance();
        d = new AlertDialog.Builder(this);
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (isOpened) {
                    isOpened = false;
                    _fab.show();
                    listview1.setVisibility(View.VISIBLE);
                    linear_grid.setVisibility(View.GONE);
                    textview1.setText("Themes Store");
                } else {
                    finish();
                }
            }
        });

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                final int _position = _param3;
                d.setCancelable(false);
                d.setTitle("Download theme");
                d.setMessage("Do you want to download \"".concat(fb_listmap.get(_position).get("themesname").toString().concat("\" theme scheme?")));
                d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {
                        if (indexTheme.contains(fb_listmap.get(_position).get("themesjson").toString())) {
                            _customSnack("Duplicate theme scheme detected!", 2);
                        } else {
                            tmpMap = new HashMap<>();
                            tmpMap = fb_listmap.get(_position);
                            tmpMap.remove("uid");
                            gridMap.add(tmpMap);
                            data.edit().putString("griddata", new Gson().toJson(gridMap)).commit();
                            ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                            indexTheme.add(fb_listmap.get(_position).get("themesjson").toString());
                            _customSnack("Successfully added!", 1);
                        }
                    }
                });
                d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface _dialog, int _which) {

                    }
                });
                d.create().show();
            }
        });

        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                final int _position = _param3;
                if (fb_listmap.get(_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) || isAdmin) {
                    d.setCancelable(false);
                    d.setTitle("Delete");
                    d.setMessage("Do you want to delete \"".concat(fb_listmap.get(_position).get("themesname").toString().concat("\" theme scheme?")));
                    d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {
                            fb_themes.child(childKey.get(_position)).removeValue();
                            _customSnack("Delete success!", 1);
                        }
                    });
                    d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialog, int _which) {

                        }
                    });
                    d.create().show();
                } else {
                    _customSnack("Access denied!", 2);
                }
                return true;
            }
        });

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _fab.hide();
                isOpened = true;
                listview1.setVisibility(View.GONE);
                linear_grid.setVisibility(View.VISIBLE);
                textview1.setText("Pick themes");
            }
        });

        _fb_themes_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                childKey.add(_childKey);
                indexThmStr.add(_childValue.get("themesjson").toString());
                fb_listmap.add(_childValue);
                listview1.setAdapter(new Listview1Adapter(fb_listmap));
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                fb_listmap.remove(childKey.indexOf(_childKey));
                childKey.remove(childKey.indexOf(_childKey));
                indexThmStr.remove(indexThmStr.indexOf(_childValue.get("themesjson").toString()));
                listview1.setAdapter(new Listview1Adapter(fb_listmap));
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        fb_themes.addChildEventListener(_fb_themes_child_listener);

        _profile_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                proChildKeys.add(_childKey);
                profile_map.add(_childValue);
                listview1.setAdapter(new Listview1Adapter(fb_listmap));
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                profile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        profile_map = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                profile_map.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        listview1.setAdapter(new Listview1Adapter(fb_listmap));
                    }

                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        profile.addChildEventListener(_profile_child_listener);

        _profile_admins_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                adminsList.add(_childKey);
                if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    isAdmin = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        profile_admins.addChildEventListener(_profile_admins_child_listener);

        _Auth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _Auth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();

            }
        };
    }

    private void initializeLogic() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        _loadTheme();
        gridMap = new Gson().fromJson(data.getString("griddata", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        for (int _repeat27 = 0; _repeat27 < gridMap.size(); _repeat27++) {
            indexTheme.add(gridMap.get(indexTheme.size()).get("themesjson").toString());
        }
        grid = new GridView(ThemesstrActivity.this);

        grid.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));

        grid.setNumColumns(GridView.AUTO_FIT);

        grid.setVerticalSpacing(2);

        grid.setHorizontalSpacing(2);

        grid.setColumnWidth((int) SketchwareUtil.getDip(getApplicationContext(), 118));

        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        linear_grid.addView(grid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int _pos, long id) {
                _gridSelected(_pos);
            }
        });
        grid.setAdapter(new Gridview1Adapter(gridMap));
        linear_grid.setVisibility(View.GONE);
        listview1.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                _ScrollCheck();

            }


            public void onScrollStateChanged(AbsListView view, int scrollState) { // TODO Auto-generated method stub
                _ScrollCheck();
            }
        });
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
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
    public void onBackPressed() {
        if (isOpened) {
            isOpened = false;
            _fab.show();
            listview1.setVisibility(View.VISIBLE);
            linear_grid.setVisibility(View.GONE);
            textview1.setText("Themes Store");
        } else {
            finish();
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

    private void _shape(final double _tl, final double _tr, final double _bl, final double _br, final String _BGcolor, final String _Scolor, final double _Swidth, final View _view) {
        Double tlr = _tl;
        Double trr = _tr;
        Double blr = _bl;
        Double brr = _br;
        Double sw = _Swidth;
        android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
        s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[]{tlr.floatValue(), tlr.floatValue(), trr.floatValue(), trr.floatValue(), blr.floatValue(), blr.floatValue(), brr.floatValue(), brr.floatValue()});
        s.setColor(Color.parseColor(_BGcolor));
        s.setStroke(sw.intValue(), Color.parseColor(_Scolor));
        _view.setBackground(s);
    }

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }

    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }

    private void _gridSelected(final double _num) {
        if (gridMap.get((int) _num).get("themesname").toString().contains("$")) {
            _customSnack("Can't upload default theme scheme!", 2);
        } else {
            if (gridMap.get((int) _num).containsKey("os-thm-version")) {
                if (gridMap.get((int) _num).get("os-thm-version").toString().equals("2")) {
                    themes_map.clear();
                    themes_map = new Gson().fromJson(gridMap.get((int) _num).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    if (themes_map.get(0).containsKey("version")) {
                        if (themes_map.get(0).get("version").toString().equals("2")) {
                            d.setCancelable(false);
                            d.setTitle("Upload theme");
                            d.setMessage("Are you sure to upload \"".concat(gridMap.get((int) _num).get("themesname").toString().concat("\" theme scheme?")));
                            d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {
                                    if (indexThmStr.contains(gridMap.get((int) _num).get("themesjson").toString())) {
                                        _customSnack("Duplicate theme scheme detected!", 2);
                                    } else {
                                        map_var = new HashMap<>();
                                        map_var = gridMap.get((int) _num);
                                        map_var.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        fb_themes.push().updateChildren(map_var);
                                        _customSnack("Successfully uploaded!", 1);
                                    }
                                }
                            });
                            d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {

                                }
                            });
                            d.create().show();
                        } else {
                            _customSnack("Unsupported theme version!", 2);
                        }
                    } else {
                        _customSnack("Unsupported theme version!", 2);
                    }
                } else {
                    _customSnack("Unsupported theme version!", 2);
                }
            } else {
                _customSnack("Unsupported theme version!", 2);
            }
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

    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }

    private void _ScrollCheck() {
        if (listview1.getFirstVisiblePosition() == 0 && listview1.getTop() >= 0) {
            _fab.show();
        } else {
            _fab.hide();
        }
    }

    private void _BlackStatusBarIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview1);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        linear2.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        linear_grid.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        _fab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor(theme_map.get(0).get("colorButton").toString())));
        _fab.setRippleColor(Color.parseColor(theme_map.get(0).get("colorRipple").toString()));
        textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        _fab.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButtonText").toString()));
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
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

    public class Gridview1Adapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public Gridview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
                _v = _inflater.inflate(R.layout.theme_preview, null);
            }

            final ImageView imageview_plus = _v.findViewById(R.id.imageview_plus);
            final ImageView imageview_back = _v.findViewById(R.id.imageview_back);
            final TextView textview_title = _v.findViewById(R.id.textview_title);
            final TextView textview_name = _v.findViewById(R.id.textview_name);
            final LinearLayout linear_title = _v.findViewById(R.id.linear_title);
            final LinearLayout linear_base = _v.findViewById(R.id.linear_base);

            textview_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);

            textview_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);

            if (gridMap.get(_position).get("themesname").toString().equals("$default_1$")) {
                textview_name.setText("Vanilla");
            } else {
                if (gridMap.get(_position).get("themesname").toString().equals("$default_2$")) {
                    textview_name.setText("Dark Mode");
                } else {
                    textview_name.setText(gridMap.get(_position).get("themesname").toString());
                }
            }
            gridtmp = new Gson().fromJson(gridMap.get(_position).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            if (!gridtmp.get(0).containsKey("version")) {
                android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                gd.setColor(Color.parseColor("#F50057"));
                gd.setCornerRadius(50);
                imageview_plus.setBackground(gd);
                imageview_plus.setElevation(10);
                _shape(8, 8, 0, 0, "#2196F3", "#FFFFFF", 0, linear_title);
                _shape(8, 8, 8, 8, "#FAFAFA", "#FFFFFF", 0, linear_base);
                linear_title.setElevation(10);
                linear_base.setElevation(10);
            } else {
                if (!gridtmp.get(0).get("version").toString().equals("2")) {
                    android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                    gd.setColor(Color.parseColor("#F50057"));
                    gd.setCornerRadius(50);
                    imageview_plus.setBackground(gd);
                    imageview_plus.setElevation(10);
                    _shape(8, 8, 0, 0, "#2196F3", "#FFFFFF", 0, linear_title);
                    _shape(8, 8, 8, 8, "#FAFAFA", "#FFFFFF", 0, linear_base);
                    linear_title.setElevation(10);
                    linear_base.setElevation(10);
                } else {
                    android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                    gd.setColor(Color.parseColor(gridtmp.get(0).get("colorButton").toString()));
                    gd.setCornerRadius(50);
                    imageview_plus.setBackground(gd);
                    imageview_plus.setElevation(10);
                    linear_base.setElevation(10);
                    if (gridtmp.get(0).get("shadow").toString().equals("1"))
                        linear_title.setElevation(10);
                    else
                        linear_title.setElevation(0);
                    imageview_back.setColorFilter(Color.parseColor(gridtmp.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
                    imageview_plus.setColorFilter(Color.parseColor(gridtmp.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
                    textview_title.setTextColor(Color.parseColor(gridtmp.get(0).get("colorPrimaryText").toString()));
                    textview_name.setTextColor(Color.parseColor(gridtmp.get(0).get("colorBackgroundText").toString()));
                    _shape(8, 8, 0, 0, gridtmp.get(0).get("colorPrimary").toString(), "#FFFFFF", 0, linear_title);
                    _shape(8, 8, 8, 8, gridtmp.get(0).get("colorBackground").toString(), "#FFFFFF", 0, linear_base);
                }
            }

            return _v;
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
                _v = _inflater.inflate(R.layout.themes_store_list, null);
            }

            final LinearLayout linear1 = _v.findViewById(R.id.linear1);
            final LinearLayout linear_base = _v.findViewById(R.id.linear_base);
            final LinearLayout linear5 = _v.findViewById(R.id.linear5);
            final LinearLayout linear_title = _v.findViewById(R.id.linear_title);
            final LinearLayout linear_gravity = _v.findViewById(R.id.linear_gravity);
            final ImageView imageview_plus = _v.findViewById(R.id.imageview_plus);
            final ImageView imageview_back = _v.findViewById(R.id.imageview_back);
            final TextView textview_title = _v.findViewById(R.id.textview_title);
            final TextView textview_name = _v.findViewById(R.id.textview_name);
            final TextView textview_uploader = _v.findViewById(R.id.textview_uploader);

            try {
                themes_map.clear();
                textview_name.setText(fb_listmap.get(_position).get("themesname").toString());
                if (proChildKeys.contains(fb_listmap.get(_position).get("uid").toString())) {
                    textview_uploader.setText(profile_map.get(proChildKeys.indexOf(fb_listmap.get(_position).get("uid").toString())).get("username").toString());
                } else {
                    textview_uploader.setText(fb_listmap.get(_position).get("uid").toString());
                }
                themes_map = new Gson().fromJson(fb_listmap.get(_position).get("themesjson").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
                gd.setColor(Color.parseColor(themes_map.get(0).get("colorButton").toString()));
                gd.setCornerRadius(50);
                imageview_plus.setBackground(gd);
                imageview_plus.setElevation(10);
                linear_base.setElevation(10);
                if (themes_map.get(0).get("shadow").toString().equals("1"))
                    linear_title.setElevation(10);
                else
                    linear_title.setElevation(0);
                imageview_back.setColorFilter(Color.parseColor(themes_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
                imageview_plus.setColorFilter(Color.parseColor(themes_map.get(0).get("colorButtonText").toString()), PorterDuff.Mode.MULTIPLY);
                textview_title.setTextColor(Color.parseColor(themes_map.get(0).get("colorPrimaryText").toString()));
                _shape(8, 8, 0, 0, themes_map.get(0).get("colorPrimary").toString(), "#FFFFFF", 0, linear_title);
                _shape(8, 8, 8, 8, themes_map.get(0).get("colorBackground").toString(), "#FFFFFF", 0, linear_base);
                textview_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
                textview_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
                textview_uploader.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
                textview_title.setEllipsize(TextUtils.TruncateAt.END);
                textview_name.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
                if (adminsList.contains(fb_listmap.get(_position).get("uid").toString())) {
                    textview_uploader.setTextColor(Color.parseColor(theme_map.get(0).get("colorButton").toString()));
                } else {
                    textview_uploader.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
                }
            } catch (Exception _e) {
            }

            return _v;
        }
    }

}
