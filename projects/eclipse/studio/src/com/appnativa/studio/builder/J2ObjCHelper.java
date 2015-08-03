/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appnativa.studio.builder;

import com.appnativa.rare.scripting.Functions;
import com.appnativa.util.Helper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 *
 * @author decoteaud
 */
public class J2ObjCHelper {
  HashSet<String>   deletions      = new HashSet<String>();
  ArrayList<String> changes        = new ArrayList<String>();
  ArrayList<String> additions      = new ArrayList<String>();
  long              lastModifiedTP = 0;
  boolean           showStdOut     = false;
  boolean           fullBuild;
  String            generatedSourceDir;
  File              generatedSourceDirFile;
  File              includeFile;
  String            includePathPrefix;
  String            mappingFile;
  String            projectDir;
  File              projectDirFile;
  String            sdkDir;

  public J2ObjCHelper(String sdk, String project) {
    sdkDir         = sdk;
    projectDir     = project;
    projectDirFile = new File(project);
  }

  public void add(JarOutputStream target, String name, File f) {
    String file = name;

    file = file.replace(".java", ".m");
    additions.add(includePathPrefix + "/" + file);

    BufferedInputStream in = null;

    try {
      JarEntry entry = new JarEntry(name);

      entry.setTime(f.lastModified());
      target.putNextEntry(entry);
      in = new BufferedInputStream(new FileInputStream(f));

      byte[] buffer = new byte[1024];

      while(true) {
        int count = in.read(buffer);

        if (count == -1) {
          break;
        }

        target.write(buffer, 0, count);
      }

      target.closeEntry();
      in.close();
    } catch(Exception e) {
      if (in != null) {
        try {
          in.close();
        } catch(IOException ignore) {}
      }
    }
  }

  public boolean canBuild(PrintWriter pw) {
    updateGenerationInfo(pw);

    return generatedSourceDirFile != null;
  }

  public void remove(String name) {
    name = name.replace(".java", ".m");
    deletions.add(generatedSourceDirFile.getName() + "/" + name);

    File f = new File(generatedSourceDirFile, name);

    if (f.exists()) {
      f.delete();
    }
    
    //change name to be header file from  source file
    name = name.replace(".m", ".h");
    f = new File(generatedSourceDirFile, name);

    if (f.exists()) {
      f.delete();
    }
  }

  public boolean transpile(IProject project, File jarFile, PrintWriter pw, final IProgressMonitor monitor)
          throws Exception {
    if (additions.isEmpty() && deletions.isEmpty()) {
      return false;
    }

    if (!additions.isEmpty()) {
      if (!generatedSourceDirFile.exists()) {
        generatedSourceDirFile.mkdirs();
      }

      ParamList params = createArgs(project, jarFile);

      pw.println("Invoking ObjectiveC translater: " + params.toString());

      Process        p        = Runtime.getRuntime().exec(params.getParams());
      BufferedReader ri       = new BufferedReader(new InputStreamReader(p.getInputStream()));
      BufferedReader re       = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      String         lastLine = null;

      try {
        String line = null;

        do {
          while(ri.ready() && (line = ri.readLine()) != null) {
            if (showStdOut) {
              pw.println(line);
              pw.flush();
            }

            if (line.trim().length() > 0) {
              lastLine = line;
              monitor.setTaskName(line);
            }
          }

          while(re.ready() && (line = re.readLine()) != null) {
            pw.println(line);
            pw.flush();
            monitor.setTaskName(line);
          }
        } while(!isProcessFinished(p, monitor));

        //process finished  read what ever is left
        while(ri.ready() && (line = ri.readLine()) != null) {
          if (showStdOut) {
            pw.println(line);
            pw.flush();
          }

          if (line.trim().length() > 0) {
            lastLine = line;
            monitor.setTaskName(line);
          }
        }

        while(re.ready() && (line = re.readLine()) != null) {
          pw.println(line);
          monitor.setTaskName(line);
          pw.flush();
        }
      } catch(Exception ignore) {}

      try {
        ri.close();
      } catch(Exception egnore) {}

      try {
        re.close();
      } catch(Exception egnore) {}

      if (!showStdOut && (lastLine != null)) {
        pw.println(lastLine);
      }
    }

    if (includeFile != null) {
      updateIncludeFile(pw);
    }

    pw.flush();

    return true;
  }

  ParamList createArgs(IProject project, File jarFile) {
    String pathsep = File.pathSeparator;
    String out     = generatedSourceDirFile.getAbsolutePath();
    String j2objc  = sdkDir + "/j2objc";

    if (!generatedSourceDirFile.exists()) {
      generatedSourceDirFile.mkdirs();
    }

    ParamList params = new ParamList();

    params.append("java").append("-cp").append(j2objc + "/lib/*");
    params.append("-jar");
    params.append(j2objc + "/lib/j2objc.jar");
    params.append("-classpath");

    StringBuilder sb = new StringBuilder();

    sb.append(sdkDir + "/lib/appnativa.rare.ios.jar");
    sb.append(pathsep).append(j2objc).append("/lib/j2objc_annotations.jar");
    sb.append(pathsep).append(sdkDir).append("/lib/appnativa.spot.jar");
    sb.append(pathsep).append(sdkDir).append("/lib/appnativa.util.jar");
    params.append(sb.toString());
    params.append("-use-arc").append("--mapping").append(j2objc + "/sp.mappings");
    params.append("--prefixes").append(sdkDir + "/lib/apple/resources/raw/pkg.properties");

    if (mappingFile != null) {
      params.append("--prefixes").append(mappingFile);
    }

    params.append("-sourcepath");
    params.append(getSourcePath(project, sb));
    params.append("-d").append(out);
    params.append(jarFile.getAbsolutePath());

    return params;
  }

