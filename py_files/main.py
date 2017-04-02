import serial
import os
arduinoSerialData = serial.Serial('/dev/ttyACM0', 9600)
while 1:
    if(arduinoSerialData.inWaiting()):
        print "got here"
        myData = arduinoSerialData.readline()
        if myData[:len('SERIAL IS AVAILABLE')] == 'SERIAL IS AVAILABLE':
            os.system('python3 image_capture3.py')
        while arduinoSerialData.inWaiting():
            arduinoSerialData.readline()
