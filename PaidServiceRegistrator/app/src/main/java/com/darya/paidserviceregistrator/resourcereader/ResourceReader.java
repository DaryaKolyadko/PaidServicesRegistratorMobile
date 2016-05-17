package com.darya.paidserviceregistrator.resourcereader;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Даша on 13.05.2016.
 */
public class ResourceReader {
    private static Properties properties;
    private static String fileName = "app.properties";
    private static Context context;

    public static String getString(String id) {
        return properties.getProperty(id);
    }

    public static void init(Context appContext) {
        context = appContext;

        try {
            properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String randomLineLength = "randomLineLength";
    public static String wcfServiceUrl = "wcfServiceUrl";
    public static String wcfServiceNamespace = "wcfServiceNamespace";
    public static String getParamValue = "getParamValue";
    public static String checkCredentials = "checkCredentials";
    public static String setParamValue = "setParamValue";
    public static String commandPath = "commandPath";
    public static String getServiceList = "getServiceList";
    public static String parameterLogin = "parameterLogin";
    public static String parameterPassword = "parameterPassword";
    public static String parameterName = "parameterName";
    public static String parameterValue = "parameterValue";
}