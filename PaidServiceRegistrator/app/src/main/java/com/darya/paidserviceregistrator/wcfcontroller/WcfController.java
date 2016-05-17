package com.darya.paidserviceregistrator.wcfcontroller;

import com.darya.paidserviceregistrator.R;
import com.darya.paidserviceregistrator.resourcereader.ResourceReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * Created by Даша on 13.05.2016.
 */
public class WcfController {
    private static String login;
    private static String password;

    private static StringBuilder sb = new StringBuilder();

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static String GetServices(String login, String password) {
        try
        {
            SoapObject request = new SoapObject(ResourceReader.getString(ResourceReader.wcfServiceNamespace),
                    ResourceReader.getString(ResourceReader.getServiceList));
            //request.addProperty("Name", "Qing");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(ResourceReader.
                    getString(ResourceReader.wcfServiceUrl));
            androidHttpTransport.call(ResourceReader.getString(ResourceReader.commandGetServiceList),
                    envelope);
            sb.append(envelope.toString() + "\n");
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
            String resultData = result.toString();
            sb.append(resultData + "\n");
        }
        catch (Exception e) {
            sb.append("Error:\n" + e.getMessage() + "\n");
        }

//            JSONObject jsonLogin =  new JSONObject();
//            JSONObject jsonPassword =  new JSONObject();
//            jsonLogin.put()
//            newUser.put("name", rUser.name);
//            newUser.put("email", rUser.email);
//            newUser.put("password", rUser.password);
//            newUser.put("phone",  rUser.phone);
//            newUser.put("nic", rUser.nic);
//            newUser.put("userType", rUser.userType);
//            newUser.put("image", rUser.image);
//
//            String response = postData(url, newUser.toString());
//            Log.d("response", response);
//
//            if(response!="")
//            {
//                try {
//                    JSONObject user = new JSONObject(response);
//                    User returnUser = new User( user.getInt("id"),user.getString("name"),user.getString("email"),user.getString("password"),user.getString("phone"),user.getString("nic"),user.getString("userType"), user.getString("street"), user.getString("city"), user.getString("country"), Double.parseDouble(user.getString("lat")), Double.parseDouble(user.getString("lng")),user.getInt("is_login"),user.getInt("is_vehicle_added"), user.getString("reg_id"), user.getInt("isError"), user.getString("errorMessage"), user.getString("image"));
//                    return returnUser;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return  null;
//        HttpClient client = new DefaultHttpClient();
//        URI website = new URI("http://10.0.2.2/AutoOglasi/Service1.svc/createUser");
//        HttpPost request = new HttpPost();
//        request.setEntity(new StringEntity(user.toString()));
//        request.addHeader("content-type", "application/json");
//        request.setURI(website);
//        HttpResponse response = client.execute(request);
        // }
        return "lol";
    }
}