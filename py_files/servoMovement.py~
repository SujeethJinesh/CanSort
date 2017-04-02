import serial

COMPOST_CONFIG = bytes([0])
LANDFILL_CONFIG = bytes([90])
RECYCLING_CONFIG = bytes([180])

def set_servos(can):
    arduinoSerialData = serial.Serial('/dev/ttyACM0', 9600)
    if can == 'compost':
        arduinoSerialData.write(COMPOST_CONFIG)
    elif can == 'landfill':
        arduinoSerialData.write(LANDFILL_CONFIG)
    else:
        arduinoSerialData.write(RECYCLING_CONFIG)
