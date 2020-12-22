package com.example.bigproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import static android.util.Base64.encodeToString;

public class DbBitmapUtility {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        if(bitmap!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        if(image!=null){
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }
    public static String getStrings(byte[] image) {
        if(image!=null){
            String string=encodeToString(image, Base64.DEFAULT);
            return string;
        }
        return null;
    }
    public static Bitmap StringToBitmap(String image){
        if(image!=null){
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return  decodedByte;
        }
        return null;
    }
}
