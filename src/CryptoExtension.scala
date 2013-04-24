package org.nlogo.extensions.crypto

import
  org.nlogo.api.{ Argument, Context, DefaultClassManager, DefaultReporter, PrimitiveManager, Syntax },
    Syntax.StringType

class CryptoExtension extends DefaultClassManager {

  def load(primitiveManager: PrimitiveManager) {
    primitiveManager.addPrimitive("rsa-encrypt",   RSAEncrypt)
    primitiveManager.addPrimitive("base64-encode", Base64Encode)
  }

  object RSAEncrypt extends DefaultReporter {

    import
      java.{ math, security },
        math.BigInteger,
        security.{ KeyFactory, spec },
          spec.RSAPublicKeySpec

    import javax.crypto.Cipher

    private val AlgorithmName = "RSA"

    private val factory = KeyFactory.getInstance(AlgorithmName)

    override def getSyntax = {
      import Syntax.{ StringType => ST }
      Syntax.reporterSyntax(Array(ST, ST, ST), ST)
    }

    override def report(args: Array[Argument], context: Context) : AnyRef = {

      val Seq(input, modulus, exponent) = args.toSeq map (_.getString)
      val key    = factory.generatePublic(new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(exponent)))
      val cipher = Cipher.getInstance(AlgorithmName)
      cipher.init(Cipher.ENCRYPT_MODE, key)

      val inBytes   = input.getBytes(ByteEncoding)
      val encrypted = cipher.doFinal(inBytes)
      val encoded   = encodeBase64_B2S(encrypted)
      encoded

    }

  }

  object Base64Encode extends DefaultReporter {
    override def getSyntax = Syntax.reporterSyntax(Array(StringType), StringType)
    override def report(args: Array[Argument], context: Context) : AnyRef = {
      val Seq(input) = args.toSeq map (_.getString)
      encodeBase64_B2S(input.getBytes(ByteEncoding))
    }
  }

}

