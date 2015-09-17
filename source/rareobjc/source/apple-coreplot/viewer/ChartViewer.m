//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-coreplot/com/appnativa/rare/viewer/ChartViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/ui/chart/coreplot/ChartHandler.h"
#include "com/appnativa/rare/viewer/ChartViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"

@implementation RAREChartViewer

- (id)init {
  return [super init];
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (void)onConfigurationChangedWithBoolean:(BOOL)reset {
  if (chartHandler_ != nil) {
    [((RAREChartHandler *) check_class_cast(chartHandler_, [RAREChartHandler class])) reloadChartsWithRAREiPlatformComponent:chartComponent_ withRAREChartDefinition:chartDefinition_];
  }
  [super onConfigurationChangedWithBoolean:reset];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREChartViewer = { "ChartViewer", "com.appnativa.rare.viewer", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREChartViewer;
}

@end
