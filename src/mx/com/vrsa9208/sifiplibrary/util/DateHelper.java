/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrador
 */
public class DateHelper {
    
    public static String getStringDateNow(){
        return new SimpleDateFormat("y-M-d").format(new GregorianCalendar().getTime());
    }
    
    public static GregorianCalendar dateToGregorianCalendar(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        
        return calendar;
    }
    
    public static String CalendarToString(Calendar calendar){
        GregorianCalendar gregorianCalendar = (GregorianCalendar) calendar;
        return new SimpleDateFormat("y-M-d").format(gregorianCalendar.getTime());
    }
}
