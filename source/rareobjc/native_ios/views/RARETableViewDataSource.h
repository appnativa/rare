//
//  RARETableViewDataSource.h
//  RareTOUCH
//
//  Created by Don DeCoteau on 10/1/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "com/appnativa/rare/ui/iPlatformListDataModel.h"

@interface RARETableViewDataSource : NSObject <UITableViewDataSource>  {
@public
  id<RAREiPlatformListDataModel> list_;
}
-(id) initWithList: (id<RAREiPlatformListDataModel>) list;
-(void) setList: (id<RAREiPlatformListDataModel>) list;
-(RARERenderableDataItem*) getItem:(int) index;

@end



