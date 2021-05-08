package net.risingcode.coop_picker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ScrollingActivity extends AppCompatActivity {
    TextView dispChosen;
    ImageView dispNotSelected;
    ImageView dispNotChosen;
    public static final String TAG = "Coop-Picker";
    public static final int[] IMAGE_IDS = {
            R.id.cdr_0, R.id.cdr_1, R.id.cdr_2, R.id.cdr_3,
            R.id.cdr_4, R.id.cdr_5, R.id.cdr_6, R.id.cdr_7,
            R.id.cdr_8, R.id.cdr_9, R.id.cdr_10, R.id.cdr_11,
            R.id.cdr_12, R.id.cdr_13, R.id.cdr_14, R.id.cdr_15,
            R.id.cdr_16, R.id.cdr_17,//};/*,
            R.id.chosen, R.id.random, R.id.reset, R.id.about};//*/
    public static final int[] IMAGE_DRAWABLES = {
            R.drawable.cdr_0, R.drawable.cdr_1, R.drawable.cdr_2, R.drawable.cdr_3,
            R.drawable.cdr_4, R.drawable.cdr_5, R.drawable.cdr_6, R.drawable.cdr_7,
            R.drawable.cdr_8, R.drawable.cdr_9, R.drawable.cdr_10, R.drawable.cdr_11,
            R.drawable.cdr_12, R.drawable.cdr_13, R.drawable.cdr_14, R.drawable.cdr_15,
            R.drawable.cdr_16, R.drawable.cdr_17,//};/*,
            R.drawable.risingcode_128px, R.drawable.cdr_x, R.drawable.reset, R.drawable.about};//*/
    public static final int[] TEXT_STRINGS = {
            R.string.cdr_0, R.string.cdr_1, R.string.cdr_2, R.string.cdr_3,
            R.string.cdr_4, R.string.cdr_5, R.string.cdr_6, R.string.cdr_7,
            R.string.cdr_8, R.string.cdr_9, R.string.cdr_10, R.string.cdr_11,
            R.string.cdr_12, R.string.cdr_13, R.string.cdr_14, R.string.cdr_15,
            R.string.cdr_16, R.string.cdr_17};
    public boolean[] active = {
            false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false,
            false, false, false, false};
    List<Integer> chosen = new ArrayList<Integer>();
    private Point size = new Point(0,0);
    private GridView imageGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageGridView = (GridView) findViewById(R.id.image_gridview);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), IMAGE_DRAWABLES);
        imageGridView.setAdapter(customAdapter);
        imageGridView.setOnItemClickListener(gridViewListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_risingcode) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://risingcode.net/"));
            startActivity(browserIntent);
        } else if (id == R.id.action_debug) {
            showDebug();
        } else if (id == R.id.action_chose) {
            choseCommander();
        } else if (id == R.id.action_reset) {
            resetCommander();
        } else if (id == R.id.action_about) {
            showAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    AdapterView.OnItemClickListener gridViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ImageView img = (ImageView) view.findViewById(IMAGE_IDS[position]);
            if (img != null) {
                Log.d(TAG, String.format(Locale.getDefault(),"%s %s %s %s",
                        IMAGE_DRAWABLES[position], R.drawable.about, R.drawable.cdr_x, R.drawable.reset));
                if (IMAGE_DRAWABLES[position] == R.drawable.about) {
                    showAbout();
                } else if (IMAGE_DRAWABLES[position] == R.drawable.cdr_x) {
                    choseCommander();
                } else if (IMAGE_DRAWABLES[position] == R.drawable.reset) {
                    resetCommander();
                } else {
                    if (active[position]) {
                        active[position] = false;
                        img.setBackgroundResource(0);
                        img.setImageResource(IMAGE_DRAWABLES[position]);
                        if (chosen.contains(position)) {
                            chosen.remove(chosen.indexOf(position));
                        }
                    } else {
                        chosen.add(position);
                        //Log.d(TAG, String.format("chosen add %d", i));
                        active[position] = true;
                        img.setBackgroundResource(IMAGE_DRAWABLES[position]);
                        img.setImageResource(R.drawable.cdr_selected_clean);
                    }
                }
            } else {
                Log.e(TAG, String.format(Locale.getDefault(), "%d %d", position, IMAGE_DRAWABLES[position]));
            }
        }
    };

    private void choseCommander() {
        dispChosen = findViewById(R.id.chosen);
        int chosen_size = chosen.size();
        if (chosen_size > 0) {
            int selected = chosen.get(new Random().nextInt(chosen.size()));
            // Log.d(TAG, String.format(Locale.getDefault(), "selected %d", selected));
            // Log.d(TAG, String.format(Locale.getDefault(), "chosen size: %d", chosen.size()));
            Log.d(TAG, String.format(Locale.getDefault(),
                    "commander: %s", getResources().getText(TEXT_STRINGS[selected])));
            dispChosen.setText(String.format(Locale.getDefault(),
                    "%d; %s", chosen.size(), getResources().getText(TEXT_STRINGS[selected])));
            Log.d(TAG, String.format("IMAGE_IDS.length: %d", IMAGE_IDS.length));
            for (int i = 0; i < IMAGE_IDS.length-3; i++) {
                if (selected != IMAGE_IDS[i]) {
                    try {
                        dispNotSelected = findViewById(IMAGE_IDS[i]);
                    } catch (ClassCastException cce) {
                        //Log.d(TAG, String.format("i: %d", i));
                        // TODO: app is unable to find view which are offscreen
                    }
                    if (dispNotSelected != null) {
                        dispNotSelected.setAlpha(0.1f);
                    } else {
                        //Log.d(TAG, String.format("IMAGE_IDS id not selected: %d", i));
                    }
                }
            }
            for (int i = 0; i < chosen_size; i++) {
                //Log.d(TAG, String.format("chosen get %d", chosen.get(i)));
                dispNotChosen = findViewById(IMAGE_IDS[chosen.get(i)]);
                dispNotChosen.setAlpha(0.25f);
            }
            findViewById(IMAGE_IDS[selected]).setAlpha(1.0f);
            dispChosen.setAlpha(1.0f);
        } else {
            dispChosen.setText(R.string.error_no_selection);
        }
    }

    private void resetCommander() {
        chosen.clear();
        dispChosen = findViewById(R.id.chosen);
        dispChosen.setText("");
        for (int i = 0; i < imageGridView.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) imageGridView.getChildAt(i);
            if (ll.getChildAt(0).getId() != R.id.chosen &&
                    ll.getChildAt(0).getId() != R.id.random &&
                    ll.getChildAt(0).getId() != R.id.reset) {
                ImageView iv = (ImageView) ll.getChildAt(0);
                active[i] = false;
                iv.setAlpha(1.0f);
                iv.setBackgroundResource(0);
                iv.setImageResource(IMAGE_DRAWABLES[i]);
            }
        }
    }

    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_about)
                .setMessage(R.string.app_description)
                .setNeutralButton(R.string.about_close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {/* Close*/}
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showDebug() {
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        String msg = (String) String.format(Locale.getDefault(),
                "%d %d\nAndroid %s\nManufacturer: %s \nModel: %s \nDevice: %s" +
                        "\nBrand: %s\nDisplay: %s\nHardware: %s\nProduct: %s\nTime: %s\nUser: %s",
                size.x, size.y, Build.VERSION.RELEASE, Build.MANUFACTURER, Build.MODEL, Build.DEVICE,
                Build.BRAND, Build.DISPLAY, Build.HARDWARE, Build.PRODUCT, Build.TIME, Build.USER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_debug).setMessage(msg)
                .setNeutralButton(R.string.about_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {/* Close*/}
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    /*private int random(int min, int max) {
        // java has quite a complex random -> way easier in lists
        return new Random().nextInt((max - min) + 1) + min;
    }*/
}
