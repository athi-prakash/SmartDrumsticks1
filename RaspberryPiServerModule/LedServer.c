/* 
 * File: LedServer.c
 * Author: Scott Bailey
 * Student ID: B00636779
 * Purpose: This file contains LED server. It lets the main app connect to the raspberry pi.
 *          Every time a message is received from the app, the LED blinks.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/sendfile.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <fcntl.h>
#include "network.h"
#include "LEDInterface.h"


/// DEFINITIONS ///

// Pin numbers for wiring pi.
#define LED_PIN 0
#define VIBE_PIN 1

// In reality only one bit is needed.
#define MAX_BUFFER_SIZE 8


/// PRIVATE DATA AND FUNCTION DECLARATIONS ///

// Create a static variable for the 8 byte message buffer.
static char r_buffer[MAX_BUFFER_SIZE];

// Introduce the file descriptor integer of the request.
static int client_socket = -1;

// Default not to have verbose output from the server. 
// Can be set via command line argument 2 '-v' for verbose.
static int verbose_enabled = 0;
	
// Invalid port id that must be overridden by argv[1].
static int portId = -1; 
	
// Fwd Declarations of the server private functions.
void RunServer(void);
void ServeClient(void);
void ParseArgs(int argc, char** argv);



/// FUNCTION DEFINITIONS ///

/*
 * The main function takes one command line argument which is the port number to
 * connect bind the network to. 
 *
 * Running user will require network priveleges from the OS.
 * 
 * Usage: ./LedServer PORT [-v]
				PORT = 1024..65534  * The port to listen on. *
				-v   * A flag enabling the server to print to stdio at runtime, this slightly 
						hurts performance and should be used only to debug issues. *
 */
int main(int argc, char *argv[])
{
    // Initialize GPIO pins to a known state.
	initBlinker(LED_PIN, VIBE_PIN);
	
	// Get the port number and verbose flag or exit.
	ParseArgs(argc, argv);
	
	// NetworkStartup will bind the server to the port through a server socket.
	NetworkStartup( portId );
	
	// This function will not return.
	RunServer();
	
	return 0;
}

/*
   The main server loop. Wait for a connection, then serve that connection until it breaks. 
   When the connection is broken we just reconnect. This function never returns.
*/
void RunServer(void)
{
	// Main program loop.
	while(1){
		if (verbose_enabled)
			printf("\nWaiting for a client connection to port %d...\n", portId);

		// WaitForConnect() will hold the program until a client accesses the port.
		WaitForConnect();
		
		// Once we have a connection, serve it until the connection breaks then come back to the waiting loop.
		ServeClient();
	}
}

/*
	Open a socket to the client that is waiting to connect. 
	Read each bit in the IP stream as an indication to send a pulse.
	
	Returns only if the connection fails (is lost).
*/
void ServeClient(void)
{
	// OpenClientSocket() will create a socket and return a file descriptor for r/w.
	client_socket = OpenClientSocket();
	
	if (client_socket < 0){
		printf("Network error, could not connect to the client socket. \n");
		exit(-1);
	}
    
	// timeout value of 0 seconds.
	// We only care about one bit at a time, so we want recv to return ASAP.
	struct timeval t;    
	t.tv_sec = 0;
	setsockopt(client_socket, SOL_SOCKET, SO_RCVTIMEO, (const void *)(&t), sizeof(t)); 			
		
	// While connected read a bit at a time from the Android phone application. This code 
	// should be kept as efficient as possible to be close to realtime.
	while(1) {
		
		// Possibly switch this to an ifdef to prevent runtime checks.
		if(verbose_enabled)
			printf("Reading from socket %d.\n",client_socket);

		// Read in the message. Each bit is a 'beat' so they should be read individually.
		// No flags required.

		// for now every message will cause a blink and vibration pulse.
		// Eventually buffering should be implemented to reduce network latency.
		if (recv(client_socket, r_buffer, 1, 0) > 0){
			
			// Blink Pin 0  == GPIO 17 on by setting it low for 200ms then back up to high. 
			// LED is set-up using the 5V pin as the power source and the IO pin as the sink so that eventually
			// batteries could be used to reduce board power consumption.
			// Buzz  Pin 1  == GPIO 18 using the same method.
			blinkLEDandVibe(LED_PIN, VIBE_PIN, 200);
			
			// Possibly switch this to an ifdef to prevent runtime checks.
			if(verbose_enabled)
				printf("Received a message %s.\n", r_buffer);
			
		// Connection issue.
		} else {
			
			if (verbose_enabled)
				printf("\nConnection lost. Retrying.. (%d).\n", client_socket);
			
			// Dead connection, wait for a new one.
			close(client_socket);
			break;
		}

	}
	// Exit condition: Connection breaks.
}

/*
   Parse the command line arguments for a good port number or else exit. Also accepts a -v verbose flag.
   
   Could be expanded for future options such as IP addresses.
*/
void ParseArgs(int argc, char** argv)
{
	// Initialize the buffer as NUL.
	memset(r_buffer, '\0', MAX_BUFFER_SIZE);
	
	// A little bit of pessimism for health reasons.
	int result = -1;
	
	// If there are only two arguments (inc. file name) the other must be a valid
	// port number or else the server will quit.
	if (argc == 2) {
		result = sscanf(argv[1], "%d", &portId);
		
		if (result != 1 ){
			printf("\nThe port id entered was not a valid integer.\n");
			exit(-1);
		}
	}
	
	// If there are three arguments there should be a valid port number
	// and a valid flag for enabling verbose mode.
	else if (argc == 3){
		result = sscanf(argv[1], "%d", &portId);
		if (result != 1 ){
			printf("\nThe port id entered was not a valid integer.\n");
			exit(-1);
		}
		if (strcmp(argv[2], "-v") == 0){
			verbose_enabled = 1;
		}
		else{
			printf("\nIgnoring argument 2: '%s', use -v for verbose.\n", argv[2]);
		}
	}
	
	// Not enough arguments.
	else {
		printf("The port number is a required input to this program. %d\n", argc);
		exit(-1);
	}
	
	// Verify the range of the port that we will support.
	if (!((portId < 65335) && (portId > 1024))) {
		printf("You have entered %d, an invalid port for user level processes.\n"
									 "Please choose a port between 1024 and 65335.\n", portId);
		exit(-1);
	}
}
