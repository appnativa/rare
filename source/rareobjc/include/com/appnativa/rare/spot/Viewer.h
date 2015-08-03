//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Viewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTViewer_H_
#define _RARESPOTViewer_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTMenuItem;
@class RARESPOTViewer_CDisableBehavior;
@class SPOTBoolean;
@class SPOTPrintableString;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTViewer : RARESPOTWidget {
 @public
  SPOTPrintableString *contextURL_;
  SPOTPrintableString *templateURL_;
  SPOTPrintableString *collapsedTitle_;
  SPOTPrintableString *icon_;
  SPOTBoolean *windowOnly_;
  SPOTPrintableString *scriptURL_;
  SPOTBoolean *local_;
  RARESPOTViewer_CDisableBehavior *disableBehavior_;
  id linkedData_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTMenuItem *)addPopupMenuWithRARESPOTMenuItem:(RARESPOTMenuItem *)item;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)code;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)url
                                       withInt:(int)target;
- (RARESPOTMenuItem *)addPopupMenuWithNSString:(NSString *)value
                                  withNSString:(NSString *)url
                                  withNSString:(NSString *)target;
- (void)setScriptURLWithNSString:(NSString *)url;
- (void)setContextURLWithNSString:(NSString *)url;
- (void)copyAllFieldsTo:(RARESPOTViewer *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTViewer, contextURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, templateURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, collapsedTitle_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, icon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, windowOnly_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, scriptURL_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, local_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, disableBehavior_, RARESPOTViewer_CDisableBehavior *)
J2OBJC_FIELD_SETTER(RARESPOTViewer, linkedData_, id)

typedef RARESPOTViewer ComAppnativaRareSpotViewer;

#define RARESPOTViewer_CDisableBehavior_disable_both 2
#define RARESPOTViewer_CDisableBehavior_disable_container 0
#define RARESPOTViewer_CDisableBehavior_disable_widgets 1

@interface RARESPOTViewer_CDisableBehavior : SPOTEnumerated {
}

+ (int)disable_container;
+ (int)disable_widgets;
+ (int)disable_both;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
- (void)spot_setAttributes;
@end

#endif // _RARESPOTViewer_H_
