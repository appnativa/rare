//
//  RARETextFieldFormatter.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 7/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/listener/iTextChangeListener.h>
#import "RARETextFieldFormatter.h"

@implementation RARETextFieldFormatter

- (BOOL)isPartialStringValid:(NSString **)partialStringPtr proposedSelectedRange:(NSRangePointer)proposedSelRangePtr originalString:(NSString *)origString originalSelectedRange:(NSRange)origSelRange errorDescription:(NSString **)error {
    if(changeListener_) {
        int startIndex=(int)origSelRange.location;
        int endIndex=(int)origSelRange.length+startIndex;
        return [changeListener_ textChangingWithId:source_ withInt:startIndex withInt:endIndex withJavaLangCharSequence:*partialStringPtr];
    }
    if(formatter_) {
        return [formatter_ isPartialStringValid:partialStringPtr proposedSelectedRange:proposedSelRangePtr originalString:origString originalSelectedRange:origSelRange errorDescription:error];
    }
    return YES;
}

- (NSString *)stringForObjectValue:(id)obj {
    if(formatter_) {
        return [formatter_ stringForObjectValue:obj];
    }
    return obj;
}
-(void) sparDispose {
  changeListener_=nil;
  formatter_=nil;
  source_=nil;
}

- (BOOL)getObjectValue:(out id *)obj forString:(NSString *)string errorDescription:(out NSString **)error {
    if(formatter_) {
        return [formatter_ getObjectValue:obj forString:string errorDescription:error];
    }
    *obj=string;
    return YES;
}

- (BOOL)isPartialStringValid:(NSString *)partialString newEditingString:(NSString **)newString errorDescription:(NSString **)error {
    if(formatter_) {
        return [formatter_ isPartialStringValid:partialString newEditingString:newString errorDescription:error];
    }
    return [super isPartialStringValid:partialString newEditingString:newString errorDescription:error];
}

@end
