package com.wayonsys.cn.ImChat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allen on 2016/4/5.
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final ZoneId ZONE = ZoneId.of("Asia/Shanghai");
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public  static final String DEFAULT_DATEMINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public  static final String DEFAULT_MINUTE_FORMAT = "HH:mm";
    public static final String DEFAULT_YEAR_FORMAT = "yyyy";

    private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();
    private static Map<String, DateTimeFormatter> dateTimeFormatMap = new HashMap<>();
    static {
        dateTimeFormatMap.put(DEFAULT_DATE_FORMAT,DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        dateTimeFormatMap.put(DEFAULT_DATETIME_FORMAT,DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
        dateTimeFormatMap.put(DEFAULT_YEAR_FORMAT,DateTimeFormatter.ofPattern(DEFAULT_YEAR_FORMAT));
        dateTimeFormatMap.put(DEFAULT_DATEMINUTE_FORMAT,DateTimeFormatter.ofPattern(DEFAULT_DATEMINUTE_FORMAT));
        dateTimeFormatMap.put(DEFAULT_MINUTE_FORMAT,DateTimeFormatter.ofPattern(DEFAULT_MINUTE_FORMAT));
    }

    public static String getCurrentDateTimeString() {
        LocalDateTime now = LocalDateTime.now(ZONE);
        return convertDateTime2String(now);
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZONE);
    }

    public static Long getCurrentMillis() {
        Clock clock = Clock.system(ZONE);
        return clock.millis();
    }

    public static String convertDate2String(Date date){
        if(date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        return sdf.format(date);
    }

    public static Long getCurrentSecond() {
        Clock clock = Clock.system(ZONE);
        return clock.millis()/1000;
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now(ZONE);
    }

    public static Long getTodayFromSecond() {
        return getCurrentLocalDate().atStartOfDay(ZONE).toEpochSecond();
    }

    public static LocalDate getCurrentMonth() {

        LocalDate date = getCurrentLocalDate();
        return LocalDate.of(date.getYear(),date.getMonth(),1);
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String convertDate2String(LocalDate date) {

        if(date == null) {
            return "";
        }
        DateTimeFormatter df = getDateTimeFormat(DEFAULT_DATE_FORMAT);
        return df.format(date);
    }

    public static String convertDateTime2String(LocalDateTime dateTime) {
        DateTimeFormatter df = getDateTimeFormat(DEFAULT_DATETIME_FORMAT);
        return df.format(dateTime);
    }

    public static DateFormat getDateFormat(String dateFormat) {

        DateFormat result = dateFormatMap.get(dateFormat);
        if (result == null) {
            DateFormat format = new SimpleDateFormat(dateFormat);
            dateFormatMap.put(dateFormat, format);
            return format;
        }
        return result;
    }

    public static DateTimeFormatter getDateTimeFormat(String dateFormat) {

        DateTimeFormatter result = dateTimeFormatMap.get(dateFormat);
        if (result == null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
            dateTimeFormatMap.put(dateFormat, format);
            return format;
        }
        return result;
    }

    public static boolean isSameDay(LocalDateTime d1, LocalDateTime d2) {

        if(d1 == null || d2 == null) {
            return false;
        }
        if(d1.getYear() == d2.getYear() && d1.getDayOfYear() == d2.getDayOfYear()) {
            return true;
        }
        return false;
    }

    public static LocalDate convertString2Date(String date) {

        if(StringUtils.isBlank(date)) {
            return null;
        }
        if(date.length() > 10) {
            date = date.substring(0,10);
        }
        DateTimeFormatter d = getDateTimeFormat(DEFAULT_DATE_FORMAT);
        return LocalDate.parse(date);
    }

    public static LocalDateTime getLocalDateTime(String date) {

        if(StringUtils.isBlank(date)) {
            return null;
        }
        DateTimeFormatter d;
        if(date.length() >= 19) {
            date = date.substring(0,19);
            d = getDateTimeFormat(DEFAULT_DATETIME_FORMAT);
        }else{
            d = getDateTimeFormat(DEFAULT_DATEMINUTE_FORMAT);
        }

        LocalDateTime result = LocalDateTime.parse(date,d);

        return result;
    }

    public static LocalDateTime getDateFrom(String date) {

        if(StringUtils.isBlank(date)) {
            return null;
        }
        LocalDate localDate = convertString2Date(date);
        LocalDateTime result = LocalDateTime.of(localDate, LocalTime.MIN);
        return result;
    }

    public static LocalDateTime getDateFrom() {
        LocalDate localDate = getCurrentLocalDate();
        LocalDateTime result = LocalDateTime.of(localDate, LocalTime.MIN);
        return result;
    }

    public static LocalDateTime getDateTo(String date) {

        if(StringUtils.isBlank(date)) {
            return null;
        }
        LocalDate localDate = convertString2Date(date);
        LocalDateTime result = LocalDateTime.of(localDate, LocalTime.MAX);
        return result;
    }

    public static LocalDateTime getDateFrom(LocalDate localDate) {
        LocalDateTime result = LocalDateTime.of(localDate, LocalTime.MIN);
        return result;
    }

    public static LocalDateTime getEndDate(LocalDate localDate) {
        LocalDateTime result = LocalDateTime.of(localDate, LocalTime.MAX);
        return result;
    }

    public static LocalDateTime getDateTo(LocalDate localDate) {
        LocalDateTime result = LocalDateTime.of(localDate,LocalTime.of(23,59,59));
        return result;
    }

    public static LocalDate getYesterday() {
        LocalDate date = getCurrentLocalDate();
        return date.plusDays(-1);
    }

    public static LocalDate getLastMonth() {
        LocalDate date = getCurrentLocalDate();
        return date.plusMonths(-1);
    }



    public static LocalDateTime asLocalDateTime(Date date) {

        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant,ZONE);
    }

    public static int getAge(LocalDate birthday) {

        LocalDate now = getCurrentLocalDate();
        Period period = Period.between(birthday, now);
        return period.getYears();
    }


    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZONE).toLocalDate();
    }


    public static long getDays(LocalDate day) {

        LocalDate now = getCurrentLocalDate();
        return getDays(day, now);
    }

    public static long getDays(LocalDate date1, LocalDate date2) {
        long result = ChronoUnit.DAYS.between(date1, date2);
        return result;
    }

    public static Boolean isFirstDayOfMonth() {
        return false;
    }



    public static void main(String[] args) {

        long days = ChronoUnit.DAYS.between(getCurrentLocalDate(), getCurrentLocalDate());
        log.info(String.valueOf(days));






    }




}
