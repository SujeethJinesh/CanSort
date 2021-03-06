#include <Servo.h> 

#define IR_PIN 8
#define TRIG_PIN 13
#define ECHO_PIN 12
Servo rservo;
Servo vservo1;
Servo vservo2;
int rval;
 
void setup() 
{ 
  rservo.attach(9);
  vservo1.attach(10);
  vservo2.attach(11);
  pinMode(IR_PIN, INPUT);
  Serial.begin(9600);
  pinMode(TRIG_PIN,OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  rval = 90;
} 
 
void loop() 
{ 
  
  digitalWrite(TRIG_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_PIN,HIGH);
  delayMicroseconds(10);
  long duration = pulseIn(ECHO_PIN,HIGH);
  long distance = (duration/2)/29.1;
  
  if (Serial.available()) {
    rval = (int) Serial.read();
    dumpTrash();
  }
  if(distance < 20) {
    Serial.println("SERIAL IS AVAILABLE"); 
  }
} 

void dumpTrash() {
  digitalWrite(13, HIGH);
  rservo.write(rval);
  delay(5000);
  vservo1.write(45);
  vservo2.write(45);
  delay(3000);
  vservo1.write(90);
  vservo2.write(90);
  delay(3000);
}
