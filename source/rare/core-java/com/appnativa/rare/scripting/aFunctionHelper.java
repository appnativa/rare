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

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.util.Base64;
import com.appnativa.util.Helper;
import com.appnativa.util.ISO88591Helper;
import com.appnativa.util.SNumber;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Iterator;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class aFunctionHelper {
  public static String aesDecrypt(String val, String password, String salt, int iteration, boolean base64) {
    if (val == null) {
      return "";
    }

    try {
      byte[]           sb      = ISO88591Helper.getInstance().getBytes(salt);
      PBEKeySpec       pswd    = new PBEKeySpec(password.toCharArray(), sb, iteration, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      PBEKey           key     = (PBEKey) factory.generateSecret(pswd);
      byte[]           b       = ISO88591Helper.getInstance().getBytes(val);
      Cipher           cipher  = Cipher.getInstance("AES");

      cipher.init(Cipher.DECRYPT_MODE, key);

      byte[] decrypted = cipher.doFinal(b);

      if (base64) {
        return Base64.encode(decrypted);
      }

      return ISO88591Helper.getInstance().getString(decrypted);
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  public static String aesEncrypt(String val, String password, String salt, int iteration, boolean base64) {
    if (val == null) {
      return "";
    }

    try {
      byte[]           sb      = ISO88591Helper.getInstance().getBytes(salt);
      PBEKeySpec       pswd    = new PBEKeySpec(password.toCharArray(), sb, iteration, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      PBEKey           key     = (PBEKey) factory.generateSecret(pswd);
      byte[]           b       = ISO88591Helper.getInstance().getBytes(val);
      Cipher           cipher  = Cipher.getInstance("AES");

      cipher.init(Cipher.ENCRYPT_MODE, key);

      byte[] encrypted = cipher.doFinal(b);

      if (base64) {
        return Base64.encode(encrypted);
      }

      return ISO88591Helper.getInstance().getString(encrypted);
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Generates a password-based key
   *
   * @param password
   *          the password
   * @param salt
   *          the salt
   * @param iteration
   *          the iteration
   *
   * @return the key as a ISO-88591 string
   */
  public static String generateKey(String password, String salt, int iteration) {
    try {
      byte[]           sb      = ISO88591Helper.getInstance().getBytes(salt);
      PBEKeySpec       pswd    = new PBEKeySpec(password.toCharArray(), sb, iteration, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      PBEKey           key     = (PBEKey) factory.generateSecret(pswd);

      return ISO88591Helper.getInstance().getString(key.getEncoded());
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Generates a set of random bytes that can be use a salt for cryptographic
   * purposes
   *
   * @param bytes
   *          the number of bytes to generate
   * @return the salt as a ISO-88591 string
   */
  public static String generateSalt(int bytes) {
    try {
      if (bytes < 1) {
        return "";
      }

      byte[]       salt = new byte[bytes];
      SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");

      rand.nextBytes(salt);

      return ISO88591Helper.getInstance().getString(salt);
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Computes a Hash-based Message Authentication Code (HMAC) using the MD5 hash
   * function.
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param hexout
   *          true to return the value as a hex string; false otherwise
   *
   * @return the results of the computation
   */
  public static String hmacMD5(String val, String key, boolean hexout) {
    if (val == null) {
      return "";
    }

    byte[]        b        = ISO88591Helper.getInstance().getBytes(val);
    byte[]        kb       = ISO88591Helper.getInstance().getBytes(key);
    SecretKeySpec skeySpec = new SecretKeySpec(kb, "HmacMD5");

    try {
      Mac mac = Mac.getInstance("HmacMD5");

      mac.init(skeySpec);

      if (hexout) {
        return SNumber.bytesToHexString(mac.doFinal(b));
      }

      return ISO88591Helper.getInstance().getString(mac.doFinal(b));
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Computes a Hash-based Message Authentication Code (HMAC) using the SHA hash
   * function.
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the results of the computation
   */
  public static String hmacSHA(String val, String key, boolean base64) {
    if (val == null) {
      return "";
    }

    byte[]        b        = ISO88591Helper.getInstance().getBytes(val);
    byte[]        kb       = ISO88591Helper.getInstance().getBytes(key);
    SecretKeySpec skeySpec = new SecretKeySpec(kb, "HmacSHA1");

    try {
      Mac mac = Mac.getInstance("HmacSHA1");

      mac.init(skeySpec);

      if (base64) {
        return Base64.encode(mac.doFinal(b));
      }

      return ISO88591Helper.getInstance().getString(mac.doFinal(b));
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Computes the Message Authentication Code for the specified value
   *
   * @param val
   *          the value
   * @param key
   *          the key
   * @param algorithm
   *          the mac algorithm
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the Message Authentication Code for the specified value
   */
  public static String mac(String val, String key, String algorithm, boolean base64) {
    if (val == null) {
      return "";
    }

    byte[]        b        = ISO88591Helper.getInstance().getBytes(val);
    byte[]        kb       = ISO88591Helper.getInstance().getBytes(key);
    SecretKeySpec skeySpec = new SecretKeySpec(kb, algorithm);

    try {
      Mac mac = Mac.getInstance(algorithm);

      mac.init(skeySpec);

      if (base64) {
        return Base64.encode(mac.doFinal(b));
      }

      return ISO88591Helper.getInstance().getString(mac.doFinal(b));
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  /**
   * Computes the MD5 hash of the specified value
   *
   * @param val
   *          the value
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   *
   * @return the MD5 hash of the specified value
   */
  public static String md5(String val, boolean base64) {
    if (val == null) {
      return "";
    }

    byte[]        b  = ISO88591Helper.getInstance().getBytes(val);
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("MD5");
    } catch(NoSuchAlgorithmException ex) {
      throw new ApplicationException(ex);
    }

    byte[] dig = md.digest(b);

    if (base64) {
      return Base64.encode(dig);
    }

    return ISO88591Helper.getInstance().getString(dig);
  }

  /**
   * Computes the SHA hash of the specified value
   *
   * @param val
   *          the value
   * @param base64
   *          true to return the value as a base64 string; false otherwise
   * @return the SHA hash of the specified value
   */
  public static String sha1(byte[] val, boolean base64) {
    if (val == null) {
      return "";
    }

    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch(NoSuchAlgorithmException ex) {
      throw new ApplicationException(ex);
    }

    byte[] dig = md.digest(val);

    if (base64) {
      return Base64.encode(dig);
    }

    return ISO88591Helper.getInstance().getString(dig);
  }

  /**
   * Computes the SHA hash of the specified value
   *
   * @param val
   *          the value
   * @return the SHA hash of the specified value
   */
  public static String sha1(String val, boolean base64) {
    if (val == null) {
      return "";
    }

    byte[]        b  = ISO88591Helper.getInstance().getBytes(val);
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch(NoSuchAlgorithmException ex) {
      throw new ApplicationException(ex);
    }

    byte[] dig = md.digest(b);

    if (base64) {
      return Base64.encode(dig);
    }

    return ISO88591Helper.getInstance().getString(dig);
  }

  public static StringBuilder getMethods(Object o, StringBuilder sb) {
    try {
      Method        a[]  = o.getClass().getDeclaredMethods();
      String        name = o.getClass().getName() + ".";
      Method        m;
      int           mods;
      StringBuilder sig = new StringBuilder();

      sb.append("\nMethods for class:" + name + "\n");

      TreeMap map = new TreeMap();

      for (int i = 0; i < a.length; i++) {
        m    = a[i];
        mods = m.getModifiers();

        if ((mods & Modifier.PUBLIC) > 0 && (mods & Modifier.STATIC) == 0) {
          Helper.toString(m, sig, "com.appnativa.");
          map.put(sig.toString(), null);
          sig.setLength(0);
        }
      }

      Iterator it = map.keySet().iterator();

      while(it.hasNext()) {
        sb.append((String) it.next()).append('\n');
      }
    } catch(Exception e) {
      sb.setLength(0);
      sb.append("Exception : ").append(e.toString());
    }

    return sb;
  }
}
