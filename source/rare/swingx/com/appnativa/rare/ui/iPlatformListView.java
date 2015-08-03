package com.appnativa.rare.ui;



public interface iPlatformListView  extends iListView{
  iPlatformRenderingComponent prepareRendererForReuse(iPlatformRenderingComponent renderingComponent, int row, int col);
}
