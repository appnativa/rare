//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UIBalloonBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIBalloonBorder_H_
#define _RAREUIBalloonBorder_H_

@class RAREUIColor;
@class RAREaUIBalloonBorder_PeakLocationEnum;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIBalloonBorder.h"

@interface RAREUIBalloonBorder : RAREaUIBalloonBorder {
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc
withRAREaUIBalloonBorder_PeakLocationEnum:(RAREaUIBalloonBorder_PeakLocationEnum *)pl
                withFloat:(float)peakSize;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc
withRAREaUIBalloonBorder_PeakLocationEnum:(RAREaUIBalloonBorder_PeakLocationEnum *)pl;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc;
- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness;
- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (BOOL)canUseMainLayer;
@end

typedef RAREUIBalloonBorder ComAppnativaRareUiBorderUIBalloonBorder;

#endif // _RAREUIBalloonBorder_H_
