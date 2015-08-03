//License
/***
 * Java TelnetD library (embeddable telnet daemon)
 * Copyright (c) 2000-2005 Dieter Wimberger 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the author nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS ``AS
 * IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 ***/

package net.wimpi.telnetd;

import java.io.InputStream;
import net.wimpi.telnetd.io.terminal.TerminalManager;
import net.wimpi.telnetd.net.PortListener;
import net.wimpi.telnetd.shell.ShellManager;
import net.wimpi.telnetd.util.PropertiesLoader;
import net.wimpi.telnetd.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Iterator;

/**
 * Class that implements a configurable and embeddable
 * telnet daemon.
 *
 * @author Dieter Wimberger
 * @version 2.0 (16/07/2006)
 */
public class TelnetD {

  private static Log log = LogFactory.getLog(TelnetD.class);
  public static Log debuglog = log;
  public static Log syslog = log;
  private static TelnetD c_Self = null;	//reference of the running singleton
  private List m_Listeners;
  private ShellManager m_ShellManager;


  /**
   * Constructor creating a TelnetD instance.<br>
   * Private so that only  the factory method can create the
   * singleton instance.
   */
  private TelnetD() {
    c_Self = this;	//sets the singleton reference
    m_Listeners = new ArrayList(5);
  }//constructor

  /**
   * Start this telnet daemon, respectively
   * all configured listeners.<br>
   */
  public void start() {
    log.debug("start()");
    for (int i = 0; i < m_Listeners.size(); i++) {
      PortListener plis = (PortListener) m_Listeners.get(i);
      plis.start();
    }
  }//start

  /**
   * Stop this telnet daemon, respectively
   * all configured listeners.
   */
  public void stop() {
    for (int i = 0; i < m_Listeners.size(); i++) {
      PortListener plis = (PortListener) m_Listeners.get(i);
      //shutdown the Portlistener resources
      plis.stop();
    }
  }//stop

  /**
   * Accessor method to version information.
   *
   * @return String that contains version information.
   */
  public String getVersion() {
    return VERSION;
  }//getVersion

  /**
   * Method to prepare the ShellManager.<br>
   * Creates and prepares a Singleton instance of the ShellManager,
   * with settings from the passed in Properties.
   *
   * @param settings Properties object that holds main settings.
   * @throws BootException if preparation fails.
   */
  private void prepareShellManager(Properties settings)
      throws BootException {

    //use factory method  for creating mgr singleton
    m_ShellManager = ShellManager.createShellManager(settings);
    if (m_ShellManager == null) {
      System.exit(1);
    }
  }//prepareShellManager


  /**
   * Method to prepare the PortListener.<br>
   * Creates and prepares and runs a PortListener, with settings from the
   * passed in Properties. Yet the Listener will not accept any incoming
   * connections before startServing() has been called. this has the advantage
   * that whenever a TelnetD Singleton has been factorized, it WILL 99% not fail
   * any longer (e.g. serve its purpose).
   *
   * @param settings Properties object that holds main settings.
   * @throws BootException if preparation fails.
   */
  private void prepareListener(String name, Properties settings)
      throws BootException {

    //factorize PortListener
    PortListener listener = PortListener.createPortListener(name, settings);
    //start the Thread derived PortListener
    try {
      m_Listeners.add(listener);
    } catch (Exception ex) {
      throw new BootException("Failure while starting PortListener thread: " + ex.getMessage());
    }

  }//prepareListener

  private void prepareTerminals(Properties terminals)
      throws BootException {

    TerminalManager.createTerminalManager(terminals);
  }//prepareTerminals

  /**
   * Returns a {@link PortListener} for the given
   * identifier.
   *
   * @param id the identifier of the {@link PortListener} instance.
   * @return {@link PortListener} instance or null if an instance
   *         with the given identifier does not exist.
   */
  public PortListener getPortListener(String id) {
    if(id==null || id.length() == 0) {
      return null;
    }
    for (Iterator iterator = m_Listeners.iterator(); iterator.hasNext();) {
      PortListener portListener = (PortListener) iterator.next();
      if(portListener.getName().equals(id)) {
        return portListener;
      }
    }
    return null;
  }//getPortListener

  /**
   * Factory method to create a TelnetD Instance.
   *
   * @param main Properties object with settings for the TelnetD.
   * @return TenetD instance that has been properly set up according to the
   *         passed in properties, and is ready to start serving.
   * @throws BootException if the setup process fails.
   */
  public static TelnetD createTelnetD(Properties main)
      throws BootException {

    if (c_Self == null) {
      TelnetD td = new TelnetD();
      td.prepareShellManager(main);
      td.prepareTerminals(main);
      String[] listnames = StringUtil.split(main.getProperty("listeners"), ",");
      for (int i = 0; i < listnames.length; i++) {
        td.prepareListener(listnames[i], main);
      }
      return td;
    } else {
      throw new BootException("Singleton already instantiated.");
    }

  }//createTelnetD

  /**
   * Factory method to create a TelnetD singleton instance,
   * loading the standard properties files from the given
   * String containing an URL location.<br>
   *
   * @param urlprefix String containing an URL prefix.
   * @return TenetD instance that has been properly set up according to the
   *         passed in properties, and is ready to start serving.
   * @throws BootException if the setup process fails.
   */
  public static TelnetD createTelnetD(String urlprefix)
      throws BootException {

    try {
      return createTelnetD(PropertiesLoader.loadProperties(urlprefix));
    } catch (IOException ex) {
      log.error(ex);
      throw new BootException("Failed to load configuration from given URL.");
    }
  }//createTelnetD

 /**
   * Factory method to create a TelnetD singleton instance,
   * loading the standard properties files from the given
   * String containing an URL location.<br>
   *
   * @param int stream containing properties
   * @return TenetD instance that has been properly set up according to the
   *         passed in properties, and is ready to start serving.
   * @throws BootException if the setup process fails.
   */
  public static TelnetD createTelnetD(InputStream in)
      throws BootException {

    try {
      return createTelnetD(PropertiesLoader.loadProperties(in));
    } catch (IOException ex) {
      log.error(ex);
      throw new BootException("Failed to load configuration from given stream.");
    }
  }//createTelnetD


  /**
   * Accessor method for the Singleton instance of this class.<br>
   *
   * @return TelnetD Singleton instance reference.
   */
  public static TelnetD getReference() {
    if (c_Self != null) {
      return ((TelnetD) c_Self);
    } else {
      return null;
    }
  }//getReference

 
  private static final String VERSION = "2.0";

}//class TelnetD
