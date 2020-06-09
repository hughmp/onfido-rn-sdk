package com.onfidornsdk;

import com.facebook.react.bridge.ReadableMap;
import com.onfido.android.sdk.capture.ui.options.FlowStep;
import com.onfido.android.sdk.capture.DocumentType;
import com.onfido.android.sdk.capture.utils.CountryCode;
import com.onfido.android.sdk.capture.ui.options.CaptureScreenStep;

class OnfidoFlowSteps {

    private final ReadableMap options;
    final List<FlowStep> flowStepList;


    public OnfidoFlowSteps(ReadableMap options) {
        this.flowStepList = new ArrayList<>();
        this.options = options;
    }

    private void setWelcomeStep() {
        // configure welcome step
        if (options.hasKey("withWelcomeStep")) {
            if (options.getBoolean("withWelcomeStep") == true) {
                flowStepList.add(FlowStep.WELCOME);
            }
        }
    }

    private void setDocumentStep() {
        // configure document step
        if (options.hasKey("withDocumentStep")) {
            if (options.getBoolean("withDocumentStep") == true) {
                flowStepList.add(FlowStep.CAPTURE_DOCUMENT);
                setDocumentTypeAndCountryCode();
            }
        }
    }

    private void setDocumentTypeAndCountryCode() {
        // configure document type and country code
        if (options.hasKey("documentType") && options.hasKey("documentCountryCode")) {
            try {
                DocumentType documentType = DocumentType.valueOf(options.getString("documentType"));
                CountryCode countryCode = getCountryCodeFromString(options.getString("documentCountryCode"));

                flowStepList.add(new CaptureScreenStep(documentType, countryCode));
            }
            catch {
                return;
            }
        }
    }

    public static CountryCode getCountryCodeFromString(String countryCodeString) {
        CountryCode countryCode = null;

        for (CountryCode cc : CountryCode.values()) {
            if (cc.getAlpha3().equals(countryCodeString)) {
                countryCode = cc;
            }
        }

        if (countryCode == null) {
            throw new Exception("Unexpected countryCodeString.");
        }

        return countryCode;
    }

    public static FlowStep[] get() {
        setWelcomeStep();
        setDocumentStep();

        return flowStepList;
    }

}