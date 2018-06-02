
#ifndef NETWORK_H
#define NETWORK_H

#include <stdio.h>

// Initialize network module variables. Call First.
extern void NetworkStartup( int port );

// Blocks until there is a network connection. Call second.
extern void WaitForConnect();

// Call after WaitForConnect to create and return the socket file descriptor.
extern int OpenClientSocket();

#endif
