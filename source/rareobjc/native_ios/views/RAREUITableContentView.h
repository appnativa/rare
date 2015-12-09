//
//  RAREUITableContentView.h
//  RareIOS
//
//  Created by Don DeCoteau on 6/9/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPView.h"
@class RAREUIStroke;
@class RAREUIColor;
@interface RAREUITableContentView : RAREAPView
@property (nonatomic,retain) NSObject* cellRenderers;

-(void) sparPrepareForReuse;
@end
