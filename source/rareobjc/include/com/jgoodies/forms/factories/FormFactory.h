//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/jgoodies/forms/factories/FormFactory.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREJGFormFactory_H_
#define _RAREJGFormFactory_H_

@class RAREJGColumnSpec;
@class RAREJGConstantSize;
@class RAREJGRowSpec;

#import "JreEmulation.h"

@interface RAREJGFormFactory : NSObject {
}

+ (RAREJGColumnSpec *)MIN_COLSPEC;
+ (RAREJGColumnSpec *)PREF_COLSPEC;
+ (RAREJGColumnSpec *)DEFAULT_COLSPEC;
+ (RAREJGColumnSpec *)GLUE_COLSPEC;
+ (RAREJGColumnSpec *)LABEL_COMPONENT_GAP_COLSPEC;
+ (RAREJGColumnSpec *)RELATED_GAP_COLSPEC;
+ (RAREJGColumnSpec *)UNRELATED_GAP_COLSPEC;
+ (RAREJGColumnSpec *)BUTTON_COLSPEC;
+ (RAREJGColumnSpec *)GROWING_BUTTON_COLSPEC;
+ (RAREJGRowSpec *)MIN_ROWSPEC;
+ (RAREJGRowSpec *)PREF_ROWSPEC;
+ (RAREJGRowSpec *)DEFAULT_ROWSPEC;
+ (RAREJGRowSpec *)GLUE_ROWSPEC;
+ (RAREJGRowSpec *)RELATED_GAP_ROWSPEC;
+ (RAREJGRowSpec *)UNRELATED_GAP_ROWSPEC;
+ (RAREJGRowSpec *)NARROW_LINE_GAP_ROWSPEC;
+ (RAREJGRowSpec *)LINE_GAP_ROWSPEC;
+ (RAREJGRowSpec *)PARAGRAPH_GAP_ROWSPEC;
+ (RAREJGRowSpec *)BUTTON_ROWSPEC;
- (id)init;
+ (RAREJGColumnSpec *)createGapColumnSpecWithRAREJGConstantSize:(RAREJGConstantSize *)gapWidth;
+ (RAREJGRowSpec *)createGapRowSpecWithRAREJGConstantSize:(RAREJGConstantSize *)gapHeight;
@end

typedef RAREJGFormFactory ComJgoodiesFormsFactoriesFormFactory;

#endif // _RAREJGFormFactory_H_
