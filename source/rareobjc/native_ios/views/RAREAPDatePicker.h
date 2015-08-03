//
// Created by Don DeCoteau on 7/11/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>


@interface RAREAPDatePicker : UIDatePicker
- (id)initWithShowTime:(BOOL)showTime andShowTimeOnly:(BOOL)showTimeOnly;

@property(nonatomic) BOOL weekdaysOnly;
@property(nonatomic) BOOL weekendsOnly;

- (void)setDateInMillis:(long long)date;

- (void)setMinDate:(NSDate*)date;

- (void)setDateValue:(NSDate*)date;

- (void)setMaxDate:(NSDate*) date;

- (void)setRangeWithStartDate:(long long)startDate andEndDate:(long long)endDate;

- (long long)getDateInMillis;

- (void)setMultiSelect:(BOOL)multiSelect;

- (void)setShowTime:(BOOL)showTime;

- (void)setShowTimeOnly:(BOOL)showTimeOnly;

- (void)setEndDateInMillis:(long long)date;
- (void)setEndDate:(NSDate*)date;


@end