//
// Created by Don DeCoteau on 7/11/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RAREAPDatePicker.h"
#import "APView+Component.h"
#import "com/appnativa/rare/platform/apple/ui/view/DatePickerView.h"

@implementation RAREAPDatePicker

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithShowTime:(BOOL)showTime andShowTimeOnly:(BOOL)showTimeOnly {
  if (self = [super init]) {
    self.backgroundColor=[UIColor whiteColor];
    if (showTimeOnly) {
      self.datePickerMode=UIDatePickerModeTime;
    }
    else {
      if (showTime) {
        self.datePickerMode=UIDatePickerModeDateAndTime;
      }
      else {
        self.datePickerMode=UIDatePickerModeDate;
      }
    }
    [self addTarget:self
             action:@selector(dateChanged:)
   forControlEvents:UIControlEventValueChanged];
  }
  return self;
}
-(CGSize)sizeThatFits:(CGSize)size {
  size=[super sizeThatFits:size];
  return size;
}
- (void)setShowTime:(BOOL)showTime {
  switch(self.datePickerMode) {
    case UIDatePickerModeDateAndTime:
    case UIDatePickerModeDate:
      self.datePickerMode=showTime ? UIDatePickerModeDateAndTime : UIDatePickerModeDate;
      break;
    default:
      break;
  }
}

- (void)setShowTimeOnly:(BOOL)showTimeOnly {
  switch(self.datePickerMode) {
    case UIDatePickerModeDateAndTime:
      self.datePickerMode=showTimeOnly ? UIDatePickerModeTime : UIDatePickerModeDateAndTime;
      break;
    case UIDatePickerModeDate:
      self.datePickerMode=showTimeOnly ? UIDatePickerModeTime : UIDatePickerModeDate;
      break;
    default:
      self.datePickerMode=showTimeOnly ? UIDatePickerModeTime : UIDatePickerModeDateAndTime;
      break;
  }
}

- (void)setDateInMillis:(long long)date {
  self.date = [NSDate dateWithTimeIntervalSince1970:(date / 1000.0)];
}

- (long long)getDateInMillis {
  return (long long) [self.date timeIntervalSince1970] * 1000.0;
}

- (void)setRangeWithStartDate:(long long)startDate andEndDate:(long long)endDate {
}
-(void)setEndDateInMillis: (long long)date {
}

- (void)setMultiSelect:(BOOL)multiSelect {
}

- (void)setMinDateInMillis:(long long)date {
  self.minimumDate=[NSDate dateWithTimeIntervalSince1970:(date / 1000.0)];
}

- (void)setMaxDateInMillis:(long long)date {
  self.maximumDate=[NSDate dateWithTimeIntervalSince1970:(date / 1000.0)];
}
-(void)setMaxDate:(NSDate *)date {
  self.maximumDate=date;
}
-(void)setMinDate:(NSDate *)date {
  self.minimumDate=date;
}

-(void)setDateValue:(NSDate *)date {
  self.date=date;
}

- (void)dateChanged:(id)sender {
  RAREDatePickerView *view = (RAREDatePickerView *) self.sparView;
  NSDate *date = self.date;
  [view timeChangedWithLong:[date timeIntervalSince1970] * 1000.0];
}
- (void)setEndDate:(NSDate*)date {
  
}
-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}

@end