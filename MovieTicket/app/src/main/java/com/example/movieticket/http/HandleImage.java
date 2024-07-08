package com.example.movieticket.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.movieticket.models.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class HandleImage implements Serializable {

    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String AVATAR_KEY = Constant.sp_avatarURL;

    public HandleImage(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(Constant.sp, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public byte[] convertInputImage(String path) {
        // get File
        File image = new File(path);

        // try with resource for automatic close stream
        try (
                // put file into stream
                FileInputStream fis = new FileInputStream(image);
        ) {
            // create object byte[] type for storage image
            byte[] bytes = new byte[(int) image.length()];
            // put image in stream into byte[]
            fis.read(bytes);

            String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
            editor.putString(AVATAR_KEY, base64);
            editor.apply();
            return bytes;
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }
    }

    public Bitmap drawImageFromByte (byte[] imageSerialize) {
        if (imageSerialize != null) {
            return BitmapFactory.decodeByteArray(imageSerialize, 0, imageSerialize.length);
        }
        return null;
    }

    public String convertImageString(byte[] data) {
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        editor.putString(AVATAR_KEY, base64);
        editor.apply();
        return base64;
    }

}
