//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UINotifier.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREUINotifier_H_
#define _RAREUINotifier_H_

@class RAREExpansionEvent;
@class RAREPushButtonWidget;
@class RAREUICompoundBorder;
@class RAREUINotifier_LengthEnum;
@class RAREUINotifier_LocationEnum;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformIcon;
@protocol RAREiPopup;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "java/lang/Enum.h"
#include "java/lang/Runnable.h"

@interface RAREUINotifier : NSObject {
 @public
  id<RAREiPopup> popupWindow_;
  RAREPushButtonWidget *pushButton_;
}

+ (RAREUICompoundBorder *)defaultBorder;
+ (void)setDefaultBorder:(RAREUICompoundBorder *)defaultBorder;
- (id)initWithNSString:(NSString *)text
               withInt:(int)length
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
  withJavaLangRunnable:(id<JavaLangRunnable>)runner;
- (id<RAREiPopup>)getPopup;
- (RAREPushButtonWidget *)getPushButton;
- (void)showWithFloat:(float)x
            withFloat:(float)y;
- (void)showWithRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location;
- (void)dispose;
+ (void)playSound;
+ (void)showMessageWithNSString:(NSString *)text;
+ (void)showMessageWithNSString:(NSString *)text
                        withInt:(int)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location;
+ (void)showMessageWithNSString:(NSString *)text
                        withInt:(int)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location
          withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
           withJavaLangRunnable:(id<JavaLangRunnable>)runner;
+ (void)showMessageWithNSString:(NSString *)text
  withRAREUINotifier_LengthEnum:(RAREUINotifier_LengthEnum *)length
withRAREUINotifier_LocationEnum:(RAREUINotifier_LocationEnum *)location;
+ (id<RAREiPlatformBorder>)getDefaultBorder;
- (void)copyAllFieldsTo:(RAREUINotifier *)other;
@end

J2OBJC_FIELD_SETTER(RAREUINotifier, popupWindow_, id<RAREiPopup>)
J2OBJC_FIELD_SETTER(RAREUINotifier, pushButton_, RAREPushButtonWidget *)

typedef RAREUINotifier ComAppnativaRareUiUINotifier;

typedef enum {
  RAREUINotifier_Length_SHORT = 0,
  RAREUINotifier_Length_MEDIUM = 1,
  RAREUINotifier_Length_LONG = 2,
} RAREUINotifier_Length;

@interface RAREUINotifier_LengthEnum : JavaLangEnum < NSCopying > {
}
+ (RAREUINotifier_LengthEnum *)SHORT;
+ (RAREUINotifier_LengthEnum *)MEDIUM;
+ (RAREUINotifier_LengthEnum *)LONG;
+ (IOSObjectArray *)values;
+ (RAREUINotifier_LengthEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RAREUINotifier_Location_TOP = 0,
  RAREUINotifier_Location_CENTER = 1,
  RAREUINotifier_Location_BOTTOM = 2,
} RAREUINotifier_Location;

@interface RAREUINotifier_LocationEnum : JavaLangEnum < NSCopying > {
}
+ (RAREUINotifier_LocationEnum *)TOP;
+ (RAREUINotifier_LocationEnum *)CENTER;
+ (RAREUINotifier_LocationEnum *)BOTTOM;
+ (IOSObjectArray *)values;
+ (RAREUINotifier_LocationEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RAREUINotifier_$1 : NSObject < RAREiPopupMenuListener > {
 @public
  RAREUINotifier *this$0_;
  id<JavaLangRunnable> val$runner_;
}

- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)arg0;
- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)arg0;
- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)arg0;
- (id)initWithRAREUINotifier:(RAREUINotifier *)outer$
        withJavaLangRunnable:(id<JavaLangRunnable>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREUINotifier_$1, this$0_, RAREUINotifier *)
J2OBJC_FIELD_SETTER(RAREUINotifier_$1, val$runner_, id<JavaLangRunnable>)

@interface RAREUINotifier_$1_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREUINotifier_$1 *this$0_;
}

- (void)run;
- (id)initWithRAREUINotifier_$1:(RAREUINotifier_$1 *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREUINotifier_$1_$1, this$0_, RAREUINotifier_$1 *)

#endif // _RAREUINotifier_H_