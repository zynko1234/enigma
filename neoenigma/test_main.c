#include <stdio.h>
#include "src/rotor.h"
#include "src/util.h"

int main(int argc, char ** argv)
{
    printf("%s\n", "TEST MAIN ENTRY POINT");
    uint8_t * array = array_malloc(SIZE_U8(12));
    printf("Array size %lu\n", array_size(array));
    printf("Array length %lu\n", ARRAY_LEN(array, array[0]));
    array_free(array);
    return 0;
}