package shelton.c169.wgustudentplanner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateOnClickListener implements OnClickListener {

    private Context context;
    private EditText editText;

    DateOnClickListener(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dateString = String.format
                                ("%s/%s/%s", monthOfYear + 1, dayOfMonth, year);
                        editText.setText(dateString);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}