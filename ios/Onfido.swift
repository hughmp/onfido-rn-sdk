import Foundation
import Onfido

@objc(Onfido)
class Onfido: NSObject {
  @objc func startSDK(_ token: String,
                      options: NSDictionary,
                      resolver resolve: @escaping RCTResponseSenderBlock,
                      rejecter reject: @escaping RCTResponseSenderBlock) -> Void {
    DispatchQueue.main.async {
        self.run(token: token, options: options, resolver: resolve, rejecter: reject)
    }
  }
  
  private func run(token: String,
                   options: NSDictionary,
                   resolver resolve: @escaping RCTResponseSenderBlock,
                   rejecter reject: @escaping RCTResponseSenderBlock) {
    
    // initialise config builder
    var configBuilder = OnfidoConfig.builder()
      .withSDKToken(token)
    
    // handle theme
    if let theme = options["iosTheme"] as? NSDictionary, theme.count > 0 {
        let appearance = Appearance(
            primaryColor: hexToUiColor(hex: theme["primaryColor"]),
            primaryTitleColor: hexToUiColor(hex: theme["primaryTitleColor"]),
            primaryBackgroundPressedColor: hexToUiColor(hex: theme["primaryBackgroundPressedColor"]),
            secondaryBackgroundPressedColor: hexToUiColor(hex: theme["secondaryBackgroundPressedColor"]),
            fontRegular: theme["fontRegular"] as? String,
            fontBold: theme["fontBold"] as? String,
            supportDarkMode: theme["supportDarkMode"] as? Bool ?? true
        )
        configBuilder = configBuilder.withAppearance(appearance)
    }
    
    // handle welcome step
    if let withWelcomeStep = options["withWelcomeStep"] as? Bool, withWelcomeStep == true {
        configBuilder = configBuilder.withWelcomeStep()
    }
    
    // handle document step
    if let withDocumentStep = options["withDocumentStep"] as? Bool, withDocumentStep == true {
        let countryCode: String
        let defaultCountryCode: String = "USA"
        
        if let cc = options["countryCode"] as? String {
            countryCode = cc
        } else {
            countryCode = defaultCountryCode
        }
        
        if let documentType = options["documentType"] as? String {
            switch documentType {
            case "PASSPORT":
                configBuilder = configBuilder.withDocumentStep(ofType: .passport, andCountryCode: countryCode)
            case "DRIVING_LICENCE":
                configBuilder = configBuilder.withDocumentStep(ofType: .drivingLicence, andCountryCode: countryCode)
            case "NATIONAL_IDENTITY_CARD":
                configBuilder = configBuilder.withDocumentStep(ofType: .nationalIdentityCard, andCountryCode: countryCode)
            case "RESIDENCE_PERMIT":
                configBuilder = configBuilder.withDocumentStep(ofType: .residencePermit, andCountryCode: countryCode)
            case "VISA":
                configBuilder = configBuilder.withDocumentStep(ofType: .visa, andCountryCode: countryCode)
            case "WORK_PERMIT":
                configBuilder = configBuilder.withDocumentStep(ofType: .workPermit, andCountryCode: countryCode)
            default:
                configBuilder = configBuilder.withDocumentStep()
            }
        } else {
            configBuilder = configBuilder.withDocumentStep()
        }
    }
    
    // handle face step
    if let withFaceStep = options["withFaceStep"] as? Bool, withFaceStep == true {
        if let faceVariant = options["faceVariant"] as? String {
            switch faceVariant {
            case "PHOTO":
                configBuilder = configBuilder.withFaceStep(ofVariant: .photo(withConfiguration: nil))
            case "VIDEO":
                configBuilder = configBuilder.withFaceStep(ofVariant: .video(withConfiguration: nil))
            default:
                configBuilder = configBuilder.withFaceStep(ofVariant: .photo(withConfiguration: nil))
            }
        } else {
            configBuilder = configBuilder.withFaceStep(ofVariant: .photo(withConfiguration: nil))
        }
    }

    // build config
    let config: OnfidoConfig
    do {
        config = try configBuilder.build()
    } catch OnfidoConfigError.missingSteps {
        reject(["CONFIG_MISSING_STEPS"])
        return
    } catch OnfidoConfigError.missingToken {
        reject(["CONFIG_MISSING_TOKEN"])
        return
    } catch let error {
        reject(["CONFIG_ERROR_UNHANDLED", "\(error)"])
        return
    }
    
    // build flow
    let onfidoFlow = OnfidoFlow(withConfiguration: config)
      .with(responseHandler: { [weak self] response in
        switch response {
        case let .error(error):
          reject(["RESPONSE_ERROR", "\(error)"])
          self?.dismiss()
        case .success(_):
          resolve(["RESPONSE_SUCCESS"])
          self?.dismiss()
        case .cancel:
          reject(["RESPONSE_CANCEL"])
          self?.dismiss()
        default:
            reject(["RESPONSE_UNHANDLED", "\(response)"])
            self?.dismiss()
        }
      })
    
    // execute flow
    do {
      let onfidoRun = try onfidoFlow.run()
      onfidoRun.modalPresentationStyle = .fullScreen
      UIApplication.shared.windows.first?.rootViewController?.present(onfidoRun, animated: true)
    } catch OnfidoFlowError.cameraPermission {
        reject(["FLOW_CAMERA_PERMISSION"])
        return
    } catch OnfidoFlowError.failedToWriteToDisk {
        reject(["FLOW_FAILED_TO_WRITE_TO_DISK"])
        return
    } catch OnfidoFlowError.microphonePermission {
        reject(["FLOW_MICROPHONE_PERMISSION"])
        return
    } catch let error {
        reject(["FLOW_ERROR_UNHANDLED", "\(error)"])
        return
    }
  }
    
  private func dismiss() {
    UIApplication.shared.windows.first?.rootViewController?.presentedViewController?.dismiss(animated: true)
  }
}
