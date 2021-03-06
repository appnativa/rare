//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Slider.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/Slider.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTReal.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"

@implementation RARESPOTSlider

- (id)init {
  return [self initRARESPOTSliderWithBoolean:YES];
}

- (id)initRARESPOTSliderWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    value_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withBoolean:NO];
    minValue_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withBoolean:NO];
    maxValue_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"100" withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    snapToTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    minorTickSpacing_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:nil withNSString:@"0" withBoolean:NO];
    majorTickSpacing_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:nil withNSString:@"10" withBoolean:NO];
    showLabels_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTrack_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    leftLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    leftIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    rightLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    rightIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    thumbIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    paintInnerTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    trackPainter_ = nil;
    sliderOffset_ = [[SPOTInteger alloc] initWithNSString:nil withLong:0 withBoolean:NO];
    tickLabels_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTSliderWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    value_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withBoolean:NO];
    minValue_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withBoolean:NO];
    maxValue_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"100" withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    snapToTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    minorTickSpacing_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:nil withNSString:@"0" withBoolean:NO];
    majorTickSpacing_ = [[SPOTReal alloc] initWithNSString:nil withNSString:@"0" withNSString:nil withNSString:@"10" withBoolean:NO];
    showLabels_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showTrack_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    leftLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    leftIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    rightLabel_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:YES];
    rightIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    thumbIcon_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    paintInnerTicks_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    trackPainter_ = nil;
    sliderOffset_ = [[SPOTInteger alloc] initWithNSString:nil withLong:0 withBoolean:NO];
    tickLabels_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 19;
  self->attributeSizeHint_ += 1;
  [super spot_setElements];
  [self spot_defineAttributeWithNSString:@"onChange" withNSString:nil];
  [self spot_addElementWithNSString:@"value" withISPOTElement:value_];
  [self spot_addElementWithNSString:@"minValue" withISPOTElement:minValue_];
  [self spot_addElementWithNSString:@"maxValue" withISPOTElement:maxValue_];
  [self spot_addElementWithNSString:@"horizontal" withISPOTElement:horizontal_];
  [self spot_addElementWithNSString:@"snapToTicks" withISPOTElement:snapToTicks_];
  [self spot_addElementWithNSString:@"minorTickSpacing" withISPOTElement:minorTickSpacing_];
  [self spot_addElementWithNSString:@"majorTickSpacing" withISPOTElement:majorTickSpacing_];
  [self spot_addElementWithNSString:@"showLabels" withISPOTElement:showLabels_];
  [self spot_addElementWithNSString:@"showTicks" withISPOTElement:showTicks_];
  [self spot_addElementWithNSString:@"showTrack" withISPOTElement:showTrack_];
  [self spot_addElementWithNSString:@"leftLabel" withISPOTElement:leftLabel_];
  [self spot_addElementWithNSString:@"leftIcon" withISPOTElement:leftIcon_];
  [((SPOTPrintableString *) nil_chk(leftIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [leftIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"rightLabel" withISPOTElement:rightLabel_];
  [self spot_addElementWithNSString:@"rightIcon" withISPOTElement:rightIcon_];
  [((SPOTPrintableString *) nil_chk(rightIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [rightIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"thumbIcon" withISPOTElement:thumbIcon_];
  [((SPOTPrintableString *) nil_chk(thumbIcon_)) spot_defineAttributeWithNSString:@"alt" withNSString:nil];
  [thumbIcon_ spot_defineAttributeWithNSString:@"slice" withNSString:nil];
  [thumbIcon_ spot_defineAttributeWithNSString:@"size" withNSString:nil];
  [thumbIcon_ spot_defineAttributeWithNSString:@"scaling" withNSString:nil];
  [thumbIcon_ spot_defineAttributeWithNSString:@"density" withNSString:nil];
  [self spot_addElementWithNSString:@"paintInnerTicks" withISPOTElement:paintInnerTicks_];
  [((SPOTBoolean *) nil_chk(paintInnerTicks_)) spot_defineAttributeWithNSString:@"tickType" withNSString:@"minor"];
  [paintInnerTicks_ spot_defineAttributeWithNSString:@"offset" withNSString:nil];
  [paintInnerTicks_ spot_defineAttributeWithNSString:@"color" withNSString:nil];
  [paintInnerTicks_ spot_defineAttributeWithNSString:@"tickStyle" withNSString:@"line"];
  [paintInnerTicks_ spot_defineAttributeWithNSString:@"paintArea" withNSString:@"value"];
  [self spot_addElementWithNSString:@"trackPainter" withISPOTElement:trackPainter_];
  [self spot_addElementWithNSString:@"sliderOffset" withISPOTElement:sliderOffset_];
  [self spot_addElementWithNSString:@"tickLabels" withISPOTElement:tickLabels_];
}

- (RARESPOTGridCell *)getTrackPainter {
  return trackPainter_;
}

- (RARESPOTGridCell *)getTrackPainterReference {
  if (trackPainter_ == nil) {
    trackPainter_ = [[RARESPOTGridCell alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"trackPainter" withISPOTElement:trackPainter_];
  }
  return trackPainter_;
}

- (void)setTrackPainterWithISPOTElement:(id<iSPOTElement>)reference {
  trackPainter_ = (RARESPOTGridCell *) check_class_cast(reference, [RARESPOTGridCell class]);
  (void) [self spot_setReferenceWithNSString:@"trackPainter" withISPOTElement:reference];
}

- (SPOTSet *)getTickLabels {
  return tickLabels_;
}

- (SPOTSet *)getTickLabelsReference {
  if (tickLabels_ == nil) {
    tickLabels_ = [[SPOTSet alloc] initWithNSString:@"label" withISPOTElement:[[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:80 withBoolean:NO] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"tickLabels" withISPOTElement:tickLabels_];
  }
  return tickLabels_;
}

- (void)setTickLabelsWithISPOTElement:(id<iSPOTElement>)reference {
  tickLabels_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"tickLabels" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTSlider *)other {
  [super copyAllFieldsTo:other];
  other->horizontal_ = horizontal_;
  other->leftIcon_ = leftIcon_;
  other->leftLabel_ = leftLabel_;
  other->majorTickSpacing_ = majorTickSpacing_;
  other->maxValue_ = maxValue_;
  other->minValue_ = minValue_;
  other->minorTickSpacing_ = minorTickSpacing_;
  other->paintInnerTicks_ = paintInnerTicks_;
  other->rightIcon_ = rightIcon_;
  other->rightLabel_ = rightLabel_;
  other->showLabels_ = showLabels_;
  other->showTicks_ = showTicks_;
  other->showTrack_ = showTrack_;
  other->sliderOffset_ = sliderOffset_;
  other->snapToTicks_ = snapToTicks_;
  other->thumbIcon_ = thumbIcon_;
  other->tickLabels_ = tickLabels_;
  other->trackPainter_ = trackPainter_;
  other->value_ = value_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getTrackPainter", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "getTrackPainterReference", NULL, "LRARESPOTGridCell", 0x1, NULL },
    { "setTrackPainterWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getTickLabels", NULL, "LSPOTSet", 0x1, NULL },
    { "getTickLabelsReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setTickLabelsWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "value_", NULL, 0x1, "LSPOTReal" },
    { "minValue_", NULL, 0x1, "LSPOTReal" },
    { "maxValue_", NULL, 0x1, "LSPOTReal" },
    { "horizontal_", NULL, 0x1, "LSPOTBoolean" },
    { "snapToTicks_", NULL, 0x1, "LSPOTBoolean" },
    { "minorTickSpacing_", NULL, 0x1, "LSPOTReal" },
    { "majorTickSpacing_", NULL, 0x1, "LSPOTReal" },
    { "showLabels_", NULL, 0x1, "LSPOTBoolean" },
    { "showTicks_", NULL, 0x1, "LSPOTBoolean" },
    { "showTrack_", NULL, 0x1, "LSPOTBoolean" },
    { "leftLabel_", NULL, 0x1, "LSPOTPrintableString" },
    { "leftIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "rightLabel_", NULL, 0x1, "LSPOTPrintableString" },
    { "rightIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "thumbIcon_", NULL, 0x1, "LSPOTPrintableString" },
    { "paintInnerTicks_", NULL, 0x1, "LSPOTBoolean" },
    { "trackPainter_", NULL, 0x4, "LRARESPOTGridCell" },
    { "sliderOffset_", NULL, 0x1, "LSPOTInteger" },
    { "tickLabels_", NULL, 0x4, "LSPOTSet" },
  };
  static J2ObjcClassInfo _RARESPOTSlider = { "Slider", "com.appnativa.rare.spot", NULL, 0x1, 8, methods, 19, fields, 0, NULL};
  return &_RARESPOTSlider;
}

@end
