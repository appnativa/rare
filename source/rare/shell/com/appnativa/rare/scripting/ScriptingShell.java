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

package com.appnativa.rare.scripting;

import com.appnativa.rare.Platform;
import com.appnativa.rare.terminal.Display;
import com.appnativa.rare.terminal.KeystrokeHandler;
import com.appnativa.rare.terminal.SameMachineFilter;
import com.appnativa.rare.terminal.Terminal;
import com.appnativa.rare.terminal.TerminalReader;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;

import net.wimpi.telnetd.TelnetD;
import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.io.TerminalIO;
import net.wimpi.telnetd.io.terminal.ColorHelper;
import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionData;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Scripting shell
 *
 * @author Don DeCoteau
 */
public class ScriptingShell implements Shell, iScriptingIO {
  static volatile TelnetD         m_telnetD  = null;
  static HashMap<String, Command> commandMap = new HashMap<String, Command>();
  static iShellEngineProvider     engineProvider;
  private static final String     bannerText = "Rare Java Scripting Shell\n" + "Copyright (C) 2007 appNativa Inc.\n"
                                               + "\n" + "\n" + "TelnetD library (embeddable telnet daemon)\n"
                                               + "Copyright (C) 2000-2005 Dieter Wimberger\n";
  private static String helpText = "clear/cls - clears the screen\n"
                                   + "    write - writes the contents of a variable. Use one asterisk\n"
                                   + "            (i.e. write *) to write all engine scoped variables and two\n"
                                   + "            to write all global scoped variables (i.e. write **)\n"
                                   + "     load - loads a script into the environment\n"
                                   + " language - allows you to change to a different scripting language\n"
                                   + "  viewers - lists all viewers that have been loaded\n"
                                   + "  targets - lists all viewers that have been loaded\n"
                                   + "  scripts - lists all scripts that have been loaded\n"
                                   + "quit/exit - disconnects the session\n"
                                   + " shutdown - shuts down the scripting telnet daemon\n"
                                   + "     help - brings up this screen\n"
                                   + "   ctrl-c - to interrupt a running script\n"
                                   + " ctrl-s/q - Controls screen output that is not paged\n"
                                   + "   prefix - Forces shell commands to require a prefix so they don't\n"
                                   + "            conflict with similar named functions/commands used by\n"
                                   + "            the current scripting language (e.g. prefix ! would require\n"
                                   + "            all shell commands to start with !)\n"
                                   + "   dump   - Dumps connection/session information\n";
  private static ScriptingShell SHELL;

  static {
    commandMap.put("cls", Command.CLEAR);
    commandMap.put("clear", Command.CLEAR);
    commandMap.put("write", Command.WRITE);
    commandMap.put("w", Command.WRITE);
    commandMap.put("language", Command.LANGUAGE);
    commandMap.put("help", Command.HELP);
    commandMap.put("help", Command.HELP);
    commandMap.put("shutdown", Command.SHUTDOWN);
    commandMap.put("quit", Command.EXIT);
    commandMap.put("exit", Command.EXIT);
    commandMap.put("widgets", Command.WIDGETS);
    commandMap.put("viewers", Command.VIEWERS);
    commandMap.put("scripts", Command.SCRIPTS);
    commandMap.put("targets", Command.TARGETS);
    commandMap.put("load", Command.LOAD);
    commandMap.put("dump", Command.DUMP);
    commandMap.put("prefix", Command.PREFIX);
    commandMap.put("properties", Command.PROPERTIES);
    commandMap.put("props", Command.PROPERTIES);
    commandMap.put("propsv", Command.PROPERTIES_VALUE);
    commandMap.put("focus", Command.FOCUS);
    commandMap.put("trace", Command.TRACE);
    commandMap.put("later", Command.LATER);
  }

  String                  sessionPrompt = "";
  volatile boolean        shuttingDown  = false;
  boolean                 helpLoaded    = false;
  String                  cmdPrefix;
  PrintWriter             errorWriter;
  Reader                  inputReader;
  PrintWriter             outputWriter;
  iScriptingEngine        scriptingEngine;
  Terminal                terminal;
  private Connection      m_Connection;
  private BasicTerminalIO m_IO;

  /**
   *
   */
  public static enum Command {
    CLEAR, DUMP, EXIT, SHUTDOWN, WRITE, LANGUAGE, PREFIX, HELP, SCRIPTS, TARGETS, VIEWERS, WIDGETS, LOAD, PROPERTIES,
    FOCUS, PROPERTIES_VALUE, TRACE, LATER
  }

