#include "numtheory.h"
#include "randstate.h"
#include "rsa.h"
#include <gmp.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdbool.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>

#define OPTIONS "b:i:n:d:s:vh"

// @desc: prints the help message for -h command
// @param: N/A
// @return: N/A

void help_print(void) {
    printf("SYNOPSIS\n   Generates an RSA public/private key pair.\n\nUSAGE\n   ./keygen [-hv] [-b "
           "bits] -n pbfile -d pvfile\n\nOPTIONS\n   -h              Display program help and "
           "usage.\n   -v              Display verbose program output.\n   -b bits         Minimum "
           "bits needed for public key n (default: 256).\n   -i confidence   Miller-Rabin "
           "iterations for testing primes (default: 50).\n   -n pbfile       Public key file "
           "(default: rsa.pub).\n   -d pvfile       Private key file (default: rsa.priv).\n   -s "
           "seed         Random seed for testing.\n");
    return;
}

// @desc: The main function, reads command line options and generates public and private keys. can also print statistics
// @param: arc and argv are needed for command line input
// @return: N/A
int main(int argc, char **argv) {
    int opt = 0;
    uint64_t iters = 50;
    uint64_t seed = time(NULL);
    uint64_t min_bits = 256;
    bool v = false;
    mpz_t p, q, n, e, d, rop, s, temp;
    mpz_inits(p, q, n, e, d, rop, s, temp, NULL);

    FILE *rsa_pub = fopen("rsa.pub", "w+");

    FILE *rsa_priv = fopen("rsa.priv", "w+");

    while ((opt = getopt(argc, argv, OPTIONS)) != -1) {
        switch (opt) {
        case 'h': help_print(); return 0;
        case 'b': min_bits = strtoul(optarg, NULL, 10); break;
        case 'i': iters = strtoul(optarg, NULL, 10); break;
        case 's': seed = strtoul(optarg, NULL, 10); break;
        case 'n': rsa_pub = fopen(optarg, "w+"); break;
        case 'd': rsa_priv = fopen(optarg, "w+"); break;
        case 'v': v = true; break;
        default: return 0;
        }
    }

    //handles permissions for the private key
    fchmod(fileno(rsa_priv), 0600);

    //makes the public and private keys
    randstate_init(seed);
    rsa_make_pub(p, q, n, e, min_bits, iters);
    rsa_make_priv(d, e, p, q);
    mpz_set(temp, d);

    //obtains the username
    char *username = getenv("USER");
    mpz_set_str(rop, username, 62);
    rsa_sign(s, rop, d, n);
    mpz_set(d, temp);

    //writes the public and private keys to their repsective files
    rsa_write_pub(n, e, s, username, rsa_pub);
    rsa_write_priv(n, d, rsa_priv);
    mpz_set(d, temp);

    //handles verbose statistics
    if (v == true) {
        printf("user = %s\n", username);
        gmp_printf("s (%lu bits) = %Zd\n", mpz_sizeinbase(s, 2), s);
        gmp_printf("p (%lu bits) = %Zd\n", mpz_sizeinbase(p, 2), p);
        gmp_printf("q (%lu bits) = %Zd\n", mpz_sizeinbase(q, 2), q);
        gmp_printf("n (%lu bits) = %Zd\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("e (%lu bits) = %Zd\n", mpz_sizeinbase(e, 2), e);
        gmp_printf("d (%lu bits) = %Zd\n", mpz_sizeinbase(d, 2), d);
    }

    mpz_clears(p, q, n, e, d, rop, s, temp, NULL);
    fclose(rsa_pub);
    fclose(rsa_priv);
    randstate_clear();

    return 0;
}
