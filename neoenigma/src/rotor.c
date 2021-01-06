#include <stddef.h>

#include "rotor.h"

Rotor* init()
{
   Rotor * ret = (Rotor *)malloc(sizeof(Rotor));
   ret->current_pos = 0;
   ret->init_pos = 0;
   ret->neighbor = NULL;

   // TODO: Figure out if this is the way I want to do this initialization.
   // Initialize the map nodes.
   size_t mapsize = sizeof(ret->node_map) / sizeof(RotorNode);

   for(uint8_t i = 0; i < mapsize; i++)
   {

   }

   // Set all the function pointers.
   ret->turn = &turn_func;

   return ret;
}

void turn_func(void * self)
{
   Rotor * current_rotor = (Rotor *)self; 
   current_rotor->current_pos++;

   // TODO: Add the logic for triggering a turn of the neighbor rotor.
   if(0)
   {
      // Recursively call turn on the neighbor in the chain of neighbor rotors.
      if(current_rotor->neighbor != NULL)
      {
         Rotor * neighbor_rotor = current_rotor->neighbor;
         neighbor_rotor->turn(neighbor_rotor);
      }
   }

   return;
}