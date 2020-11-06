package org.humanoid.notes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jugal Mistry on 6/19/2019.
 */
public class Utility {

    public static String getCurrentTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
}
