#include <stdbool.h>

#include "rotor_node.h"

#define ROTOR_WIDTH 256

/*******************************************************************************
 * NAME:
 *    Rotor
 * DESCRIPTION:
 *    Simulates the individual clock rotors of an enigma machine. Contains a
 *    fixed array of one-to-one input to output translations. It also contains
 *    a pointer to a potential neighbor rotor to pass translated input to for
 *    further conversion. The rotor can turn on be stationary (e.g. to simulate
 *    a stationary reflecting rotor).
 *******************************************************************************
 * NAME:
 *    turn_rate
 * TYPE:
 *    uint8_t
 * DESCRIPTION:
 *    The amount of turns this rotor must complete before it commands the next
 *    rotor to turn (if not stationary).
 ****************
 * NAME:
 *    current_pos
 * TYPE:
 *    uint8_t
 * DESCRIPTION:
 *    The current turn position of the rotor (if not stationary).
 ****************
 * NAME:
 *    init_pos
 * TYPE:
 *    uint8_t
 * DESCRIPTION:
 *    The starting position of the rotor when it is created. (used to reset if
 *    not stationary).
 ****************
 * NAME:
 *    turn_flag
 * TYPE:
 *    bool
 * DESCRIPTION:
 *    Flag denoting if the rotor turns when given input, and causes neighbors to
 *    turn after the turn rate is reached. If false then the rotor will remain
 *    stationary after given input.
 ****************
 * NAME:
 *    node_map
 * TYPE:
 *    Array of RotorNode
 * DESCRIPTION:
 *    Map of the one-to-one node connections. All connections uniquely point to
 *    another connection, and all connection point back to the connections that
 *    point to them.
 ******************************************************************************/
typedef struct Rotor
{
   uint8_t turn_rate;
   uint8_t current_pos;
   uint8_t init_pos;
   bool turn_flag;
   RotorNode node_map[ROTOR_WIDTH];
   struct Rotor * neighbor;

   // TODO: Add function pointers to map to rotor.c functions.
   void (* turn)(void * self);
   void (* reset)(void * self);

} Rotor;

Rotor * init();
void turn_func(void * self);