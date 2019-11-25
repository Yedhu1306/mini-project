package com.example.androidsmartcitytraveller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class TravelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final int TIME_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID1 = 1;
    private EditText view1;
    private EditText view2;
  //  public EditText ed;
    //public EditText ed2;
    private int hr1;
    private int min1;
    public int hr1_new;
    public int hr2_new;
    public int min1_new;
    public int min2_new;
    private int hr2;
    private int min2;
    String timeset1;
    String timeset2;
    int total_time;

    private Button b1;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view1 = (EditText) findViewById(R.id.edittext1);
        view2 = (EditText) findViewById(R.id.edittext2);
        final Calendar c1 = Calendar.getInstance();
        final Calendar c2 = Calendar.getInstance();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hr1 = c1.get(Calendar.HOUR_OF_DAY);
        min1 = c1.get(Calendar.MINUTE);
        hr2 = c2.get(Calendar.HOUR_OF_DAY);
        min2 = c2.get(Calendar.MINUTE);
        //ed = (EditText) findViewById(R.id.edittext1);
        //ed2 = (EditText) findViewById(R.id.edittext2);
        updateTime(hr1, min1);

        updateTime1(hr2, min2);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                createdDialog(0).show();
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                createdDialog(1).show();
            }
        });
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConvertintoMin(hr1_new, min1_new, hr2_new, min2_new, timeset1, timeset2);


            }
        });

    }


    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hr1, min1, false);
            case TIME_DIALOG_ID1:
                return new TimePickerDialog(this, timePickerListener1, hr2, min2, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            hr1 = hourOfDay;
            min1 = minutes;
            updateTime(hr1, min1);

        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            hr2 = hourOfDay;
            min2 = minutes;
            updateTime1(hr2, min2);
        }
    };


    private static String utilTime(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else return String.valueOf(value);
    }


    void ConvertintoMin(int hours1, int min1, int hours2, int min2, String timeset1, String timeset2) {
        int hour1tomin1 = 0;
        int hour2tomin2 = 0;
        int diff = 0;
        //am-am or pm-pm
        if (timeset1 == timeset2) {
            if (hours1 == 12) {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour1tomin1 - hour2tomin2;

            } else if (hours2 == 12) {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour1tomin1 + hour2tomin2;


            } else {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour2tomin2 - hour1tomin1;
            }
        } else if ((timeset1 == "AM" && timeset2 == "PM")) {
            if (hours2 == 12) {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour2tomin2 - hour1tomin1;
            } else if (hours1 == 12) {
                hours1 -= 12;
                hours2 += 12;
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour2tomin2 - hour1tomin1;
            }
                else
                {
                    hours2=hours2+12;
                    hour1tomin1 = hours1 * 60 + min1;
                    hour2tomin2 = hours2 * 60 + min2;
                    diff = hour2tomin2 - hour1tomin1;

                }
            }
         else if (timeset1 == "PM" && timeset2 == "AM") {
            if (hours2 == 12) {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour2tomin2 - hour1tomin1;
            } else if (hours1 == 12) {
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour1tomin1 + hour2tomin2;
            }
            else {
                hours2=hours2+12;
                hour1tomin1 = hours1 * 60 + min1;
                hour2tomin2 = hours2 * 60 + min2;
                diff = hour2tomin2 - hour1tomin1;
            }
        }



        if(diff<120||diff>=600)
        {
            Toast.makeText(this, "Please enter minimum time interval of 2 hours and maximum of 10 hours", Toast.LENGTH_SHORT).show();
        }

        else
            {
            total_time=diff;
            DataSharing dataSharing=new DataSharing(TravelActivity.this);
            dataSharing.add("time",total_time+"");
          //  Toast.makeText(this,hour2tomin2-hour1tomin1+"",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(TravelActivity.this,questionnaire1.class);
            startActivity(intent);
            }
        }

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        hr1_new=hours;
        min1_new=mins;
        timeset1=timeSet;
        String a1Time = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        view1.setText(a1Time);

    }
    private void updateTime1(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        hr2_new=hours;
        min2_new=mins;
        timeset2=timeSet;
        String a2Time = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        view2.setText(a2Time);
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(TravelActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(true)
        .setNegativeButton("Cancel",null);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //android.support.v4.app.Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id) {

            case R.id.nav_home:
                Intent h=new Intent(TravelActivity.this,TravelActivity.class);
                startActivity(h);
                break;
            case R.id.nav_aboutus:
                Intent about=new Intent(TravelActivity.this,AboutUs_activity.class);
                startActivity(about);
                break;
            case R.id.nav_feedback:
                Intent f=new Intent(TravelActivity.this,Feedback_activity.class);
                startActivity(f);
                break;
            case R.id.nav_logout:

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TravelActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_travelplan:
                Intent intent1=new Intent(TravelActivity.this,TravelPlanActivity.class);
                startActivity(intent1);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
