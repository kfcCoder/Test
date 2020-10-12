package com.example.alarmmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.alarmmanagerexample.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
        Button bt_TimePicker = findViewById(R.id.button_timepicker);
        Button bt_Cancel = findViewById(R.id.button_cancel);
        mAlarmManager = getSystemService(AlarmManager.class);

        bt_TimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open TimePickerFragment
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        bt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    private void updateTimeText(Calendar calendar) {
        String timeText = "Alarm set for: ";

        // getTimeInstance(int style), 只顯示時間(按照指定的格式)
        // DateFormat.SHORT, 完全为数字，如 12.13.52 或 3:30pm
        // format(Date date), Formats a Date into a date/time string.
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        mTextView.setText(timeText);
    }

    private void startAlarm(Calendar calendar) {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // 時間到就會發起PendingIntent(發廣播到Receiver)
        // setExact(int type, long triggerAtMillis, PendingIntent operation), Schedule an alarm to be delivered precisely at the stated time.
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        mAlarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }
}
