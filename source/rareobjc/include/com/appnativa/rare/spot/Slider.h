//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Slider.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTSlider_H_
#define _RARESPOTSlider_H_

@class RARESPOTGridCell;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTReal;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Widget.h"

@interface RARESPOTSlider : RARESPOTWidget {
 @public
  SPOTReal *value_;
  SPOTReal *minValue_;
  SPOTReal *maxValue_;
  SPOTBoolean *horizontal_;
  SPOTBoolean *snapToTicks_;
  SPOTReal *minorTickSpacing_;
  SPOTReal *majorTickSpacing_;
  SPOTBoolean *showLabels_;
  SPOTBoolean *showTicks_;
  SPOTBoolean *showTrack_;
  SPOTPrintableString *leftLabel_;
  SPOTPrintableString *leftIcon_;
  SPOTPrintableString *rightLabel_;
  SPOTPrintableString *rightIcon_;
  SPOTPrintableString *thumbIcon_;
  SPOTBoolean *paintInnerTicks_;
  RARESPOTGridCell *trackPainter_;
  SPOTInteger *sliderOffset_;
  SPOTSet *tickLabels_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (RARESPOTGridCell *)getTrackPainter;
- (RARESPOTGridCell *)getTrackPainterReference;
- (void)setTrackPainterWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getTickLabels;
- (SPOTSet *)getTickLabelsReference;
- (void)setTickLabelsWithISPOTElement:(id<iSPOTElement>)reference;
- (void)copyAllFieldsTo:(RARESPOTSlider *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTSlider, value_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, minValue_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, maxValue_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, horizontal_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, snapToTicks_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, minorTickSpacing_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, majorTickSpacing_, SPOTReal *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, showLabels_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, showTicks_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, showTrack_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, leftLabel_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, leftIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, rightLabel_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, rightIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, thumbIcon_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, paintInnerTicks_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, trackPainter_, RARESPOTGridCell *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, sliderOffset_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTSlider, tickLabels_, SPOTSet *)

typedef RARESPOTSlider ComAppnativaRareSpotSlider;

#endif // _RARESPOTSlider_H_
