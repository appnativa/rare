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

package com.appnativa.rare.viewer;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.FormHelper;
import com.appnativa.rare.spot.Form;
import com.appnativa.rare.spot.Link;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.json.JSONWriter;

public class aFormViewer extends GroupBoxViewer {

  /** link for submitting the form */
  protected ActionLink formLink;

  /** event listener list */
  protected EventListenerList listenerList;
  protected boolean           retainInitialFieldValues;

  /** attributes to submit with the form */
  protected Map<String, Object> submitAttributes;

  /**
   * Constructs a new instance
   */
  public aFormViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aFormViewer(iContainer parent) {
    super(null);
    widgetType               = WidgetType.Form;
    retainInitialFieldValues = true;
    actAsFormViewer          = true;
  }

  public void addActionListener(iActionListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iActionListener.class, l);
  }

  @Override
  public void configure(Viewer vcfg) {
    vcfg = checkForURLConfig(vcfg);

    Form fc = (Form) vcfg;

    actAsFormViewer = fc.actAsFormViewer.booleanValue();
    configureEx(fc);
    retainInitialFieldValues = fc.retainInitialFieldValues.booleanValue();
    isSubmittable            = true;

    if (fc.submitAttributes.spot_hasValue()) {
      submitAttributes = DataParser.parseNameValuePairs(fc.submitAttributes);
    }

    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
  }

  @Override
  public HashMap getHTTPValuesHash() {
    HashMap map = super.getHTTPValuesHash();

    if (submitAttributes != null) {
      if (map == null) {
        map = new HashMap(submitAttributes);
      } else {
        map.putAll(submitAttributes);
      }
    }

    return map;
  }

  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  /**
   * Submits the form Synonym for submitForm()
   */
  public void submit() {
    submitForm(null);
  }

  @Override
  public void submitForm(iFunctionCallback cb) {
    aWidgetListener wl = this.getWidgetListener();

    if ((wl != null) && wl.isEnabled(iConstants.EVENT_SUBMIT)) {
      DataEvent e = new DataEvent(getDataComponent(), null);

      wl.evaluate(iConstants.EVENT_SUBMIT, e, false);

      if (e.isConsumed()) {
        return;
      }
    }

    ActionEvent e = new ActionEvent(getDataComponent(), "submitForm");

    if (listenerList != null) {
      Utils.fireActionEvent(listenerList, e);
    }

    if ((actionListener != null) && (cb == null) && (actionListener != formLink)) {
      actionListener.actionPerformed(e);
    } else if (formLink != null) {
      try {
        getAppContext().getWindowViewer().getContentAsString(formLink, cb);
      } catch(MalformedURLException ex) {
        Platform.ignoreException(null, ex);
      }
    }
  }

  @Override
  public void writeHTTPContent(boolean first, Writer writer, String boundary) {
    writeHTTPContent(first, writer, boundary, null);
  }

  /**
   * Writes out the value of the widget in a form compatible with the HTTP Form
   * <b>multipart/form-data</b> encoding type.
   *
   * @param first
   *          whether this is the first widget to write its value
   * @param writer
   *          the writer
   * @param boundary
   *          the multi-part content boundary
   * @param attributes
   *          additional attributes ti be
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void writeHTTPContent(boolean first, Writer writer, String boundary, Map<String, Object> attributes) {
    try {
      if (attributes != null) {
        FormHelper.writeHTTPContent(first, this, writer, boundary, attributes, true);
      }

      if (submitAttributes != null) {
        FormHelper.writeHTTPContent(first, this, writer, boundary, submitAttributes, true);
      }

      int     len = widgetList.size();
      iWidget a;

      for (int i = 0; i < len; i++) {
        a = widgetList.get(i);

        if (a.isSubmittable() && a.isValidForSubmission(true)) {
          a.writeHTTPContent(first, writer, boundary);
        }
      }
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public boolean writeHTTPValue(boolean first, Writer writer) {
    return writeHTTPValue(first, writer, null);
  }

  /**
   * Writes out the value of the widget in a form compatible with the HTTP Forms
   * <b>application/x-www-form-urlencoded</b> encoding type.
   *
   * @param first
   *          whether this is the first widget to write its value
   * @param writer
   *          the writer to use
   * @param attributes
   *          additional attributes ti be
   *
   * @return true if data was written; false otherwise
   * @throws IOException
   *           if an I/O error occurs
   */
  public boolean writeHTTPValue(boolean first, Writer writer, Map<String, Object> attributes) {
    try {
      if ((attributes != null) && FormHelper.writeHTTPValues(first, this, writer, attributes, first)) {
        first = false;
      }

      if ((submitAttributes != null) && FormHelper.writeHTTPValues(first, this, writer, submitAttributes, first)) {
        first = false;
      }

      int     len = widgetList.size();
      iWidget a;

      for (int i = 0; i < len; i++) {
        a = widgetList.get(i);

        if (a.isSubmittable() && a.isValidForSubmission(true)) {
          if (a.writeHTTPValue(first, writer)) {
            first = false;
          }
        }
      }

      return !first;
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public void writeJSONValue(JSONWriter writer) {
    writeJSONValue(writer, null);
  }

  /**
   * Writes out the value of the widget in a form compatible with the JSON
   *
   * @param writer
   *          the writer to use
   *
   * @param attributes
   *          additional attributes ti be
   * @throws IOException
   *           if an I/O error occurs
   */
  public void writeJSONValue(JSONWriter writer, Map<String, Object> attributes) {
    try {
      int len;

      if (!isEnabled()) {
        return;
      }

      String name = getHTTPFormName();

      if (actAsFormViewer && (name != null)) {
        writer.key(name);
        writer.object();
      }

      if (attributes != null) {
        FormHelper.writeJSONValues(this, writer, attributes, true);
      }

      if (submitAttributes != null) {
        FormHelper.writeJSONValues(this, writer, submitAttributes, true);
      }

      len = widgetList.size();

      iWidget a;

      for (int i = 0; i < len; i++) {
        a = widgetList.get(i);

        if (a.isSubmittable() && a.isValidForSubmission(true)) {
          a.writeJSONValue(writer);
        }
      }

      if (actAsFormViewer && (name != null)) {
        writer.endObject();
      }
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public void setSubmittAttribute(String name, Object value) {
    if (submitAttributes == null) {
      submitAttributes = new HashMap<String, Object>();
    }

    submitAttributes.put(name, value);
  }

  public void setSubmittAttributes(Map attributes) {
    if (submitAttributes == null) {
      submitAttributes = new HashMap<String, Object>();
    }

    submitAttributes.putAll(attributes);
  }

  @Override
  public Object getAttribute(String key) {
    Object o;

    o = super.getAttribute(key);

    if (o != null) {
      return o;
    }

    if (submitAttributes != null) {
      o = submitAttributes.get(key);
    }

    return o;
  }

  /**
   * Gets the link that will be used to submit the form.
   *
   * @return the link that will be used to submit the form or null if the form
   *         does not have an action link
   */
  public ActionLink getFormLink() {
    return formLink;
  }

  @Override
  public Object getSubmittAttribute(String name) {
    return (submitAttributes == null)
           ? null
           : submitAttributes.get(name);
  }

  @Override
  public boolean isRetainInitialWidgetValues() {
    return retainInitialFieldValues;
  }

  @Override
  protected void clearConfiguration(boolean dispose) {
    super.clearConfiguration(dispose);

    if (submitAttributes != null) {
      submitAttributes.clear();
    }

    if (listenerList != null) {
      listenerList.clear();
    }

    if (dispose) {
      if (formLink != null) {
        formLink.clear();
      }

      formLink         = null;
      listenerList     = null;
      submitAttributes = null;
      listenerList     = null;
    }
  }

  @Override
  protected ActionLink createActionLink(Link link) {
    formLink = new FormLink(link);

    return formLink;
  }

  /**
   * Specialized link for submitting a form
   *
   * @version 0.3, 2007-05-14
   * @author Don DeCoteau
   */
  private class FormLink extends ActionLink {
    boolean dataWritten;

    /**
     * Constructs a new instance
     */
    FormLink(Link link) {
      super(aFormViewer.this, link);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      run();
    }

    @Override
    public void close() {
      dataWritten = false;
      super.close();
    }

    public void writeFormData() throws IOException {
      if (!dataWritten) {
        this.setMultiPartForm(hasMultipartContent || (submissionFiles != null));

        try {
          Writer w = this.openForOutput();

          if (this.getRequestEncoding() == RequestEncoding.JSON) {
            JSONWriter jw=new JSONWriter(w);
            jw.object();
            aFormViewer.this.writeJSONValue(jw, getAttributes());
            jw.endObject();
          } else if (isMultiPartForm()) {
            aFormViewer.this.writeHTTPContent(true, w, getPartBoundary(), getAttributes());
            FormHelper.writeBoundaryEnd(w, getPartBoundary());
          } else {
            aFormViewer.this.writeHTTPValue(true, w, getAttributes());
            w.write("\n");
          }
        } finally {
          dataWritten = true;
        }
      }
    }

    @Override
    protected void closeOutput() throws IOException {
      if (!dataWritten) {
        writeFormData();
      }

      super.closeOutput();
    }
  }
}
