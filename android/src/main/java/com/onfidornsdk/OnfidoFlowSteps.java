package com.onfidornsdk;

import java.util.List;
import java.util.ArrayList;

import com.facebook.react.bridge.ReadableMap;

import com.onfido.android.sdk.capture.ui.options.FlowStep;
import com.onfido.android.sdk.capture.DocumentType;
import com.onfido.android.sdk.capture.utils.CountryCode;
import com.onfido.android.sdk.capture.ui.options.CaptureScreenStep;
import com.onfido.android.sdk.capture.ui.camera.face.FaceCaptureStep;
import com.onfido.android.sdk.capture.ui.camera.face.FaceCaptureVariant;

class OnfidoFlowSteps {

    private ReadableMap options;
    List<FlowStep> flowStepList;

    public OnfidoFlowSteps() {}

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
        if (options.hasKey("withDocumentStep") && options.getBoolean("withDocumentStep") == true) {
            if (options.hasKey("documentType") && options.hasKey("documentCountryCode")) {
                setDocumentStepWithOptions();
            } else {
                flowStepList.add(FlowStep.CAPTURE_DOCUMENT);
            }
        }
    }

    private void setDocumentStepWithOptions() {
        // configure document type and country code
        DocumentType documentType = DocumentType.valueOf(options.getString("documentType"));
        CountryCode countryCode = getCountryCodeFromString(options.getString("documentCountryCode"));

        flowStepList.add(new CaptureScreenStep(documentType, countryCode));
    }

    private CountryCode getCountryCodeFromString(String countryCodeString) {
        CountryCode countryCode = null;

        for (CountryCode cc : CountryCode.values()) {
            if (cc.getAlpha3().equals(countryCodeString)) {
                countryCode = cc;
            }
        }

        if (countryCode == null) {
            return CountryCode.GB;
        }

        return countryCode;
    }

    private void setFaceStep() {
        // configure face capture step
        if (options.hasKey("withFaceStep") && options.getBoolean("withFaceStep") == true) {
            if (options.hasKey("faceVariant")) {
                final String faceVariant = options.getString("faceVariant");
                if (faceVariant.equals("PHOTO")) {
                    flowStepList.add(new FaceCaptureStep(FaceCaptureVariant.PHOTO));
                }
                if (faceVariant.equals("VIDEO")) {
                    flowStepList.add(new FaceCaptureStep(FaceCaptureVariant.VIDEO));
                }
            } else {
                flowStepList.add(new FaceCaptureStep(FaceCaptureVariant.PHOTO));
            }
        }
    }


    public FlowStep[] getFromOptions(ReadableMap options) {
        this.options = options;
        this.flowStepList = new ArrayList<>();

        setWelcomeStep();
        setDocumentStep();
        setFaceStep();

        flowStepList.add(FlowStep.FINAL);

        return flowStepList.toArray(new FlowStep[0]);
    }

}