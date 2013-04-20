# NetLogo Crypto Extension

This extension allows you perform cryptography in NetLogo.  Currently, it is not very full-featured, though, as it only performs RSA encryption with a public key.

## Building

Use the NETLOGO environment variable to tell the Makefile which NetLogoLite.jar to compile against.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0
    make

If compilation succeeds, `crypto.jar` will be created.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo Crypto extension is in the public domain.  To the extent possible under law, Uri Wilensky has waived all copyright and related or neighboring rights.
