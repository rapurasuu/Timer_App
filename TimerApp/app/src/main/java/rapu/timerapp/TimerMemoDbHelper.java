package rapu.timerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimerMemoDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = TimerMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME = "TimerApp.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_TIMER_LIST = "TB_Timer";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_END_DATE = "EndDate";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_TIMER_LIST +
                                                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                COLUMN_NAME + " TEXT NOT NULL, " +
                                                COLUMN_END_DATE + " TEXT NOT NULL);";

    public TimerMemoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            sqLiteDatabase.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
