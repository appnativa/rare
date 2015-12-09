/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare;

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.spot.CollapsibleInfo;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.Plot;
import com.appnativa.rare.spot.Region;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.spot.TemplateContext;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SDFNode;
import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.spot.iSPOTTemplateHandler;
import com.appnativa.util.Helper;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.iStructuredNode;

import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles the templating of SPOT sequences
 *
 * @author Don DeCoteau
 */
public class TemplateHandler implements iSPOTTemplateHandler {
  iWidget                                                      _context;
  protected HashMap<String, URL>                               _nameMap      = new HashMap<String, URL>();
  protected IdentityHashMap<SPOTSequence, Template>            _contextMap   = new IdentityHashMap<SPOTSequence,
                                                                                 Template>();
  protected HashMap<String, SoftReference<Template>>           _templateMap  = new HashMap<String,
                                                                                 SoftReference<Template>>();
  protected IdentityArrayList<Template>                        _contextStack = new IdentityArrayList<Template>();
  protected Template                                           _contextTemplate;
  protected Template                                           _defaultTemplate;
  private boolean                                              _autoSkin;
  private URL                                                  _baseURL;
  public static Object                                         TAG = null;
  static IdentityHashMap<iPlatformAppContext, TemplateHandler> _handlerMap;
  static iPlatformAppContext                                   _defaultApp;
  static TemplateHandler                                       _defaultHandler;

  @Override
  public void applyTemplate(SPOTSequence seq, iStructuredNode node) {
    seq.spot_setTemplateHandler(this);

    SPOTPrintableString ps = createURLString(node);

    if (ps != null) {
      try {
        loadContextTemplate(seq, ps);
      } catch(MalformedURLException ex) {
        Platform.debugLog(ex.toString());

        return;
      }
    }

    iStructuredNode tnode = node.getChild("templateName");
    String          name  = (tnode == null)
                            ? null
                            : tnode.getValueAsString();

    if (name == null) {
      name = (String) node.getAttribute("baseTemplateName");
    }

    if ((name == null) && _autoSkin) {
      name = seq.spot_getClassShortName();
    }

    if (name != null) {
      String tname = (String) ((tnode == null)
                               ? node.getAttribute("templateContext")
                               : tnode.getAttribute("context"));

      applyTemplate(seq, name, tname);
    }
  }

  public void applyTemplate(SPOTSequence seq, SDFNode node) {
    seq.spot_setTemplateHandler(this);

    SPOTPrintableString ps = createURLString(node);

    if (ps != null) {
      try {
        loadContextTemplate(seq, ps);
      } catch(MalformedURLException ex) {
        Platform.debugLog(ex.toString());

        return;
      }
    }

    SDFNode tnode = node.getNode("templateName");
    String  name  = (tnode == null)
                    ? null
                    : tnode.getNodeValue();

    if (name == null) {
      name = node.getNodeAttribute("baseTemplateName");
    }

    if ((name == null) && _autoSkin) {
      name = seq.spot_getClassShortName();
    }

    if (name != null) {
      String tname = (tnode == null)
                     ? node.getNodeAttribute("templateContext")
                     : tnode.getNodeAttribute("context");

      applyTemplate(seq, name, tname);
    }
  }

  public void applyTemplate(SPOTSequence seq, String name) {
    if ((name == null) && _autoSkin) {
      name = seq.spot_getClassShortName();
    }

    if (name != null) {
      applyTemplate(seq, name, null);
    }
  }

  public void applyTemplate(SPOTSequence seq, String name, String tname) {
    Template t = _contextTemplate;

    if (tname != null) {
      if ((t == null) ||!t.nameEquals(tname)) {
        t = getTemplate(tname);
      }
    } else {
      if (t == null) {
        t = _defaultTemplate;
      }
    }

    if (t == null) {
      return;
    }

    TemplateSequence ts = null;

    if (seq instanceof Widget) {
      ts = t.getWidget(name);
    } else if (seq instanceof ItemDescription) {
      ts = t.getColumn(name);
    } else if (seq instanceof DataItem) {
      ts = t.getDataItem(name);
    } else if (seq instanceof Region) {    // do before grid cell
      ts = t.getRegion(name);
    } else if (seq instanceof GridCell) {
      ts = t.getCellPainter(name);
    } else if (seq instanceof ScrollPane) {
      ts = t.getScrollPane(name);
    } else if (seq instanceof CollapsibleInfo) {
      ts = t.getCollapsible(name);
    } else if (seq instanceof Plot) {
      ts = t.getPlot(name);
    }

    if (ts != null) {
      ts.populate(seq);
    }
  }

