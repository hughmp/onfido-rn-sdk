/*
 * colors can be 6 or 8 character hex string
 * with a preceding `#` e.g. `#ffffff`
 */
type OnfidoIosTheme = {
  primaryColor: string;
  primaryTitleColor: string;
  primaryBackgroundPressedColor: string;
  secondaryBackgroundPressedColor: string;
  fontRegular?: string;
  fontBold?: string;
  supportDarkMode: boolean;
};

export enum OnfidoDocument {
  PASSPORT = 'PASSPORT',
  DRIVING_LICENCE = 'DRIVING_LICENCE',
  NATIONAL_IDENTITY_CARD = 'NATIONAL_IDENTITY_CARD',
  RESIDENCE_PERMIT = 'RESIDENCE_PERMIT',
  VISA = 'VISA',
  WORK_PERMIT = 'WORK_PERMIT',
}

export enum OnfidoDocumentCountryCode {
  // TODO: add all countries from i18n-iso-countries
  GBR = 'GBR',
  USA = 'USA',
}

export enum OnfidoFaceVariant {
  PHOTO = 'PHOTO',
  VIDEO = 'VIDEO',
}

export enum OnfidoError {
  CONFIG_MISSING_STEPS = 'CONFIG_MISSING_STEPS',
  CONFIG_MISSING_TOKEN = 'CONFIG_MISSING_TOKEN',
  CONFIG_ERROR_UNHANDLED = 'CONFIG_ERROR_UNHANDLED',
  RESPONSE_ERROR = 'RESPONSE_ERROR',
  RESPONSE_CANCEL = 'RESPONSE_CANCEL',
  RESPONSE_UNHANDLED = 'RESPONSE_UNHANDLED',
  FLOW_CAMERA_PERMISSION = 'FLOW_CAMERA_PERMISSION',
  FLOW_FAILED_TO_WRITE_TO_DISK = 'FLOW_FAILED_TO_WRITE_TO_DISK',
  FLOW_MICROPHONE_PERMISSION = 'FLOW_MICROPHONE_PERMISSION',
  FLOW_ERROR_UNHANDLED = 'FLOW_ERROR_UNHANDLED',
}

export enum OnfidoSuccess {
  RESPONSE_SUCCESS = 'RESPONSE_SUCCESS',
}

export type OnfidoOptions = {
  iosTheme?: OnfidoIosTheme;
  withWelcomeStep?: boolean;
  withFaceStep?: boolean;
  withDocumentStep?: boolean;
  documentType?: OnfidoDocument;
  documentCountryCode?: OnfidoDocumentCountryCode;
  faceVariant?: OnfidoFaceVariant;
};

export type OnfidoType = {
  startSDK(
    token: string,
    options: OnfidoOptions,
    resolve: (msg: OnfidoSuccess) => void,
    reject: (msg: OnfidoError) => void
  ): void;
};
