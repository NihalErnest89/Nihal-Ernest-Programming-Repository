#pragma once

#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <gmp.h>

void rsa_make_pub(mpz_t p, mpz_t q, mpz_t n, mpz_t e, uint64_t nbits, uint64_t iters);

void rsa_write_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile);

void rsa_read_pub(mpz_t n, mpz_t e, mpz_t s, char username[], FILE *pbfile);

void rsa_make_priv(mpz_t d, mpz_t e, mpz_t p, mpz_t q);

void rsa_write_priv(mpz_t n, mpz_t d, FILE *pvfile);

void rsa_read_priv(mpz_t n, mpz_t d, FILE *pvfile);

void rsa_encrypt(mpz_t c, mpz_t m, mpz_t e, mpz_t n);

void rsa_encrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t e);

void rsa_decrypt(mpz_t m, mpz_t c, mpz_t d, mpz_t n);

void rsa_decrypt_file(FILE *infile, FILE *outfile, mpz_t n, mpz_t d);

void rsa_sign(mpz_t s, mpz_t m, mpz_t d, mpz_t n);

bool rsa_verify(mpz_t m, mpz_t s, mpz_t e, mpz_t n);
