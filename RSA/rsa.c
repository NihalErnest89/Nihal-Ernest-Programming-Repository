#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <gmp.h>
#include <math.h>
#include "rsa.h"
#include "numtheory.h"
#include "randstate.h"

//@desc: creates two large primes, their product, and the public exponent. These are parts of an RSA public key
//@param: p and q are the primes being made, n is their product, e is the public exponent, nbits and iters are used for the primality
//@return: N/A
//@cite: assignment doc
void rsa_make_pub(mpz_t p, mpz_t q, mpz_t n, mpz_t e, uint64_t nbits, uint64_t iters) {
    uint64_t bp = rand() % (((3 * nbits) / 4) + 1 - (nbits / 4)) + (nbits / 4);
    uint64_t bq = nbits - bp + 1;
    make_prime(p, bp, iters);
    make_prime(q, bq, iters);

    mpz_t tot, sub1, sub2, result, random;
    mpz_inits(tot, sub1, sub2, result, random, NULL);

    mpz_sub_ui(sub1, p, 1);
    mpz_sub_ui(sub2, q, 1);
    mpz_mul(n, p, q);

    mpz_mul(tot, sub1, sub2);

    while (mpz_cmp_ui(result, 1) != 0) {
        mpz_mul(tot, sub1, sub2);
        mpz_urandomb(random, state, nbits);
        mpz_set(e, random);
        gcd(result, random, tot);
    }

    mpz_clears(tot, sub1, sub2, random, result, NULL);
    return;
}

//@desc: writes a public RSA key to pbfile
//@param: n, e, s, and username are the items being written to the file, each are hextrings with trailing newlines. pbfile is the file being written to
//@return: N/A
//@cite: N/A
void rsa_write_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile) {
    gmp_fprintf(pbfile, "%Zx\n%Zx\n%Zx\n%s\n", n, e, s, username);
    return;
}

//@desc: reads a public RSA key from pbfile
//@param: n, e, s, and username are the items being read from the file, each are hextrings with trailing newlines. pbfile is the file being read from
//@return: N/A
//@cite: N/A

void rsa_read_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile) {
    gmp_fscanf(pbfile, "%Zx\n%Zx\n%Zx\n%s\n", n, e, s, username);
    return;
}

//@desc: makes a new RSA private key d given primes p and q, and exponent e
//@param: d is where the new key is stored, p and q are primes, and e is the exponent
//@return: N/A
//@cire: N/A
void rsa_make_priv(mpz_t d, mpz_t e, mpz_t p, mpz_t q) {
    mpz_t sp, sq, ppq;
    mpz_inits(sp, sq, ppq, NULL);

    mpz_sub_ui(sp, p, 1);
    mpz_sub_ui(sq, q, 1);
    mpz_mul(ppq, sp, sq);
    mod_inverse(d, e, ppq);

    mpz_clears(sp, sq, ppq, NULL);
    return;
}

//@desc: writes aprivate RSA key to pvfile
//@param: n and d are to be written to pvfile as hexstrings with trailing newlines
//@return: N/A
//@cite: N/A
void rsa_write_priv(mpz_t n, mpz_t d, FILE *pvfile) {
    gmp_fprintf(pvfile, "%Zx\n%Zx\n", n, d);
    return;
}

//@desc: reads aprivate RSA key from pvfile
//@param: n and d are to be read from pvfile as hexstrings with trailing newlines
//@return: N/A
//@cite: N/A

void rsa_read_priv(mpz_t n, mpz_t d, FILE *pvfile) {
    gmp_fscanf(pvfile, "%Zx\n%Zx\n", n, d);
    return;
}

//@desc: performs rsa encryption by computing ciphertext c using m, e, and n
//@param: c is where the encrypted text is stored, m is the message, e is the exponent, and n is the modulus
//@return: N/A
//@cite: N/A
void rsa_encrypt(mpz_t c, mpz_t m, mpz_t e, mpz_t n) {
    pow_mod(c, m, e, n);
}

//@desc: encrypts the contents of infile, writing the encrypted contents to outfile
//@param: contents of infile encrypted to outfile, n is the modulus, and e is the exponent
//@return: N/A
//@cite: assignment doc
void rsa_encrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t e) {
    uint64_t k = floor((mpz_sizeinbase(n, 2) - 1) / 8);

    uint8_t *block = (uint8_t *) calloc(k, sizeof(uint8_t));
    size_t j;
    block[0] = 0xFF;
    mpz_t m, c, tempn, tempe;
    mpz_inits(m, c, tempn, tempe, NULL);

    mpz_set(tempn, n);
    mpz_set(tempe, e);
    while (!feof(infile)) {
        mpz_set(n, tempn);
        mpz_set(e, tempe);
        j = fread(block + 1, sizeof(uint8_t), k - 1, infile);
        mpz_import(m, j + 1, 1, sizeof(uint8_t), 1, 0, block);
        rsa_encrypt(c, m, e, n);
        gmp_fprintf(outfile, "%Zx\n", c);
    }
    //gmp_fprintf(outfile, "\n");
    free(block);
    mpz_clears(m, c, tempn, tempe, NULL);
    return;
}

//@desc: Performs RSA decryption, computing message m by decrypting ciphertext c using private key d and public modulus n.
//@param: m is the decrypted, message, c is the message to be decrypted, d is the priv key and n is the modulus
//@return: N/A
//@cite: N/A
void rsa_decrypt(mpz_t m, mpz_t c, mpz_t d, mpz_t n) {
    pow_mod(m, c, d, n);
}

//@desc: Decrypts the contents of infile, writing the decrypted contents to outfile.
//@param: infile is the file containing the text to be decrypted, outfile is where the decrypted text go, n is the modulus and d is the private key
//@return: N/A
//@cite: assignment doc
void rsa_decrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t d) {
    uint64_t k = mpz_sizeinbase(n, 2);
    k -= 1;
    k = floor(k / 8);
    uint8_t *block = (uint8_t *) calloc(k, sizeof(uint8_t));
    size_t j;
    mpz_t c, m, tempd, tempn;
    mpz_inits(c, m, tempd, tempn, NULL);
    mpz_set(tempd, d);
    mpz_set(tempn, n);
    while (!feof(infile)) {
        mpz_set(d, tempd);
        mpz_set(n, tempn);
        gmp_fscanf(infile, "%Zx\n", c);
        rsa_decrypt(m, c, d, n);
        mpz_export(block, &j, 1, sizeof(uint8_t), 1, 0, m);
        fwrite(block + 1, sizeof(uint8_t), j - 1, outfile);
    }

    free(block);
    mpz_clears(c, m, tempd, tempn, NULL);
    return;
}

//@desc: Performs RSA signing, producing signature s by signing message m using private key d and public modulus n
//@param: s is the signature produced from message m, e is the exponent and n is the modulus
//@return: N/A
//@cite: N/A
void rsa_sign(mpz_t s, mpz_t m, mpz_t d, mpz_t n) {
    pow_mod(s, m, d, n);
    return;
}

//@desc: Performs RSA verification, returning true if signature s is verified and false otherwise.
//@param: m is the message being verified by signature s, e is the exponent and n is the modulus
//@return: returns boolean representing whether or not the message is verified
//@cite: assignment doc
bool rsa_verify(mpz_t m, mpz_t s, mpz_t e, mpz_t n) {
    mpz_t t;
    mpz_init(t);
    pow_mod(t, s, e, n);
    if (mpz_cmp(t, m) == 0) {
        mpz_clear(t);
        return true;
    }
    mpz_clear(t);
    return false;
}
