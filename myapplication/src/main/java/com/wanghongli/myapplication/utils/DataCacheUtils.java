package com.wanghongli.myapplication.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class DataCacheUtils {
    private static final String TAG = "DataCacheUtils";

    //Banner
    public static <T> T convertDataFromJson(Class<T> tClass, String json) {
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();

            return gson.fromJson(json, tClass);

        }
        return null;
    }

    //List<Banner>
    public static <T> List<T> convertDataListFromJson(Class<T> tClass, String json) {

        ParameterizedTypeImpl parameterizedTypeList = new ParameterizedTypeImpl(List.class, new Class[]{tClass});
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            return gson.fromJson(json, parameterizedTypeList);

        }
        return null;
    }


    public static String convertToJsonFromObject(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }


    /**
     * 把对象保存到本地文件
     *
     * @param data 要保存的具体java 对象
     * @param <T>
     */
    public static <T> void saveDataToFile(T data, File file) {
        if (file == null) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(data);

        Logger.v("%s save data to file = %s", TAG, LogJsonFormat.format(json));

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(json.getBytes("UTF-8"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getDataFromFile(Class<T> tClass, File file) {
        return readDataFromFile(tClass, file);
    }


    /**
     * getDataListFromFile(Banner.class,file)
     *
     * @param aClass
     * @param file
     * @param <T>
     * @return
     */
    public static <T> List<T> getDataListFromFile(Class<T> aClass, File file) {
        ParameterizedTypeImpl parameterizedTypeList = new ParameterizedTypeImpl(List.class, new Class[]{aClass});
        return readDataFromFile(parameterizedTypeList, file);
    }

    private static <T> T readDataFromFile(Type type, File file) {
        if (file == null) {
            return null;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte bytes[] = new byte[1024];
            int len = -1;
            StringBuilder builder = new StringBuilder();
            while ((len = fileInputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len, "UTF-8"));
            }

            String json = builder.toString();

            Logger.v("%s read data from file  json = %s", TAG, LogJsonFormat.format(json));

            if (!TextUtils.isEmpty(json)) {
                Gson gson = new Gson();
                return gson.fromJson(json, type);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
