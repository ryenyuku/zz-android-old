package tw.music.streamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MessageActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private String keyTextChild = "";
    private HashMap<String, Object> map = new HashMap<>();
    private boolean isAdmin = false;
    private HashMap<String, Object> roles_index = new HashMap<>();

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();
    private ArrayList<String> childKeys = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> commentMap = new ArrayList<>();
    private ArrayList<String> adminsList = new ArrayList<>();
    private ArrayList<String> profileList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> profileMap = new ArrayList<>();
    private ArrayList<String> dialog_list = new ArrayList<>();
    private ArrayList<String> imgIndex = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> imgMap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> rolesinfo_map = new ArrayList<>();
    private ArrayList<String> rolesinfo_index = new ArrayList<>();

    private LinearLayout linear1;
    private ListView listview1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private ImageView image_back;
    private TextView text_title;
    private EditText input_msg;
    private ImageView image_send;

    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private DatabaseReference profile = _firebase.getReference("profile/text");
    private ChildEventListener _profile_child_listener;
    private DatabaseReference profile_admins = _firebase.getReference("profile/admins");
    private ChildEventListener _profile_admins_child_listener;
    private SharedPreferences data;
    private DatabaseReference comments_db = _firebase.getReference("upload/msg");
    private ChildEventListener _comments_db_child_listener;
    private Calendar cal = Calendar.getInstance();
    private AlertDialog.Builder d;
    private Intent intent = new Intent();
    private DatabaseReference prof_img = _firebase.getReference("profile/image");
    private ChildEventListener _prof_img_child_listener;
    private DatabaseReference prof_roles = _firebase.getReference("profile/roles");
    private ChildEventListener _prof_roles_child_listener;
    private DatabaseReference prof_rolesinfo = _firebase.getReference("profile/rolesinfo");
    private ChildEventListener _prof_rolesinfo_child_listener;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.message);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        listview1 = findViewById(R.id.listview1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        image_back = findViewById(R.id.image_back);
        text_title = findViewById(R.id.text_title);
        input_msg = findViewById(R.id.input_msg);
        image_send = findViewById(R.id.image_send);
        Auth = FirebaseAuth.getInstance();
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);
        d = new AlertDialog.Builder(this);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        input_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                int _maxHeightTemp = (int) SketchwareUtil.getDip(getApplicationContext(), 65);
                if (input_msg.getHeight() >= _maxHeightTemp) {
                    linear3.getLayoutParams().height = _maxHeightTemp;
                } else {
                    linear3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        image_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (input_msg.getText().toString().trim().length() > 0) {
                    cal = Calendar.getInstance();
                    map = new HashMap<>();
                    map.put("key", keyTextChild);
                    map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    map.put("msg", input_msg.getText().toString().trim());
                    map.put("time", String.valueOf(cal.getTimeInMillis()));
                    comments_db.push().updateChildren(map);
                    _customSnack("Commented!", 1);
                    input_msg.setText("");
                } else {
                    _customSnack("Input is empty", 2);
                }
            }
        });

        _profile_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                profileList.add(_childKey);
                profileMap.add(_childValue);
                int _index = listview1.getFirstVisiblePosition();
                View _view = listview1.getChildAt(0);
                int _top = (_view == null) ? 0 : _view.getTop();
                listview1.setAdapter(new Listview1Adapter(commentMap));
                listview1.setSelectionFromTop(_index, _top);
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
                        profileMap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                profileMap.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        int _index = listview1.getFirstVisiblePosition();
                        View _view = listview1.getChildAt(0);
                        int _top = (_view == null) ? 0 : _view.getTop();
                        listview1.setAdapter(new Listview1Adapter(commentMap));
                        listview1.setSelectionFromTop(_index, _top);
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
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_childKey)) {
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

        _comments_db_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.get("key").toString().equals(keyTextChild)) {
                    childKeys.add(0, _childKey);
                    commentMap.add(0, _childValue);
                    int _index = listview1.getFirstVisiblePosition();
                    View _view = listview1.getChildAt(0);
                    int _top = (_view == null) ? 0 : _view.getTop();
                    listview1.setAdapter(new Listview1Adapter(commentMap));
                    listview1.setSelectionFromTop(_index, _top);
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
                if (childKeys.contains(_childKey)) {
                    commentMap.remove(childKeys.indexOf(_childKey));
                    childKeys.remove(childKeys.indexOf(_childKey));
                    int _index = listview1.getFirstVisiblePosition();
                    View _view = listview1.getChildAt(0);
                    int _top = (_view == null) ? 0 : _view.getTop();
                    listview1.setAdapter(new Listview1Adapter(commentMap));
                    listview1.setSelectionFromTop(_index, _top);
                }
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        comments_db.addChildEventListener(_comments_db_child_listener);

        _prof_img_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                imgIndex.add(_childKey);
                imgMap.add(_childValue);
                int _index = listview1.getFirstVisiblePosition();
                View _view = listview1.getChildAt(0);
                int _top = (_view == null) ? 0 : _view.getTop();
                listview1.setAdapter(new Listview1Adapter(commentMap));
                listview1.setSelectionFromTop(_index, _top);
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                prof_img.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        imgMap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                imgMap.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        int _index = listview1.getFirstVisiblePosition();
                        View _view = listview1.getChildAt(0);
                        int _top = (_view == null) ? 0 : _view.getTop();
                        listview1.setAdapter(new Listview1Adapter(commentMap));
                        listview1.setSelectionFromTop(_index, _top);
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
        prof_img.addChildEventListener(_prof_img_child_listener);

        _prof_roles_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                roles_index.put(_childKey, _childValue.get("type").toString());
                int _index = listview1.getFirstVisiblePosition();
                View _view = listview1.getChildAt(0);
                int _top = (_view == null) ? 0 : _view.getTop();
                listview1.setAdapter(new Listview1Adapter(commentMap));
                listview1.setSelectionFromTop(_index, _top);
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
        prof_roles.addChildEventListener(_prof_roles_child_listener);

        _prof_rolesinfo_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                rolesinfo_index.add(_childValue.get("type").toString());
                rolesinfo_map.add(_childValue);
                int _index = listview1.getFirstVisiblePosition();
                View _view = listview1.getChildAt(0);
                int _top = (_view == null) ? 0 : _view.getTop();
                listview1.setAdapter(new Listview1Adapter(commentMap));
                listview1.setSelectionFromTop(_index, _top);
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
        prof_rolesinfo.addChildEventListener(_prof_rolesinfo_child_listener);

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
        keyTextChild = getIntent().getStringExtra("key");
        dialog_list.add("Profile");
        dialog_list.add("Delete");
        dialog_list.add("Copy");
        dialog_list.add("Cancel");
        text_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        input_msg.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
		/*
Glide.with(getApplicationContext()).load(Uri.parse("lol")).into(image_back);
*/
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

    private void _customNav(final String _color) {
        //Code From StackOverFlow.com And Converted By TeamWorks DEV
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.setNavigationBarColor(Color.parseColor(_color));
        }
    }

    private boolean isDark(int color) {
        return androidx.core.graphics.ColorUtils.calculateLuminance(color) < 0.5;
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


    private void _BlackStatusBarIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private void _shadow(final View _v, final double _n) {
        _v.setElevation((float) _n);
    }


    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
    }


    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _customNav(theme_map.get(0).get("colorBackground").toString());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), image_back);
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), image_send);
        if (theme_map.get(0).get("shadow").toString().equals("1")) {
            _shadow(linear1, 10);
            if (isDark(Color.parseColor(theme_map.get(0).get("colorBackground").toString()))) {
                linear2.setBackgroundColor(0xFF808080);
            } else {
                linear2.setBackgroundColor(0xFFBDBDBD);
            }
        } else {
            linear2.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(theme_map.get(0).get("colorPrimaryDark").toString()));
        }
        linear1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorPrimary").toString()));
        listview1.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        linear3.setBackgroundColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
        input_msg.setHintTextColor(Color.parseColor(theme_map.get(0).get("colorHint").toString()));
        text_title.setTextColor(Color.parseColor(theme_map.get(0).get("colorPrimaryText").toString()));
        input_msg.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
        if (theme_map.get(0).get("statusbarIcon").toString().equals("0")) {
            _BlackStatusBarIcons();
        }
        image_back.setColorFilter(Color.parseColor(theme_map.get(0).get("colorPrimaryImage").toString()), PorterDuff.Mode.MULTIPLY);
        image_send.setColorFilter(Color.parseColor(theme_map.get(0).get("colorButton").toString()), PorterDuff.Mode.MULTIPLY);
    }


    private void _rippleLib() {
    }

    public void drawableclass() {
    }

    private void _resetDialog() {
        d = null;
        d = new AlertDialog.Builder(this);
    }

    private void _Linkify(final TextView _text, final String _color) {
        _text.setClickable(true);

        android.text.util.Linkify.addLinks(_text, android.text.util.Linkify.ALL);

        _text.setLinkTextColor(Color.parseColor("#" + _color.replace("#", "")));

        _text.setLinksClickable(true);
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
                _v = _inflater.inflate(R.layout.comments_layout, null);
            }

            final LinearLayout linear_base = _v.findViewById(R.id.linear_base);
            final ImageView imageview1 = _v.findViewById(R.id.imageview1);
            final LinearLayout linear2 = _v.findViewById(R.id.linear2);
            final LinearLayout linear1 = _v.findViewById(R.id.linear1);
            final TextView textview2 = _v.findViewById(R.id.textview2);
            final TextView textview1 = _v.findViewById(R.id.textview1);
            final ImageView imageview2 = _v.findViewById(R.id.imageview2);
            final TextView textview3 = _v.findViewById(R.id.textview3);

            cal.setTimeInMillis((long) (Double.parseDouble(commentMap.get(_position).get("time").toString())));
            linear_base.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(theme_map.get(0).get("colorRipple").toString())));
            linear_base.setClickable(true);
            if (adminsList.contains(commentMap.get(_position).get("uid").toString())) {
                textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorButton").toString()));
                android.graphics.drawable.GradientDrawable _gD = new android.graphics.drawable.GradientDrawable();
                _gD.setColor(Color.parseColor(theme_map.get(0).get("colorBackground").toString()));
                _gD.setShape(android.graphics.drawable.GradientDrawable.OVAL);
                _gD.setStroke(5, Color.parseColor(theme_map.get(0).get("colorButton").toString()));
                imageview1.setBackground(_gD);
            } else {
                textview1.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
                imageview1.setBackgroundColor(Color.TRANSPARENT);
            }
            textview2.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
            textview3.setTextColor(Color.parseColor(theme_map.get(0).get("colorBackgroundText").toString()));
            if (imgIndex.contains(commentMap.get(_position).get("uid").toString())) {
                imageview1.clearColorFilter();
                Glide.with(getApplicationContext()).asBitmap().load(imgMap.get(imgIndex.indexOf(commentMap.get(_position).get("uid").toString())).get("url").toString()).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(imageview1) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageview1.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                imageview1.setColorFilter(Color.parseColor(theme_map.get(0).get("colorBackgroundImage").toString()), PorterDuff.Mode.MULTIPLY);
                imageview1.setImageResource(R.drawable.ic_person);
            }
            if (profileList.contains(commentMap.get(_position).get("uid").toString())) {
                textview1.setText(profileMap.get(profileList.indexOf(commentMap.get(_position).get("uid").toString())).get("username").toString());
            } else {
                textview1.setText(commentMap.get(_position).get("uid").toString());
            }
            if (roles_index.containsKey(commentMap.get(_position).get("uid").toString())) {
                imageview2.setVisibility(View.VISIBLE);
                if (rolesinfo_index.contains(roles_index.get(commentMap.get(_position).get("uid").toString()).toString())) {
                    Glide.with(getApplicationContext()).load(Uri.parse(rolesinfo_map.get(rolesinfo_index.indexOf(roles_index.get(commentMap.get(_position).get("uid").toString()).toString())).get("img").toString())).into(imageview2);
                } else {
                    imageview2.setImageResource(R.drawable.ic_unknown_roles);
                }
            } else {
                imageview2.setVisibility(View.GONE);
            }
            textview2.setText(commentMap.get(_position).get("msg").toString());
            textview3.setText(new SimpleDateFormat("E, dd MMM yyyy HH:mm").format(cal.getTime()));
            _Linkify(textview2, "#2196F3");
            linear_base.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View _vlol) {
                    d.setCancelable(false);
                    d.setAdapter(new ArrayAdapter(MessageActivity.this, android.R.layout.simple_list_item_1, dialog_list), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dia, int _pos_dialog) {
                            _resetDialog();
                            if (_pos_dialog == 0) {
                                intent.setClass(getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("uid", commentMap.get(_position).get("uid").toString());
                                startActivity(intent);
                            } else {
                                if (_pos_dialog == 1) {
                                    if (commentMap.get(_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) || isAdmin) {
                                        d.setCancelable(false);
                                        d.setTitle("Delete");
                                        d.setMessage("Are you sure to delete this comment?");
                                        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface _dialog, int _which) {
                                                comments_db.child(childKeys.get(_position)).removeValue();
                                                _customSnack("Deleted!", 1);
                                                _resetDialog();
                                            }
                                        });
                                        d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface _dialog, int _which) {
                                                _resetDialog();
                                            }
                                        });
                                        d.create().show();
                                    } else {
                                        _customSnack("Can't delete! Access denied.", 2);
                                    }
                                } else {
                                    if (_pos_dialog == 2) {
                                        ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", commentMap.get(_position).get("msg").toString()));
                                        _customSnack("Copied!", 1);
                                    } else {
                                        if (_pos_dialog == 3) {

                                        }
                                    }
                                }
                            }
                        }
                    });

                    d.show();
                    return true;
                }
            });
            textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
            textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);

            return _v;
        }
    }

}
