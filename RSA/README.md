# Assignment 6 - Public Key Crytography

This program implements the RSA method of encryption using numerical functions. There will be 3 executables: keygen, encrypt and decrypt. The keygen program handles key generation by producing public and private pairs of RSA keys. The encrypt program uses the generated public key to encrypt data, and the decrypt program uses the paired private key to decrypt the encrypted files. The RSA functions are written in rsa.c and use the mathematical functions in numtheory.c. A randstad, which can take an input seed, is used for random number generation.

## Building

This program's 3 executable binaries are built using a makefile, which is included in this folder. The makefile compiles all of the c files into the binary which can be run with command line options. It also formats the .c and .h files in the folder according to clang format. The program can be built by running the following command:

```
make format all
```

### Running

The program's encrypter can be run with the following command:

```
./encrypt [-v] [-h] [-i infile] [-o outfile] [-n rsa public key file]
```

The program's decrypter can be run with the following command:

```
./decrypt [-v] [-h] [-i infile] [-o outfile] [-n rsa private key file] [-s seed]
```

The program's key generator can be run with the following command:

```
./keygen [-h] [-v] [-b bits] [-n  pbfile] [-d pvfile]
```

Using -h will print a list of valid command options. Using -v will include verbose statistics for encoding, decoding, key generator. Use -i, -o -n and -d to specify input, output, rsa public and rsa private files. -s can be used to specify seed


