package rapu.timerapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerMemo {
    private final long id;
    private String name;
    private Date endDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public TimerMemo(long id, String name, Date endDate){
        this.id = id;
        this.name = name;
        this.endDate = endDate;
    }

    public TimerMemo(long id, String name, String endDate) throws ParseException {
        this.id = id;
        this.name = name;
        this.endDate = dateFormat.parse(endDate);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDateText() {
        return dateFormat.format(endDate);
    }

    public Date getEndDate(){
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return this.getName() + ": " + this.getEndDateText();
    }
}
