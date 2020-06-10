package com.onfidornsdk;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;

import com.onfido.android.sdk.capture.Onfido;
import com.onfido.android.sdk.capture.upload.Captures;
import com.onfido.android.sdk.capture.errors.OnfidoException;
import com.onfido.android.sdk.capture.ExitCode;

class OnfidoSdkActivityEventListener extends BaseActivityEventListener {

    final Onfido client;
    private Callback resolve = null;
    private Callback reject = null;

    private static final String RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
    private static final String RESPONSE_CANCEL = "RESPONSE_CANCEL";
    private static final String FLOW_ERROR_UNHANDLED = "FLOW_ERROR_UNHANDLED";

    public OnfidoSdkActivityEventListener(final Onfido client) {
        this.client = client;
    }

    public void setCallbacks(Callback resolve, Callback reject) {
        this.resolve = resolve;
        this.reject = reject;
    }

    @Override
    public void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        client.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
            @Override
            public void userCompleted(Captures captures) {
                resolve.invoke(RESPONSE_SUCCESS);
            }

            @Override
            public void userExited(ExitCode exitCode) {
                reject.invoke(RESPONSE_CANCEL, exitCode.toString());
            }

            @Override
            public void onError(OnfidoException e) {
                reject.invoke(FLOW_ERROR_UNHANDLED, e.getMessage());
            }
        });
    }

}