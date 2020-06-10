# onfido-rn-sdk

A `react-native` wrapper for the `onfido-ios-sdk` and `onfido-android-sdk` identity verification SDKs.

## Installation

```sh
npm install onfido-rn-sdk
```

## Usage

### Quick Start

```js
import Onfido from 'onfido-rn-sdk';

const token = 'YOUR_SDK_TOKEN_HERE';
const options = {};
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
