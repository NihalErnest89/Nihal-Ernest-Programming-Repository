#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <gmp.h>
#include "numtheory.h"
#include "randstate.h"

// @desc: stores the greates common divios of a and b in d
// @params: d is where the output is stored, a and b are the numbers whose gcd is being determined
// @return: N/A
// @cite: assignment doc
void gcd(mpz_t d, mpz_t a, mpz_t b) {
    mpz_t t;
    mpz_t m;
    mpz_inits(t, m, NULL);
    while (mpz_cmp_ui(b, 0) != 0) {
        mpz_set(t, b);
        mpz_mod(m, a, b);
        mpz_set(b, m);
        mpz_set(a, t);
    }
    mpz_set(d, a);
    mpz_clears(t, m, NULL);
}

// @desc: stores the inverse of a modulo n in i
// @params: i is where the inverse mod is stored, a is the number being modulused by n
// @return: N/A
// @cite: assignment doc
void mod_inverse(mpz_t i, mpz_t a, mpz_t n) {
    mpz_t r, rp, t, tp;

    mpz_init_set(r, n);
    mpz_init_set(rp, a);
    mpz_init_set_ui(t, 0);
    mpz_init_set_ui(tp, 1);

    mpz_t q, temp, pr;

    mpz_inits(q, temp, pr, NULL);
    while (mpz_cmp_ui(rp, 0) != 0) {
        mpz_fdiv_q(q, r, rp);
        mpz_set(temp, r);
        mpz_set(r, rp);

        mpz_mul(pr, q, rp);
        mpz_sub(rp, temp, pr);

        mpz_set(temp, t);
        mpz_set(t, tp);
        mpz_mul(pr, q, tp);
        mpz_sub(tp, temp, pr);
    }

    if (mpz_cmp_ui(r, 1) > 0) {
        mpz_set_ui(i, 0);
        mpz_clears(r, rp, t, tp, q, temp, pr, NULL);
        return;
    }

    if (mpz_cmp_ui(t, 0) < 0) {
        mpz_add(t, t, n);
    }
    mpz_set(i, t);
    mpz_clears(r, rp, t, tp, q, temp, pr, NULL);
    return;
}

// @desc: stores the base raised to the exponent power modulo modulus, in out
// @param: out is where the result is stored, base, exponent and modulus have roles in the equation in accordance with their names
// @return: N/A
// @cite: assignment doc
void pow_mod(mpz_t out, mpz_t base, mpz_t exponent, mpz_t modulus) {
    mpz_t v, p, temp, temp2;
    mpz_inits(v, p, temp, temp2, NULL);

    mpz_set_ui(v, 1);
    mpz_set(p, base);

    while (mpz_cmp_ui(exponent, 0) > 0) {

        mpz_mod_ui(temp, exponent, 2);
        if (mpz_cmp_ui(temp, 1) == 0) {
            mpz_mul(v, v, p);
            mpz_mod(v, v, modulus);
        }

        mpz_set(temp2, p);
        mpz_mul(p, temp2, p);

        mpz_mod(p, p, modulus);
        mpz_fdiv_q_ui(exponent, exponent, 2);
    }

    mpz_set(out, v);
    mpz_clears(v, p, temp, temp2, NULL);
    return;
}

// @desc: determines whether or not n is a prime number, given iters number of iterations
// @param: n is the number whose primality is being determined, iters is the number of iters to be run for prime checking
// @return: boolean representing whether or not n is prime
// @cite: assignment doc, TA Eugene's section
bool is_prime(mpz_t n, uint64_t iters) {
    mpz_t r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, two, mod;
    mpz_inits(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);

    mpz_mod_ui(mod, n, 2);
    if (mpz_cmp_ui(mod, 0) == 0 && mpz_cmp_ui(n, 2) != 0) {
        mpz_clears(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
        return false;
    }

    if (mpz_cmp_ui(n, 0) == 0 || mpz_cmp_ui(n, 1) == 0) {
        mpz_clears(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
        return false;
    }
    if (mpz_cmp_ui(n, 2) == 0 || mpz_cmp_ui(n, 3) == 0) {
        mpz_clears(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
        return true;
    }

    mpz_sub_ui(r, n, 1);
    mpz_set_ui(base, 2);
    while (mpz_even_p(r) != 0) {
        mpz_add_ui(s, s, 1);
        mpz_fdiv_q_ui(r, r, 2);
    }

    for (uint64_t i = 1; i <= iters; i += 1) {

        mpz_sub_ui(diff, n, 3);
        mpz_urandomm(a, state, diff);
        mpz_add_ui(a, a, 2);

        pow_mod(y, a, r, n);

        mpz_sub_ui(ycon, n, 1);
        if ((mpz_cmp_ui(y, 1) != 0) && (mpz_cmp(y, ycon) != 0)) {
            mpz_set_ui(j, 1);

            mpz_sub_ui(scon, s, 1);
            while ((mpz_cmp(j, scon) <= 0) && (mpz_cmp(y, ycon) != 0)) {
                mpz_set_ui(two, 2);
                pow_mod(y, y, two, n);
                if (mpz_cmp_ui(y, 1) == 0) {
                    mpz_clears(
                        r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
                    return false;
                }
                mpz_add_ui(j, j, 1);
            }
            if (mpz_cmp(y, ycon) != 0) {
                mpz_clears(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
                return false;
            }
        }
    }
    mpz_clears(r, s, exp, base, sub, a, diff, temp, y, ycon, j, scon, mod, two, NULL);
    return true;
}

//@desc: makes a prime number of at least bits bits, with iters iterations
//@param: p is the prime number being made, bits is the lowest number of bits the prime should be, and iters is for checking if p is prime
//@return: N/A
//@cite: assignment doc
void make_prime(mpz_t p, uint64_t bits, uint64_t iters) {
    mpz_t n;
    mpz_init(n);
    while (!is_prime(n, iters) || (mpz_sizeinbase(n, 2) < bits)) {
        //while (!is_prime(n, iters)) {
        mpz_urandomb(n, state, bits);
    }
    mpz_set(p, n);
    mpz_clear(n);
    return;
}
