package org.nlogo.extensions.crypto

import
  org.nlogo.api.{ Argument, Context, DefaultClassManager, DefaultReporter, PrimitiveManager }

class CryptoExtension extends DefaultClassManager {

  def load(primitiveManager: PrimitiveManager) {
    primitiveManager.addPrimitive("rsa-encrypt", RSAEncrypt)
  }

  object RSAEncrypt extends DefaultReporter {

    import
      java.{ math, security },
        math.BigInteger,
        security.{ KeyFactory, spec },
          spec.RSAPublicKeySpec

    import javax.crypto.Cipher
    import org.nlogo.api.Syntax

    private val AlgorithmName = "RSA"
    private val ByteEncoding  = "UTF-8"

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
      new String(cipher.doFinal(input.getBytes(ByteEncoding)), ByteEncoding)
    }

  }

}

