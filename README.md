# onfido-rn-sdk

A `react-native` wrapper for the `onfido-ios-sdk` and `onfido-android-sdk` identity verification SDKs.

## Installation

```sh
npm install onfido-rn-sdk
```

## Usage

### Quick Start

```ts
import Onfido, {
  OnfidoFaceVariant,
  OnfidoDocument,
  OnfidoOptions,
  OnfidoDocumentCountryCode,
} from 'onfido-rn-sdk';

const token = 'YOUR_TOKEN_HERE'; // get your SDK token from Onfido
const options: OnfidoOptions = {
  iosTheme: {
    primaryColor: '#000000',
    primaryTitleColor: '#00c56b',
    primaryBackgroundPressedColor: '#c50000',
    secondaryBackgroundPressedColor: '#ffffff',
    supportDarkMode: true,
  },
  withWelcomeStep: true,
  withDocumentStep: true,
  withFaceStep: true,
  faceVariant: OnfidoFaceVariant.PHOTO,
  documentType: OnfidoDocument.DRIVING_LICENCE,
  documentCountryCode: OnfidoDocumentCountryCode.GBR,
};

const successHandler = message => console.log(message);
const errorHandler = message => console.log(message);

Onfido.startSDK(token, options, successHandler, errorHandler);
```

### Pre-Requisites

**ios**

1. Your project must have some swift code and a bridging header in order to correctly compile the SDK. If it does not, open `Xcode` and add a blank `.swift` file and let it generate a bridging header for you. (TODO: [see here]())

2. The Onfido SDK makes use of the device Camera. You will be required to have the `NSCameraUsageDescription` and `NSMicrophoneUsageDescription` keys in your application's `Info.plist` file:

```xml
<key>NSCameraUsageDescription</key>
<string>Required for document and facial capture</string>
<key>NSMicrophoneUsageDescription</key>
<string>Required for video capture</string>
```

**Note**: Both keys will be required for app submission.

3. Ensure your `Podfile` references as a minimum iOS platform version of `10.0`.

```
platform :ios, '10.0'
```

**Android**

1. Until the Onfido package is available in `jcenter` you will need to modify the `repositories` directive in your application's `build.gradle`:

```
repositories {
    maven {
        url "https://dl.bintray.com/onfido/maven"
    }
}
```

2. Enable multidex by modifying the `defaultConfig` in your applications `android/app/build.gradle`:

```
defaultConfig {
    multiDexEnabled true
}
```

## License

MIT
