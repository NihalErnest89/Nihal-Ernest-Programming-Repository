#include "numtheory.h"
#include "randstate.h"
#include "rsa.h"
#include <gmp.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>

#define OPTIONS "-hi:o:n:v"

// @desc: prints the help message for -h command
// @param: N/A
// @return: N/A

void help_print() {
    printf("SYNOPSIS\n   Encrypts data using RSA encryption.\n   Encrypted data is decrypted by "
           "the decrypt program.\n\nUSAGE\n   ./encrypt [-hv] [-i infile] [-o outfile] -n "
           "pubkey\n\nOPTIONS\n   -h              Display program help and usage.\n   -v           "
           "   Display verbose program output.\n   -i infile       Input file of data to encrypt "
           "(default: stdin).\n   -o outfile      Output file for encrypted data (default: "
           "stdout).\n   -n pbfile       Public key file (default: rsa.pub).\n");
    return;
}
// @desc: The main function, reads command line options and encrypts files. can also print statistics
// @param: arc and argv are needed for command line input
// @return: N/A

int main(int argc, char **argv) {
    int opt = 0;
    bool v = false;
    FILE *infile = stdin;
    FILE *outfile = stdout;
    FILE *pbfile = fopen("rsa.pub", "r");

    while ((opt = getopt(argc, argv, OPTIONS)) != -1) {
        switch (opt) {
        case 'h':
            help_print();
            fclose(infile);
            fclose(outfile);
            fclose(pbfile);
            return 0;
        case 'i': infile = fopen(optarg, "r"); break;

        case 'o': outfile = fopen(optarg, "w+"); break;
        case 'n': pbfile = fopen(optarg, "r"); break;
        case 'v': v = true; break;
        default: return 0;
        }
    }

    mpz_t n, e, s, tempe, tempn, rop;
    char username[512];
    mpz_inits(n, e, s, tempe, tempn, rop, NULL);

    //reads the public file
    rsa_read_pub(n, e, s, username, pbfile);
    mpz_set_str(rop, username, 62);
    mpz_set(tempe, e);
    mpz_set(tempn, n);

    //verifies the file contents
    if (rsa_verify(rop, s, e, n) == false) {
        fprintf(stderr, "invalid user");
        mpz_clears(n, e, s, tempe, tempn, rop, NULL);
        fclose(infile);
        fclose(outfile);
        fclose(pbfile);
        return 0;
    }

    mpz_set(e, tempe);
    mpz_set(n, tempn);

    //handles verbose printing
    if (v == true) {
        printf("user = %s\n", username);
        gmp_printf("s (%lu bits) = %Zd\n", mpz_sizeinbase(s, 2), s);
        gmp_printf("n (%lu bits) = %Zd\n", mpz_sizeinbase(n, 2), n);
        gmp_printf("e (%lu bits) = %Zd\n", mpz_sizeinbase(e, 2), e);
    }

    mpz_set(e, tempe);
    mpz_set(n, tempn);

    //encrypts the file
    rsa_encrypt_file(infile, outfile, n, e);

    fclose(infile);
    fclose(outfile);
    fclose(pbfile);
    mpz_clears(n, e, s, tempe, tempn, rop, NULL);

    return 0;
}
