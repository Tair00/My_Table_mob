package ru.mvlikhachev.mytablepr.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import ru.mvlikhachev.mytablepr.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Получить текущее время
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Создать новый экземпляр TimePickerDialog и вернуть его
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.TimePickerDialogStyle, this, hour, minute, DateFormat.is24HourFormat(getActivity())) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Установка цвета кнопок
                Button positiveButton = getButton(TimePickerDialog.BUTTON_POSITIVE);
                Button negativeButton = getButton(TimePickerDialog.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.mainColor));
                    negativeButton.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }
        };

        return timePickerDialog;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Обработать выбранное время
        // Например, можно вывести его в лог
        Log.d("TimePickerFragment", "Выбрано время: " + hourOfDay + ":" + minute);
    }
}