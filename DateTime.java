//Represents a specific date and time, implementing date comparison and formatting
public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateTime(
            int year,
            int month,
            int day,
            int hour,
            int minute) {
//validate month boundaries (1 - 12)
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "Month must be between 1 and 12."
            );
        }
        
//validate day boundaries (1 - 31)
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException(
                    "Day must be between 1 and 31."
            );
        }
        
//validate hour boundaries (0 - 23)
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException(
                    "Hour must be between 0 and 23."
            );
        }
        
//validate minute boundaries (0 - 59)
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException(
                    "Minute must be between 0 and 59."
            );
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }
//format the date and time
    @Override
    public String format() {

        return String.format(
                "%02d/%02d/%04d %02d:%02d",
                month,
                day,
                year,
                hour,
                minute
        );
    }
//compares this DateTime with another one 
@Override
public int compareTo(IDateTime other) {

    if (this.year < other.getYear()) {
        return -1;
    } else if (this.year > other.getYear()) {
        return 1;
    }

    if (this.month < other.getMonth()) {
        return -1;
    } else if (this.month > other.getMonth()) {
        return 1;
    }

    if (this.day < other.getDay()) {
        return -1;
    } else if (this.day > other.getDay()) {
        return 1;
    }

    if (this.hour < other.getHour()) {
        return -1;
    } else if (this.hour > other.getHour()) {
        return 1;
    }

    if (this.minute < other.getMinute()) {
        return -1;
    } else if (this.minute > other.getMinute()) {
        return 1;
    }

    return 0;//both DateTimes are equal.
}

    @Override
    public String toString() {
        return format();
    }
}