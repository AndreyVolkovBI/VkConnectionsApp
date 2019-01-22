package com.andreyvolkov.vkconnectionsapp.Model;

import android.content.Context;
import android.util.Log;

import com.andreyvolkov.vkconnectionsapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

public class JSONData {

    private String getStringFromFile(Integer fileId) {
        String line = "";
        try {
            InputStream is = getApplicationContext().getResources().openRawResource(fileId);
            Charset inputCharset = Charset.forName("Cp1251");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, inputCharset));
            while ((line = reader.readLine()) != null) {
                return line;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    String verifiedUsers = getStringFromFile(R.raw.verified_users);
    String categories = getStringFromFile(R.raw.categories);
}
