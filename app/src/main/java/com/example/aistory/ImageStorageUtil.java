package com.example.aistory;
import android.content.Context;
import android.graphics.Bitmap;
import java.io.FileOutputStream;
import java.io.IOException;
import android.util.Log;

public class ImageStorageUtil {

    public static String saveImageToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        String filePath = "";
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            filePath = context.getFilesDir() + "/" + fileName;
            Log.d("ImageStorageUtil", "Image saved at: " + filePath);  // 파일 경로 출력
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;  // 저장된 파일 경로를 반환
    }
}
