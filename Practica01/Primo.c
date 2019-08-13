#include <stdio.h>

int esPrimo(int a);

int main()
{
	printf("%s\n", "Introduce un número:");
	int t;
	scanf("%i", &t);
	printf("%s%i\n", "Introdujiste: ", t);
	printf("%s\n", "Los números primos menores a él, son: ");

	int y;
	for(y = t -1; y >= 1; y--)
		if(esPrimo(y))
			printf("%i\n", y);
}

int esPrimo(int a)
{
	int x;

	for(x = a / 2; x >= 2; x--)
		if((a % x) == 0)
			return 0;

	return 1;
}