  public Template getContextTemplate() {
    return _contextTemplate;
  }

  public Template getTemplate(String name) {
    URL u = _nameMap.get(name);

    if (u == null) {
      return null;
    }

    SoftReference<Template> r = _templateMap.get(name);
    Template                t = (r == null)
                                ? null
                                : r.get();

    if (t == null) {
      _nameMap.remove(name);
      _templateMap.remove(name);
      t = loadTemplate(Platform.getContextRootViewer(), u, true, null);
    }

    return t;
  }

  public Template loadTemplate(iWidget context, ActionLink link, boolean cache, String name) {
    try {
      return loadTemplate(context, link.getURL(context), cache, name);
    } catch(MalformedURLException ex) {
      Platform.ignoreException(Platform.getResourceAsString("Rare.runtime.text.templateLoadFailure"), ex);

      return null;
    }
  }

  public Template loadTemplate(iWidget context, URL url, boolean cache, String name) {
    iURLConnection conn = null;

    try {
      TemplateContext tc      = new TemplateContext();
      boolean         urlName = false;

      DataParser.loadSPOTObject(context, conn = context.getAppContext().openConnection(url), tc);

      Template t = new Template(this, tc, getContext());

      if (cache) {
        if (name == null) {
          name = tc.name.getValue();
        }

        if ((name == null) || (name.length() == 0)) {
          name    = JavaURLConnection.toInternalForm(url);
          urlName = true;
        }

        URL ou = _nameMap.get(name);

        if ((ou != null) &&!urlName) {
          String s = Platform.getResourceAsString("Rare.runtime.text.replacingTemplate");

          s = Helper.expandString(s, name + " (" + ou.toExternalForm() + ")", name + " (" + url.toExternalForm() + ")");
          Platform.debugLog(s);
        }

        _nameMap.put(name, url);
        _templateMap.put(name, new SoftReference(t));
      }

      return t;
    } catch(Exception ex) {
      Platform.ignoreException("failed to load template", ex);

      return null;
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

  @Override
  public void popContextTemplate(SPOTSequence seq) {
    final Template t = _contextMap.remove(seq);

    if (t != null) {
      _contextTemplate = null;

      int len = _contextStack.size();

      if (len > 0) {
        _contextStack.remove(len - 1);
      }

      if (len > 1) {
        _contextTemplate = _contextStack.get(len - 2);
      }

      if (t.getCustomizer() != null) {
        t.getCustomizer().customize(t, seq);
      }
    }

    if (_contextTemplate != null) {
      _autoSkin = _contextTemplate.autoSkin;
    } else if (_defaultTemplate != null) {
      _autoSkin = _defaultTemplate.autoSkin;
    }
  }

  public void reset(SPOTSequence seq, SPOTPrintableString ps) throws MalformedURLException {
    _autoSkin = false;
    _contextStack.clear();
    _contextMap.clear();
    _defaultTemplate = null;
    _contextTemplate = null;

    if ((ps != null) && (ps.getValue() != null)) {
      loadContextTemplate(seq, ps);
    }
  }

  public void setContext(iWidget context) {
    _context = context;
  }

  private iWidget getContext() {
    return (_context != null)
           ? _context
           : Platform.getContextRootViewer();
  }

  private void setDefaultTemplateEx(iWidget context, String url, boolean cache) throws MalformedURLException {
    ActionLink link = new ActionLink(context, context.getURL(url));

    _autoSkin = false;
    _contextStack.clear();
    _contextMap.clear();
    _defaultTemplate = null;
    _contextTemplate = null;

    Template t = loadTemplate(context, link, cache, null);

    if (t != null) {
      _autoSkin        = t.autoSkin;
      _contextTemplate = t;
      _defaultTemplate = t;
    }
  }

  SPOTPrintableString createURLString(iStructuredNode node) {
    try {
      boolean cache = "mainWindow".equals(node.getName());

      node = node.getChild("templateURL");

      if ((node == null) || (node.getValue() == null)) {
        return null;
      }

      SPOTPrintableString ps = new SPOTPrintableString();

      ps.fromStructuredNode(node);

      if (cache) {
        ps.spot_setAttribute("cache", "true");
      }

      return ps;
    } catch(Exception ex) {
      return null;
    }
  }

  Template loadContextTemplate(SPOTSequence seq, SPOTPrintableString ps) throws MalformedURLException {
    iWidget    context = getContext();
    ActionLink link    = ActionLink.getActionLink(getContext(), ps, 0);

    if ((_baseURL != null) &&!link.isInlineURL() && (ps.getValue() != null)) {
      String url = ps.getValue();

      if (url.indexOf(':') == -1) {
        url = context.expandString(url, false);

        URL u = context.getAppContext().createURL(_baseURL, url);

        link.setURL(u);
      }
    }

    boolean  cache = "true".equals(ps.spot_getAttribute("cache"));
    Template t     = loadTemplate(context, link, cache, null);

    if (t != null) {
      _autoSkin        = t.autoSkin;
      _contextTemplate = t;

      if (seq instanceof MainWindow) {
        _defaultTemplate = t;
      } else if (!cache) {
        _contextStack.add(t);
        _contextMap.put(seq, t);
      }
    }

    return t;
  }

  public static void disposing(iWidget w) {
    iPlatformAppContext app = w.getAppContext();

    if (app == null) {
      app = Platform.getAppContext();
    }

    if (app != null) {
      TemplateHandler th = getHandler(app);

      if ((th != null) && (th._context == w)) {
        th._context = app.getRootViewer();
      }
    }
  }

  public static TemplateSequence getDefault(iPlatformAppContext app, String name) {
    if (app == null) {
      app = Platform.getAppContext();
    }

    TemplateHandler th = getHandler(app);
    Template        t  = (th == null)
                         ? null
                         : th._defaultTemplate;

    return (t == null)
           ? null
           : t.getWidget(name);
  }

  public static TemplateHandler getInstance(iPlatformAppContext app) {
    return getTemplateHandler(app.getRootViewer());
  }

  public static TemplateHandler getInstance(iWidget context, URL base) {
    TemplateHandler th = getTemplateHandler(context);

    if (th == null) {
      th = new TemplateHandler();
      setTemplateHandler(context, th);
    }

    th._context = context;
    th._baseURL = base;

    return th;
  }

  public static void setDefaultTemplate(iPlatformAppContext app, String url, boolean autoSkin)
          throws MalformedURLException {
    getTemplateHandler(app.getRootViewer()).setDefaultTemplateEx(app.getRootViewer(), url, autoSkin);
  }

  public static void setInstance(iPlatformAppContext app, TemplateHandler th) {
    if ((_defaultApp == null) || (_defaultApp == app)) {
      _defaultApp     = app;
      _defaultHandler = th;
    } else {
      if (_handlerMap == null) {
        _handlerMap = new IdentityHashMap<iPlatformAppContext, TemplateHandler>(2);
        _handlerMap.put(_defaultApp, _defaultHandler);
      }

      _handlerMap.put(app, th);
    }
  }

  private static TemplateHandler getHandler(iPlatformAppContext app) {
    if (app == _defaultApp) {
      return _defaultHandler;
    }

    return (_handlerMap == null)
           ? null
           : _handlerMap.get(app);
  }

  private static TemplateHandler getTemplateHandler(iWidget context) {
    iPlatformAppContext app = (context != null)
                              ? context.getAppContext()
                              : Platform.getAppContext();
    iResourceFinder     rf  = app.getResourceFinder();

    return (rf == null)
           ? getHandler(app)
           : rf.getTemplateHandler(context);
  }

  private static void setTemplateHandler(iWidget context, TemplateHandler th) {
    iPlatformAppContext app = (context != null)
                              ? context.getAppContext()
                              : Platform.getAppContext();
    iResourceFinder     rf  = app.getResourceFinder();

    if (rf != null) {
      rf.setTemplateHandler(context, th);
    } else {
      setInstance(app, th);
    }
  }

  public static interface iCustomizer {
    void customize(Template t, SPOTSequence seq);
  }


  public static class Template {
    final boolean                 autoSkin;
    SPOTSet                       cellPainters;
    Map<String, TemplateSequence> collapsibleMap;
    SPOTSet                       collapsibles;
    SPOTSet                       columns;
    Map<String, TemplateSequence> columnsMap;
    SPOTSet                       dataItems;
    Map<String, TemplateSequence> dataItemsMap;
    TemplateHandler               handler;
    Map<String, TemplateSequence> paintersMap;
    Map<String, TemplateSequence> plotMap;
    SPOTSet                       plots;
    Map<String, TemplateSequence> regionMap;
    SPOTSet                       regions;
    Map<String, TemplateSequence> scrollMap;
    SPOTSet                       scrollPanes;
    Map<String, TemplateSequence> widgetMap;
    private iCustomizer           customizer;
    private Object                linkedData;
    private final String          name;

    public Template(TemplateHandler th, TemplateContext t, iWidget context) {
      handler      = th;
      columns      = DataParser.resolveSet(context, t.columns, ItemDescription.class);
      cellPainters = DataParser.resolveSet(context, t.cellPainters, GridCell.class);
      dataItems    = DataParser.resolveSet(context, t.dataItems, DataItem.class);
      collapsibles = DataParser.resolveSet(context, t.collapsibles, CollapsibleInfo.class);
      plots        = DataParser.resolveSet(context, t.plots, Plot.class);
      regions      = DataParser.resolveSet(context, t.regions, Region.class);
      scrollPanes  = DataParser.resolveSet(context, t.scrollPanes, ScrollPane.class);
      autoSkin     = t.autoSkin.booleanValue();
      name         = t.name.getValue();
      widgetMap    = createWidgetMap(context, DataParser.resolveSet(context, t.widgets, Widget.class));
    }

    public TemplateSequence getCellPainter(String name) {
      if (paintersMap == null) {
        paintersMap  = createMap(cellPainters);
        cellPainters = null;
      }

      return paintersMap.get(name);
    }

    public TemplateSequence getCollapsible(String name) {
      if (collapsibleMap == null) {
        collapsibleMap = createMap(collapsibles);
        collapsibles   = null;
      }

      return collapsibleMap.get(name);
    }

    public TemplateSequence getColumn(String name) {
      if (columnsMap == null) {
        columnsMap = createMap(columns);
        columns    = null;
      }

      return columnsMap.get(name);
    }

    /**
     * Gets the customizer that will be called after the object has been created
     * and populated
     *
     * @return the customizer
     */
    public iCustomizer getCustomizer() {
      return customizer;
    }

    public TemplateSequence getDataItem(String name) {
      if (dataItemsMap == null) {
        dataItemsMap = createMap(dataItems);
        dataItems    = null;
      }

      return dataItemsMap.get(name);
    }

    /**
     * @return the linkedData
     */
    public Object getLinkedData() {
      return linkedData;
    }

    public TemplateSequence getPlot(String name) {
      if (plotMap == null) {
        plotMap = createMap(plots);
        plots   = null;
      }

      return plotMap.get(name);
    }

    public TemplateSequence getRegion(String name) {
      if (regionMap == null) {
        regionMap = createMap(regions);
        regions   = null;
      }

      return regionMap.get(name);
    }

    public TemplateSequence getScrollPane(String name) {
      if (scrollMap == null) {
        scrollMap   = createMap(scrollPanes);
        scrollPanes = null;
      }

      return scrollMap.get(name);
    }

    public TemplateSequence getWidget(String name) {
      return widgetMap.get(name);
    }

    /**
     * Sets a customizer to call after the object has been created and populated
     *
     * @param customizer
     *          the customizer to set
     */
    public void setCustomizer(iCustomizer customizer) {
      this.customizer = customizer;
    }

    /**
     * @param linkedData
     *          the linkedData to set
     */
    public void setLinkedData(Object linkedData) {
      this.linkedData = linkedData;
    }

    private boolean nameEquals(String s) {
      return (name == null)
             ? false
             : name.equals(s);
    }

    Map<String, TemplateSequence> createMap(SPOTSet set) {
      int                 len = set.size();
      Map                 map = new HashMap<String, TemplateSequence>(len);
      SPOTSequence        c;
      String              s;
      iPlatformAppContext app = Platform.getAppContext();

      for (int i = len - 1; i > -1; i--) {
        c = (SPOTSequence) set.getEx(i);
        s = c.spot_getAttribute("os");

        if (!app.okForOS(c)) {
          set.remove(i);

          continue;
        }

        s = c.spot_elementFor("templateName").spot_stringValue();

        if (s != null) {
          map.put(s, new TemplateSequence(c));
        }
      }

      return map;
    }

    Map<String, TemplateSequence> createWidgetMap(iWidget context, SPOTSet set) {
      int                 len = set.size();
      Map                 map = new HashMap<String, TemplateSequence>(len);
      SPOTSequence        c;
      String              s;
      SPOTAny             any;
      String              tc;
      String              tn;
      iPlatformAppContext app = Platform.getAppContext();

      for (int i = len - 1; i > -1; i--) {
        c   = null;
        any = (SPOTAny) set.get(i);

        if (!app.okForOS(any)) {
          set.remove(i);

          continue;
        }

        s = any.spot_getAttribute("url");

        if ((s != null) && (s.length() > 0)) {
          try {
            URL url = context.getURL(s);

            c = (SPOTSequence) DataParser.loadSPOTObject(context, context.getAppContext().openConnection(url), null);
          } catch(Exception ex) {
            Platform.ignoreException("Failed to load template widget from url", ex);

            continue;
          }
        }

        if (c == null) {
          c = (SPOTSequence) any.getValue();
        }

        TemplateSequence ts = null;

        tn = any.spot_getAttribute("baseTemplateName");
        tc = any.spot_getAttribute("templateContext");

        if (tn != null) {
          if (tn.equals("__base__")) {
            tn = c.spot_getClassShortName();
          }

          if (tc != null) {
            Template t = handler.getTemplate(tc);

            ts = (t == null)
                 ? null
                 : t.getWidget(tn);
          } else {
            ts = (TemplateSequence) map.get(tn);

            if ((ts == null) && (handler._contextTemplate != null)) {
              ts = handler._contextTemplate.getWidget(tn);
            }
          }
        }

        s = c.spot_elementFor("templateName").spot_stringValue();

        if (s == null) {
          s = c.spot_getClassShortName();
        }

        if (ts != null) {
          map.put(s, new TemplateSequence(ts, c));
        } else {
          map.put(s, new TemplateSequence(c));
        }
      }

      return map;
    }
  }


  public static class TemplateSequence {
    ArrayList<iSPOTElement> elements = new ArrayList<iSPOTElement>();
    Map<String, String>     attributes;

    TemplateSequence(SPOTSequence seq) {
      seq.spot_getValuesThatWereSet(elements);
      attributes = seq.spot_getAttributesEx();
    }

    TemplateSequence(TemplateSequence base, SPOTSequence seq) {
      elements.addAll(base.elements);
      seq.spot_getValuesThatWereSet(elements);
      attributes = seq.spot_getAttributesEx();

      if (base.attributes != null) {
        if (attributes == null) {
          attributes = new LinkedHashMap<String, String>();
        }

        attributes.putAll(base.attributes);
      }
    }

    public void addAttribute(String name, String value) {
      attributes.put(name, value);
    }

    public void addElement(iSPOTElement e) {
      elements.add(e);
    }

    public void clear() {
      elements.clear();

      if (attributes != null) {
        attributes.clear();
      }
    }

    /**
     * Copies the member values of the specified list that have the same names
     * as members of this sequence
     *
     * @param list
     *          The list to copy from
     */
    public void spot_copySharedMemberValues(SPOTSequence seq, List list) {
      int          n    = (list == null)
                          ? 0
                          : list.size();
      String       name = null;
      iSPOTElement ti   = null;
      iSPOTElement ti2  = null;

      for (int i = 0; i < n; i++) {
        ti = (iSPOTElement) list.get(i);

        if (ti != null) {
          name = ti.spot_getName();
          ti2  = seq.spot_elementFor(name);

          if (ti2 != null) {
            ti2.spot_copy(ti, true);

            if (TAG != null) {
              ti2.spot_setLinkedData(TAG);
            }
          } else if (seq.spot_hasNamedElement(name)) {
            ti2 = (iSPOTElement) ti.clone();
            seq.spot_setReferenceVariable(name, ti2);

            if (TAG != null) {
              ti2.spot_setLinkedData(TAG);
            }
          }
        }
      }
    }

    void populate(SPOTSequence seq) {
      spot_copySharedMemberValues(seq, elements);

      if (attributes != null) {
        seq.spot_addAttributes(attributes);
      }
    }
  }


  public static boolean hasWidgetTemplate(String name) {
    Template t = getInstance(Platform.getAppContext()).getContextTemplate();;
    return t==null ? null : t.getWidget(name)!=null;
  }
}
