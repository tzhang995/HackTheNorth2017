package hackers.hackthenorth2017;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;

/**
 * Created by tony on 17/09/17.
 */

public class ImageActivity extends Activity{
    public Uri uri;
    private int _xDelta;
    private int _yDelta;
    private ViewGroup rootLayout;

    private android.widget.RelativeLayout.LayoutParams layoutParams;
    Activity myActivity;
    private String mUsername = "anon";
    EditText editText;
    EditText blankText;
    Button done;
    ImageView pikachu;
    ImageView turtleshell;
    ImageView goosebeak;
    ImageView sharkmouth;
    ImageView dogears;
    ImageView catearswhiskers;
    ImageView horns;
    ImageView panda;
    ImageView trumphair;
    ImageView tophat;
    int counter;
    Button next;

    View curFilter;

    ArrayList<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        counter=0;
        next = (Button) findViewById(R.id.next);

        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        uri = Uri.parse(getIntent().getStringExtra("ImageUri"));
        myActivity = this;

        editText = (EditText) findViewById(R.id.edit_text);
        blankText = (EditText) findViewById(R.id.blankText);
        dogears = (ImageView) findViewById(R.id.dogears);
        turtleshell = (ImageView) findViewById(R.id.turtleshell);
        pikachu = (ImageView) findViewById(R.id.pikachu);
        goosebeak = (ImageView) findViewById(R.id.goosebeak);
        sharkmouth = (ImageView) findViewById(R.id.sharkmouth);
        catearswhiskers = (ImageView) findViewById(R.id.catearswhiskers);
        horns = (ImageView) findViewById(R.id.horns);
        panda = (ImageView) findViewById(R.id.panda);
        trumphair = (ImageView) findViewById(R.id.trumphair);
        tophat = (ImageView) findViewById(R.id.tophat);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 500);
        dogears.setLayoutParams(layoutParams);
        turtleshell.setLayoutParams(layoutParams);
        pikachu.setLayoutParams(layoutParams);
        goosebeak.setLayoutParams(layoutParams);
        sharkmouth.setLayoutParams(layoutParams);
        catearswhiskers.setLayoutParams(layoutParams);
        horns.setLayoutParams(layoutParams);
        panda.setLayoutParams(layoutParams);
        trumphair.setLayoutParams(layoutParams);
        tophat.setLayoutParams(layoutParams);
        dogears.setVisibility(View.GONE);
        turtleshell.setVisibility(View.GONE);
        pikachu.setVisibility(View.GONE);
        goosebeak.setVisibility(View.GONE);
        sharkmouth.setVisibility(View.GONE);
        catearswhiskers.setVisibility(View.GONE);
        horns.setVisibility(View.GONE);
        panda.setVisibility(View.GONE);
        trumphair.setVisibility(View.GONE);
        tophat.setVisibility(View.GONE);
        dogears.setOnTouchListener(new ChoiceTouchListener());
        turtleshell.setOnTouchListener(new ChoiceTouchListener());
        pikachu.setOnTouchListener(new ChoiceTouchListener());
        goosebeak.setOnTouchListener(new ChoiceTouchListener());
        sharkmouth.setOnTouchListener(new ChoiceTouchListener());
        catearswhiskers.setOnTouchListener(new ChoiceTouchListener());
        horns.setOnTouchListener(new ChoiceTouchListener());
        panda.setOnTouchListener(new ChoiceTouchListener());
        trumphair.setOnTouchListener(new ChoiceTouchListener());
        tophat.setOnTouchListener(new ChoiceTouchListener());
        views = new ArrayList<>();
        views.add(editText);
        views.add(turtleshell);
        views.add(dogears);
        views.add(sharkmouth);
        views.add(goosebeak);
        views.add(pikachu);
        views.add(catearswhiskers);
        views.add(horns);
        views.add(panda);
        views.add(tophat);
        views.add(trumphair);
        curFilter = editText;
        ImageView img = (ImageView) findViewById(R.id.ImageFilter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearFocus();
                blankText.requestFocus();
                InputMethodManager imm = (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        img.setImageURI(uri);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                curFilter.setVisibility(View.GONE);
                if (counter< views.size()){
                    curFilter = views.get(counter);
                } else {
                    curFilter = views.get(0);
                    counter = 0;
                }
                curFilter.setVisibility(View.VISIBLE);

            }
        });

        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new ButtonPress());
    }

    public class ButtonPress implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            done.setVisibility(View.GONE);
            takeScreenshot();
//            Intent returnIntent = new Intent();
//            returnIntent.setData(uri);
//            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void openScreenshot(File imageFile) {
        Uri uri = Uri.fromFile(imageFile);

        Intent returnIntent = new Intent();
        returnIntent.setData(uri);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);

            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -300;
                    layoutParams.bottomMargin = -300;
                    view.setLayoutParams(layoutParams);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - params.leftMargin;
                    _yDelta = Y - params.topMargin;
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }
}
