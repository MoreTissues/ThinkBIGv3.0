package com.thinkbig.thinkbig.AppoinmentHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Show Appointment List";
    }
}
