/*
 * @(#)ProjectGenerator.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.wizards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.appnativa.studio.Utilities;
import com.appnativa.studio.builder.ProjectNature;
import com.appnativa.studio.builder.ProjectNatureIOS;
import com.appnativa.studio.preferences.MainPreferencePage;
import com.appnativa.studio.properties.PropertiesConstants;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.util.Streams;

public class ProjectGenerator {
  static String[] PATH_SINGLE_2SCREEN = new String[] { "assets", "res", "assets/scripts", "assets/small",
          "assets/large" };
  static String[] PATH_SINGLE_3SCREEN = new String[] {
    "assets", "res", "assets/scripts", "assets/small", "assets/large", "assets/medium"
  };
  static String[] PATH_SINGLE_SCREEN  = new String[] { "assets", "res", "assets/scripts" };
  Application     application;
  URI             location;
  String          packageName;
  String          projectName;
  MainWindow[]    windows;

  public static enum ProjectType {
    SWING, ANDROID, XCODE, IOS_JAVA;
  }

  ProjectGenerator(String projectName, String packageName, URI location, Application application,
                   MainWindow... windows) {
    this.windows     = windows;
    this.application = application;
    this.projectName = projectName;
    this.packageName = packageName;
    this.location    = location;
  }

//  public static void cloneDirectory(File source, File dest, final String myappRename, boolean project,
//                                    String linkLocation)
//          throws Exception {
//    final java.nio.file.Path targetPath = dest.toPath();
//    final java.nio.file.Path sourcePath = source.toPath();
//    final String xcodeapp="XCodeApplication";
//    final String projectName=dest.getName();
//    final boolean xcode=source.getName().contains(xcodeapp);
//
//    Files.walkFileTree(sourcePath, new SimpleFileVisitor<java.nio.file.Path>() {
//      @Override
//      public FileVisitResult preVisitDirectory(final java.nio.file.Path dir, final BasicFileAttributes attrs)
//              throws IOException {
//        if (!dir.getFileName().toString().startsWith(".")) {
//        java.nio.file.Path npath = targetPath.resolve(sourcePath.relativize(dir));
//        String             s     = npath.toString();
//          if(xcode && s.contains(xcodeapp)) {
//            s = s.replace(xcodeapp, projectName);
//            npath = new File(s).toPath();
//            
//          }
//          Files.createDirectories(npath);
//        }
//
//        return FileVisitResult.CONTINUE;
//      }
//      @Override
//      public FileVisitResult visitFile(final java.nio.file.Path file, final BasicFileAttributes attrs)
//              throws IOException {
//        java.nio.file.Path npath = targetPath.resolve(sourcePath.relativize(file));
//        String             s     = npath.toString();
//
//        if (!s.endsWith(".DS_Store")) {
//          if (s.contains("myapp")) {
//            s     = s.replace("myapp", myappRename);
//            npath = new File(s).toPath();
//          }
//          if(xcode && s.contains(xcodeapp)) {
//            s = s.replace(xcodeapp, projectName);
//            npath = new File(s).toPath();
//            
//          }
//
//          Files.copy(file, npath);
//        }
//
//        return FileVisitResult.CONTINUE;
//      }
//    });
//  
//    if (project) {
//      File   f           = new File(dest, ".project");
//      String s           = readAndReplace(f.getAbsolutePath(), "appNativaApplication", projectName);
//
//      if (linkLocation != null) {
//        s = s.replace("appNativaLinkLocation", linkLocation);
//      }
//
//      f.delete();
//
//      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
//
//      w.write(s);
//      w.close();
//      f = new File(dest, ".classpath");
//      s = readAndReplace(f.getAbsolutePath(), "appNativaSDKLocation", MainPreferencePage.getAppNativaSDKDirectory());
//      f.delete();
//      w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
//      w.write(s);
//      w.close();
//    }
//  }
  public static void cloneDirectory(final File source, final File dest, final String myappRename, boolean project,
                                    String linkLocation, boolean iosjava)
          throws Exception {
    final String  xcodeapp      = "XCodeApplication";
    final String  projectName   = dest.getName();
    final int     sourcePathLen = source.getCanonicalPath().length();
    final boolean xcode         = source.getName().contains(xcodeapp);

    walkFileTree(source, new iFileVisitor() {
      @Override
      public boolean preVisitDirectory(final File dir) throws IOException {
        if (!dir.getName().startsWith(".")) {
          File   npath = new File(dest, dir.getCanonicalPath().substring(sourcePathLen));
          String s     = npath.getAbsolutePath();

          if (xcode && s.contains(xcodeapp)) {
            s     = s.replace(xcodeapp, projectName);
            npath = new File(s);
          }

          npath.mkdirs();
        }

        return true;
      }
      @Override
      public boolean visitFile(final File file) throws IOException {
        File   npath = new File(dest, file.getCanonicalPath().substring(sourcePathLen));
        String s     = npath.getAbsolutePath();

        if (!s.endsWith(".DS_Store")) {
          if (s.contains("myapp")) {
            s     = s.replace("myapp", myappRename);
            npath = new File(s);
          }

          if (xcode && s.contains(xcodeapp)) {
            s     = s.replace(xcodeapp, projectName);
            npath = new File(s);
          }

          copy(file, npath);
        }

        return true;
      }
    });

    if (project) {
      File   f = new File(dest, ".project");
      String s = readAndReplace(f.getAbsolutePath(), "appNativaApplication", projectName);

      if (linkLocation != null) {
        s = s.replace("appNativaLinkLocation", linkLocation);
      }

      f.delete();

      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      w.write(s);
      w.close();
      f = new File(dest, ".classpath");
      s = readAndReplace(f.getAbsolutePath(), "appNativaSDKLocation", MainPreferencePage.getAppNativaSDKDirectory());
      if(iosjava) {
        s=s.replace("appnativa.rare.swing.jar","appnativa.rare.ios.jar");
      }
      f.delete();
      w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
    }
  }

  public static void copy(File s, File t) throws IOException {
    FileInputStream  fis = new FileInputStream(s);
    FileOutputStream fos = new FileOutputStream(t);
    FileChannel      in  = fis.getChannel();
    FileChannel      out = fos.getChannel();

    in.transferTo(0, s.length(), out);
    out.close();
    in.close();
    fos.close();
    fis.close();
  }

  public static Application createApplicationObject(String sdkPath, String packageName, UIColor fg, UIColor bg,
          UIColor lc, UIColor dfg)
          throws Exception {
    String name = packageName;
    int    n    = name.lastIndexOf('.');

    if (n != -1) {
      name = name.substring(n + 1);
    }

    String       fname = "application.rml";
    String       s     = sdkPath + "/assets/" + fname;
    File         f     = new File(s);
    URL          base  = f.toURI().toURL();
    StringReader r     = new StringReader(readAndReplace(s, "myapp", name));
    Application  a     = new Application();

    Utilities.loadSPOTObject(r, base, a);

    StringBuilder sb = new StringBuilder();

    if (bg != null) {
      sb.append("    Rare.background=Color|").append(bg).append('\n');
    }

    if (fg != null) {
      sb.append("    Rare.foreground=Color|").append(fg).append('\n');
    }

    if (dfg != null) {
      sb.append("    Rare.disabledForeground=Color|").append(dfg).append('\n');
    }

    if (lc != null) {
      sb.append("    Rare.backgroundShadow=Color|").append(lc).append('\n');
    }

    sb.append(a.lookAndFeelPropertiesURL.getValue());
    a.lookAndFeelPropertiesURL.setValue(sb.toString());

    return a;
  }

  public void generate(Shell shell, IProgressMonitor monitor, String swing, String android, String ios)
          throws Exception {
    try {
      int    count   = 3;
      String sdkPath = MainPreferencePage.getAppNativaSDKDirectory();
      String appId   = packageName;
      int    n       = packageName.lastIndexOf('.');

      if (n != -1) {
        appId = appId.substring(n + 1);
      }

      String appName = application.name.getValue();
      String appTitle;

      if (application.getMainWindow() == null) {
        appTitle = windows[0].title.getValue();
      } else {
        appTitle = application.getMainWindow().title.getValue();
      }

      IProject project          = createBaseProject(projectName, location);
      File     projectDir       = project.getLocation().toFile();
      File     workspaceDir     = projectDir.getParentFile();
      File     androidDirectory = null;
      String   linkProject      = null;
      IProject androidProject   = null;

      if (android != null) {
        count += 1;
      }

      if (swing != null) {
        count += 1;
      }

      if (ios != null) {
        count += 1;
      }

      int screenCount = 0;

      if (windows != null) {
        for (MainWindow w : windows) {
          if (w != null) {
            screenCount++;
          }
        }
      }

      String[] paths;

      switch(screenCount) {
        case 2 :
          paths = PATH_SINGLE_2SCREEN;

          break;

        case 3 :
          paths = PATH_SINGLE_3SCREEN;

          break;

        default :
          paths = PATH_SINGLE_SCREEN;

          break;
      }

      if (android != null) {
        monitor.beginTask("Creating Android project...", count--);
        androidDirectory = new File(workspaceDir, android);
        createAndroidProject(sdkPath, androidDirectory.getAbsolutePath(), packageName, appId, appName, appTitle);

        if (location == null) {
          androidProject = ResourcesPlugin.getWorkspace().getRoot().getProject(android);
          importProject(androidProject, monitor);
        } else {
          androidProject = importProject(androidDirectory, monitor);
        }

        addToProjectStructure(androidProject, paths);
        androidProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
        monitor.worked(1);
        monitor.setTaskName("Creating RML project...");
      } else {
        monitor.beginTask("Creating RML project...", count--);
      }

      addNature(project);

      if (androidProject == null) {
        addToProjectStructure(project, paths);
        cloneDirectory(new File(sdkPath + "/resources/drawable"), new File(projectDir, "res/drawable"), appId, false, null, false);
        cloneDirectory(new File(sdkPath + "/resources/drawable-mdpi"), new File(projectDir, "res/drawable-mdpi"), appId, false, null, false);
        cloneDirectory(new File(sdkPath + "/resources/drawable-xhdpi"), new File(projectDir, "res//drawable-xhdpi"), appId, false, null, false);
        cloneDirectory(new File(sdkPath + "/resources/drawable-port-mdpi"), new File(projectDir, "res/drawable-port-mdpi"), appId, false, null, false);
        cloneDirectory(new File(sdkPath + "/resources/drawable-port-xhdpi"), new File(projectDir, "res/drawable-port-xhdpi"), appId, false, null, false);
        updateCoreAssets(sdkPath, new File(projectDir, "assets").getAbsolutePath(), application, appId);
        linkProject = projectDir.getName();
      } else {
        addProjectLinks(project, androidDirectory);
        linkProject = androidDirectory.getName();
        project.setPersistentProperty(PropertiesConstants.ANDROID_PROJECT, android);
      }

      final IFile efile;

      if (screenCount > 1) {
        File dir;

        if (androidDirectory == null) {
          dir = new File(projectDir, "assets");
        } else {
          dir = new File(androidDirectory, "assets");
        }

        MainWindow w    = windows[0];
        File       file = new File(dir, "small/mainwindow.rml");

        write(file, w.toSDF(), monitor);
        w = windows[1];

        if (w != null) {
          file = new File(dir, "medium/mainwindow.rml");
          write(file, w.toSDF(), monitor);
        }

        w    = windows[2];
        file = new File(dir, "large/mainwindow.rml");
        write(file, w.toSDF(), monitor);
        efile = project.getFile(new Path("assets/large/mainwindow.rml"));
      } else {
        efile = project.getFile(new Path("assets/application.rml"));
      }

      try {
        project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
      } catch(Exception e) {
        e.printStackTrace();
      }

      if (swing != null) {
        monitor.worked(1);
        monitor.setTaskName("Creating SWING Project...");

        File sd = new File(workspaceDir, swing);

        createSwingProject(sdkPath, sd.getAbsolutePath(), packageName, appId, appName, appTitle,
                           "$%7BPARENT-1-PROJECT_LOC%7D/" + linkProject);

        IProject p;

        if (location == null) {
          p = ResourcesPlugin.getWorkspace().getRoot().getProject(swing);
          importProject(p, monitor);
        } else {
          p = importProject(sd, monitor);
        }

        project.setPersistentProperty(PropertiesConstants.SWING_PROJECT_TAG, "it");
        p.setPersistentProperty(PropertiesConstants.SWING_PROJECT, swing);
        p.refreshLocal(IResource.DEPTH_INFINITE, monitor);
      }

      Runnable xcodeRunnable = null;

      if (ios != null) {
        monitor.worked(1);
        monitor.setTaskName("Creating XCode Project...");

        File xd = new File(workspaceDir, ios);

        cloneXCodeProject(sdkPath, xd.getAbsolutePath(), projectName, packageName, appName, linkProject);
        project.setPersistentProperty(PropertiesConstants.XCODE_PROJECT, ios);
        updateIOSAssets(sdkPath, xd.getAbsolutePath(), appId,projectName);

        IProject p;

        if (location == null) {
          p = ResourcesPlugin.getWorkspace().getRoot().getProject(ios);
          importProject(p, monitor);
        } else {
          p = importProject(xd, monitor);
        }

        addIOSNature(p);
        p.refreshLocal(IResource.DEPTH_INFINITE, monitor);

        if (Platform.OS_MACOSX.equals(Platform.getOS())) {
          p.setPersistentProperty(PropertiesConstants.XCODE_PROJECT_FILE, projectName + "/" + projectName + ".xcodeproj");
          final String s = new File(xd, projectName + "/" + projectName + ".xcodeproj").getAbsolutePath();

          xcodeRunnable = new Runnable() {
            @Override
            public void run() {
              try {
                Program.launch(s);
              } catch(Exception e) {
                e.printStackTrace();
              }
            }
          };
        }
      }

      monitor.worked(1);
      monitor.setTaskName("Opening files for editing...");
      shell.getDisplay().asyncExec(new Runnable() {
        public void run() {
          IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

          try {
            IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(efile.getName());

            page.openEditor(new FileEditorInput(efile), desc.getId());
          } catch(PartInitException e) {}
        }
      });

      if (xcodeRunnable != null) {
        shell.getDisplay().asyncExec(xcodeRunnable);
      }
    } finally {
      monitor.done();
    }
  }

  public static String readAndReplace(String file, String what, String with) throws IOException {
    InputStreamReader r    = new InputStreamReader(new FileInputStream(file), "utf-8");
    String            data = Streams.readerToString(r);

    data = data.replace(what, with);
    r.close();

    return data;
  }

  public static boolean walkFileTree(File dir, iFileVisitor visitior) throws IOException {
    if (!visitior.preVisitDirectory(dir)) {
      return false;
    }

    File[] a = dir.listFiles();

    if (a != null) {
      for (File f : a) {
        if (f.isDirectory()) {
          if (!walkFileTree(f, visitior)) {
            break;
          }
        } else if (f.isFile()) {
          if (!visitior.visitFile(f)) {
            break;
          }
        }
      }
    }

    return true;
  }

  static void cloneAndroidProject(String sdkPath, String outPath, String pkg, String appName, String title,
                                  String target) {
    try {
      String path   = sdkPath + "/projects/AndroidApplication";
      File   source = new File(path);
      File   dest   = new File(outPath);
      String rename = pkg;
      int    n      = rename.lastIndexOf('.');

      if (n != -1) {
        rename = rename.substring(n + 1);
      }

      cloneDirectory(source, dest, rename, true, null, false);
      cloneDirectory(new File(sdkPath + "/resources"), new File(dest + "/res"), rename, false, null, false);
      File libs=new File(dest + "/libs");
      libs.mkdirs();
      copy(new File(sdkPath + "/lib/appnativa.rare.android.jar"), new File(dest + "/libs/appnativa.rare.android.jar"));
      copy(new File(sdkPath + "/lib/appnativa.util.jar"), new File(dest + "/libs/appnativa.util.jar"));
      copy(new File(sdkPath + "/lib/appnativa.spot.jar"), new File(dest + "/libs/appnativa.spot.jar"));

      String fname = "AndroidManifest.xml";
      String s     = outPath + "/" + fname;
      File   f     = new File(s);

      s = readAndReplace(s, "myapp", rename);
      s     = s.replace("appNativaAppName", appName);
      s = s.replace("com.appnativa.application", pkg);
      s = s.replace("com.appnativa.application", pkg);
      f.delete();

      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      w.write(s);
      w.close();
      fname = "res/values/strings.xml";
      s     = outPath + "/" + fname;
      s     = readAndReplace(s, "myapp", rename);
      s     = s.replace("appNativaAppName", appName);
      f     = new File(outPath + "/" + fname);
      f.delete();
      w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
      fname = "res/layout/splash_screen.xml";
      s     = outPath + "/" + fname;
      s     = readAndReplace(s, "myapp", rename);
      f     = new File(outPath + "/" + fname);
      f.delete();
      w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
      fname = "src/com/appnativa/application/MainActivity.java";
      s     = outPath + "/" + fname;

      String data = readAndReplace(s, "com.appnativa.application", pkg);

      data = data.replace("appNativaAndroidProjectName", dest.getName());
      f    = new File(outPath + "/" + fname);
      f.delete();
      f = f.getParentFile();

      File p1 = f;
      File p  = new File(outPath + "/src");

      while(!p.equals(p1)) {
        p1.delete();
        p1 = p1.getParentFile();
      }

      fname = "src/" + pkg;
      fname = fname.replace('.', '/') + "/MainActivity.java";
      f     = new File(outPath + "/" + fname);
      f.getParentFile().mkdirs();
      w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(data);
      w.close();

      if (target != null) {
        fname = "project.properties";
        f     = new File(outPath + "/" + fname);

        InputStreamReader r  = new InputStreamReader(new FileInputStream(f), "utf-8");
        BufferedReader    br = new BufferedReader(r, 256);
        StringBuilder     sb = new StringBuilder();
        String            line;

        while((line = br.readLine()) != null) {
          if (line.trim().startsWith("target=")) {
            line = "target=" + target;
          }

          sb.append(line).append('\n');
        }

        r.close();
        f.delete();
        w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
        w.write(sb.toString());
        w.close();
      }
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  void cloneXCodeProject(String sdkPath, String outPath, String mainProjectName, String pkg, String appName,
                         String linkProject)
          throws Exception {
    String path   = sdkPath + "/projects/XCodeApplication";
    File   source = new File(path);
    String rename = pkg;
    int    n      = rename.lastIndexOf('.');

    if (n != -1) {
      rename = rename.substring(n + 1);
    }

    createXCodeJavaProject(sdkPath, outPath, pkg, "$%7BPARENT-1-PROJECT_LOC%7D/" + linkProject);

    String projectName = mainProjectName;
    File   dest        = new File(outPath, mainProjectName);

    outPath = dest.getAbsolutePath();
    cloneDirectory(source, dest, rename, false, null, false);

    String fname = projectName + ".xcodeproj/project.pbxproj";    //copy renames XCodeApplication files
    String s     = outPath + "/" + fname;
    File   f     = new File(s);

    s = readAndReplace(s, "myapp", rename);
    s = s.replace("com.appnativa.application", pkg);
    s = s.replace("XCodeApplication", projectName);
    s = s.replace("appNativaSDKLocation", sdkPath);
    s = s.replace("appNativaAppName", appName);
    s = s.replace("appNativaLinkLocation", "../../" + linkProject);
    s = s.replace("appNativaOrganizationName", MainPreferencePage.getOrganizationName());
    f.delete();

    OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

    w.write(s);
    w.close();
    fname = projectName + "/AppDelegate.m";
    s     = outPath + "/" + fname;
    f     = new File(s);
    s     = readAndReplace(s, "myapp", rename);
    s     = s.replace("appNativaLinkedProjectName", linkProject);
    f.delete();
    w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
    w.write(s);
    w.close();
    fname = projectName + "/" + projectName + "-Info.plist";
    s     = outPath + "/" + fname;
    f     = new File(s);
    s     = readAndReplace(s, "myapp", rename);
    s     = s.replace("com.appnativa.application", pkg);
    f.delete();
    w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
    w.write(s);
    w.close();
    fname = projectName + "/Images.xcassets/AppIcon.appiconset/Contents.json";
    s     = outPath + "/" + fname;
    f     = new File(s);
    s     = readAndReplace(s, "myapp", rename);
    f.delete();
    w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
    w.write(s);
    w.close();
    fname = projectName + "/Images.xcassets/LaunchImage.launchimage/Contents.json";
    s     = outPath + "/" + fname;
    f     = new File(s);
    s     = readAndReplace(s, "myapp", rename);
    f.delete();
    w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
    w.write(s);
    w.close();
    fname="appnativa_user_pkg.properties";
    s     = outPath + "/" + fname;
    f     = new File(s);
    s     = readAndReplace(s, "myapp", rename);
    f.delete();
    f=f.getParentFile().getParentFile();
    f     = new File(f,fname);
    w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
    w.write(s);
    w.close();
  }

  void createAndroidProject(String sdkPath, String outPath, String pkg, String appId, String appName, String appTitle) {
    String platform = MainPreferencePage.getLatestAndroidPlatform();

    cloneAndroidProject(sdkPath, outPath, pkg, appName, appTitle, platform);
    updateCoreAssets(sdkPath, outPath + "/assets", application, appId);
  }

  void createSwingProject(String sdkPath, String outPath, String pkg, String appId, String appName, String appTitle,
                          String linkLocation)
          throws Exception {
    String path   = sdkPath + "/projects/SwingApplication";
    File   source = new File(path);
    File   dest   = new File(outPath);
    String rename = pkg;
    int    n      = rename.lastIndexOf('.');

    if (n != -1) {
      rename = rename.substring(n + 1);
    }

    cloneDirectory(source, dest, rename, true, linkLocation, false);

    String fname = "src/com/appnativa/application/MainSwing.java";
    String s     = outPath + "/" + fname;
    String data  = readAndReplace(s, "com.appnativa.application", pkg);
    String link  = new File(linkLocation).getName();
    String swing = dest.getName();

    data = data.replace("appNativaSwingProjectName", swing);
    data = data.replace("appNativaLinkedProjectName", link);

    File f = new File(outPath + "/" + fname);

    f.delete();
    f = f.getParentFile();

    File p1 = f;
    File p  = new File(outPath + "/src");

    while(!p.equals(p1)) {
      p1.delete();
      p1 = p1.getParentFile();
    }

    fname = "src/" + pkg;
    fname = fname.replace('.', '/') + "/MainSwing.java";
    f     = new File(outPath + "/" + fname);
    f.getParentFile().mkdirs();

    OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

    w.write(data);
    w.close();
  }

  void createXCodeJavaProject(String sdkPath, String outPath, String pkg, String linkLocation) throws Exception {
    String path   = sdkPath + "/projects/SwingApplication";
    File   source = new File(path);
    File   dest   = new File(outPath);
    String rename = pkg;
    int    n      = rename.lastIndexOf('.');

    if (n != -1) {
      rename = rename.substring(n + 1);
    }

    cloneDirectory(source, dest, rename, true, linkLocation, true);

    String fname = "src/com/appnativa/application/MainSwing.java";
    File   f     = new File(outPath + "/" + fname);

    f.delete();
  }

  static void updateCoreAssets(String sdkPath, String outPath, Application a, String name) {
    try {
      String fname = "application.rml";
      File   f     = new File(outPath + "/" + fname);

      f.getParentFile().mkdirs();

      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      w.write(a.toString());
      w.close();

      String s;

      fname = "resource_strings.properties";
      s     = sdkPath + "/assets/" + fname;
      s     = readAndReplace(s, "myapp", name);
      f     = new File(outPath + "/" + fname);
      w     = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
      fname = "resource_icons.properties";
      s     = sdkPath + "/assets/" + fname;
      s     = readAndReplace(s, "myapp", name);
      f     = new File(outPath + "/" + fname);
      w     = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
      fname = "attributes.json";
      s     = sdkPath + "/assets/" + fname;
      s     = readAndReplace(s, "myapp", name);
      f     = new File(outPath + "/" + fname);
      w     = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
      w.write(s);
      w.close();
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  static void updateIOSAssets(String sdkPath, String outPath, String name,String projectName) {
    try {
      String fname = "transpiler.properties";
      String s     = sdkPath + "/assets/" + fname;

      s = readAndReplace(s, "myapp", name);
      s = s.replace("XCodeApplication", projectName);

      File               f = new File(outPath + "/" + fname);
      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      w.write(s);
      w.close();
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  private void addNature(IProject project) throws CoreException {
    if (!project.hasNature(ProjectNature.NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[]            prevNatures = description.getNatureIds();
      String[]            newNatures  = new String[prevNatures.length + 1];

      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = ProjectNature.NATURE_ID;
      description.setNatureIds(newNatures);

      IProgressMonitor monitor = null;

      project.setDescription(description, monitor);
    }
  }

  private void addIOSNature(IProject project) throws CoreException {
    if (!project.hasNature(ProjectNatureIOS.NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[]            prevNatures = description.getNatureIds();
      String[]            newNatures  = new String[prevNatures.length + 1];

      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = ProjectNatureIOS.NATURE_ID;
      description.setNatureIds(newNatures);

      IProgressMonitor monitor = null;

      project.setDescription(description, monitor);
    }
  }
  private void addProjectLinks(IProject project, File androidDirectory) throws CoreException {
    File    f        = new File(androidDirectory, "assets");
    IPath   location = new Path(f.getAbsolutePath());
    IFolder link     = project.getFolder("assets");

    link.createLink(location, IResource.NONE, null);
    f        = new File(androidDirectory, "res");
    location = new Path(f.getAbsolutePath());
    link     = project.getFolder("res");
    link.createLink(location, IResource.NONE, null);
  }

  private void addToProjectStructure(IProject project, String[] paths) throws Exception {
    for (String path : paths) {
      IFolder etcFolders = project.getFolder(path);

      createFolder(etcFolders);
    }
  }

  private IProject createBaseProject(String projectName, URI location) throws CoreException {
    IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

    if (!newProject.exists()) {
      URI                 projectLocation = location;
      IProjectDescription desc            = newProject.getWorkspace().newProjectDescription(projectName);

      if ((location != null) && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
        projectLocation = null;
      }

      desc.setLocationURI(projectLocation);
      newProject.create(desc, null);

      if (!newProject.isOpen()) {
        newProject.open(null);
      }
    }

    return newProject;
  }

  private void createFolder(IFolder folder) throws CoreException {
    IContainer parent = folder.getParent();

    if (parent instanceof IFolder) {
      createFolder((IFolder) parent);
    }

    if (!folder.exists()) {
      folder.create(false, true, null);
    }
  }

  private IProject importProject(File path, IProgressMonitor monitor) throws CoreException {
    IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(new File(path,
                                        ".project").getAbsolutePath()));
    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());

    project.create(description, monitor);
    project.open(monitor);

    return project;
  }

  private void importProject(IProject project, IProgressMonitor monitor) throws CoreException {
    project.create(monitor);
    project.open(monitor);
  }

  private void write(File f, String data, IProgressMonitor monitor) {
    try {
      OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");

      w.write(data);
      w.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  interface iFileVisitor {
    public boolean preVisitDirectory(File dir) throws IOException;

    public boolean visitFile(File file) throws IOException;
  }
}
