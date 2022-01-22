#include <stdint.h>
#include <gmp.h>
#include "randstate.h"

gmp_randstate_t state;

//@desc: Initializes the global random state named state with a Mersenne Twister algorithm, using seed as the random seed
//@param: seed to randomize the state;
//@return: N/A
//@cite: N/A
void randstate_init(uint64_t seed) {
    gmp_randinit_mt(state);
    gmp_randseed_ui(state, seed);
    return;
}

//@desc: Clears and frees all memory used by the initialized global random state named state.
//@param: N/A
//@return: N/A
//@cite: N/A
void randstate_clear(void) {
    gmp_randclear(state);
    return;
}
