package rapu.timerapp;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TimerMemoDataSource dataSource;

    private TimerAdapter timerAdapter;

    private Timer timer;

    private boolean isTimerRunning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        dataSource = new TimerMemoDataSource(this);

        timer = new Timer();

        timerAdapter = new TimerAdapter(this, dataSource.getTimers(), dataSource);

        // Layout for ListView
        View view_listViewTimers = findViewById(R.id.view_listViewTimers);
        ((TextView) view_listViewTimers.findViewById(R.id.textView_header)).setText(R.string.current_timers);
        view_listViewTimers.findViewById(R.id.button_fold_unfold).setOnClickListener(this);
        view_listViewTimers.findViewById(R.id.layout_foldable).setVisibility(View.GONE);
        ListView listView_timers = view_listViewTimers.findViewById(R.id.listView_timers);
        listView_timers.setAdapter(timerAdapter);


        // Layout for new Timer
        View view_newTimer = findViewById(R.id.view_newTimer);
        ((TextView) view_newTimer.findViewById(R.id.textView_header)).setText(R.string.new_timer);
        view_newTimer.findViewById(R.id.button_fold_unfold).setOnClickListener(this);
        view_newTimer.findViewById(R.id.layout_foldable).setVisibility(View.GONE);
        ((TimePicker) view_newTimer.findViewById(R.id.timePicker_endTime)).setIs24HourView(true);
        view_newTimer.findViewById(R.id.butto_AddTimer).setOnClickListener(this);

        startTimer();
    }

    protected void startTimer() {
        isTimerRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            timerAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_fold_unfold:{
                View currentViewLevel = view;

                do {
                    currentViewLevel = (View) currentViewLevel.getParent();
                }while (currentViewLevel.getId() != R.id.layout_foldableParent);

                View foldableView = currentViewLevel.findViewById(R.id.layout_foldable);
                Button button_foldUnfold = currentViewLevel.findViewById(R.id.button_fold_unfold);

                if (foldableView.getVisibility() == View.VISIBLE){
                    foldableView.setVisibility(View.GONE);
                    button_foldUnfold.setText(R.string.button_folded);
                } else {
                    foldableView.setVisibility(View.VISIBLE);
                    button_foldUnfold.setText(R.string.button_unfolded);
                }
                break;
            }
            case R.id.butto_AddTimer:{
                View view_newTimer = findViewById(R.id.view_newTimer);

                EditText editText_timerName = view_newTimer.findViewById(R.id.editText_timerName);
                DatePicker datePicker = view_newTimer.findViewById(R.id.datePicker_endDate);
                TimePicker timePicker = view_newTimer.findViewById(R.id.timePicker_endTime);

                int year, month, day, hour, minute;
                String name = String.valueOf(editText_timerName.getText());

                year = datePicker.getYear() - 1900;
                month = datePicker.getMonth();
                day = datePicker.getDayOfMonth();
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();

                Date date = new Date(year, month, day, hour, minute);

                TimerMemo timerMemo = new TimerMemo(-1, name, date);

                dataSource.addTimer(timerMemo);

                timerAdapter.clear();
                timerAdapter.addAll(dataSource.getTimers());
            }
        }
    }
}
