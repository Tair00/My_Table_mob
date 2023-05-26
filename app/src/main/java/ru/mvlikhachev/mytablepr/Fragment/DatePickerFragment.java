package ru.mvlikhachev.mytablepr.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import ru.mvlikhachev.mytablepr.R;


public class DatePickerFragment extends DialogFragment

     implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Получение текущей даты
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Создание нового объекта DatePickerDialog и его возврат
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerDialogStyle, this, year, month, day) {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Установка цвета кнопок
                Button positiveButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);
                Button negativeButton = getButton(DatePickerDialog.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.mainColor));
                    negativeButton.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }
        };

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
            // Обработка выбранной даты

        }
}
