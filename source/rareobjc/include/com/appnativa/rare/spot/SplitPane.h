//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/SplitPane.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTSplitPane_H_
#define _RARESPOTSplitPane_H_

@class IOSDoubleArray;
@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTScrollPane;
@class RARESPOTSplitPane_CSplitOrientation;
@class RARESPOTWidget;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTSplitPane : RARESPOTViewer {
 @public
  RARESPOTSplitPane_CSplitOrientation *splitOrientation_;
  SPOTBoolean *oneTouchExpandable_;
  SPOTBoolean *autoAdjustProportions_;
  SPOTBoolean *showGripper_;
  SPOTInteger *dividerSize_;
  SPOTBoolean *continuousLayout_;
  SPOTBoolean *actAsFormViewer_;
  SPOTSet *splitProportions_;
  SPOTSet *regions_;
  SPOTPrintableString *transitionAnimator_;
  RARESPOTScrollPane *scrollPane_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (SPOTSet *)getSplitProportions;
- (SPOTSet *)getSplitProportionsReference;
- (void)setSplitProportionsWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (void)setSplitProportionsWithDoubleArray:(IOSDoubleArray *)props;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap;
- (void)copyAllFieldsTo:(RARESPOTSplitPane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTSplitPane, splitOrientation_, RARESPOTSplitPane_CSplitOrientation *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, oneTouchExpandable_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, autoAdjustProportions_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, showGripper_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, dividerSize_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, continuousLayout_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, actAsFormViewer_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, splitProportions_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, regions_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, transitionAnimator_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSplitPane, scrollPane_, RARESPOTScrollPane *)

typedef RARESPOTSplitPane ComAppnativaRareSpotSplitPane;

#define RARESPOTSplitPane_CSplitOrientation_left_to_right 1
#define RARESPOTSplitPane_CSplitOrientation_top_to_bottom 0

@interface RARESPOTSplitPane_CSplitOrientation : SPOTEnumerated {
}

+ (int)top_to_bottom;
+ (int)left_to_right;
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
@end

#endif // _RARESPOTSplitPane_H_
