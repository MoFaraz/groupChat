package thirdproject.groupchat.Controller;

import thirdproject.groupchat.Model.MyMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GetDate {

    public static String getCurrentDate () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getCurrentTime () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getDifferentTime (MyMessage message) throws ParseException {
        String time1 = message.getTime();
        String time2 = getCurrentTime();

        // Creating a SimpleDateFormat object
        // to parse time in the format HH:MM:SS
        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm:ss");

        // Parsing the Time Period
        Date date1 = simpleDateFormat.parse(time1);
        Date date2 = simpleDateFormat.parse(time2);

        // Calculating the difference in milliseconds
        long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());

        // Calculating the difference in Hours
        long differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                % 24;

        // Printing the answer
        if (differenceInHours < 1)
            return "Last Seen recently";
        else
            return differenceInHours + " Hours ago";
    }
}
