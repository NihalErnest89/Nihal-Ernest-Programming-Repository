#pragma once

#include <stdint.h>
#include <gmp.h>

extern gmp_randstate_t state;

void randstate_init(uint64_t seed);

void randstate_clear(void);
