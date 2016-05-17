package com.darya.paidserviceregistrator.util;

import com.darya.paidserviceregistrator.entities.Service;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 17.05.2016.
 */
public class SoapObjectParser {
    public static List<Service> parse(SoapObject soapObject) {
        List<Service> serviceList = new ArrayList<>();

        for (int i = 0; i < soapObject.getPropertyCount(); i++) {
            SoapObject service = (SoapObject) soapObject.getProperty(i);
            String id = service.getProperty("Id").toString();
            String name = service.getProperty("Name").toString();
            serviceList.add(getService(id, name));
        }
        return serviceList;
    }

    private static Service getService(String id, String name) {
        int castedId = Integer.parseInt(id);
        return new Service(castedId, name);
    }
}
