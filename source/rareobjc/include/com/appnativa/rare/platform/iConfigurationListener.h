//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/platform/iConfigurationListener.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiConfigurationListener_H_
#define _RAREiConfigurationListener_H_

#import "JreEmulation.h"

@protocol RAREiConfigurationListener < NSObject, JavaObject >
- (void)onConfigurationChangedWithId:(id)changes;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
@end

@interface RAREiConfigurationListener : NSObject {
}
+ (NSString *)CONFIGURATION_CHANGED_PROPERTY;
+ (NSString *)CONFIGURATION_WILLCHANGE_PROPERTY;
@end

#define ComAppnativaRarePlatformIConfigurationListener RAREiConfigurationListener

#endif // _RAREiConfigurationListener_H_
