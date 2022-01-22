CC = clang
CFLAGS = -Wall -Wpedantic -Werror -Wextra $(shell pkg-config --cflags gmp)
LFLAGS = $(shell pkg-config --libs gmp) -lm

all: encrypt decrypt keygen

encrypt: randstate.o numtheory.o rsa.o encrypt.o
	$(CC) $(LFLAGS) -o encrypt randstate.o numtheory.o rsa.o encrypt.o

decrypt: randstate.o numtheory.o rsa.o decrypt.o
	$(CC) $(LFLAGS) -o decrypt randstate.o numtheory.o rsa.o decrypt.o

keygen: randstate.o numtheory.o rsa.o keygen.o
	$(CC) $(LFLAGS) -o keygen randstate.o numtheory.o rsa.o keygen.o

encrypt.o: encrypt.c
	$(CC) $(CFLAGS) -c encrypt.c

decrypt.o: decrypt.c
	$(CC) $(CFLAGS) -c decrypt.c

keygen.o: keygen.c
	$(CC) $(CFLAGS) -c keygen.c

randstate.o: randstate.c
	$(CC) $(CFLAGS) -c randstate.c

numtheory.o: numtheory.c
	$(CC) $(CFLAGS) -c numtheory.c

rsa.o: rsa.c
	$(CC) $(CFLAGS) -c rsa.c

clean:
	rm -f encrypt decrypt keygen *.o

format:
	clang-format -i -style=file *.[ch]

