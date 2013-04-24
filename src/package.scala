package org.nlogo.extensions

import org.apache.commons.codec.binary.Base64

package object crypto {

  val ByteEncoding  = "UTF-8"

  def encodeBase64_B2S(bytes: Array[Byte]) : String      = Base64.encodeBase64String(bytes)
  def decodeBase64_S2B(str: String)        : Array[Byte] = Base64.decodeBase64(str)

  def decodeBase64_B2B(bytes: Array[Byte]) : Array[Byte] = Base64.decodeBase64(bytes)
  def encodeBase64_B2B(bytes: Array[Byte]) : Array[Byte] = Base64.encodeBase64(bytes)


}
