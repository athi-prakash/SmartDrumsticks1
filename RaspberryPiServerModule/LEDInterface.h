#ifndef LED_INTERFACE_H
#define LED_INTERFACE_H

/*
	Initialize the output pins for the application.
*/
extern void initBlinker(int, int);

/*
	Trigger a blink.
*/
extern void blinkLEDandVibe(int,int);

#endif
