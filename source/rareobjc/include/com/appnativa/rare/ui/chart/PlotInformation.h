//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/PlotInformation.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREPlotInformation_H_
#define _RAREPlotInformation_H_

@class RAREPlotInformation_LabelTypeEnum;
@class RAREPlotInformation_ShapeStyleEnum;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUIPoint;
@class RAREUIStroke;
@protocol RAREiBorder;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@interface RAREPlotInformation : NSObject {
 @public
  float foregroundAlpha_;
  float lineThickness_;
  float outlineThickness_;
  RAREPlotInformation_LabelTypeEnum *labelType_;
  RAREPlotInformation_ShapeStyleEnum *shapeStyle_;
  BOOL showGridLines_;
  RAREUIColor *bgColor_;
  RAREUIImage *bgImage_;
  RAREUIColor *gridColor_;
  RAREUIStroke *gridStroke_;
  RAREUIColor *labelsBackground_;
  id<RAREiBorder> labelsBorder_;
  RAREUIFont *labelsFont_;
  RAREUIColor *labelsForeground_;
  NSString *labelsFormat_;
  RAREUIPoint *labelsOffset_;
  RAREUIInsets *margin_;
  RAREUIColor *outlineColor_;
  RAREUIColor *fillColor_;
  RAREUIColor *borderColor_;
}

- (id)init;
- (id)initWithRAREUIColor:(RAREUIColor *)bg
          withRAREUIColor:(RAREUIColor *)gc
         withRAREUIInsets:(RAREUIInsets *)margin
         withRAREUIStroke:(RAREUIStroke *)stroke
          withRAREUIImage:(RAREUIImage *)bgimage
withRAREPlotInformation_ShapeStyleEnum:(RAREPlotInformation_ShapeStyleEnum *)ss;
- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setBackgroundImageWithRAREUIImage:(RAREUIImage *)bgImage;
- (void)setForegroundAlphaWithFloat:(float)foregroundAlpha;
- (void)setGridColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setGridStrokeWithRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setLabelTypeWithRAREPlotInformation_LabelTypeEnum:(RAREPlotInformation_LabelTypeEnum *)type;
- (void)setLabelsBackgroundWithRAREUIColor:(RAREUIColor *)labelsBackground;
- (void)setLabelsBorderWithRAREiBorder:(id<RAREiBorder>)labelsBorder;
- (void)setLabelsFontWithRAREUIFont:(RAREUIFont *)labelsFont;
- (void)setLabelsForegroundWithRAREUIColor:(RAREUIColor *)labelsForeground;
- (void)setLabelsFormatWithNSString:(NSString *)labelsFormat;
- (void)setLabelsOffsetWithRAREUIPoint:(RAREUIPoint *)labelsOffset;
- (void)setLineThicknessWithFloat:(float)lineThickness;
- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)margin;
- (void)setOutlineColorWithRAREUIColor:(RAREUIColor *)outlineColor;
- (void)setOutlineThicknessWithFloat:(float)outlineThickness;
- (void)setFillColorWithRAREUIColor:(RAREUIColor *)fillColor;
- (void)setShapeStyleWithRAREPlotInformation_ShapeStyleEnum:(RAREPlotInformation_ShapeStyleEnum *)style;
- (void)setShowGridLinesWithBoolean:(BOOL)showGridLines;
- (RAREUIColor *)getBackgroundColor;
- (RAREUIImage *)getBackgroundImage;
- (float)getForegroundAlpha;
- (RAREUIColor *)getGridColor;
- (RAREUIStroke *)getGridStroke;
- (RAREPlotInformation_LabelTypeEnum *)getLabelType;
- (RAREUIColor *)getLabelsBackground;
- (id<RAREiBorder>)getLabelsBorder;
- (RAREUIFont *)getLabelsFont;
- (RAREUIColor *)getLabelsForeground;
- (NSString *)getLabelsFormat;
- (RAREUIPoint *)getLabelsOffset;
- (float)getLineThickness;
- (RAREUIInsets *)getMargin;
- (RAREUIColor *)getOutlineColor;
- (float)getOutlineThickness;
- (RAREUIColor *)getFillColor;
- (RAREPlotInformation_ShapeStyleEnum *)getShapeStyle;
- (RAREPlotInformation_ShapeStyleEnum *)getShapeStyleEx;
- (BOOL)isShowGridLines;
- (RAREUIColor *)getBorderColor;
- (void)setBorderColorWithRAREUIColor:(RAREUIColor *)borderColor;
- (void)copyAllFieldsTo:(RAREPlotInformation *)other;
@end

J2OBJC_FIELD_SETTER(RAREPlotInformation, labelType_, RAREPlotInformation_LabelTypeEnum *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, shapeStyle_, RAREPlotInformation_ShapeStyleEnum *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, bgColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, bgImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, gridColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, gridStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsBackground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsBorder_, id<RAREiBorder>)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsForeground_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsFormat_, NSString *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, labelsOffset_, RAREUIPoint *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, margin_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, outlineColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, fillColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREPlotInformation, borderColor_, RAREUIColor *)

typedef RAREPlotInformation ComAppnativaRareUiChartPlotInformation;

typedef enum {
  RAREPlotInformation_LabelType_VALUES = 0,
  RAREPlotInformation_LabelType_TOOLTIPS = 1,
  RAREPlotInformation_LabelType_LINKED_DATA = 2,
} RAREPlotInformation_LabelType;

@interface RAREPlotInformation_LabelTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREPlotInformation_LabelTypeEnum *)VALUES;
+ (RAREPlotInformation_LabelTypeEnum *)TOOLTIPS;
+ (RAREPlotInformation_LabelTypeEnum *)LINKED_DATA;
+ (IOSObjectArray *)values;
+ (RAREPlotInformation_LabelTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RAREPlotInformation_ShapeStyle_NONE = 0,
  RAREPlotInformation_ShapeStyle_FILLED = 1,
  RAREPlotInformation_ShapeStyle_OUTLINED = 2,
  RAREPlotInformation_ShapeStyle_FILLED_AND_OUTLINED = 3,
} RAREPlotInformation_ShapeStyle;

@interface RAREPlotInformation_ShapeStyleEnum : JavaLangEnum < NSCopying > {
}
+ (RAREPlotInformation_ShapeStyleEnum *)NONE;
+ (RAREPlotInformation_ShapeStyleEnum *)FILLED;
+ (RAREPlotInformation_ShapeStyleEnum *)OUTLINED;
+ (RAREPlotInformation_ShapeStyleEnum *)FILLED_AND_OUTLINED;
+ (IOSObjectArray *)values;
+ (RAREPlotInformation_ShapeStyleEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREPlotInformation_H_