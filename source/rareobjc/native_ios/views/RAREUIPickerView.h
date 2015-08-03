//
// Created by Don DeCoteau on 7/15/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>

@protocol RAREiSpinnerModel;


@interface RAREUIPickerView : UIPickerView <UIPickerViewDelegate,UIPickerViewDataSource>  {
    NSInteger actualCount_;
}
@property BOOL plainText;
@property BOOL isCircular;
-(int) getSelectedIndexEx;
-(void) setSelectedIndexEx: (int) index;

@end