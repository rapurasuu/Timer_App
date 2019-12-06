package rapu.timerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimerAdapter extends ArrayAdapter<TimerMemo> {
    private TimerMemoDataSource dataSource;

    public TimerAdapter(Context context, List<TimerMemo> timers, TimerMemoDataSource dataSource) {
        super(context, 0, timers);

        this.dataSource = dataSource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final TimerMemo timer = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_timer, parent, false);
        }

        TextView textView_name = convertView.findViewById(R.id.textView_timerName);
        TextView textView_noch = convertView.findViewById(R.id.textView_noch);
        TextView textView_bis = convertView.findViewById(R.id.textView_bis);

        Date currentTime = Calendar.getInstance().getTime();
        Date endDate = timer.getEndDate();

        long diff = endDate.getTime() - currentTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String temp = getContext().getString(R.string.textView_stillXTime);
        String time = String.format(temp + ": %1$d Tage, %2$d Stunden, %3$d Minuten, %4$d Sekunden", diffDays, diffHours, diffMinutes, diffSeconds);

        textView_name.setText(timer.getName());
        textView_noch.setText(time);

        temp = getContext().getString(R.string.textView_untilXTime );
        textView_bis.setText(temp + ": " + timer.getEndDateText());

        Button button_deleteTimer = convertView.findViewById(R.id.button_deleteTimer);
        button_deleteTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.deleteTimer((int) timer.getId());
                remove(timer);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
