#include <Servo.h>

Servo thing;
Servo thing2;
void setup() 
{
  pinMode(3, OUTPUT);
  pinMode(5, OUTPUT);
  thing.attach(3);
  thing2.attach(5);
  Serial.begin(9600);
}

void loop() 
{
  thing.write(80)
  thing2.write(50);
  delay(1000);
  thing.write(100);
  thing2.write(80);
  delay(1000);
}  
