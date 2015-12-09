//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aLinearPanel.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaLinearPanel_H_
#define _RAREaLinearPanel_H_

@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"

@interface RAREaLinearPanel : RAREaFormsPanel {
 @public
  BOOL firstComponentAdded_;
  NSString *colSpec_;
  BOOL horizontal_;
  NSString *rowSpec_;
  int spacing_;
  NSString *originalRowSpec_;
  NSString *originalColSpec_;
}

- (id)initWithBoolean:(BOOL)horizontal;
- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)addComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                  withNSString:(NSString *)spec;
- (void)setSpecsWithBoolean:(BOOL)horizontal
               withNSString:(NSString *)rspec
               withNSString:(NSString *)cspec;
- (void)addExpansionComponent;
- (void)addSeparatorComponent;
- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setColumnSpecWithNSString:(NSString *)spec;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setRowSpecWithNSString:(NSString *)spec;
- (void)setSpacingWithInt:(int)spacing;
- (int)getSpacing;
- (BOOL)isHorizontal;
- (void)updateFormLayout;
- (void)addFirstComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                       withNSString:(NSString *)spec;
- (void)reverseComponentOrder;
- (void)copyAllFieldsTo:(RAREaLinearPanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaLinearPanel, colSpec_, NSString *)
J2OBJC_FIELD_SETTER(RAREaLinearPanel, rowSpec_, NSString *)
J2OBJC_FIELD_SETTER(RAREaLinearPanel, originalRowSpec_, NSString *)
J2OBJC_FIELD_SETTER(RAREaLinearPanel, originalColSpec_, NSString *)

typedef RAREaLinearPanel ComAppnativaRareUiALinearPanel;

#endif // _RAREaLinearPanel_H_
