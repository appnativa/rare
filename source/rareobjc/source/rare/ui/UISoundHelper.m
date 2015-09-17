//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UISoundHelper.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/UISound.h"
#include "com/appnativa/rare/ui/UISoundHelper.h"
#include "java/lang/Exception.h"

@implementation RAREUISoundHelper

static RAREUISound * RAREUISoundHelper_success_;
static RAREUISound * RAREUISoundHelper_error_;

+ (RAREUISound *)success {
  return RAREUISoundHelper_success_;
}

+ (void)setSuccess:(RAREUISound *)success {
  RAREUISoundHelper_success_ = success;
}

+ (RAREUISound *)error {
  return RAREUISoundHelper_error_;
}

+ (void)setError:(RAREUISound *)error {
  RAREUISoundHelper_error_ = error;
}

- (id)init {
  return [super init];
}

+ (void)beep {
  [RAREPlatformHelper beep];
}

+ (void)errorSound {
  if (RAREUISoundHelper_error_ == nil) {
    NSString *s = [RAREPlatform getResourceAsStringWithNSString:@"Rare.sound.error"];
    if (s == nil) {
      s = @"rare_raw_error_beep";
    }
    @try {
      RAREUISoundHelper_error_ = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getSoundWithNSString:s];
    }
    @catch (JavaLangException *e) {
      [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
    }
  }
  if (RAREUISoundHelper_error_ != nil) {
    [RAREUISoundHelper_error_ play];
  }
}

+ (void)successSound {
  if (RAREUISoundHelper_success_ == nil) {
    NSString *s = [RAREPlatform getResourceAsStringWithNSString:@"Rare.sound.success"];
    if (s != nil) {
      @try {
        RAREUISoundHelper_success_ = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getSoundWithNSString:s];
      }
      @catch (JavaLangException *e) {
        [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
      }
    }
  }
  if (RAREUISoundHelper_success_ != nil) {
    [((RAREUISound *) nil_chk(RAREUISoundHelper_error_)) play];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "success_", NULL, 0x8, "LRAREUISound" },
    { "error_", NULL, 0x8, "LRAREUISound" },
  };
  static J2ObjcClassInfo _RAREUISoundHelper = { "UISoundHelper", "com.appnativa.rare.ui", NULL, 0x1, 0, NULL, 2, fields, 0, NULL};
  return &_RAREUISoundHelper;
}

@end
