package rapu.timerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimerMemoDataSource {

    private static final String LOG_TAG = TimerMemoDataSource.class.getSimpleName();

    private static final String DB_SELECT_ALL = "SELECT * FROM " + TimerMemoDbHelper.TABLE_TIMER_LIST;
    private static final String DB_DELETE_WHERE = TimerMemoDbHelper.COLUMN_ID + " = ?";

    private SQLiteDatabase database;
    private TimerMemoDbHelper dbHelper;

    public TimerMemoDataSource(Context context){
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new TimerMemoDbHelper(context);
    }

    public List<TimerMemo> getTimers(){
        List<TimerMemo> timers = new ArrayList<>();

        this.database = dbHelper.getWritableDatabase();

        Cursor cursor = this.database.rawQuery(DB_SELECT_ALL, null);

        while (cursor.moveToNext()){
            try {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String endDateText = cursor.getString(2);
                TimerMemo timerMemo = new TimerMemo(id, name, endDateText);

                timers.add(timerMemo);
            } catch (ParseException exception){
                exception.printStackTrace();
            }
        }

        database.close();
        return timers;
    }

    public void addTimer(TimerMemo timerMemo){
        database = this.dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TimerMemoDbHelper.COLUMN_NAME, timerMemo.getName());
        values.put(TimerMemoDbHelper.COLUMN_END_DATE, timerMemo.getEndDateText());

        database.insert(TimerMemoDbHelper.TABLE_TIMER_LIST, null, values);

        database.close();
    }

    public void deleteTimer(int timerId){
        database = this.dbHelper.getWritableDatabase();

        database.delete(TimerMemoDbHelper.TABLE_TIMER_LIST, DB_DELETE_WHERE, new String[]{String.valueOf(timerId)});

        database.close();
    }
}
