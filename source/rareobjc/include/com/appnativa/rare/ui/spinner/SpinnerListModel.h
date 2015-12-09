//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/SpinnerListModel.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESpinnerListModel_H_
#define _RARESpinnerListModel_H_

@protocol JavaUtilList;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/spinner/aSpinnerModel.h"

@interface RARESpinnerListModel : RAREaSpinnerModel {
 @public
  BOOL isCircular__;
  id<JavaUtilList> list_;
  int index_;
}

- (id)initWithBoolean:(BOOL)circular;
- (id)initWithJavaUtilList:(id<JavaUtilList>)values
               withBoolean:(BOOL)circular;
- (id)fromStringWithNSString:(NSString *)value;
- (void)setListWithJavaUtilList:(id<JavaUtilList>)list;
- (void)setValueWithId:(id)value;
- (void)dispose;
- (id<JavaUtilList>)getList;
- (id)getNextValue;
- (id)getPreviousValue;
- (id)getValue;
- (BOOL)isCircular;
- (void)copyAllFieldsTo:(RARESpinnerListModel *)other;
@end

J2OBJC_FIELD_SETTER(RARESpinnerListModel, list_, id<JavaUtilList>)

typedef RARESpinnerListModel ComAppnativaRareUiSpinnerSpinnerListModel;

#endif // _RARESpinnerListModel_H_
