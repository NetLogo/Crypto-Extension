package org.nlogo.extensions

import org.apache.commons.codec.binary.Base64

package object crypto {
  val ByteEncoding  = "UTF-8"
  def encodeBase64(bytes: Array[Byte]) : String = new String(Base64.encodeBase64(bytes), ByteEncoding)
  def decodeBase64(bytes: Array[Byte]) : String = new String(Base64.decodeBase64(bytes), ByteEncoding)
}
