package br.org.cesar.knot.beamsensor.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import br.org.cesar.knot.beamsensor.model.BeamSensorData;

public final class Utils {

    private Utils() {
    }

    public static BitmapDescriptor vectorToBitmap(@DrawableRes int id, Resources resources) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(resources, id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public static Calendar loadNextDateFromHistory(BeamSensorData data) {

        Calendar calendar = null;

        try {
            if (data != null && data.getTimestamp() != null) {
                calendar = Calendar.getInstance();
                String output = data.getTimestamp().replace("T", " ").replace("Z", "");

                //2017-04-17T16:48:29.172Z
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
                calendar.setTime(sdf.parse(output));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            calendar = null;
        }

        return calendar;
    }


    public static String convertDate(String zDate) {
        String convertedDate = zDate;

        try {
            String output = zDate.replace("T", " ").replace("Z", "");
            //2017-04-17T16:48:29.172Z
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = sdf.parse(output);

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

            convertedDate = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

}
