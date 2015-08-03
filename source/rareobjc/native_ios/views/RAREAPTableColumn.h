//
//  RAREAPTableColumn.h
//  RareOSX
//
//  Created by Don DeCoteau on 4/2/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RAREAPLabelView.h"
@class RAREColumn;
@interface RAREAPTableColumn : RAREAPLabelView {
  @public
  int modelIndex;
  __weak RAREColumn* columnDescription;
  
}

-(id) initWithModelIndex: (int) index andDescription: (RAREColumn*) description;

@end
