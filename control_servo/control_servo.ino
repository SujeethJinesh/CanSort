#include <Servo.h> 
 
Servo rservo;
Servo vservo1;
Servo vservo2;
int rval;
 
void setup() 
{ 
  rservo.attach(9);
  vservo1.attach(10);
  vservo2.attach(11);
  Serial.begin(9600);
  rval = 90;
} 
 
void loop() 
{ 
  if (Serial.available()) {
    rval = (int) Serial.read();
    dumpTrash();
  }
  rservo.write(rval);                  // sets the servo position according to the scaled value 
  delay(15);                           // waits for the servo to get there 
} 

void dumpTrash() {
  rservo.write(rval);
  delay(5000);
  vservo1.write(45);
  vservo2.write(45);
  delay(3000);
  vservo1.write(90);
  vservo2.write(90);
  delay(3000);
}
