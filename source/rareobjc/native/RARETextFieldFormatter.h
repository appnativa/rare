//
//  RARETextFieldFormatter.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 7/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol RAREiTextChangeListener;

@interface RARETextFieldFormatter : NSFormatter  {
@public
    id<RAREiTextChangeListener> changeListener_;
    id source_;
    NSFormatter* formatter_;
}
-(void) sparDispose;
@end
