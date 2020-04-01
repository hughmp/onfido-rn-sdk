import { NativeModules } from 'react-native';
import { OnfidoType } from './types';

export default NativeModules.Onfido as OnfidoType;
export * from './types';
