package com.onfidornsdk;

import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Callback;

import com.onfido.android.sdk.capture.Onfido;
import com.onfido.android.sdk.capture.ExitCode;
import com.onfido.android.sdk.capture.OnfidoConfig;
import com.onfido.android.sdk.capture.OnfidoFactory;
import com.onfido.android.sdk.capture.errors.OnfidoException;
import com.onfido.android.sdk.capture.upload.Captures;
import com.onfido.android.sdk.capture.ui.options.FlowStep;

public class OnfidoRnSdkModule extends ReactContextBaseJavaModule {

    private static final String CONFIG_ERROR_UNHANDLED = "CONFIG_ERROR_UNHANDLED";

    final Onfido client;
    private final ReactApplicationContext reactContext;
    private final OnfidoSdkActivityEventListener activityEventListener;

    public OnfidoRnSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.client = OnfidoFactory.create(reactContext).getClient();
        this.activityEventListener = new OnfidoSdkActivityEventListener(client);
        reactContext.addActivityEventListener(activityEventListener);
    }

    @Override
    public String getName() {
        return "Onfido";
    }

    @ReactMethod
    public void startSDK(String token, ReadableMap options, Callback resolve, Callback reject) {

        try {
            activityEventListener.setCallbacks(resolve, reject);

            OnfidoFlowSteps onfidoFlowSteps = new OnfidoFlowSteps();
            Activity currentActivity = super.getCurrentActivity();

            if (currentActivity == null) {
                throw new Exception("current activity is null");
            }

            FlowStep[] flow = onfidoFlowSteps.getFromOptions(options);

            final OnfidoConfig onfidoConfig = OnfidoConfig.builder(currentActivity)
                    .withSDKToken(token)
                    .withCustomFlow(flow)
                    .build();

            client.startActivityForResult(currentActivity, 1, onfidoConfig);
        }
        catch (final Exception e) {
            reject.invoke(CONFIG_ERROR_UNHANDLED, String.valueOf(e));
            return;
        }

    }
}
