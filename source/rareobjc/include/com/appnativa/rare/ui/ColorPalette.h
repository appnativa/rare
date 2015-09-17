//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-colorchooser/com/appnativa/rare/ui/ColorPalette.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREColorPalette_H_
#define _RAREColorPalette_H_

@class IOSObjectArray;
@class RARERenderableDataItem;
@class RAREUIColor;
@protocol JavaUtilList;

#import "JreEmulation.h"

@interface RAREColorPalette : NSObject {
 @public
  IOSObjectArray *colors_;
  int columns_;
  NSString *name_;
  int rows_;
}

+ (RAREColorPalette *)colorPalette16;
+ (void)setColorPalette16:(RAREColorPalette *)colorPalette16;
+ (RAREColorPalette *)colorPalette40;
+ (void)setColorPalette40:(RAREColorPalette *)colorPalette40;
+ (RAREColorPalette *)grayPaletteGray16;
+ (void)setGrayPaletteGray16:(RAREColorPalette *)grayPaletteGray16;
- (id)init;
- (id)initWithRAREUIColorArray:(IOSObjectArray *)colors;
- (id)initWithNSString:(NSString *)name
  withRAREUIColorArray:(IOSObjectArray *)colors
               withInt:(int)columns
               withInt:(int)rows;
- (RARERenderableDataItem *)createListItemWithRAREUIColor:(RAREUIColor *)color;
- (id<JavaUtilList>)createListItems;
- (void)setColumnsWithInt:(int)columns;
- (void)setMatrixWithInt:(int)columns
                 withInt:(int)rows;
- (void)setNameWithNSString:(NSString *)name;
- (void)setPaletteWithRAREUIColorArray:(IOSObjectArray *)colors;
- (void)setRowsWithInt:(int)rows;
+ (RAREColorPalette *)getColorPalette16;
+ (RAREColorPalette *)getColorPalette40;
- (IOSObjectArray *)getColors;
- (int)getColumns;
+ (RAREColorPalette *)getGrayPalette16;
- (NSString *)getName;
- (int)getRows;
- (void)copyAllFieldsTo:(RAREColorPalette *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorPalette, colors_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREColorPalette, name_, NSString *)

typedef RAREColorPalette ComAppnativaRareUiColorPalette;

#endif // _RAREColorPalette_H_
