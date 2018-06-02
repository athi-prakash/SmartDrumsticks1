#include <wiringPi.h>
#include "LEDInterface.h"

#include <stdio.h>

/*
   Initialize the LED and Vibration Pins.
*/
void initBlinker(int lpin, int vpin)
{
	// A call to the wiring pi library's initialization process.
    wiringPiSetup();
	
	// Initialize the IO modes of the pins.
    pinMode(lpin,OUTPUT);
	pinMode(vpin,OUTPUT);
	
	// Pulse.
    blinkLED(lpin, vpin, 500);
}

/*
	Pulse the indicators for the user.
*/ 
void blinkLEDandVibe(int ledpin, int vibepin, int delayTime)
{
	/*
		The LED is to be powered by some supply which could be either 
		the raspberry pi's 5v pin or another source, therefore this function
		just enable current flow by putting the pins into a low voltage state
		to act as a path to ground.
		
		The opposite is true for the vibrator.
	*/
    digitalWrite(vibepin, HIGH); 
	digitalWrite(ledpin, LOW); 
	
	// Pulse for delatTime milliseconds.
	delay (delayTime);
    
	// Cut off the current.
	digitalWrite(ledpin, HIGH);
	digitalWrite(vibepin, LOW);
}


