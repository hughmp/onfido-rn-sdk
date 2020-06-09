import * as React from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import Onfido, {
  OnfidoFaceVariant,
  OnfidoDocument,
  OnfidoOptions,
  OnfidoDocumentCountryCode,
} from 'onfido-rn-sdk';
import { NativeModules } from 'react-native';

export default function App() {
  function start() {
    const token = '.'; // get your SDK token
    const options: OnfidoOptions = {
      // iosTheme: {
      //   primaryColor: '#000000',
      //   primaryTitleColor: '#00c56b',
      //   primaryBackgroundPressedColor: '#c50000',
      //   secondaryBackgroundPressedColor: '#ffffff',
      //   supportDarkMode: true,
      // },
      withWelcomeStep: true,
      withDocumentStep: true,
      withFaceStep: true,
      faceVariant: OnfidoFaceVariant.VIDEO,
      documentType: OnfidoDocument.PASSPORT,
      documentCountryCode: OnfidoDocumentCountryCode.GBR,
    };

    Onfido.startSDK(token, options, console.log, console.log);
  }

  return (
    <View style={styles.container}>
      <Text>Onfido</Text>
      <Button title="start" onPress={start} />
      <Button
        title="test"
        onPress={() => {
          // Onfido.sampleMethod('test', 1, console.log);
          console.log(NativeModules.Onfido.testMethod('test', 1, console.log));
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
