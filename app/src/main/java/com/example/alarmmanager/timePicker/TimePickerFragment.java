package com.example.alarmmanager.timePicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance(); // 已預設為系統時間
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24時制
        int minute = calendar.get(Calendar.MINUTE);

        // TimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView)
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)getActivity(),
                hour, minute, DateFormat.is24HourFormat(getActivity()));

    }
}
