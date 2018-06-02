# READ ME FOR THE RASPBERRY PI MODULE

## Description
The raspberry pi behaves as the server for the drumstick application.
Using tethering or a common wifi network, the android phone can connect to the server
by opening a socket on port 8080 with the static ip 192.168.43.10.

Every time the application sends the raspberry pi an IP message, the LED and vibrator will pulse.

## Set-up
The application automatically starts when the Raspberry Pi turns on using the following command in the /etc/rc.local script.

sudo /home/pi/LedServer 8080

## Hardware set-up. 

See user guide.