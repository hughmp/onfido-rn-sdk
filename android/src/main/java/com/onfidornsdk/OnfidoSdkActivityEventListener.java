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

    public OnfidoSdkActivityEventListener(final Onfido client){
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
                resolve.invoke();
            }

            @Override
            public void userExited(ExitCode exitCode) {
                reject.invoke(exitCode.toString());
            }

            @Override
            public void onError(OnfidoException e) {
                reject.invoke(e.getMessage());
            }
        });
    }

}