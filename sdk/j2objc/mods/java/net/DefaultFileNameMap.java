/*
 * @(#)DefaultFileNameMap.java   2013-02-18
 * 
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package java.net;

import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Don DeCoteau
 */
public class DefaultFileNameMap implements FileNameMap {

  /**  */
  public static final String                   DEFAULT_MIME_TYPE = "application/octet-stream";
  private static final HashMap<String, String> mimeMap           = new HashMap<String, String>(186, (float) .25);

  static {
    mimeMap.put("3g2", "video/3gpp2");
    mimeMap.put("3gp", "video/3gpp");
    mimeMap.put("3gpp", "video/3gpp");
    mimeMap.put("3gpp2", "video/3gpp2");
    mimeMap.put("323", "text/h323");
    mimeMap.put("acx", "application/internet-property-stream");
    mimeMap.put("ai", "application/postscript");
    mimeMap.put("aif", "audio/x-aiff");
    mimeMap.put("aifc", "audio/x-aiff");
    mimeMap.put("aiff", "audio/x-aiff");
    mimeMap.put("asf", "video/x-ms-asf");
    mimeMap.put("asr", "video/x-ms-asf");
    mimeMap.put("asx", "video/x-ms-asf");
    mimeMap.put("au", "audio/basic");
    mimeMap.put("avi", "video/x-msvideo");
    mimeMap.put("axs", "application/olescript");
    mimeMap.put("bas", "text/plain");
    mimeMap.put("bcpio", "application/x-bcpio");
    mimeMap.put("bin", "application/octet-stream");
    mimeMap.put("bmp", "image/bmp");
    mimeMap.put("c", "text/plain");
    mimeMap.put("cat", "application/vnd.ms-pkiseccat");
    mimeMap.put("cdf", "application/x-cdf");
    mimeMap.put("cer", "application/x-x509-ca-cert");
    mimeMap.put("class", "application/octet-stream");
    mimeMap.put("clp", "application/x-msclip");
    mimeMap.put("cmx", "image/x-cmx");
    mimeMap.put("cod", "image/cis-cod");
    mimeMap.put("cpio", "application/x-cpio");
    mimeMap.put("crd", "application/x-mscardfile");
    mimeMap.put("crl", "application/pkix-crl");
    mimeMap.put("crt", "application/x-x509-ca-cert");
    mimeMap.put("csh", "application/x-csh");
    mimeMap.put("css", "text/css");
    mimeMap.put("csv", "text/csv");
    mimeMap.put("dcr", "application/x-director");
    mimeMap.put("der", "application/x-x509-ca-cert");
    mimeMap.put("dir", "application/x-director");
    mimeMap.put("dll", "application/x-msdownload");
    mimeMap.put("dms", "application/octet-stream");
    mimeMap.put("doc", "application/msword");
    mimeMap.put("dot", "application/msword");
    mimeMap.put("dvi", "application/x-dvi");
    mimeMap.put("dxr", "application/x-director");
    mimeMap.put("eps", "application/postscript");
    mimeMap.put("etx", "text/x-setext");
    mimeMap.put("evy", "application/envoy");
    mimeMap.put("exe", "application/octet-stream");
    mimeMap.put("fif", "application/fractals");
    mimeMap.put("flr", "x-world/x-vrml");
    mimeMap.put("flv", "avideo/flv");
    mimeMap.put("gif", "image/gif");
    mimeMap.put("gtar", "application/x-gtar");
    mimeMap.put("gz", "application/x-gzip");
    mimeMap.put("h", "text/plain");
    mimeMap.put("hdf", "application/x-hdf");
    mimeMap.put("hlp", "application/winhlp");
    mimeMap.put("hqx", "application/mac-binhex40");
    mimeMap.put("hta", "application/hta");
    mimeMap.put("htc", "text/x-component");
    mimeMap.put("htm", "text/html");
    mimeMap.put("html", "text/html");
    mimeMap.put("htt", "text/webviewhtml");
    mimeMap.put("ico", "image/x-icon");
    mimeMap.put("ief", "image/ief");
    mimeMap.put("iii", "application/x-iphone");
    mimeMap.put("ins", "application/x-internet-signup");
    mimeMap.put("isp", "application/x-internet-signup");
    mimeMap.put("java", "application/x-java");
    mimeMap.put("jfif", "image/pipeg");
    mimeMap.put("jpe", "image/jpeg");
    mimeMap.put("jpeg", "image/jpeg");
    mimeMap.put("jpg", "image/jpeg");
    mimeMap.put("js", "application/javascript");
    mimeMap.put("js", "application/x-javascript");
    mimeMap.put("latex", "application/x-latex");
    mimeMap.put("lha", "application/octet-stream");
    mimeMap.put("lsf", "video/x-la-asf");
    mimeMap.put("lsx", "video/x-la-asf");
    mimeMap.put("lzh", "application/octet-stream");
    mimeMap.put("m13", "application/x-msmediaview");
    mimeMap.put("m14", "application/x-msmediaview");
    mimeMap.put("m3u", "audio/x-mpegurl");
    mimeMap.put("man", "application/x-troff-man");
    mimeMap.put("mdb", "application/x-msaccess");
    mimeMap.put("me", "application/x-troff-me");
    mimeMap.put("mht", "message/rfc822");
    mimeMap.put("mhtml", "message/rfc822");
    mimeMap.put("mid", "audio/mid");
    mimeMap.put("mny", "application/x-msmoney");
    mimeMap.put("mov", "video/quicktime");
    mimeMap.put("movie", "video/x-sgi-movie");
    mimeMap.put("mp2", "video/mpeg");
    mimeMap.put("mp3", "audio/mpeg");
    mimeMap.put("mpa", "video/mpeg");
    mimeMap.put("mp4", "video/mp4");
    mimeMap.put("mv4", "video/mp4");
    mimeMap.put("mpe", "video/mpeg");
    mimeMap.put("mpeg", "video/mpeg");
    mimeMap.put("mpeg4", "video/mp4");
    mimeMap.put("mpg", "video/mpeg");
    mimeMap.put("mpp", "application/vnd.ms-project");
    mimeMap.put("mpv2", "video/mpeg");
    mimeMap.put("ms", "application/x-troff-ms");
    mimeMap.put("mvb", "application/x-msmediaview");
    mimeMap.put("nsv", "application/x-nsv-vp3-mp3");
    mimeMap.put("nws", "message/rfc822");
    mimeMap.put("ogg", "video/ogg");
    mimeMap.put("ogm", "video/ogg");
    mimeMap.put("ogv", "video/ogg");
    mimeMap.put("oda", "application/oda");
    mimeMap.put("p10", "application/pkcs10");
    mimeMap.put("p12", "application/x-pkcs12");
    mimeMap.put("p7b", "application/x-pkcs7-certificates");
    mimeMap.put("p7c", "application/x-pkcs7-mime");
    mimeMap.put("p7m", "application/x-pkcs7-mime");
    mimeMap.put("p7r", "application/x-pkcs7-certreqresp");
    mimeMap.put("p7s", "application/x-pkcs7-signature");
    mimeMap.put("pbm", "image/x-portable-bitmap");
    mimeMap.put("pbm", "image/x-portable-bitmimeMap");
    mimeMap.put("pdf", "application/pdf");
    mimeMap.put("pfx", "application/x-pkcs12");
    mimeMap.put("pgm", "image/x-portable-graymap");
    mimeMap.put("pgm", "image/x-portable-graymimeMap");
    mimeMap.put("pko", "application/ynd.ms-pkipko");
    mimeMap.put("pma", "application/x-perfmon");
    mimeMap.put("pmc", "application/x-perfmon");
    mimeMap.put("pml", "application/x-perfmon");
    mimeMap.put("pmr", "application/x-perfmon");
    mimeMap.put("pmw", "application/x-perfmon");
    mimeMap.put("png", "image/png");
    mimeMap.put("pnm", "image/x-portable-anymap");
    mimeMap.put("pnm", "image/x-portable-anymimeMap");
    mimeMap.put("pot,", "application/vnd.ms-powerpoint");
    mimeMap.put("ppm", "image/x-portable-pixmap");
    mimeMap.put("ppm", "image/x-portable-pixmimeMap");
    mimeMap.put("pps", "application/vnd.ms-powerpoint");
    mimeMap.put("ppt", "application/vnd.ms-powerpoint");
    mimeMap.put("prf", "application/pics-rules");
    mimeMap.put("ps", "application/postscript");
    mimeMap.put("pub", "application/x-mspublisher");
    mimeMap.put("py", "application/x-python");
    mimeMap.put("qt", "video/quicktime");
    mimeMap.put("ra", "audio/x-pn-realaudio");
    mimeMap.put("ram", "audio/x-pn-realaudio");
    mimeMap.put("ras", "image/x-cmu-raster");
    mimeMap.put("rb", "application/x-ruby");
    mimeMap.put("rgb", "image/x-rgb");
    mimeMap.put("rmi", "audio/mid");
    mimeMap.put("roff", "application/x-troff");
    mimeMap.put("rtf", "application/rtf");
    mimeMap.put("rtx", "text/richtext");
    mimeMap.put("scd", "application/x-msschedule");
    mimeMap.put("sct", "text/scriptlet");
    mimeMap.put("sdf", "text/x-sdf");
    mimeMap.put("sdf8", "text/x-sdf;charset=utf8");
    mimeMap.put("setpay", "application/set-payment-initiation");
    mimeMap.put("setreg", "application/set-registration-initiation");
    mimeMap.put("sh", "application/x-sh");
    mimeMap.put("shar", "application/x-shar");
    mimeMap.put("sit", "application/x-stuffit");
    mimeMap.put("snd", "audio/basic");
    mimeMap.put("spc", "application/x-pkcs7-certificates");
    mimeMap.put("spl", "application/futuresplash");
    mimeMap.put("src", "application/x-wais-source");
    mimeMap.put("sst", "application/vnd.ms-pkicertstore");
    mimeMap.put("stl", "application/vnd.ms-pkistl");
    mimeMap.put("stm", "text/html");
    mimeMap.put("sv4cpio", "application/x-sv4cpio");
    mimeMap.put("sv4crc", "application/x-sv4crc");
    mimeMap.put("svg", "image/svg+xml");
    mimeMap.put("swf", "application/x-shockwave-flash");
    mimeMap.put("t", "application/x-troff");
    mimeMap.put("tar", "application/x-tar");
    mimeMap.put("tcl", "application/x-tcl");
    mimeMap.put("tex", "application/x-tex");
    mimeMap.put("texi", "application/x-texinfo");
    mimeMap.put("texinfo", "application/x-texinfo");
    mimeMap.put("tgz", "application/x-compressed");
    mimeMap.put("tif", "image/tiff");
    mimeMap.put("tiff", "image/tiff");
    mimeMap.put("tr", "application/x-troff");
    mimeMap.put("trm", "application/x-msterminal");
    mimeMap.put("tsv", "text/tab-separated-values");
    mimeMap.put("tod", "video/mp2t");
    mimeMap.put("txt", "text/plain");
    mimeMap.put("uls", "text/iuls");
    mimeMap.put("ustar", "application/x-ustar");
    mimeMap.put("vob", "video/dvd");
    mimeMap.put("vod", "application/vod");
    mimeMap.put("vcf", "text/x-vcard");
    mimeMap.put("vrml", "x-world/x-vrml");
    mimeMap.put("wav", "audio/x-wav");
    mimeMap.put("wcm", "application/vnd.ms-works");
    mimeMap.put("wdb", "application/vnd.ms-works");
    mimeMap.put("wks", "application/vnd.ms-works");
    mimeMap.put("wmf", "application/x-msmetafile");
    mimeMap.put("wmv", "x-ms-wmv");
    mimeMap.put("wma", "x-ms-wma");
    mimeMap.put("wmx", "x-ms-wmx");
    mimeMap.put("wps", "application/vnd.ms-works");
    mimeMap.put("wri", "application/x-mswrite");
    mimeMap.put("wrl", "x-world/x-vrml");
    mimeMap.put("wrz", "x-world/x-vrml");
    mimeMap.put("xaf", "x-world/x-vrml");
    mimeMap.put("xbm", "image/x-xbitmap");
    mimeMap.put("xbm", "image/x-xbitmimeMap");
    mimeMap.put("xla", "application/vnd.ms-excel");
    mimeMap.put("xlc", "application/vnd.ms-excel");
    mimeMap.put("xlm", "application/vnd.ms-excel");
    mimeMap.put("xls", "application/vnd.ms-excel");
    mimeMap.put("xlt", "application/vnd.ms-excel");
    mimeMap.put("xlw", "application/vnd.ms-excel");
    mimeMap.put("xml", "text/xml");
    mimeMap.put("xof", "x-world/x-vrml");
    mimeMap.put("xpm", "image/x-xpixmap");
    mimeMap.put("xpm", "image/x-xpixmimeMap");
    mimeMap.put("xwd", "image/x-xwindowdump");
    mimeMap.put("z", "application/x-compress");
    mimeMap.put("zip", "application/zip");
  }

  /** Creates a new instance of MIMEMap */
  public DefaultFileNameMap() {}

  public static String addMapping(String ext, String type) {
    return mimeMap.put(ext, type);
  }

  public static void addMappings(Map props, boolean clear) {
    if (clear) {
      mimeMap.clear();
    }

    Map.Entry e;
    Iterator  it = props.entrySet().iterator();

    while(it.hasNext()) {
      e = (Entry) it.next();
      mimeMap.put((String) e.getKey(), (String) e.getValue());
    }
  }

  public static String typeFromExtension(String ext) {
    String type = mimeMap.get(ext);

    return (type == null)
           ? DEFAULT_MIME_TYPE
           : type;
  }

  public static String typeFromFile(String fileName) {
    int n = fileName.lastIndexOf('.');

    if (n != -1) {
      fileName = fileName.substring(n + 1);
      n        = fileName.indexOf('#');

      if (n != -1) {
        n = fileName.indexOf('?');
      }

      if (n != -1) {
        fileName = fileName.substring(0, n);
      }

      return typeFromExtension(fileName);
    }

    return DEFAULT_MIME_TYPE;
  }

  public String getContentTypeFor(String fileName) {
    return typeFromFile(fileName);
  }
}
