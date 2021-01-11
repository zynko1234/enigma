
#include <stdlib.h>
#include <stdio.h>

/*******************************************************************************
 * MACROS
 ******************************************************************************/

/*
 * Macro for calculating the length of a self-size storing array.
 * 
 * Example:
 * long * ex_array = array_malloc(sizeof(long) * 15)
 * x = array_size(ex_array);
 * y = ARRAY_LENGTH(ex_array, ex_array[0]);
 * 
 * x is 120
 * y is 15
 */  
#define ARRAY_LEN(array_ptr, array_type) (array_size(array_ptr) / sizeof(array_type))

/*
 * Macro for multiplying a length by the size of uint8_t.
 */
#define SIZE_U8(length) (sizeof(uint8_t) * length)

/*
 * Macro for multiplying a length by the size of uint16_t.
 */
#define SIZE_U16(length) (sizeof(uint16_t) * length)

/*
 * Macro for multiplying a length by the size of uint32_t.
 */
#define SIZE_U32(length) (sizeof(uint32_t) * length)

/*
 * Macro for multiplying a length by the size of uint64_t.
 */
#define SIZE_U64(length) (sizeof(uint64_t) * length)

/*
 * Macro for multiplying a length by the size of chars.
 */
#define SIZE_CHAR(length) (sizeof(char) * length)

/*
 * Macro for multiplying a length by the size of ints.
 */
#define SIZE_INT(length) (sizeof(int) * length)

/*
 * Macro for multiplying a length by the size of floats.
 */
#define SIZE_FLOAT(length) (sizeof(float) * length)

/*
 * Macro for multiplying a length by the size of doubles.
 */
#define SIZE_DOUBLE(length) (sizeof(double) * length)

/*******************************************************************************
 * ARRAY UTILITY FUNCTIONS
 ******************************************************************************/

void * array_malloc(size_t s) 
{
  size_t * ret = malloc(sizeof(size_t) + s);
  *ret = s;
  return &ret[1];
}

void array_free(void * ptr) 
{
  free( (size_t*)ptr - 1);
}

size_t array_size(void * ptr) 
{
  return ((size_t*)ptr)[-1];
}