//
//  RAREEmptyFormatter.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 2/5/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RAREEmptyFormatter : NSFormatter
{
@public
  NSFormatter* oldFormatter;
}
-(id) initWithFormatter:(NSFormatter*) formatter;
@end
