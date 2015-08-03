//
// Created by Don DeCoteau on 7/15/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <com/appnativa/rare/ui/spinner/PickerView.h>
#import <com/appnativa/rare/ui/text/HTMLCharSequence.h>
#import "RAREUIPickerView.h"
#import "RAREAPLabelView.h"
#import "APView+Component.h"


@implementation RAREUIPickerView
+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
     self.dataSource=self;
     self.delegate=self;
     self.showsSelectionIndicator=YES;
    }

    return self;
}
-(void)sparDispose {
  self.delegate=nil;
  [super sparDispose];
}

- (int)getSelectedIndexEx {
    return (int)[self selectedRowInComponent:0];
}

- (void)setSelectedIndexEx:(int)index {
    NSInteger row=index;
    if(self.isCircular) {
        row+= (int)(floor(INT16_MAX/(2*actualCount_))*actualCount_);
    }
    [self selectRow:row inComponent:0 animated:NO];
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    RAREPickerView* view=(RAREPickerView*)self.sparView;
    if(!view) {
      return 1;
    }
    NSInteger count=[view->pickerHelper_ getRowCount];
    if(count<0) {

        count=1;
    }
    actualCount_=MIN(count,INT16_MAX);
    return self.isCircular ? INT16_MAX  : actualCount_;
}

//- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
//    RAREPickerView* view=(RAREPickerView*)self.sparView;
//    id <JavaLangCharSequence> seq = [view->pickerHelper_ getValueWithInt:row];
//    return seq ? seq.description : @"";
//}
//
//- (NSAttributedString *)pickerView:(UIPickerView *)pickerView attributedTitleForRow:(NSInteger)row forComponent:(NSInteger)component {
//    if(self.plainText) {
//        return nil;
//    }
//    RAREPickerView* view=(RAREPickerView*)self.sparView;
//    id <JavaLangCharSequence> seq = [view->pickerHelper_ getValueWithInt:row];
//    if([seq isKindOfClass:[RAREHTMLCharSequence class]]) {
//        return  (NSAttributedString *)((RAREHTMLCharSequence*)seq)->attributedText_;
//    }
//    else {
//        return [[NSAttributedString alloc] initWithString: (seq ? seq.description : @"") ];
//    }
//}
-(UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
  
  if(!view) {
    view=[RAREAPLabelView new];
  }
  RAREPickerView* pview=(RAREPickerView*)self.sparView;
  if(pview) {
    [pview->pickerHelper_ renderValueWithInt:(int)row withId:view];
  }
  return view;
}
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    RAREPickerView* view=(RAREPickerView*)self.sparView;
    [view rowSelectedWithInt:(int)row];
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