#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(Onfido, NSObject)

RCT_EXTERN_METHOD(startSDK: (NSString *)token
                  options:(NSDictionary *)options
                  resolver:(RCTResponseSenderBlock *)resolve
                  rejecter:(RCTResponseSenderBlock *)reject)

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

@end