  static String getSourcePath(IProject p, StringBuilder sb) {
    try {
      sb.setLength(0);

      IJavaProject      javaProject      = JavaCore.create(p);
      IClasspathEntry[] classpathEntries = null;

      classpathEntries = javaProject.getResolvedClasspath(true);

      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

      for (int i = 0; i < classpathEntries.length; i++) {
        IClasspathEntry entry = classpathEntries[i];

        if (entry.getContentKind() == IPackageFragmentRoot.K_SOURCE) {
          IPath     path = entry.getPath();
          IResource res  = root.findMember(path);

          if (res != null) {
            String srcPath = res.getLocation().toOSString();

            sb.append(srcPath).append(File.pathSeparator);
          }
        }
      }

      if (sb.charAt(sb.length() - 1) == File.pathSeparatorChar) {
        sb.setLength(sb.length() - 1);

        return sb.toString();
      }
    } catch(Exception ignore) {
      ignore.printStackTrace();
    }

    return "";
  }

  protected final void updateGenerationInfo(PrintWriter pw) {
    additions.clear();
    deletions.clear();
    changes.clear();

    File f = new File(projectDirFile, "transpiler.properties");

    if (f.exists() && (f.lastModified() > lastModifiedTP)) {
      try {
        generatedSourceDirFile = null;

        long       lm = f.lastModified();
        Properties p  = new Properties();

        p.load(new FileInputStream(f));

        String od = p.getProperty("outputDirectory");

        f = (od == null)
            ? null
            : new File(projectDir, od);

        if ((f == null) || (!f.exists() &&!f.mkdirs())) {
          if (f == null) {
            pw.println("Error: outputDirectory property missing in transpiler.properties!");
          } else {
            pw.println("Error: " + f.getAbsolutePath() + " does not exist/could not be created!");
          }

          return;
        }

        generatedSourceDir = od;

        String  sf = p.getProperty("includeSourceFileName");
        boolean gi = "true".equals(p.getProperty("generateIncludeSourceFile"));

        if (gi) {
          if (sf == null) {
            pw.println("Error: includeSourceFileName property missing in transpiler.properties!");

            return;
          }

          includeFile = new File(f.getParent(), sf);
        } else {
          includeFile = null;
        }

        File mf = new File(projectDirFile, "appnativa_user_pkg.properties");

        mappingFile            = mf.exists()
                                 ? mf.getAbsolutePath()
                                 : null;
        showStdOut             = "true".equals(p.getProperty("verbose"));
        generatedSourceDirFile = f;
        includePathPrefix      = f.getName();
        lastModifiedTP         = lm;
      } catch(Exception e) {
        pw.println(e.toString());
      }
    }
  }

  protected void updateIncludeFile(PrintWriter pw) {
    BufferedReader br = null;
    FileWriter     w  = null;

    try {
      TreeSet<String> oset = null;
      TreeSet<String> set  = new TreeSet<String>();

      if (includeFile.exists()) {
        FileReader r = new FileReader(includeFile);

        br = new BufferedReader(r);

        String line;

        while((line = br.readLine()) != null) {
          line = line.trim();

          if (line.startsWith("#include") || line.startsWith("#import")) {
            set.add(Functions.piece(line, "\"", 2).trim());
          }
        }

        br.close();
        br = null;
      }

      if (fullBuild) {
        oset = set;
        set  = new TreeSet<String>();
        set.addAll(additions);
        oset.removeAll(additions);
        deleteSet(oset);
      } else {
        set.removeAll(deletions);
        set.addAll(additions);
      }

      w = new FileWriter(includeFile, false);
      w.write("//Auto-generated by appNativa Studio\n");
      w.write("//Gets overwritten during the generation process\n");
      w.write("//It is expected to be one level above the\n");
      w.write("//transpiled directory\n");
      w.write("/////////////////////////////////////////////////\n");

      for (String s : set) {
        w.write("#include \"");
        w.write(s);
        w.write("\"\n");
      }

      w.close();
      w = null;
    } catch(Exception e) {
      if (br != null) {
        try {
          br.close();
        } catch(IOException ignore) {}
      }

      if (w != null) {
        try {
          w.close();
        } catch(IOException ignore) {}
      }

      pw.println("Cound not update include source file!");
      pw.println(e.toString());
    }
  }

  private void deleteSet(TreeSet<String> set) {
    try {
      for (String s : set) {
        File f = new File(generatedSourceDirFile, s);

        if (f.exists()) {
          f.delete();
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isProcessFinished(Process p, IProgressMonitor monitor) {
    if (monitor.isCanceled()) {
      p.destroy();

      return true;
    }

    try {
      p.exitValue();

      return true;
    } catch(IllegalThreadStateException e) {    //still running
    }

    try {
      Thread.sleep(200);
    } catch(InterruptedException e) {}

    try {
      p.exitValue();

      return true;
    } catch(IllegalThreadStateException e) {    //still running
    }

    return false;
  }

  static class ParamList {
    ArrayList<String> list = new ArrayList<String>();

    public ParamList append(String param) {
      list.add(param);

      return this;
    }

    public String toString() {
      return Helper.toString(list, " ");
    }

    public String[] getParams() {
      return list.toArray(new String[list.size()]);
    }
  }
}
