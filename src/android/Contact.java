package com.discotech.contact;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.net.Uri;

import android.app.Activity;
import android.content.Intent;

public class Contact extends CordovaPlugin {
    public static final String ACTION_TEXT_US = "openSmsView";
    public static final String ACTION_CALL_US = "openCallView";
    public static final String ACTION_EMAIL_US = "openEmailView";
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            String phone = "4157356716";
            String email[] = {"info@discotech.me"};

            if (ACTION_TEXT_US.equals(action)) { 
                JSONObject arg_object = args.getJSONObject(0);
                String smsText = "";
                Uri smsUri = Uri.parse("sms:" +phone);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsUri);
                // sendIntent.setData(Uri.parse("sms:4157356716"));
                sendIntent.putExtra("sms_body", smsText);
                sendIntent.putExtra("exit_on_sent", true);

                this.cordova.getActivity().startActivity(sendIntent);
                callbackContext.success();
                return true;
            }
            else if(ACTION_CALL_US.equals(action)) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone));
                callIntent.putExtra("exit_on_sent", true);

                this.cordova.getActivity().startActivity(callIntent);
                callbackContext.success();
                return true;
            }
            else if(ACTION_EMAIL_US.equals(action)) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Discotech");

                emailIntent.setType("text/plain");
                this.cordova.getActivity().startActivity(emailIntent);
                callbackContext.success();
                return true;
            }

            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}