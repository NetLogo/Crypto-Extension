package org.nlogo.extensions.crypto

import
  org.nlogo.api.{ Argument, Context, DefaultClassManager, DefaultReporter, PrimitiveManager, Syntax },
    Syntax.StringType

class CryptoExtension extends DefaultClassManager {

  def load(primitiveManager: PrimitiveManager) {
    primitiveManager.addPrimitive("rsa-encrypt",   RSAEncrypt)
    primitiveManager.addPrimitive("base64-encode", Base64Encode)
    primitiveManager.addPrimitive("base64-decode", Base64Decode)
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
      encodeBase64(cipher.doFinal(input.getBytes(ByteEncoding)))
    }

  }

  object Base64Encode extends DefaultReporter {
    override def getSyntax = Syntax.reporterSyntax(Array(StringType), StringType)
    override def report(args: Array[Argument], context: Context) : AnyRef = {
      val Seq(input) = args.toSeq map (_.getString)
      encodeBase64(input.getBytes(ByteEncoding))
    }
  }

  object Base64Decode extends DefaultReporter {
    override def getSyntax = Syntax.reporterSyntax(Array(StringType), StringType)
    override def report(args: Array[Argument], context: Context) : AnyRef = {
      val Seq(input) = args.toSeq map (_.getString)
      decodeBase64(input.getBytes(ByteEncoding))
    }
  }

}

