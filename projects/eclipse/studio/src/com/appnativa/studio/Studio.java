/*
 * @(#)Studio.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.osgi.framework.BundleContext;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.scripting.ScriptManager;
import com.appnativa.rare.spot.NameValuePair;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;
import com.appnativa.studio.builder.ProjectNature;
import com.appnativa.studio.editors.MultiPageEditor;
import com.appnativa.studio.properties.PropertiesConstants;
import com.appnativa.util.CharArray;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.Streams;

public class Studio {
  public static ResourceBundle              rareResourceBundle;
  public static ResourceBundle              resources;
  static UIImagePainter                     checkeredImagePainter;
  private static final String               BUNDLE_NAME    = "com.appnativa.studio.resource_strings";         //$NON-NLS-1$
  private static Map<String, Image>         images         = new ConcurrentHashMap<String, Image>(134);
  private static Map<String, BufferedImage> bufferedImages = new ConcurrentHashMap<String, BufferedImage>(24);
  private static Map<String, Object>        attributes     = new HashMap<String, Object>();
  private static Studio                     _instance;
  private static List<String>               fontNames;

  static {
    try {
      resources = ResourceBundle.getBundle(BUNDLE_NAME);
    } catch (MissingResourceException mre) {
    }

    URL url;

    try {
      url = Platform.class.getResource("/com/appnativa/rare/resources/raw/rare_raw_bundle.properties");

      InputStream inputStream = getInputStreamForLanguageSpecificfile(url);

      rareResourceBundle = new PropertyResourceBundle(inputStream);
      MainEmbedded.setResources(rareResourceBundle);
      ImageHelper.setCacheImages(true, 100);
      inputStream.close();
    } catch (Exception ignore) {
    }
    try {
      url = Platform.class.getResource("/com/appnativa/rare/resources/raw/rare_raw_ecmascript.init");

      InputStream inputStream = url.openStream();
      ScriptManager.jsInitCode = Streams.streamToString(inputStream);
    } catch (Exception ignore) {
    }
  }

  public Studio(BundleContext context) throws Exception {
    System.setProperty("rare.debug", "true");
    System.setProperty("sun.awt.noerasebackground", "true");
    System.setProperty("java.awt.syncLWRequests", "true");
    System.setProperty("spot.optimize", "false");
    final URL u = Activator.class.getResource("attributes.rml");

    try {
      SPOTSet attributeSet = Utilities.loadSPOTSet(u, NameValuePair.class);

      nameValuePairSetToMap(attributeSet, attributes, u);
    } catch (Exception e) {
      e.printStackTrace();
    }

    ConsoleManager.getDefault();
    loadIcons(context);

    Runnable r = new Runnable() {
      @Override
      public void run() {
        try {
          fontNames = PlatformHelper.getAvailableFontNames();
        } catch (Exception e) {
          e.printStackTrace();
          fontNames = Arrays.asList(Studio.getResourceAsString("Studio.text.noFonts"));
        }
      }
    };

    new Thread(r).start();

    try {
      Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    } catch (SecurityException e) {
    }

    final Display display = Display.getDefault();
    final Runnable timer = new Runnable() {
      @Override
      public void run() {
        Shell shell = display.getActiveShell();

        if (shell == null) {
          display.timerExec(200, this);
        } else {
          FontData fd[] = shell.getFont().getFontData();
          UIFont f = new UIFont(fd[0].getName(), UIFont.PLAIN, fd[0].getHeight());

          FontUtils.setSystemFont(f);
          createMainEmbedded(u);
        }
      }
    };
    display.asyncExec(new Runnable() {

      @Override
      public void run() {
        display.timerExec(200, timer);
      }
    });
  }

  public static void alert(String message) {
    alert(getResourceAsString("Studio.title"), message);
  }

  public static void alert(String title, String message) {
    MessageDialog.openInformation(Display.getDefault().getActiveShell(), title, message);
  }

  public static boolean confirm(String title, String message) {
    return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), title, message);
  }

  public static void dispose() {
  }

  public static void globalPreferencesChanged() {
    List<RMLDocument> list = getActiveDocuments();

    if (list != null) {
      for (RMLDocument doc : list) {
        doc.globalPreferencesChanged();
      }
    }
  }

  public static Map nameValuePairSetToMap(SPOTSet set, Map map, URL base) {
    int len = set.getCount();

    if (len == 0) {
      return null;
    }

    if (map == null) {
      map = new HashMap();
    }

    NameValuePair p;
    String s;
    Object o;
    CharArray reader = new CharArray();

    for (int i = 0; i < len; i++) {
      p = (NameValuePair) set.get(i);
      s = p.getValue();
      o = s;

      try {
        reader.set(s);
        o = Utilities.loadSPOTObject(reader, base, null);
      } catch (Exception ex) {
        Platform.ignoreException(null, ex);
      }

      map.put(p.getName(), o);
    }

    return map;
  }

  public static void openWizard(String id, Shell shell) {
    // First see if this is a "new wizard".
    IWizardDescriptor descriptor = PlatformUI.getWorkbench().getNewWizardRegistry().findWizard(id);

    // If not check if it is an "import wizard".
    if (descriptor == null) {
      descriptor = PlatformUI.getWorkbench().getImportWizardRegistry().findWizard(id);
    }

    // Or maybe an export wizard
    if (descriptor == null) {
      descriptor = PlatformUI.getWorkbench().getExportWizardRegistry().findWizard(id);
    }

    try {
      // Then if we have a wizard, open it.
      if (descriptor != null) {
        IWizard wizard = descriptor.createWizard();
        WizardDialog wd = new WizardDialog(shell, wizard);

        wd.setTitle(wizard.getWindowTitle());
        wd.open();
      }
    } catch (CoreException e) {
      e.printStackTrace();
    }
  }

  public static String prompt(String prompt) {
    return prompt(getResourceAsString("Studio.title"), prompt, "");
  }

  public static String prompt(String prompt, String value) {
    return prompt(getResourceAsString("Studio.title"), "Value", "");
  }

  public static String prompt(String title, String prompt, String value) {
    InputDialog d = new InputDialog(Display.getDefault().getActiveShell(), title, prompt, value, null);

    return d.getValue();
  }

  /**
   * Runs the runnable in the SWT thread. (Simply runs the runnable if the
   * current thread is the UI thread, otherwise calls the runnable in
   * asyncexec.)
   */
  public static void runInSWTThread(Runnable runnable) {
    if (Display.getCurrent() == null) {
      Display.getDefault().asyncExec(runnable);
    } else {
      runnable.run();
    }
  }

  public static void runInSwingThread(Runnable runnable) {
    if (SwingUtilities.isEventDispatchThread()) {
      runnable.run();
    } else {
      SwingUtilities.invokeLater(runnable);
    }
  }

  public void resourceChanged(IResourceChangeEvent event) {
    // we are only interested in POST_CHANGE events
    if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
      return;
    }

    IResourceDelta delta = event.getDelta();
    final ArrayList<IResource> changed = new ArrayList<IResource>();
    IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
      public boolean visit(IResourceDelta delta) {
        // only interested in changed resources (not added or removed)
        if ((delta.getKind() != IResourceDelta.CHANGED)) {
          return true;
        }

        IResource resource = delta.getResource();
        if (resource instanceof IFile) {
          IProject p = resource.getProject();

          try {
            if ((p != null) && p.hasNature(ProjectNature.NATURE_ID)) {
              changed.add(resource);
            }
          } catch (CoreException ignore) {
          }
        }
        return true;
      }
    };

    try {
      delta.accept(visitor);
    } catch (CoreException e) {
      e.printStackTrace();
    }

    if (!changed.isEmpty()) {
      for (IResource r : changed) {
        Project pp = Project.getProject(r, false);

        if ((pp != null) && pp.isReloadingResource(r)) {
          pp.projectChanged();
        }
      }
    }
  }

  public static void showError(final Throwable err) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        MessageDialog.openError(Display.getDefault().getActiveShell(), getResourceAsString("Studio.title"), err.toString());
      }
    };
    if(err instanceof NullPointerException) {
      err.printStackTrace();
    }
    runInSWTThread(r);
  }

  public static void startInstance(BundleContext context) throws Exception {
    if (_instance == null) {
      _instance = new Studio(context);
    }
  }

  public static void stopInstance(BundleContext context) throws Exception {
  }

  public static boolean yesNo(String message) {
    return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), getResourceAsString("Studio.title"), message);
  }

  public static Boolean yesNoCancel(String message) {
    int ret = MessageDialogEx.openEx(MessageDialog.QUESTION_WITH_CANCEL, Display.getDefault().getActiveShell(),
        getResourceAsString("Studio.title"), message, SWT.NONE);

    if (ret == 2) {
      return null;
    }

    return ret == 0;
  }

  public static List<RMLDocument> getActiveDocuments() {
    IWorkbenchPage[] a = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();

    if ((a == null) || (a.length == 0)) {
      return null;
    }

    List<RMLDocument> list = new ArrayList<RMLDocument>(a.length);

    for (IWorkbenchPage p : a) {
      IEditorPart ep = p.getActiveEditor();

      if (ep instanceof MultiPageEditor) {
        list.add(((MultiPageEditor) ep).getRMLDocument());
      }

      return list.isEmpty() ? null : list;
    }

    return null;
  }

  public static DesignPane getActiveDesignPane() {
    RMLDocument doc=getActiveDocument();
    return doc==null ? null : doc.getDesignPane();
  }
  
  public static RMLDocument getActiveDocument() {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    IEditorPart ep = page == null ? null : page.getActiveEditor();

    if (ep instanceof MultiPageEditor) {
      return ((MultiPageEditor) ep).getRMLDocument();
    }

    return null;
  }

  public static Object getAttribute(String id) {
    return attributes.get(id);
  }

  public static UIImagePainter getCheckeredImagePainter() {
    if (checkeredImagePainter == null) {
      checkeredImagePainter = new UIImagePainter(new UIImage(getResourceAsBufferedImage("Studio.icon.checkeredBackground")));
      checkeredImagePainter.setImageAlpha(64.0f / 255f);
    }

    return checkeredImagePainter;
  }

  public static UIColor getColor(org.eclipse.swt.graphics.Color c) {
    return new UIColor(c.getRed(), c.getGreen(), c.getBlue());
  }

  public static IProject findSwingProject() throws Exception {
    String swingS = null;
    IProject swing = null;
    IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    for (IProject project : projects) {
      if (project.hasNature(ProjectNature.NATURE_ID)) {
        String s = project.getPersistentProperty(PropertiesConstants.SWING_PROJECT_TAG);
        if (s != null && s.equals("it")) {
          swing = project;
          break;
        }
        if (swingS == null) {
          s = project.getPersistentProperty(PropertiesConstants.SWING_PROJECT);
          if (s != null && s.length() > 0) {
            swingS = s;
          }
        }
      }
    }
    if (swing == null && swingS != null) {
      IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(swingS);
      if (p != null && p.exists()) {
        swing = p;
      }
    }
    return swing;
  }

  public static iWidget getDesignContext() {
    IWorkbench wb = PlatformUI.getWorkbench();
    IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    IWorkbenchPage page = win.getActivePage();

    if (page.getActivePart() instanceof MultiPageEditor) {
      System.out.println("Found part)");
    }

    return Platform.getWindowViewer();
  }

  public static List<String> getFontNames() {
    return fontNames;
  }

  public static InputStream getInputStreamForLanguageSpecificfile(URL resource) throws IOException {
    InputStream inputStream = null;

    try {
      URL fileUrl = FileLocator.toFileURL(resource);
      String s = fileUrl.getFile();
      int n = s.lastIndexOf('.');

      inputStream = Utils.getLocaleSpecificFileInputStream(s.substring(0, n), "properties");
    } catch (Exception e) {
    }

    if (inputStream == null) {
      inputStream = resource.openStream();
    }

    return inputStream;
  }

  public static Studio getInstance() {
    return _instance;
  }

  public static BufferedImage getResourceAsBufferedImage(String name) {
    return (name == null) ? null : bufferedImages.get(name);
  }

  public static Image getResourceAsIcon(String name) {
    return (name == null) ? null : images.get(name);
  }

  public static String getResourceAsString(String name) {
    if (name.startsWith("Studio.")) {
      return (resources == null) ? null : resources.getString(name);
    }

    return AppContext.getContext().getResourceAsString(name);
  }

  public static RMLDocument getSelectedDocument() {
    IWorkbench w = PlatformUI.getWorkbench();
    IWorkbenchWindow ww = w == null ? null : w.getActiveWorkbenchWindow();
    IWorkbenchPage p = ww == null ? null : ww.getActivePage();

    if (p != null) {
      IEditorPart ep = p.getActiveEditor();

      if (ep instanceof MultiPageEditor) {
        return ((MultiPageEditor) ep).getRMLDocument();
      }
    }

    return null;
  }

  public static Image getWidgetIcon(String type) {
    String name = getWidgetIconName(type);

    if (name != null) {
      return images.get(name);
    }

    return null;
  }

  // private static BufferedImage convertToAWT(ImageData data) {
  // ColorModel colorModel = null;
  // PaletteData palette = data.palette;
  //
  // if (palette.isDirect) {
  // colorModel = new DirectColorModel(data.depth, palette.redMask,
  // palette.greenMask, palette.blueMask);
  //
  // BufferedImage bufferedImage = new BufferedImage(colorModel,
  // colorModel.createCompatibleWritableRaster(data.width, data.height), false,
  // null);
  // WritableRaster raster = bufferedImage.getRaster();
  // int[] pixelArray = new int[3];
  //
  // for (int y = 0; y < data.height; y++) {
  // for (int x = 0; x < data.width; x++) {
  // int pixel = data.getPixel(x, y);
  // RGB rgb = palette.getRGB(pixel);
  //
  // pixelArray[0] = rgb.red;
  // pixelArray[1] = rgb.green;
  // pixelArray[2] = rgb.blue;
  // raster.setPixels(x, y, 1, 1, pixelArray);
  // }
  // }
  //
  // return bufferedImage;
  // } else {
  // RGB[] rgbs = palette.getRGBs();
  // byte[] red = new byte[rgbs.length];
  // byte[] green = new byte[rgbs.length];
  // byte[] blue = new byte[rgbs.length];
  //
  // for (int i = 0; i < rgbs.length; i++) {
  // RGB rgb = rgbs[i];
  //
  // red[i] = (byte) rgb.red;
  // green[i] = (byte) rgb.green;
  // blue[i] = (byte) rgb.blue;
  // }
  //
  // if (data.transparentPixel != -1) {
  // colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue,
  // data.transparentPixel);
  // } else {
  // colorModel = new IndexColorModel(data.depth, rgbs.length, red, green,
  // blue);
  // }
  //
  // BufferedImage bufferedImage = new BufferedImage(colorModel,
  // colorModel.createCompatibleWritableRaster(data.width, data.height), false,
  // null);
  // WritableRaster raster = bufferedImage.getRaster();
  // int[] pixelArray = new int[1];
  //
  // for (int y = 0; y < data.height; y++) {
  // for (int x = 0; x < data.width; x++) {
  // int pixel = data.getPixel(x, y);
  //
  // pixelArray[0] = pixel;
  // raster.setPixel(x, y, pixelArray);
  // }
  // }
  //
  // return bufferedImage;
  // }
  // }
  private static BufferedImage convertToAWT(ImageData data) {
    ColorModel colorModel = null;
    PaletteData palette = data.palette;

    if (palette.isDirect) {
      colorModel = new DirectColorModel(data.depth, palette.redMask, palette.greenMask, palette.blueMask);

      BufferedImage bufferedImage = new BufferedImage(colorModel,
          colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);

      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          int pixel = data.getPixel(x, y);
          RGB rgb = palette.getRGB(pixel);

          bufferedImage.setRGB(x, y, rgb.red << 16 | rgb.green << 8 | rgb.blue);
        }
      }

      return bufferedImage;
    } else {
      RGB[] rgbs = palette.getRGBs();
      byte[] red = new byte[rgbs.length];
      byte[] green = new byte[rgbs.length];
      byte[] blue = new byte[rgbs.length];

      for (int i = 0; i < rgbs.length; i++) {
        RGB rgb = rgbs[i];

        red[i] = (byte) rgb.red;
        green[i] = (byte) rgb.green;
        blue[i] = (byte) rgb.blue;
      }

      if (data.transparentPixel != -1) {
        colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue, data.transparentPixel);
      } else {
        colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue);
      }

      BufferedImage bufferedImage = new BufferedImage(colorModel,
          colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
      WritableRaster raster = bufferedImage.getRaster();
      int[] pixelArray = new int[1];

      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          int pixel = data.getPixel(x, y);

          pixelArray[0] = pixel;
          raster.setPixel(x, y, pixelArray);
        }
      }

      return bufferedImage;
    }
  }

  private static void createMainEmbedded(URL u) {
    try {
      final MainEmbedded main = new MainEmbedded(u);

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          
          main.startApplication(null);
          DataParser.DISABLE_ALL_INLINING_OF_URLS=true;
          ColorUtils.KEEP_COLOR_KEYS=Boolean.TRUE;
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("rawtypes")
  private static void loadIcons(BundleContext context) {
    try {
      InputStream in = Activator.class.getResourceAsStream("resource_icons.properties");
      OrderedProperties p = new OrderedProperties();

      p.load(in);

      Iterator it = p.keySet().iterator();

      while (it.hasNext()) {
        String key = (String) it.next();
        String path = p.getProperty(key);

        try {
          BufferedImage img = loadImage(context, path);

          bufferedImages.put(key, img);
          images.put(key, Utilities.convertToSWTImage(img));
        } catch (Exception e) {
          Image img = loadSWTImage(path);

          images.put(key, img);

          if (key.startsWith("Studio.icon.toolbox.")) {
            bufferedImages.put(key, convertToAWT(img.getImageData()));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static BufferedImage loadImage(BundleContext context, String file) throws Exception {
    Path path = new Path(file);
    URL url = FileLocator.find(context.getBundle(), path, Collections.EMPTY_MAP);
    URL fileUrl = null;

    try {
      fileUrl = FileLocator.toFileURL(url);
    } catch (IOException e) {
      e.printStackTrace();
    }

    FileInputStream in = new FileInputStream(fileUrl.getPath());
    BufferedImage img = ImageIO.read(in);

    in.close();

    return img;
  }

  private static Image loadSWTImage(String path) {
    ImageDescriptor id = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, path);

    return id.createImage();
  }

  private static String getWidgetIconName(String type) {
    iWidget.WidgetType wt = null;

    try {
      wt = iWidget.WidgetType.valueOf(type);
    } catch (Exception ignore) {
    }

    if (wt == null) {
      if (type.equals("NumberSpinner")) {
        return "Studio.icon.toolbox.number_spinner";
      }

      if (type.equals("DateSpinner")) {
        return "Studio.icon.toolbox.date_spinner";
      }

      if (type.equals("TimeSpinner")) {
        return "Studio.icon.toolbox.time_spinner";
      }

      if (type.equals("DocumentTabPane")) {
        return "Studio.icon.toolbox.document_tabpane";
      }

      if (type.equals("FloorTabPane")) {
        return "Studio.icon.toolbox.floor_tabpane";
      }

      if (type.equals("TreeTable")) {
        return "Studio.icon.toolbox.treetable";
      }

      if (type.equals("PropertyTable")) {
        return "Studio.icon.toolbox.treetable";
      }

      if (type.equals("Browser")) {
        return "Studio.icon.toolbox.browser";
      }

      return null;
    }

    switch (wt) {
      case PushButton:
        return "Studio.icon.toolbox.pushbutton";

      case CheckBox:
        return "Studio.icon.toolbox.checkbox";

      case Label:
        return "Studio.icon.toolbox.label";

      case GroupBox:
        return "Studio.icon.toolbox.groupbox";

      case RadioButton:
        return "Studio.icon.toolbox.radiobutton";

      case ComboBox:
        return "Studio.icon.toolbox.combobox";

      case ListBox:
        return "Studio.icon.toolbox.listbox";

      case CheckBoxList:
        return "Studio.icon.toolbox.checkboxlist";

      case Tree:
        return "Studio.icon.toolbox.tree";

      case CheckBoxTree:
        return "Studio.icon.toolbox.checkboxtree";

      case Spinner:
        return "Studio.icon.toolbox.list_spinner";

      case SplitPane:
        return "Studio.icon.toolbox.vsplitpane";

      case ProgressBar:
        return "Studio.icon.toolbox.progressbar";

      case MenuBar:
        return "Studio.icon.toolbox.menubar";

      case ToolBar:
        return "Studio.icon.toolbox.toolbar";

      case StatusBar:
        return "Studio.icon.toolbox.statusbar";

      case TabPane:
        return "Studio.icon.toolbox.tabpane";

      case Table:
        return "Studio.icon.toolbox.table";

      case Line:
        return "Studio.icon.toolbox.hline";

      case Slider:
        return "Studio.icon.toolbox.hslider";

      case TextField:
        return "Studio.icon.toolbox.textfield";

      case TextArea:
        return "Studio.icon.toolbox.textarea";

      case DocumentPane:
        return "Studio.icon.toolbox.documentpane";

      case CollapsiblePane:
        return "Studio.icon.toolbox.collapsiblepane";

      case GridPane:
        return "Studio.icon.toolbox.gridpane";

      case StackPane:
        return "Studio.icon.toolbox.stackpane";

      case Form:
        return "Studio.icon.toolbox.form";

      case DateChooser:
        return "Studio.icon.toolbox.date_chooser";

      case ColorChooser:
        return "Studio.icon.toolbox.color_chooser";

      case Navigator:
        return "Studio.icon.toolbox.navigator";

      case ImagePane:
        return "Studio.icon.toolbox.imagepane";

      case Chart:
        return "Studio.icon.toolbox.piechart";

      case WidgetPane:
        return "Studio.icon.toolbox.widgetpane";

      default:
        return null;
    }
  }

  public class UncaughtExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, final Throwable e) {
      e.printStackTrace();
      runInSWTThread(new Runnable() {
        @Override
        public void run() {
          Studio.showError(e);
        }
      });
    }
  }
}