  ;

  /**
   *     Creates a new instance of ScriptingShell
   */
  public ScriptingShell() {
    SHELL = this;
  }

  /**
   * Constructs a new instance
   *
   * @param ep {@inheritDoc}
   */
  public ScriptingShell(iShellEngineProvider ep) {
    SHELL          = this;
    engineProvider = ep;
  }

  @Override
  public void bell() {
    try {
      terminal.bell();
    } catch(IOException ex) {}
  }

  public static void close() {
    if (SHELL != null) {
      SHELL.stop();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param ce {@inheritDoc}
   */
  @Override
  public void connectionIdle(ConnectionEvent ce) {
    try {
      terminal.write("CONNECTION_IDLE");
    } catch(IOException ex) {
      Platform.getDefaultExceptionHandler(null).ignoreException(null, ex);
    }
  }    // connectionIdle

  /**
   * {@inheritDoc}
   *
   * @param ce {@inheritDoc}
   */
  @Override
  public void connectionLogoutRequest(ConnectionEvent ce) {
    try {
      terminal.write("CONNECTION_LOGOUTREQUEST");
    } catch(Exception ex) {
      Platform.getDefaultExceptionHandler(null).ignoreException(null, ex);
    }
  }    // connectionLogout

  /**
   * {@inheritDoc}
   *
   * @param ce {@inheritDoc}
   */
  @Override
  public void connectionSentBreak(ConnectionEvent ce) {
    try {
      terminal.write("CONNECTION_BREAK");
    } catch(Exception ex) {
      Platform.getDefaultExceptionHandler(null).ignoreException(null, ex);
    }
  }    // connectionSentBreak

  /**
   * {@inheritDoc}
   *
   * @param ce {@inheritDoc}
   */
  @Override
  public void connectionTimedOut(ConnectionEvent ce) {
    try {
      terminal.write("CONNECTION_TIMEDOUT");
      // close connection
      m_Connection.close();
    } catch(Exception ex) {
      Platform.getDefaultExceptionHandler(null).ignoreException(null, ex);
    }
  }    // connectionTimedOut

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static Shell createShell() {
    return new ScriptingShell();
  }    // createShell

  /**
   * Method that runs a shell
   *
   * @param con Connection that runs the shell.
   */
  @Override
  @SuppressWarnings("resource")
  public void run(Connection con) {
    CharScanner  scanner = new CharScanner();
    List<String> args    = new ArrayList<String>();

    try {
      m_Connection = con;
      m_IO         = m_Connection.getTerminalIO();
      m_IO.setAutoflushing(true);
      m_Connection.addConnectionListener(this);

      Display display = new Display(m_IO);

      terminal     = new Terminal(display, Thread.currentThread());
      outputWriter = new PrintWriter(terminal.getWriter());
      errorWriter  = new PrintWriter(new ErrorWriter());
      inputReader  = terminal.getReader();
      display.clearDisplay();
      new Thread(new KeystrokeHandler(terminal, new TerminalReader(m_IO))).start();
      terminal.read(100);    // forces an pending telnet negotiations to occur
      displayBanner();
      scriptingEngine = engineProvider.getDefaultEngine(this);

      if (scriptingEngine == null) {
        chooseEngine(null);
      } else {
        sessionPrompt = scriptingEngine.getLanguageName() + ">";
        displayCenteredText(scriptingEngine.getLanguageFullName(), true, true);
      }

      terminal.linefeed();
      shuttingDown = scriptingEngine == null;

      String  line;
      boolean checkCmd;

      while(!shuttingDown) {
        checkCmd = true;
        Thread.interrupted();
        terminal.linefeed();
        terminal.write(sessionPrompt);
        line = terminal.readLine();

        if ((line == null) || (line.length() == 0)) {
          continue;
        }

        try {
          terminal.linefeed();
        } catch(InterruptedIOException ex) {
          dumpException(ex);
        }

        scanner.reset(line);
        args.clear();

        String cmd = scanner.nextToken(' ', true, false, true);

        if (cmdPrefix != null) {
          if (cmd.startsWith(cmdPrefix)) {
            cmd = cmd.substring(cmdPrefix.length());
          } else {
            checkCmd = false;
          }
        }

        if (checkCmd) {
          cmd = cmd.toLowerCase(Locale.ENGLISH);

          Command c = commandMap.get(cmd);

          if (c != null) {
            args.clear();
            args = scanner.getTokens(' ', true, false, false, args);
            handleCommand(c, args);

            continue;
          }
        }

        try {
          line = line.trim();
          scriptingEngine.eval(line);
        } catch(Exception ex) {
          dumpException(ex);
        }
      }

      shuttingDown = true;
      display.clearDisplay();

      String s = Constants.TEXT_GOODBYE;
      int    x = (80 - s.length()) / 2;

      if (x > 0) {
        terminal.setCursorColumn(x);
      }

      display.setBold(true);
      display.setForeground(TerminalIO.YELLOW);
      terminal.writeLine(s);
      terminal.linefeed();
      m_IO.close();
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);
    }
  }    // run

  public void stop() {
    if (m_telnetD != null) {
      try {
        m_telnetD.stop();
      } catch(Throwable e) {}

      m_telnetD = null;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public PrintWriter getErrorWriter() {
    return errorWriter;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public Reader getReader() {
    return inputReader;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public PrintWriter getWriter() {
    return outputWriter;
  }

  /**
   * {@inheritDoc}
   *
   * @param port {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean launch(int port) {
    if (m_telnetD != null) {
      return true;
    }

    if (port < 1000) {
      return false;
    }

    Properties props = null;

    props = createDefaultProperties();

    String name = props.getProperty("listeners");

    if ((name == null) ||!name.equals("std")) {
      System.err.println(Constants.TEXT_SHELL_FAILURE);
      System.err.println("Must use 'listeners=std' in property file");

      return false;
    }

    props.put("std.port", Integer.toString(port));

    String cf = (String) props.get("std.connectionfilter");

    if ((cf == null) || (cf.indexOf('.') == -1)) {
      props.put("std.connectionfilter", SameMachineFilter.class.getName());
    }

    props.put("std.maxcon", "1");
    props.put("std.floodprotection", "1");

    try {
      m_telnetD = TelnetD.createTelnetD(props);
      m_telnetD.start();
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
          stop();
        }
      }));

      return true;
    } catch(Exception ex) {
      Platform.ignoreException(Constants.TEXT_SHELL_FAILURE, ex);

      return false;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param language {@inheritDoc}
   *
   * @throws IOException
   */
  protected void chooseEngine(String language) throws IOException {
    do {
      chooseEngineEx(language);

      if (scriptingEngine == null) {
        terminal.linefeed();
        terminal.write(Constants.TEXT_EXIT_QUESTION);

        String exit = terminal.readLine();

        if ((exit != null) && (exit.equalsIgnoreCase("y") || exit.equalsIgnoreCase("yes"))) {
          break;
        }
      }
    } while(scriptingEngine == null);

    if (scriptingEngine != null) {
      sessionPrompt = scriptingEngine.getLanguageName() + ">";
      terminal.linefeed();
      displayCenteredText(scriptingEngine.getLanguageFullName(), true, true);
      terminal.linefeed();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param language {@inheritDoc}
   */
  protected void chooseEngineEx(String language) {
    if (language == null) {
      List<String> names = engineProvider.getLanguages();
      int          len   = names.size();

      if (len == 1) {
        try {
          language = names.get(0);
          terminal.write(language);
          terminal.linefeed();
        } catch(IOException ex) {}
      }

      while(language == null) {
        try {
          terminal.linefeed(2);

          for (int i = 0; i < len; i++) {
            terminal.writeLine("  " + (i + 1) + ") " + names.get(i));
          }

          terminal.linefeed();
          terminal.write(Constants.TEXT_CHOOSE_LANGUAGE);
          terminal.write(' ');

          String s = terminal.readLine();

          if (s != null) {
            s = s.trim();

            try {
              int n = Integer.parseInt(s);

              if ((n > 0) && (n <= len)) {
                n--;
                language = names.get(n);

                break;
              }
            } catch(NumberFormatException ex) {}

            terminal.bell();
          }

          if ((s == null) || (s.length() == 0)) {
            break;
          }
        } catch(IOException ex) {
          Platform.ignoreException("ScriptingShell", ex);

          break;
        }
      }
    }

    if (language != null) {
      int n = language.indexOf('/');

      if (n != -1) {
        language = language.substring(0, n);
      }

      iScriptingEngine e = engineProvider.getEngine(language, this);

      if (e != null) {
        scriptingEngine = e;
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  protected static Properties createDefaultProperties() {
    Properties props = new Properties();

    props.put("terminals", "vt100,ansi,windoof,xterm");
    props.put("term.vt100.class", "net.wimpi.telnetd.io.terminal.vt100");
    props.put("term.vt100.aliases", "default,vt100-am,vt102,dec-vt100");
    props.put("term.ansi.class", "net.wimpi.telnetd.io.terminal.ansi");
    props.put("term.ansi.aliases", "color-xterm,xterm-color,vt320,vt220,linux,screen");
    props.put("term.windoof.class", "net.wimpi.telnetd.io.terminal.Windoof");
    props.put("term.windoof.aliases", "");
    props.put("shells", "scripting");
    props.put("term.xterm.class", "net.wimpi.telnetd.io.terminal.xterm");
    props.put("term.xterm.aliases", "");
    props.put("shell.scripting.class", "com.appnativa.rare.scripting.ScriptingShell");
    props.put("listeners", "std");
    props.put("std.port", "6666");
    props.put("std.floodprotection", "5");
    props.put("std.maxcon", "5");
    props.put("std.time_to_warning", "3600000");
    props.put("std.time_to_timedout", "60000");
    props.put("std.housekeepinginterval", "1000");
    props.put("std.inputmode", "character");
    props.put("std.loginshell", "scripting");
    props.put("std.connectionfilter", "none");

    return props;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  protected void displayBanner() throws IOException {
    try {
      terminal.setUnderlined(true);

      int len = terminal.getDisplay().getColumns();

      len -= 4;
      terminal.setCursorColumn(2);

      for (int i = 0; i < len; i++) {
        terminal.write(' ');
      }

      terminal.linefeed();
      terminal.setUnderlined(false);
      terminal.setBold(true);

      String[] a = bannerText.split("\n");
      int      x;
      String   s;

      for (int i = 0; i < a.length; i++) {
        s = a[i];
        x = len - s.length();
        x = x / 2;

        if (x < 2) {
          x = 2;
        }

        terminal.setCursorColumn(x);
        terminal.writeLine(s);
      }

      terminal.setCursorColumn(2);
      terminal.setUnderlined(true);

      for (int i = 0; i < len; i++) {
        terminal.write(' ');
      }
    } finally {
      terminal.setUnderlined(false);
      terminal.setBold(false);
      terminal.linefeed(3);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  protected void displayHelp() throws IOException {
    try {
      if (!helpLoaded) {
        helpText = Constants.getResourceStreamAsString("shell.help");
      }
    } catch(Throwable e) {}

    helpLoaded = true;
    terminal.writeLine(helpText);
  }

  /**
   * {@inheritDoc}
   *
   * @param ex {@inheritDoc}
   */
  protected void dumpException(Throwable ex) {
    String s = ex.getMessage();

    if ((s == null) || (s.length() < 5)) {
      ex.printStackTrace(errorWriter);
    } else {
      errorWriter.println(s);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  protected void dumpSessionInfo() throws IOException {
    ConnectionData cd = m_Connection.getConnectionData();

    // output header
    terminal.write(BasicTerminalIO.CRLF + "DEBUG: Active Connection" + BasicTerminalIO.CRLF);
    terminal.write("------------------------" + BasicTerminalIO.CRLF);
    // output connection data
    terminal.write("Connected from: " + cd.getHostName() + "[" + cd.getHostAddress() + ":" + cd.getPort() + "]"
                   + BasicTerminalIO.CRLF);
    terminal.write("Guessed Locale: " + cd.getLocale() + BasicTerminalIO.CRLF);
    terminal.write(BasicTerminalIO.CRLF);
    // output negotiated terminal properties
    terminal.write("Negotiated Terminal Type: " + cd.getNegotiatedTerminalType() + BasicTerminalIO.CRLF);
    terminal.write("Negotiated Columns: " + cd.getTerminalColumns() + BasicTerminalIO.CRLF);
    terminal.write("Negotiated Rows: " + cd.getTerminalRows() + BasicTerminalIO.CRLF);
    // output of assigned terminal instance (the cast is a hack, please
    // do not copy for other TCommands, because it would break the
    // decoupling of interface and implementation!
    terminal.write(BasicTerminalIO.CRLF);
    terminal.write("Assigned Terminal instance: " + ((TerminalIO) m_IO).getTerminal());
    terminal.write(BasicTerminalIO.CRLF);
    terminal.write("Environment: " + cd.getEnvironment().toString());
    terminal.write(BasicTerminalIO.CRLF);
    // output footer
    terminal.write("-----------------------------------------------" + BasicTerminalIO.CRLF + BasicTerminalIO.CRLF);
  }

  /**
   * {@inheritDoc}
   *
   * @param cmd {@inheritDoc}
   * @param args {@inheritDoc}
   */
  protected void handleCommand(Command cmd, List<String> args) {
    try {
      terminal.linefeed();

      int    len  = args.size();
      String arg1 = (len > 0)
                    ? args.get(0)
                    : null;

      switch(cmd) {
        case CLEAR :
          terminal.clearDisplay();

          break;

        case TRACE :
          scriptingEngine.trace(arg1);
          terminal.clearDisplay();

          break;

        case LATER :
          final String code = arg1;

          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              try {
                scriptingEngine.eval(code);
              } catch(Exception ex) {
                ex.printStackTrace(errorWriter);
              }
            }
          });

          break;

        case WRITE :
          if (arg1 == null) {
            arg1 = "*";
          }

          if (arg1.equals("*")) {
            writeLocals();
          } else if (arg1.equals("**")) {
            writeGlobals();
          } else {
            Object o = scriptingEngine.get(arg1);

            if (o == null) {
              errorWriter.println(arg1 + " is undefined");
            } else {
              String s = o.toString();

              if (s == null) {
                s = "";
              }

              s = arg1 + "=" + s;
              terminal.writeLine(s);
            }
          }

          break;

        case SHUTDOWN :
          System.exit(0);

          break;

        case HELP :
          displayHelp();

          break;

        case DUMP :
          dumpSessionInfo();

          break;

        case PROPERTIES :
          dumpProperties(arg1, false);

          break;

        case PROPERTIES_VALUE :
          dumpProperties(arg1, true);

          break;

        case FOCUS :
          terminal.linefeed();

          Object o = Platform.getAppContext().getPermanentFocusOwner();

          terminal.writeLine("permanentFocusOwner=" + ((o == null)
                  ? "null"
                  : o.getClass().getName()));
          terminal.linefeed();

          if (o != null) {
            terminal.writeLine(o.toString());
            terminal.linefeed();
          }

          o = Platform.getAppContext().getFocusOwner();
          terminal.writeLine("focusOwner=" + ((o == null)
                  ? "null"
                  : o.getClass().getName()));

          if (o != null) {
            terminal.writeLine(o.toString());
            terminal.linefeed();
          }

          break;

        case LOAD :
          if (arg1 == null) {
            terminal.writeLine(Constants.TEXT_SYNTAX_ERROR_LOAD);
            bell();
          } else {
            scriptingEngine.loadScript(arg1);
          }

          break;

        case SCRIPTS :
          writeList("Loaded Scripts", scriptingEngine.getLoadedScripts());

          break;

        case VIEWERS :
          writeList("Active Viewers", engineProvider.getViewers());

          break;

        case TARGETS :
          writeList("Active targets", engineProvider.getTargets());

          break;

        case WIDGETS :
          if (arg1 == null) {
            terminal.writeLine(Constants.TEXT_SYNTAX_ERROR_WIDGETS);
            bell();
          } else {
            writeList("Widgets for '" + arg1 + "'", engineProvider.getWidgetNames(arg1));
          }

          break;

        case EXIT :
          shuttingDown = true;

          break;

        case LANGUAGE :
          chooseEngine(arg1);

          break;

        case PREFIX :
          if (arg1 == null) {
            cmdPrefix = null;
            terminal.writeLine(Constants.TEXT_PREFIX_DISABLED);
          } else {
            if (arg1.length() > 2) {
              cmdPrefix = arg1.substring(0, 2);
            }

            terminal.writeLine(String.format(Constants.TEXT_PREFIX_ENABLED, cmdPrefix));
          }

          break;

        default :
          break;
      }
    } catch(Exception ex) {
      dumpException(ex);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  protected void writeGlobals() throws IOException {
    writheeader("Global Scoped Variables");

    TreeMap map = new TreeMap(scriptingEngine.getGlobalVariables());

    terminal.writeLine(Helper.mapToString(map));
  }

  /**
   * {@inheritDoc}
   *
   * @param header {@inheritDoc}
   * @param list {@inheritDoc}
   *
   * @throws IOException
   */
  protected void writeList(String header, List<String> list) throws IOException {
    writheeader(header);

    if (list != null) {
      ArrayList<String> l = new ArrayList<String>(list);

      Collections.sort(l);
      list = l;

      int len = list.size();

      for (int i = 0; i < len; i++) {
        terminal.setCursorColumn(2);
        terminal.writeLine(list.get(i));
      }
    }

    terminal.linefeed();
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  protected void writeLocals() throws IOException {
    writheeader("Engine Scoped Variables");

    TreeMap map = new TreeMap(scriptingEngine.envVariables());

    terminal.writeLine(Helper.mapToString(map));
  }

  /**
   * {@inheritDoc}
   *
   * @param header {@inheritDoc}
   *
   * @throws IOException
   */
  protected void writheeader(String header) throws IOException {
    int x = terminal.getDisplay().getColumns() - header.length();

    x = x / 2;

    try {
      m_IO.setAutoflushing(false);

      if (x > 0) {
        for (int i = 0; i < x; i++) {
          m_IO.write(' ');
        }
      }

      terminal.setBold(true);
      terminal.setUnderlined(true);
      terminal.writeLine(header);
    } finally {
      terminal.setUnderlined(false);
      terminal.setBold(false);
      m_IO.flush();
      m_IO.setAutoflushing(true);
    }
  }

  private void displayCenteredText(String s, boolean bold, boolean inderline) throws IOException {
    int len = terminal.getDisplay().getColumns();

    len -= 4;
    terminal.setUnderlined(inderline);
    terminal.setBold(bold);

    int x = len - s.length();

    x = x / 2;

    if (x < 2) {
      x = 2;
    }

    terminal.setCursorColumn(x);
    terminal.writeLine(s);
    terminal.setUnderlined(false);
    terminal.setBold(false);
  }

  private void dumpProperties(final String prefix, final boolean vals) throws IOException {
    try {
      dumpPropertiesEx(prefix, vals);
    } catch(Exception ex) {
      dumpException(ex);
    }
  }

  private void dumpPropertiesEx(String prefix, boolean vals) throws IOException {
    Map          map      = new TreeMap();
    UIProperties defaults = Platform.getUIDefaults();

    if (prefix != null) {
      prefix = prefix.toLowerCase();
    }

    Iterator dit = defaults.entrySet().iterator();

    // Build of Map of attributes for each component
    while(dit.hasNext()) {
      Entry  e     = (Entry) dit.next();
      Object value = e.getValue();
      String s     = e.getKey().toString();

      if ((prefix != null) &&!s.toLowerCase().startsWith(prefix)) {
        continue;
      }

      if (vals) {
        map.put(s, (value == null)
                   ? null
                   : value.toString());
      } else {
        map.put(s, (value == null)
                   ? null
                   : value.getClass().getName());
      }
    }

    terminal.linefeed();

    Iterator<String> it = map.keySet().iterator();

    while(it.hasNext()) {
      String s = it.next();

      terminal.writeLine(s + "=" + map.get(s));
    }

    terminal.linefeed();
  }

  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  class ErrorWriter extends Writer {

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {}

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
      outputWriter.flush();
    }

    /**
     * {@inheritDoc}
     *
     * @param str {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public void write(String str) throws IOException {
      outputWriter.write(ColorHelper.colorizeText(str, ColorHelper.RED, true));
    }

    /**
     * {@inheritDoc}
     *
     * @param cbuf {@inheritDoc}
     * @param off {@inheritDoc}
     * @param len {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      outputWriter.write(ColorHelper.colorizeText(new String(cbuf, off, len), ColorHelper.RED, true));
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  static class VariableReader extends Reader {

    /**  */
    StringBuilder buffer = new StringBuilder();

    /**  */
    int sbOffset = 0;

    /**  */
    Iterator<Entry<String, Object>> iterator;

    /**
     * Constructs a new instance
     *
     * @param it {@inheritDoc}
     */
    VariableReader(Iterator<Entry<String, Object>> it) {
      iterator = it;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {}

    /**
     * {@inheritDoc}
     *
     * @param cbuf {@inheritDoc}
     * @param off {@inheritDoc}
     * @param len {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
      StringBuilder sb = buffer;

      if (sbOffset >= sb.length()) {
        if (!iterator.hasNext()) {
          return -1;
        }

        Entry e = iterator.next();

        sb.setLength(0);
        sbOffset = 0;
        sb.append(e.getKey()).append('=').append(e.getValue()).append('\n');
      }

      int cnt = sb.length() - sbOffset;

      if (cnt > len) {
        cnt = len;
      }

      sb.getChars(sbOffset, sbOffset + cnt, cbuf, off);
      sbOffset += cnt;

      return cnt;
    }
  }
}
