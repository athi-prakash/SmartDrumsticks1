// Credit to Dr. Brodsky example implementation of a simple 'c' socket server.


#include <stddef.h>
#include <math.h>
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <errno.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <poll.h>

#include "network.h"

static int serverSocket = -1; //err val

// Wait for infinitely for a network client using select.
extern void WaitForConnect() {
  int rlt;            
  fd_set sel;                    
  fd_set err;
  
  if( serverSocket < 0 ) 
  {                                
    perror( "Error, network not initalized" );
    abort();
  }

  FD_ZERO( &sel );                                   
  FD_ZERO( &err );
  FD_SET( serverSocket, &sel );
  FD_SET( serverSocket, &err );

  rlt = select( serverSocket + 1, &sel, NULL, &err, NULL );  

  if( ( rlt <= 0 ) || FD_ISSET( serverSocket, &err ) ) 
  {    
    perror( "Error occurred while waiting" );
    abort();
  } 
}


// Once a client is available use select, accept to connect to the client.
extern int OpenClientSocket() {
  struct sockaddr_in server;                            /* addr of client */
  int len = sizeof( server );                           /* length of addr */
  int n;                                                /* return var */
  int sock = -1;                                        /* socket for client */
  fd_set sel;                                           /* descriptor bits */
  fd_set err;
  struct timeval tv;                                    /* time to wait */
  
  if( serverSocket < 0 ) {                                 /* sanity check */
    perror( "Error, network not initalized" );
    abort();
  }

  FD_ZERO( &sel );                                      /* check for client */
  FD_ZERO( &err );
  FD_SET( serverSocket, &sel );
  FD_SET( serverSocket, &err );
  memset( &tv, 0, sizeof( tv ) );
  n = select( serverSocket + 1, &sel, NULL, &err, &tv );

  if( ( n < 0 ) || FD_ISSET( serverSocket, &err ) ) {      /* check for errors */
    perror( "Error occurred on select()" );
    abort();
  } else if( ( n > 0 ) && FD_ISSET( serverSocket, &sel ) ) { /* client is waiting*/
    /* get client connection */
    sock = accept( serverSocket, (struct sockaddr *)&server, (socklen_t *)&len );

    if( sock < 0 ) {                                    /* check for errors */
      perror( "Error occurred on select()" );
    }
  }
  return sock;                                          
}

// Init the server socket. Bind and listen to port.
extern void NetworkStartup( int port ) {
  struct sockaddr_in self;                            
  int yes = 1;                                        
  
  // TCP SOCK_STREAM socket. 
  serverSocket = socket( PF_INET, SOCK_STREAM, 0 );      
  if( serverSocket < 0 ) {
    perror( "Error while creating server socket" );
    abort();
  } 

  // persist.
  setsockopt( serverSocket, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof( int ) );
  setsockopt( serverSocket, SOL_SOCKET, SO_KEEPALIVE, &yes, sizeof( int ) );

  // Internet socket.
  self.sin_family = AF_INET;                           
  self.sin_addr.s_addr = htonl( INADDR_ANY );
  self.sin_port = htons( port );
  
  // Reserve the bind the port provided to this socket.
  if( bind( serverSocket, (struct sockaddr *)&self, sizeof( self ) ) )  {
    perror( "Error on bind()" );
    abort();
  }

  // listen for connections enables use of select, accept.
  if( listen( serverSocket, 64 ) ) {                     
    perror( "Error on listen()" );
    abort();
  }
}





