# Builds the server application. Simply call "make".
# 
# Targets & general dependencies
PROGRAM = LedServer
HEADERS = network.h wiringPi.h LEDInterface.h
OBJS = LedServer.o LEDInterface.o network.o
ADD_OBJS = 

# compilers, linkers, utilities, and flags
CC = gcc
CFLAGS = -Wall -g
COMPILE = $(CC) $(CFLAGS)
LINK = $(CC) $(CFLAGS) -o $@ 

# implicit rule to build .o from .c files
%.o: %.c $(HEADERS)
	$(COMPILE) -c -o $@ $<


# explicit rules
all: LedServer

$(PROGRAM): $(OBJS) $(ADD_OBJS)
	$(LINK) $(OBJS) $(ADD_OBJS) -lwiringPi
