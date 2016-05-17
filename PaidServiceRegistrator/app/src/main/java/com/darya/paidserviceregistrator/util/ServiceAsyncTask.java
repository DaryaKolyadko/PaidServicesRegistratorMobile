package com.darya.paidserviceregistrator.util;

import android.os.AsyncTask;

import com.darya.paidserviceregistrator.resourcereader.ResourceReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Даша on 17.05.2016.
 */
public class ServiceAsyncTask extends AsyncTask<String, Void, AttributeContainer> {
    @Override
    protected AttributeContainer doInBackground(String... params) {
        String commandName = params[2];
        SoapObject request = new SoapObject(ResourceReader.getString(ResourceReader.wcfServiceNamespace),
                commandName);
        request.addProperty(ResourceReader.getString(ResourceReader.parameterLogin), params[0]);
        request.addProperty(ResourceReader.getString(ResourceReader.parameterPassword), params[1]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        if (commandName.equals(ResourceReader.getString(ResourceReader.getParamValue))) {
            request.addProperty(ResourceReader.getString(ResourceReader.parameterName), params[3]);
        } else if (commandName.equals(ResourceReader.getString(ResourceReader.setParamValue))) {
            request.addProperty(ResourceReader.getString(ResourceReader.parameterName), params[3]);
            request.addProperty(ResourceReader.getString(ResourceReader.parameterValue), Integer.parseInt(params[4]));
        }

        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(ResourceReader.
                    getString(ResourceReader.wcfServiceUrl));
            androidHttpTransport.call(ResourceReader.getString(ResourceReader.commandPath) +
                    commandName, envelope);

            return (AttributeContainer) envelope.getResponse();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}