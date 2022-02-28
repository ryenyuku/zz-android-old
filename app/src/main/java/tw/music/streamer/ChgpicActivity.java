package tw.music.streamer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ChgpicActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

    private ArrayList<HashMap<String, Object>> theme_map = new ArrayList<>();

    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private ImageView imageview2;
    private TextView textview1;
    private ImageView imageview3;
    private TextView textview2;

    private FirebaseAuth Auth;
    private OnCompleteListener<AuthResult> _Auth_create_user_listener;
    private OnCompleteListener<AuthResult> _Auth_sign_in_listener;
    private OnCompleteListener<Void> _Auth_reset_password_listener;
    private DatabaseReference prof_img = _firebase.getReference("profile/image");
    private ChildEventListener _prof_img_child_listener;
    private SharedPreferences data;
    private Intent i = new Intent();
    private GestureImageView imageview1;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.chgpic);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        imageview2 = findViewById(R.id.imageview2);
        textview1 = findViewById(R.id.textview1);
        imageview3 = findViewById(R.id.imageview3);
        textview2 = findViewById(R.id.textview2);
        Auth = FirebaseAuth.getInstance();
        data = getSharedPreferences("teamdata", Activity.MODE_PRIVATE);

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setClass(getApplicationContext(), FilepickerActivity.class);
                i.putExtra("fileType", "image/*");
                startActivity(i);
            }
        });

        _prof_img_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    Glide.with(getApplicationContext()).load(_childValue.get("url").toString()).into(imageview1);
                    linear2.setVisibility(View.VISIBLE);
                    linear3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childKey.equals(getIntent().getStringExtra("uid"))) {
                    Glide.with(getApplicationContext()).load(_childValue.get("url").toString()).into(imageview1);
                }
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageview1 = new GestureImageView(this);
        imageview1.setLayoutParams(params);
        imageview1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        linear2.addView(imageview1);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor("#000000"));
        }
        _loadTheme();
        _customNav("#000000");
        if (!getIntent().getStringExtra("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            imageview3.setVisibility(View.GONE);
        }
        linear2.setVisibility(View.GONE);
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/googlesans.ttf"), 0);
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
        if (!data.getString("tmpPath", "").equals("")) {
            i.setClass(getApplicationContext(), CropActivity.class);
            i.putExtra("path", data.getString("tmpPath", ""));
            startActivity(i);
            data.edit().remove("tmpPath").commit();
            finish();
        }
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
		/*
Glide.with(getApplicationContext()).load(Uri.parse("lol")).into(imageview2);
*/
    }

    private void _loadTheme() {
        theme_map = new Gson().fromJson(data.getString("themesjson", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
        }.getType());
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview2);
        _circleRipple(theme_map.get(0).get("colorRipple").toString(), imageview3);
    }

    private void _circleRipple(final String _color, final View _v) {
        android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_color)});
        android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb, null, null);
        _v.setBackground(ripdrb);
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

    public interface Animation {
        boolean update(GestureImageView view, long time);
    }

    public interface FlingAnimationListener {
        void onMove(float x, float y);

        void onComplete();
    }

    public interface GestureImageViewListener {
        void onTouch(float x, float y);

        void onScale(float scale);

        void onPosition(float x, float y);
    }


    public interface MoveAnimationListener {
        void onMove(float x, float y);
    }


    public interface ZoomAnimationListener {
        void onZoom(float scale, float x, float y);

        void onComplete();
    }

    public static class Animator extends Thread {
        private GestureImageView view;
        private Animation animation;
        private boolean running = false;
        private boolean active = false;
        private long lastTime = -1L;

        public Animator(GestureImageView view, String threadName) {
            super(threadName);
            this.view = view;
        }

        @Override
        public void run() {
            running = true;
            while (running) {
                while (active && animation != null) {
                    long time = System.currentTimeMillis();
                    active = animation.update(view, time - lastTime);
                    view.redraw();
                    lastTime = time;
                    while (active) {
                        try {
                            if (view.waitForDraw(32)) {
                                break;
                            }
                        } catch (InterruptedException ignore) {
                            active = false;
                        }
                    }
                }
                synchronized (this) {
                    if (running) {
                        try {
                            wait();
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
            }
        }

        public synchronized void finish() {
            running = false;
            active = false;
            notifyAll();
        }

        public void play(Animation transformer) {
            if (active) {
                cancel();
            }
            this.animation = transformer;
            activate();
        }

        public synchronized void activate() {
            lastTime = System.currentTimeMillis();
            active = true;
            notifyAll();
        }

        public void cancel() {
            active = false;
        }
    }

    public static class FlingAnimation implements Animation {
        private float velocityX;
        private float velocityY;
        private float factor = 0.95f;
        private float threshold = 10;
        private FlingAnimationListener listener;

        @Override
        public boolean update(GestureImageView view, long time) {
            float seconds = (float) time / 1000.0f;
            float dx = velocityX * seconds;
            float dy = velocityY * seconds;
            velocityX *= factor;
            velocityY *= factor;
            boolean active = (Math.abs(velocityX) > threshold && Math.abs(velocityY) > threshold);
            if (listener != null) {
                listener.onMove(dx, dy);
                if (!active) {
                    listener.onComplete();
                }
            }
            return active;
        }

        public void setVelocityX(float velocityX) {
            this.velocityX = velocityX;
        }

        public void setVelocityY(float velocityY) {
            this.velocityY = velocityY;
        }

        public void setFactor(float factor) {
            this.factor = factor;
        }

        public void setListener(FlingAnimationListener listener) {
            this.listener = listener;
        }
    }

    public static class FlingListener extends android.view.GestureDetector.SimpleOnGestureListener {
        private float velocityX;
        private float velocityY;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            return true;
        }

        public float getVelocityX() {
            return velocityX;
        }

        public float getVelocityY() {
            return velocityY;
        }
    }

    public static class GestureImageView extends ImageView {
        public static final String GLOBAL_NS = "http://schemas.android.com/apk/res/android";
        public static final String LOCAL_NS = "http://schemas.polites.com/android";
        private final java.util.concurrent.Semaphore drawLock = new java.util.concurrent.Semaphore(0);
        private Animator animator;
        private android.graphics.drawable.Drawable drawable;
        private float x = 0, y = 0;
        private boolean layout = false;
        private float scaleAdjust = 1.0f;
        private float startingScale = -1.0f;
        private float scale = 1.0f;
        private float maxScale = 5.0f;
        private float minScale = 0.75f;
        private float fitScaleHorizontal = 1.0f;
        private float fitScaleVertical = 1.0f;
        private float rotation = 0.0f;
        private float centerX;
        private float centerY;
        private Float startX, startY;
        private int hWidth;
        private int hHeight;
        private int resId = -1;
        private boolean recycle = false;
        private boolean strict = false;
        private int displayHeight;
        private int displayWidth;
        private int alpha = 255;
        private ColorFilter colorFilter;
        private int deviceOrientation = -1;
        private int imageOrientation;
        private GestureImageViewListener gestureImageViewListener;
        private GestureImageViewTouchListener gestureImageViewTouchListener;
        private OnTouchListener customOnTouchListener;
        private OnClickListener onClickListener;

        public GestureImageView(Context context, AttributeSet attrs, int defStyle) {
            this(context, attrs);
        }

        public GestureImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            String scaleType = attrs.getAttributeValue(GLOBAL_NS, "scaleType");
            if (scaleType == null || scaleType.trim().length() == 0) {
                setScaleType(ScaleType.CENTER_INSIDE);
            }
            String strStartX = attrs.getAttributeValue(LOCAL_NS, "start-x");
            String strStartY = attrs.getAttributeValue(LOCAL_NS, "start-y");
            if (strStartX != null && strStartX.trim().length() > 0) {
                startX = Float.parseFloat(strStartX);
            }
            if (strStartY != null && strStartY.trim().length() > 0) {
                startY = Float.parseFloat(strStartY);
            }
            setStartingScale(attrs.getAttributeFloatValue(LOCAL_NS, "start-scale", startingScale));
            setMinScale(attrs.getAttributeFloatValue(LOCAL_NS, "min-scale", minScale));
            setMaxScale(attrs.getAttributeFloatValue(LOCAL_NS, "max-scale", maxScale));
            setStrict(attrs.getAttributeBooleanValue(LOCAL_NS, "strict", strict));
            setRecycle(attrs.getAttributeBooleanValue(LOCAL_NS, "recycle", recycle));
            initImage();
        }

        public GestureImageView(Context context) {
            super(context);
            setScaleType(ScaleType.CENTER_INSIDE);
            initImage();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (drawable != null) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                    displayHeight = MeasureSpec.getSize(heightMeasureSpec);
                    if (getLayoutParams().width == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
                        float ratio = (float) getImageWidth() / (float) getImageHeight();
                        displayWidth = Math.round((float) displayHeight * ratio);
                    } else {
                        displayWidth = MeasureSpec.getSize(widthMeasureSpec);
                    }
                } else {
                    displayWidth = MeasureSpec.getSize(widthMeasureSpec);
                    if (getLayoutParams().height == android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
                        float ratio = (float) getImageHeight() / (float) getImageWidth();
                        displayHeight = Math.round((float) displayWidth * ratio);
                    } else {
                        displayHeight = MeasureSpec.getSize(heightMeasureSpec);
                    }
                }
            } else {
                displayHeight = MeasureSpec.getSize(heightMeasureSpec);
                displayWidth = MeasureSpec.getSize(widthMeasureSpec);
            }
            setMeasuredDimension(displayWidth, displayHeight);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if (changed || !layout) {
                setupCanvas(displayWidth, displayHeight, getResources().getConfiguration().orientation);
            }
        }

        protected void setupCanvas(int measuredWidth, int measuredHeight, int orientation) {
            if (deviceOrientation != orientation) {
                layout = false;
                deviceOrientation = orientation;
            }
            if (drawable != null && !layout) {
                int imageWidth = getImageWidth();
                int imageHeight = getImageHeight();
                hWidth = Math.round(((float) imageWidth / 2.0f));
                hHeight = Math.round(((float) imageHeight / 2.0f));
                measuredWidth -= (getPaddingLeft() + getPaddingRight());
                measuredHeight -= (getPaddingTop() + getPaddingBottom());
                computeCropScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
                if (startingScale <= 0.0f) {
                    computeStartingScale(imageWidth, imageHeight, measuredWidth, measuredHeight);
                }
                scaleAdjust = startingScale;
                this.centerX = (float) measuredWidth / 2.0f;
                this.centerY = (float) measuredHeight / 2.0f;
                if (startX == null) {
                    x = centerX;
                } else {
                    x = startX;
                }
                if (startY == null) {
                    y = centerY;
                } else {
                    y = startY;
                }
                gestureImageViewTouchListener = new GestureImageViewTouchListener(this, measuredWidth, measuredHeight);
                if (isLandscape()) {
                    gestureImageViewTouchListener.setMinScale(minScale * fitScaleHorizontal);
                } else {
                    gestureImageViewTouchListener.setMinScale(minScale * fitScaleVertical);
                }
                gestureImageViewTouchListener.setMaxScale(maxScale * startingScale);
                gestureImageViewTouchListener.setFitScaleHorizontal(fitScaleHorizontal);
                gestureImageViewTouchListener.setFitScaleVertical(fitScaleVertical);
                gestureImageViewTouchListener.setCanvasWidth(measuredWidth);
                gestureImageViewTouchListener.setCanvasHeight(measuredHeight);
                gestureImageViewTouchListener.setOnClickListener(onClickListener);
                drawable.setBounds(-hWidth, -hHeight, hWidth, hHeight);
                super.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (customOnTouchListener != null) {
                            customOnTouchListener.onTouch(v, event);
                        }
                        return gestureImageViewTouchListener.onTouch(v, event);
                    }
                });
                layout = true;
            }
        }

        protected void computeCropScale(int imageWidth, int imageHeight, int measuredWidth, int measuredHeight) {
            fitScaleHorizontal = (float) measuredWidth / (float) imageWidth;
            fitScaleVertical = (float) measuredHeight / (float) imageHeight;
        }

        protected void computeStartingScale(int imageWidth, int imageHeight, int measuredWidth, int measuredHeight) {
            switch (getScaleType()) {
                case CENTER:
                    startingScale = 1.0f;
                    break;
                case CENTER_CROP:
                    startingScale = Math.max((float) measuredHeight / (float) imageHeight, (float) measuredWidth / (float) imageWidth);
                    break;
                case CENTER_INSIDE:
                    float wRatio = (float) imageWidth / (float) measuredWidth;
                    float hRatio = (float) imageHeight / (float) measuredHeight;
                    if (wRatio > hRatio) {
                        startingScale = fitScaleHorizontal;
                    } else {
                        startingScale = fitScaleVertical;
                    }
                    break;
            }
        }

        protected boolean isRecycled() {
            if (drawable != null && drawable instanceof android.graphics.drawable.BitmapDrawable) {
                Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    return bitmap.isRecycled();
                }
            }
            return false;
        }

        protected void recycle() {
            if (recycle && drawable != null && drawable instanceof android.graphics.drawable.BitmapDrawable) {
                Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (layout) {
                if (drawable != null && !isRecycled()) {
                    canvas.save();
                    float adjustedScale = scale * scaleAdjust;
                    canvas.translate(x, y);
                    if (rotation != 0.0f) {
                        canvas.rotate(rotation);
                    }
                    if (adjustedScale != 1.0f) {
                        canvas.scale(adjustedScale, adjustedScale);
                    }
                    drawable.draw(canvas);
                    canvas.restore();
                }
                if (drawLock.availablePermits() <= 0) {
                    drawLock.release();
                }
            }
        }

        public boolean waitForDraw(long timeout) throws InterruptedException {
            return drawLock.tryAcquire(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
        }

        @Override
        protected void onAttachedToWindow() {
            animator = new Animator(this, "GestureImageViewAnimator");
            animator.start();
            if (resId >= 0 && drawable == null) {
                setImageResource(resId);
            }
            super.onAttachedToWindow();
        }

        public void animationStart(Animation animation) {
            if (animator != null) {
                animator.play(animation);
            }
        }

        public void animationStop() {
            if (animator != null) {
                animator.cancel();
            }
        }

        @Override
        protected void onDetachedFromWindow() {
            if (animator != null) {
                animator.finish();
            }
            if (recycle && drawable != null && !isRecycled()) {
                recycle();
                drawable = null;
            }
            super.onDetachedFromWindow();
        }

        protected void initImage() {
            if (this.drawable != null) {
                this.drawable.setAlpha(alpha);
                this.drawable.setFilterBitmap(true);
                if (colorFilter != null) {
                    this.drawable.setColorFilter(colorFilter);
                }
            }
            if (!layout) {
                requestLayout();
                redraw();
            }
        }

        public void setImageBitmap(Bitmap image) {
            this.drawable = new android.graphics.drawable.BitmapDrawable(getResources(), image);
            initImage();
        }

        @Override
        public void setImageDrawable(android.graphics.drawable.Drawable drawable) {
            this.drawable = drawable;
            initImage();
        }

        public void setImageResource(int id) {
            if (this.drawable != null) {
                this.recycle();
            }
            if (id >= 0) {
                this.resId = id;
                setImageDrawable(getContext().getResources().getDrawable(id));
            }
        }

        public int getScaledWidth() {
            return Math.round(getImageWidth() * getScale());
        }

        public int getScaledHeight() {
            return Math.round(getImageHeight() * getScale());
        }

        public int getImageWidth() {
            if (drawable != null) {
                return drawable.getIntrinsicWidth();
            }
            return 0;
        }

        public int getImageHeight() {
            if (drawable != null) {
                return drawable.getIntrinsicHeight();
            }
            return 0;
        }

        public void moveBy(float x, float y) {
            this.x += x;
            this.y += y;
        }

        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void redraw() {
            postInvalidate();
        }

        public void setMinScale(float min) {
            this.minScale = min;
            if (gestureImageViewTouchListener != null) {
                gestureImageViewTouchListener.setMinScale(min * fitScaleHorizontal);
            }
        }

        public void setMaxScale(float max) {
            this.maxScale = max;
            if (gestureImageViewTouchListener != null) {
                gestureImageViewTouchListener.setMaxScale(max * startingScale);
            }
        }

        public float getScale() {
            return scaleAdjust;
        }

        public void setScale(float scale) {
            scaleAdjust = scale;
        }

        public float getImageX() {
            return x;
        }

        public float getImageY() {
            return y;
        }

        public boolean isStrict() {
            return strict;
        }

        public void setStrict(boolean strict) {
            this.strict = strict;
        }

        public boolean isRecycle() {
            return recycle;
        }

        public void setRecycle(boolean recycle) {
            this.recycle = recycle;
        }

        public void reset() {
            x = centerX;
            y = centerY;
            scaleAdjust = startingScale;
            if (gestureImageViewTouchListener != null) {
                gestureImageViewTouchListener.reset();
            }
            redraw();
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        public GestureImageViewListener getGestureImageViewListener() {
            return gestureImageViewListener;
        }

        public void setGestureImageViewListener(GestureImageViewListener pinchImageViewListener) {
            this.gestureImageViewListener = pinchImageViewListener;
        }

        @Override
        public android.graphics.drawable.Drawable getDrawable() {
            return drawable;
        }

        @Override
        public void setAlpha(int alpha) {
            this.alpha = alpha;
            if (drawable != null) {
                drawable.setAlpha(alpha);
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            this.colorFilter = cf;
            if (drawable != null) {
                drawable.setColorFilter(cf);
            }
        }

        @Override
        public void setImageURI(Uri mUri) {
            if ("content".equals(mUri.getScheme())) {
                try {
                    String[] orientationColumn = {android.provider.MediaStore.Images.Media.ORIENTATION};
                    android.database.Cursor cur = getContext().getContentResolver().query(mUri, orientationColumn, null, null, null);
                    if (cur != null && cur.moveToFirst()) {
                        imageOrientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                    }
                    java.io.InputStream in = null;
                    try {
                        in = getContext().getContentResolver().openInputStream(mUri);
                        Bitmap bmp = BitmapFactory.decodeStream(in);
                        if (imageOrientation != 0) {
                            Matrix m = new Matrix();
                            m.postRotate(imageOrientation);
                            Bitmap rotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
                            bmp.recycle();
                            setImageDrawable(new android.graphics.drawable.BitmapDrawable(getResources(), rotated));
                        } else {
                            setImageDrawable(new android.graphics.drawable.BitmapDrawable(getResources(), bmp));
                        }
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (cur != null) {
                            cur.close();
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                setImageDrawable(android.graphics.drawable.Drawable.createFromPath(mUri.toString()));
            }
            if (drawable == null) {
                mUri = null;
            }
        }

        @Override
        public Matrix getImageMatrix() {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            return super.getImageMatrix();
        }

        @Override
        public void setImageMatrix(Matrix matrix) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
        }

        @Override
        public void setScaleType(ScaleType scaleType) {
            if (scaleType == ScaleType.CENTER || scaleType == ScaleType.CENTER_CROP || scaleType == ScaleType.CENTER_INSIDE) {
                super.setScaleType(scaleType);
            } else if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
        }

        @Override
        public void invalidateDrawable(android.graphics.drawable.Drawable dr) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            super.invalidateDrawable(dr);
        }

        @Override
        public int[] onCreateDrawableState(int extraSpace) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            return super.onCreateDrawableState(extraSpace);
        }

        @Override
        public void setAdjustViewBounds(boolean adjustViewBounds) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            super.setAdjustViewBounds(adjustViewBounds);
        }

        @Override
        public void setImageLevel(int level) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            super.setImageLevel(level);
        }

        @Override
        public void setImageState(int[] state, boolean merge) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
        }

        @Override
        public void setSelected(boolean selected) {
            if (strict) {
                throw new UnsupportedOperationException("Not supported");
            }
            super.setSelected(selected);
        }

        @Override
        public void setOnTouchListener(OnTouchListener l) {
            this.customOnTouchListener = l;
        }

        public float getCenterX() {
            return centerX;
        }

        public float getCenterY() {
            return centerY;
        }

        public boolean isLandscape() {
            return getImageWidth() >= getImageHeight();
        }

        public boolean isPortrait() {
            return getImageWidth() <= getImageHeight();
        }

        public void setStartingScale(float startingScale) {
            this.startingScale = startingScale;
        }

        public void setStartingPosition(float x, float y) {
            this.startX = x;
            this.startY = y;
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            this.onClickListener = l;
            if (gestureImageViewTouchListener != null) {
                gestureImageViewTouchListener.setOnClickListener(l);
            }
        }

        public boolean isOrientationAligned() {
            if (deviceOrientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                return isLandscape();
            } else if (deviceOrientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                return isPortrait();
            }
            return true;
        }

        public int getDeviceOrientation() {
            return deviceOrientation;
        }
    }

    public static class GestureImageViewTouchListener implements OnTouchListener {
        private final PointF current = new PointF();
        private final PointF last = new PointF();
        private final PointF next = new PointF();
        private final PointF midpoint = new PointF();
        private final VectorF scaleVector = new VectorF();
        private final VectorF pinchVector = new VectorF();
        private GestureImageView image;
        private OnClickListener onClickListener;
        private boolean touched = false;
        private boolean inZoom = false;
        private float initialDistance;
        private float lastScale = 1.0f;
        private float currentScale = 1.0f;
        private float boundaryLeft = 0;
        private float boundaryTop = 0;
        private float boundaryRight = 0;
        private float boundaryBottom = 0;
        private float maxScale = 5.0f;
        private float minScale = 0.25f;
        private float fitScaleHorizontal = 1.0f;
        private float fitScaleVertical = 1.0f;
        private int canvasWidth = 0;
        private int canvasHeight = 0;
        private float centerX = 0;
        private float centerY = 0;
        private float startingScale = 0;
        private boolean canDragX = false;
        private boolean canDragY = false;
        private boolean multiTouch = false;
        private int displayWidth;
        private int displayHeight;
        private int imageWidth;
        private int imageHeight;
        private FlingListener flingListener;
        private FlingAnimation flingAnimation;
        private ZoomAnimation zoomAnimation;
        private MoveAnimation moveAnimation;
        private GestureDetector tapDetector;
        private GestureDetector flingDetector;
        private GestureImageViewListener imageListener;

        public GestureImageViewTouchListener(final GestureImageView image, int displayWidth, int displayHeight) {
            super();
            this.image = image;
            this.displayWidth = displayWidth;
            this.displayHeight = displayHeight;
            this.centerX = (float) displayWidth / 2.0f;
            this.centerY = (float) displayHeight / 2.0f;
            this.imageWidth = image.getImageWidth();
            this.imageHeight = image.getImageHeight();
            startingScale = image.getScale();
            currentScale = startingScale;
            lastScale = startingScale;
            boundaryRight = displayWidth;
            boundaryBottom = displayHeight;
            boundaryLeft = 0;
            boundaryTop = 0;
            next.x = image.getImageX();
            next.y = image.getImageY();
            flingListener = new FlingListener();
            flingAnimation = new FlingAnimation();
            zoomAnimation = new ZoomAnimation();
            moveAnimation = new MoveAnimation();
            flingAnimation.setListener(new FlingAnimationListener() {
                @Override
                public void onMove(float x, float y) {
                    handleDrag(current.x + x, current.y + y);
                }

                @Override
                public void onComplete() {
                }
            });
            zoomAnimation.setZoom(2.0f);
            zoomAnimation.setZoomAnimationListener(new ZoomAnimationListener() {
                @Override
                public void onZoom(float scale, float x, float y) {
                    if (scale <= maxScale && scale >= minScale) {
                        handleScale(scale, x, y);
                    }
                }

                @Override
                public void onComplete() {
                    inZoom = false;
                    handleUp();
                }
            });
            moveAnimation.setMoveAnimationListener(new MoveAnimationListener() {
                @Override
                public void onMove(float x, float y) {
                    image.setPosition(x, y);
                    image.redraw();
                }
            });
            tapDetector = new GestureDetector(image.getContext(), new android.view.GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    startZoom(e);
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (!inZoom) {
                        if (onClickListener != null) {
                            onClickListener.onClick(image);
                            return true;
                        }
                    }
                    return false;
                }
            });
            flingDetector = new GestureDetector(image.getContext(), flingListener);
            imageListener = image.getGestureImageViewListener();
            calculateBoundaries();
        }

        private void startFling() {
            flingAnimation.setVelocityX(flingListener.getVelocityX());
            flingAnimation.setVelocityY(flingListener.getVelocityY());
            image.animationStart(flingAnimation);
        }

        private void startZoom(MotionEvent e) {
            inZoom = true;
            zoomAnimation.reset();
            float zoomTo;
            if (image.isLandscape()) {
                if (image.getDeviceOrientation() == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                    int scaledHeight = image.getScaledHeight();
                    if (scaledHeight < canvasHeight) {
                        zoomTo = fitScaleVertical / currentScale;
                        zoomAnimation.setTouchX(e.getX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    } else {
                        zoomTo = fitScaleHorizontal / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    }
                } else {
                    int scaledWidth = image.getScaledWidth();
                    if (scaledWidth == canvasWidth) {
                        zoomTo = currentScale * 4.0f;
                        zoomAnimation.setTouchX(e.getX());
                        zoomAnimation.setTouchY(e.getY());
                    } else if (scaledWidth < canvasWidth) {
                        zoomTo = fitScaleHorizontal / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(e.getY());
                    } else {
                        zoomTo = fitScaleHorizontal / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    }
                }
            } else {
                if (image.getDeviceOrientation() == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                    int scaledHeight = image.getScaledHeight();
                    if (scaledHeight == canvasHeight) {
                        zoomTo = currentScale * 4.0f;
                        zoomAnimation.setTouchX(e.getX());
                        zoomAnimation.setTouchY(e.getY());
                    } else if (scaledHeight < canvasHeight) {
                        zoomTo = fitScaleVertical / currentScale;
                        zoomAnimation.setTouchX(e.getX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    } else {
                        zoomTo = fitScaleVertical / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    }
                } else {
                    int scaledWidth = image.getScaledWidth();
                    if (scaledWidth < canvasWidth) {
                        zoomTo = fitScaleHorizontal / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(e.getY());
                    } else {
                        zoomTo = fitScaleVertical / currentScale;
                        zoomAnimation.setTouchX(image.getCenterX());
                        zoomAnimation.setTouchY(image.getCenterY());
                    }
                }
            }
            zoomAnimation.setZoom(zoomTo);
            image.animationStart(zoomAnimation);
        }

        private void stopAnimations() {
            image.animationStop();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!inZoom) {
                if (!tapDetector.onTouchEvent(event)) {
                    if (event.getPointerCount() == 1 && flingDetector.onTouchEvent(event)) {
                        startFling();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        handleUp();
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        stopAnimations();
                        last.x = event.getX();
                        last.y = event.getY();
                        if (imageListener != null) {
                            imageListener.onTouch(last.x, last.y);
                        }
                        touched = true;
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (event.getPointerCount() > 1) {
                            multiTouch = true;
                            if (initialDistance > 0) {
                                pinchVector.set(event);
                                pinchVector.calculateLength();
                                float distance = pinchVector.length;
                                if (initialDistance != distance) {
                                    float newScale = (distance / initialDistance) * lastScale;
                                    if (newScale <= maxScale) {
                                        scaleVector.length *= newScale;
                                        scaleVector.calculateEndPoint();
                                        scaleVector.length /= newScale;
                                        float newX = scaleVector.end.x;
                                        float newY = scaleVector.end.y;
                                        handleScale(newScale, newX, newY);
                                    }
                                }
                            } else {
                                initialDistance = MathUtils.distance(event);
                                MathUtils.midpoint(event, midpoint);
                                scaleVector.setStart(midpoint);
                                scaleVector.setEnd(next);
                                scaleVector.calculateLength();
                                scaleVector.calculateAngle();
                                scaleVector.length /= lastScale;
                            }
                        } else {
                            if (!touched) {
                                touched = true;
                                last.x = event.getX();
                                last.y = event.getY();
                                next.x = image.getImageX();
                                next.y = image.getImageY();
                            } else if (!multiTouch) {
                                if (handleDrag(event.getX(), event.getY())) {
                                    image.redraw();
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        protected void handleUp() {
            multiTouch = false;
            initialDistance = 0;
            lastScale = currentScale;
            if (!canDragX) {
                next.x = centerX;
            }
            if (!canDragY) {
                next.y = centerY;
            }
            boundCoordinates();
            if (!canDragX && !canDragY) {
                if (image.isLandscape()) {
                    currentScale = fitScaleHorizontal;
                    lastScale = fitScaleHorizontal;
                } else {
                    currentScale = fitScaleVertical;
                    lastScale = fitScaleVertical;
                }
            }
            image.setScale(currentScale);
            image.setPosition(next.x, next.y);
            if (imageListener != null) {
                imageListener.onScale(currentScale);
                imageListener.onPosition(next.x, next.y);
            }
            image.redraw();
        }

        protected void handleScale(float scale, float x, float y) {
            currentScale = scale;
            if (currentScale > maxScale) {
                currentScale = maxScale;
            } else if (currentScale < minScale) {
                currentScale = minScale;
            } else {
                next.x = x;
                next.y = y;
            }
            calculateBoundaries();
            image.setScale(currentScale);
            image.setPosition(next.x, next.y);
            if (imageListener != null) {
                imageListener.onScale(currentScale);
                imageListener.onPosition(next.x, next.y);
            }
            image.redraw();
        }

        protected boolean handleDrag(float x, float y) {
            current.x = x;
            current.y = y;
            float diffX = (current.x - last.x);
            float diffY = (current.y - last.y);
            if (diffX != 0 || diffY != 0) {
                if (canDragX) next.x += diffX;
                if (canDragY) next.y += diffY;
                boundCoordinates();
                last.x = current.x;
                last.y = current.y;
                if (canDragX || canDragY) {
                    image.setPosition(next.x, next.y);
                    if (imageListener != null) {
                        imageListener.onPosition(next.x, next.y);
                    }
                    return true;
                }
            }
            return false;
        }

        public void reset() {
            currentScale = startingScale;
            next.x = centerX;
            next.y = centerY;
            calculateBoundaries();
            image.setScale(currentScale);
            image.setPosition(next.x, next.y);
            image.redraw();
        }

        public float getMaxScale() {
            return maxScale;
        }

        public void setMaxScale(float maxScale) {
            this.maxScale = maxScale;
        }

        public float getMinScale() {
            return minScale;
        }

        public void setMinScale(float minScale) {
            this.minScale = minScale;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        protected void setCanvasWidth(int canvasWidth) {
            this.canvasWidth = canvasWidth;
        }

        protected void setCanvasHeight(int canvasHeight) {
            this.canvasHeight = canvasHeight;
        }

        protected void setFitScaleHorizontal(float fitScale) {
            this.fitScaleHorizontal = fitScale;
        }

        protected void setFitScaleVertical(float fitScaleVertical) {
            this.fitScaleVertical = fitScaleVertical;
        }

        protected void boundCoordinates() {
            if (next.x < boundaryLeft) {
                next.x = boundaryLeft;
            } else if (next.x > boundaryRight) {
                next.x = boundaryRight;
            }
            if (next.y < boundaryTop) {
                next.y = boundaryTop;
            } else if (next.y > boundaryBottom) {
                next.y = boundaryBottom;
            }
        }

        protected void calculateBoundaries() {
            int effectiveWidth = Math.round((float) imageWidth * currentScale);
            int effectiveHeight = Math.round((float) imageHeight * currentScale);
            canDragX = effectiveWidth > displayWidth;
            canDragY = effectiveHeight > displayHeight;
            if (canDragX) {
                float diff = (float) (effectiveWidth - displayWidth) / 2.0f;
                boundaryLeft = centerX - diff;
                boundaryRight = centerX + diff;
            }
            if (canDragY) {
                float diff = (float) (effectiveHeight - displayHeight) / 2.0f;
                boundaryTop = centerY - diff;
                boundaryBottom = centerY + diff;
            }
        }
    }

    public static class MathUtils {

        public static float distance(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

        public static float distance(PointF p1, PointF p2) {
            float x = p1.x - p2.x;
            float y = p1.y - p2.y;
            return (float) Math.sqrt(x * x + y * y);
        }

        public static float distance(float x1, float y1, float x2, float y2) {
            float x = x1 - x2;
            float y = y1 - y2;
            return (float) Math.sqrt(x * x + y * y);
        }

        public static void midpoint(MotionEvent event, PointF point) {
            float x1 = event.getX(0);
            float y1 = event.getY(0);
            float x2 = event.getX(1);
            float y2 = event.getY(1);
            midpoint(x1, y1, x2, y2, point);
        }

        public static void midpoint(float x1, float y1, float x2, float y2, PointF point) {
            point.x = (x1 + x2) / 2.0f;
            point.y = (y1 + y2) / 2.0f;
        }

        public static float angle(PointF p1, PointF p2) {
            return angle(p1.x, p1.y, p2.x, p2.y);
        }

        public static float angle(float x1, float y1, float x2, float y2) {
            return (float) Math.atan2(y2 - y1, x2 - x1);
        }

        public void rotate(PointF p1, PointF p2, float angle) {
            float px = p1.x;
            float py = p1.y;
            float ox = p2.x;
            float oy = p2.y;
            p1.x = ((float) Math.cos(angle) * (px - ox) - (float) Math.sin(angle) * (py - oy) + ox);
            p1.y = ((float) Math.sin(angle) * (px - ox) + (float) Math.cos(angle) * (py - oy) + oy);
        }
    }

    public static class MoveAnimation implements Animation {
        private boolean firstFrame = true;
        private float startX;
        private float startY;
        private float targetX;
        private float targetY;
        private long animationTimeMS = 100;
        private long totalTime = 0;
        private MoveAnimationListener moveAnimationListener;

        @Override
        public boolean update(GestureImageView view, long time) {
            totalTime += time;
            if (firstFrame) {
                firstFrame = false;
                startX = view.getImageX();
                startY = view.getImageY();
            }
            if (totalTime < animationTimeMS) {
                float ratio = (float) totalTime / animationTimeMS;
                float newX = ((targetX - startX) * ratio) + startX;
                float newY = ((targetY - startY) * ratio) + startY;
                if (moveAnimationListener != null) {
                    moveAnimationListener.onMove(newX, newY);
                }
                return true;
            } else {
                if (moveAnimationListener != null) {
                    moveAnimationListener.onMove(targetX, targetY);
                }
            }
            return false;
        }

        public void reset() {
            firstFrame = true;
            totalTime = 0;
        }

        public float getTargetX() {
            return targetX;
        }

        public void setTargetX(float targetX) {
            this.targetX = targetX;
        }

        public float getTargetY() {
            return targetY;
        }

        public void setTargetY(float targetY) {
            this.targetY = targetY;
        }

        public long getAnimationTimeMS() {
            return animationTimeMS;
        }

        public void setAnimationTimeMS(long animationTimeMS) {
            this.animationTimeMS = animationTimeMS;
        }

        public void setMoveAnimationListener(MoveAnimationListener moveAnimationListener) {
            this.moveAnimationListener = moveAnimationListener;
        }
    }

    public static class VectorF {
        public final PointF start = new PointF();
        public final PointF end = new PointF();
        public float angle;
        public float length;

        public void calculateEndPoint() {
            end.x = (float) Math.cos(angle) * length + start.x;
            end.y = (float) Math.sin(angle) * length + start.y;
        }

        public void setStart(PointF p) {
            this.start.x = p.x;
            this.start.y = p.y;
        }

        public void setEnd(PointF p) {
            this.end.x = p.x;
            this.end.y = p.y;
        }

        public void set(MotionEvent event) {
            this.start.x = event.getX(0);
            this.start.y = event.getY(0);
            this.end.x = event.getX(1);
            this.end.y = event.getY(1);
        }

        public float calculateLength() {
            length = MathUtils.distance(start, end);
            return length;
        }

        public float calculateAngle() {
            angle = MathUtils.angle(start, end);
            return angle;
        }
    }

    public static class ZoomAnimation implements Animation {
        private boolean firstFrame = true;
        private float touchX;
        private float touchY;
        private float zoom;
        private float startX;
        private float startY;
        private float startScale;
        private float xDiff;
        private float yDiff;
        private float scaleDiff;
        private long animationLengthMS = 200;
        private long totalTime = 0;
        private ZoomAnimationListener zoomAnimationListener;

        @Override
        public boolean update(GestureImageView view, long time) {
            if (firstFrame) {
                firstFrame = false;
                startX = view.getImageX();
                startY = view.getImageY();
                startScale = view.getScale();
                scaleDiff = (zoom * startScale) - startScale;
                if (scaleDiff > 0) {
                    VectorF vector = new VectorF();
                    vector.setStart(new PointF(touchX, touchY));
                    vector.setEnd(new PointF(startX, startY));
                    vector.calculateAngle();
                    float length = vector.calculateLength();
                    vector.length = length * zoom;
                    vector.calculateEndPoint();
                    xDiff = vector.end.x - startX;
                    yDiff = vector.end.y - startY;
                } else {
                    xDiff = view.getCenterX() - startX;
                    yDiff = view.getCenterY() - startY;
                }
            }
            totalTime += time;
            float ratio = (float) totalTime / (float) animationLengthMS;
            if (ratio < 1) {
                if (ratio > 0) {
                    float newScale = (ratio * scaleDiff) + startScale;
                    float newX = (ratio * xDiff) + startX;
                    float newY = (ratio * yDiff) + startY;
                    if (zoomAnimationListener != null) {
                        zoomAnimationListener.onZoom(newScale, newX, newY);
                    }
                }
                return true;
            } else {
                float newScale = scaleDiff + startScale;
                float newX = xDiff + startX;
                float newY = yDiff + startY;
                if (zoomAnimationListener != null) {
                    zoomAnimationListener.onZoom(newScale, newX, newY);
                    zoomAnimationListener.onComplete();
                }
                return false;
            }
        }

        public void reset() {
            firstFrame = true;
            totalTime = 0;
        }

        public float getZoom() {
            return zoom;
        }

        public void setZoom(float zoom) {
            this.zoom = zoom;
        }

        public float getTouchX() {
            return touchX;
        }

        public void setTouchX(float touchX) {
            this.touchX = touchX;
        }

        public float getTouchY() {
            return touchY;
        }

        public void setTouchY(float touchY) {
            this.touchY = touchY;
        }

        public long getAnimationLengthMS() {
            return animationLengthMS;
        }

        public void setAnimationLengthMS(long animationLengthMS) {
            this.animationLengthMS = animationLengthMS;
        }

        public ZoomAnimationListener getZoomAnimationListener() {
            return zoomAnimationListener;
        }

        public void setZoomAnimationListener(ZoomAnimationListener zoomAnimationListener) {
            this.zoomAnimationListener = zoomAnimationListener;
        }
    }

}
