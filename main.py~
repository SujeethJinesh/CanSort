import serial
import os
arduinoSerialData = serial.Serial('/dev/ttyACM0', 9600)
while 1:
    if(arduinoSerialData.inWaiting()):
        myData = arduinoSerialData.readline()
        if myData == 'detected':
            os.system('python3 image_capture3.py')
