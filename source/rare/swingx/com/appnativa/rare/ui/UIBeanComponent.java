package com.appnativa.rare.ui;

import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.widget.iBeanIntegrator;
import com.appnativa.rare.widget.iWidget;

public class UIBeanComponent extends Component implements iBeanIntegrator {
	public UIBeanComponent() {
		super(new JPanelEx());
	}

	@Override
  public void configure(iWidget w, Widget cfg) {
		
	}

	@Override
  public void disposing() {
	}

	@Override
  public void handleConnection(iURLConnection conn) {
	}

	@Override
  public void initializeListeners(aWidgetListener l) {
	}

	@Override
  public boolean wantsURLConnection() {
		return false;
	}

	@Override
  public void setFromHTTPFormValue(Object value) {
	}

	@Override
  public void setValue(Object value) {
	}

	@Override
  public iPlatformComponent getContainer() {
		return this;
	}

	@Override
  public iPlatformComponent getDataComponent() {
		return this;
	}

	@Override
  public Object getHTTPFormValue() {
		return null;
	}

	@Override
  public Object[] getSelectedObjects() {
		return null;
	}

	@Override
  public Object getSelection() {
		return null;
	}

	@Override
  public Object getValue() {
		return null;
	}
}
