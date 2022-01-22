#include <stdio.h>
#include <stdlib.h>
#include <gmp.h>
#include <inttypes.h>
#include <math.h>
#include <time.h>
#include <fcntl.h>
#include <unistd.h>

#include "randstate.h"
#include "rsa.h"
#include "numtheory.h"

#define OPTIONS "-hvi:o:n:"

// @desc: prints the help message for -h command
// @param: N/A
// @return: N/A

void help_print(void) {
    printf(
        "SYNOPSIS\n   Decrypts data using RSA decryption.\n   Encrypted data is encrypted by the "
        "encrypt program.\n\nUSAGE\n   /home/nernest/resources/asgn6/decrypt [-hv] [-i infile] [-o "
        "outfile] -n privkey\n\nOPTIONS\n   -h              Display program help and usage.\n   -v "
        "             Display verbose program output.\n   -i infile       Input file of data to "
        "decrypt (default: stdin).\n   -o outfile      Output file for decrypted data (default: "
        "stdout).\n   -n pvfile       Private key file (default: rsa.priv).\n");
}

// @desc: The main function, reads command line options and decrypts files. can also print statistics
// @param: arc and argv are needed for command line input
// @return: N/A
int main(int argc, char **argv) {
    mpz_t n, d;
    mpz_inits(n, d, NULL);
    int opt = 0;
    bool v = false;
    FILE *infile = stdin;
    FILE *pvfile = fopen("rsa.priv", "r");
    FILE *outfile = stdout;

    while ((opt = getopt(argc, argv, OPTIONS)) != -1) {
        switch (opt) {
        case 'h': help_print(); return 0;
        case 'i': infile = fopen(optarg, "r"); break;
        case 'v': v = true; break;
        case 'o': outfile = fopen(optarg, "w+"); break;
        case 'n': pvfile = fopen(optarg, "r"); break;
        default: return 0;
        }
    }

    rsa_read_priv(n, d, pvfile);

    // handles verbose stats
    if (v == true) {
        gmp_printf("n (%lu bits) = %Zd\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("d (%lu bits) = %Zd\n", mpz_sizeinbase(d, 2), d);
    }

    // decrypts the file
    rsa_decrypt_file(infile, outfile, n, d);

    fclose(infile);
    fclose(outfile);
    fclose(pvfile);
    mpz_clears(n, d, NULL);

    return 0;
}
