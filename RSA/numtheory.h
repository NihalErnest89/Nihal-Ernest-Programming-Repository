#pragma once

#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <gmp.h>

void gcd(mpz_t d, mpz_t a, mpz_t b);

void mod_inverse(mpz_t i, mpz_t a, mpz_t n);

void pow_mod(mpz_t out, mpz_t base, mpz_t exponent, mpz_t modulus);

bool is_prime(mpz_t n, uint64_t iters);

void make_prime(mpz_t p, uint64_t bits, uint64_t iters);
