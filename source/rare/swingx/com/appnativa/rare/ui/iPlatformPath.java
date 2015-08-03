package com.appnativa.rare.ui;

import java.awt.geom.GeneralPath;

public interface iPlatformPath extends iPath {
	GeneralPath getPath();
	iPlatformPath setPath(GeneralPath path);
  iPlatformPath copy();
}
