/*Synerduino Rdar using ultransonic sensors upto 2 meters*/
/*in your arduino  Trigger pin D2 Echo Pin D3 */
/*Bluetooth/Radio is set at 57600 recommended */
/*look for Data folder in the application folder and chenge the COM # to which ever your device is assign to*/
// Includes the Servo library
#include <Servo.h>

/* Defines Tirg and Echo pins of the Ultrasonic Sensor*/
const int trigPin = 2;
const int echoPin = 3;

/* Variables for the duration and the distance*/
long duration;
int distance;

/*set Distance to trigger the PWM Aux Switch in cm*/
int inputdistance = 100; // set Distance to trigger the PWM Aux Switch in cm

  Servo myServo;// Creates a servo object for controlling the servo motor
  Servo myServoA;// Creates a servo object for controlling the servo motor 
  Servo myServoB;// Creates a servo object for controlling the servo motor
  void setup() {
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  /*Serial.Baud Rate*/
  //Serial.begin(9600);//Lower refresh speed more range
  Serial.begin(57600);//use by SIK radios set as RAW serial
  //Serial.begin(115200);//higher refresh speed less range
  
  /*PWM input and Output */
  myServo.attach(4); // Defines on which pin is the Radar servo motor attached
  myServoA.attach(5); // Defines on which pin is the  A servo PWM Out attached(connect for Controller)
  myServoB.attach(6); // Defines on which pin is the  B servo PWM Out attached(connect for Controller)

  
//------------------------------------------------------------------------------
pinMode( 13 , OUTPUT); // LED indicator
//------------------------------------------------------------------------------

}
void loop() {

  
  myServoA.write( 60 );     //Servo A (degrees
  myServoB.write( 60 );     //Servo B(degrees)
  
//------------------------------------------------------------------------------  
  // rotates the servo motor from 15 to 90 degrees
  for(int i=165;i>90;i--)
  { myServo.write(i);
    
  distance = calculateDistance();// Calls a function for calculating the distance measured by the Ultrasonic sensor for each degree
  delay(5);
  Serial.print(i); // Sends the current degree into the Serial Port
  Serial.print(","); // Sends addition character right next to the previous value needed later in the Processing IDE for indexing
  Serial.print(distance); // Sends the distance value into the Serial Port
  Serial.print("."); // Sends addition character right next to the previous value needed later in the Processing IDE for indexing
  
  }

 {
  
}
//------------------------------------------------------------------------------

 if (( ( distance ) < ( inputdistance ) ))
  {
    digitalWrite(13 , HIGH);  //Relay Pin condition
    myServoA.write( 120 );    //Servo A (degrees) 
    myServoB.write( 120 );    //Servo B(degrees) 
    Serial.print("object");
    delay(3000);              //how long to hold this mode after the radar detech a near obstacle  

    
  }
  else
  {
    digitalWrite(13 , LOW);  //Relay Pin condition
    myServoA.write( 30 );    //Servo A(degrees)
    myServoB.write( 30 );    //Servo B(degrees)
  }
//------------------------------------------------------------------------------
 // rotates the servo motor from 90 to 165 degrees
  for(int i=90;i>15;i--)
  { myServo.write(i);
    
  distance = calculateDistance();// Calls a function for calculating the distance measured by the Ultrasonic sensor for each degree
  delay(5);
  Serial.print(i); // Sends the current degree into the Serial Port
  Serial.print(","); // Sends addition character right next to the previous value needed later in the Processing IDE for indexing
  Serial.print(distance); // Sends the distance value into the Serial Port
  Serial.print("."); // Sends addition character right next to the previous value needed later in the Processing IDE for indexing

  }

 {
  
}
//------------------------------------------------------------------------------


  if (( ( distance ) < ( inputdistance ) ))
  {
    digitalWrite(13 , HIGH);
    myServoA.write( 120 );
    myServoB.write( 120 );
    Serial.print("object");
    delay(3000);

  }
  else
  {
    digitalWrite(13 , LOW);
    myServoA.write( 30 );
    myServoB.write( 30 );
  }
//------------------------------------------------------------------------------
  // Repeats the previous lines from 165 to 90 degrees
  for(int i=15;i<=90;i++)
  { myServo.write(i);
  delay(5);
  distance = calculateDistance();
  Serial.print(i);
  Serial.print(",");
  Serial.print(distance);
  Serial.print(".");
  
  }
{
  
}
//------------------------------------------------------------------------------


  if (( ( distance ) < ( inputdistance ) ))
  {
    digitalWrite(13 , HIGH);
    myServoA.write( 120 );
    myServoB.write( 120 );
    Serial.print("object");
    delay(3000);

  }
  else
  {
    digitalWrite(13 , LOW);
    myServoA.write( 30 );
    myServoB.write( 30 );
  }
//------------------------------------------------------------------------------
  
  // Repeats the previous lines from 90 to 15 degrees
  for(int i=90;i<=165;i++)
  { myServo.write(i);
  delay(5);
  distance = calculateDistance();
  Serial.print(i);
  Serial.print(",");
  Serial.print(distance);
  Serial.print(".");
  
  }
{
  
}

//------------------------------------------------------------------------------


  if (( ( distance ) < ( inputdistance ) ))
  {
    digitalWrite(13 , HIGH);
    myServoA.write( 120 );
    myServoB.write( 120 );
    Serial.print("object");
    delay(3000);

       
  }
  else
  {
    digitalWrite(13 , LOW);
    myServoB.write( 30 );
    myServoB.write( 30 );
  }


//------------------------------------------------------------------------------

}
// Function for calculating the distance measured by the Ultrasonic sensor
int calculateDistance(){ 
  
  digitalWrite(trigPin, LOW); 
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH); 
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH); // Reads the echoPin, returns the sound wave travel time in microseconds
  distance= duration*0.034/2;
  return distance;
}
