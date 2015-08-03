/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 *
 * @author decoteaud
 */
public class FileEx extends File {
  int    colonPos = -2;
  String _path;

  public FileEx(String pathname) {
    super(pathname);
    _path = pathname;

    int n = pathname.indexOf(':');

    if ((n < 3) || pathname.startsWith("file:")) {
      _path = super.getPath();
    }
  }

  public FileEx(URI uri) {
    super(uri);
    this._path = super.getPath();
  }

  public FileEx(File parent, String child) {
    super(parent, child);
  }

  public FileEx(String parent, String child) {
    super(parent, child);
  }

  public IFile toIFile() {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    IPath location = Path.fromOSString(getAbsolutePath());
    IFile ifile = workspace.getRoot().getFileForLocation(location);
    return ifile;
  }

  public String getPath() {
    return (_path == null) ? super.getPath() : _path;
  }
}
