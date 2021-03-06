//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/aSpinnerModel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaSpinnerModel_H_
#define _RAREaSpinnerModel_H_

@class RAREChangeEvent;
@class RAREEventListenerList;
@protocol RAREiChangeListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"

@interface RAREaSpinnerModel : NSObject < RAREiSpinnerModel > {
 @public
  RAREEventListenerList *listenerList_;
  RAREChangeEvent *changeEvent_;
  BOOL editable_;
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (BOOL)isEditable;
- (void)dispose;
- (void)setEditableWithBoolean:(BOOL)editable;
- (NSString *)toStringWithId:(id)value;
- (id)fromStringWithNSString:(NSString *)value;
- (void)fireStateChanged;
- (id)getNextValue;
- (id)getPreviousValue;
- (id)getValue;
- (BOOL)isCircular;
- (void)setValueWithId:(id)param0;
- (id)init;
- (void)copyAllFieldsTo:(RAREaSpinnerModel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSpinnerModel, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREaSpinnerModel, changeEvent_, RAREChangeEvent *)

typedef RAREaSpinnerModel ComAppnativaRareUiSpinnerASpinnerModel;

#endif // _RAREaSpinnerModel_H_
