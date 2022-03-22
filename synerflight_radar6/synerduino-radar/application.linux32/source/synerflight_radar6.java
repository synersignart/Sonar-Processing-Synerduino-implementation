import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import java.awt.event.KeyEvent; 
import java.io.IOException; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class synerflight_radar6 extends PApplet {

 // imports library for serial communication
 // imports library for reading the data from the serial port

Serial myPort; // defines Object Serial
// defubes variables

String angle="";
String distance="";
String data="";
String noObject;
float pixsDistance;
int iAngle, iDistance;
int index1=0;
int index2=0;
PFont orcFont;



public void setup() {
 
String[] lines = loadStrings("data.txt");

for (int b = 0 ; b < lines.length; b++) {

  println("there are " + lines[0] + " lines");
//println("there are " + lines[2] + " lines");
//println("there are " + lines.length + " lines");
println(lines[b]);
}

  // ***CHANGE THIS TO YOUR SCREEN RESOLUTION***
 
 //myPort = new Serial(this, lines[0], 9600); // starts the serial communication see Data Folder
myPort = new Serial(this, lines[0], 57600); // starts the serial communication see Data Folder
 //myPort = new Serial(this, lines[0], 115200); // starts the serial communication see Data Folder
   myPort.bufferUntil('.');                      // reads the data from the serial port up to the character '.'. So actually it reads this: angle,distance.
}



public void draw() {
    
  
  fill(42,52,99);  // Fade color simulating motion blur and slow fade of the moving line
  noStroke();
  fill(0,4); 
  rect(0, 0, width, height-height*0.065f); 
  
  fill(0,247,255); // Radar color
  
  // calls the functions for drawing the radar
  drawRadar(); 
  drawLine();
  drawObject();
  drawText();
}
public void serialEvent (Serial myPort) { // starts reading data from the Serial Port
  // reads the data from the Serial Port up to the character '.' and puts it into the String variable "data".
  data = myPort.readStringUntil('.');
  data = data.substring(0,data.length()-1);
  
  index1 = data.indexOf(","); // find the character ',' and puts it into the variable "index1"
  angle= data.substring(0, index1); // read the data from position "0" to position of the variable index1 or thats the value of the angle the Arduino Board sent into the Serial Port
  distance= data.substring(index1+1, data.length()); // read the data from position "index1" to the end of the data pr thats the value of the distance
  // converts the String variables into Integer
  iAngle = PApplet.parseInt(angle);
  iDistance = PApplet.parseInt(distance);
}
public void drawRadar() {
  pushMatrix();
  translate(width/2,height-height*0.074f); // moves the starting coordinats to new location
  noFill();
  strokeWeight(2);
  stroke(0,245,255);
  //draws the arc lines 1 Meter
 //arc(0,0,(width-width*0.030),(width-width*0.030),PI,TWO_PI);
 //arc(0,0,(width-width*0.230),(width-width*0.230),PI,TWO_PI);
 //arc(0,0,(width-width*0.430),(width-width*0.430),PI,TWO_PI);
 //arc(0,0,(width-width*0.630),(width-width*0.630),PI,TWO_PI);
 //arc(0,0,(width-width*0.830),(width-width*0.830),PI,TWO_PI);
 //arc(0,0,(width-width*1.030),(width-width*1.030),PI,TWO_PI);
 
  //draws the arc lines 2 Meter
 arc(0,0,(width-width*0.030f),(width-width*0.030f),PI,TWO_PI);
 arc(0,0,(width-width*0.280f),(width-width*0.280f),PI,TWO_PI);
 arc(0,0,(width-width*0.530f),(width-width*0.530f),PI,TWO_PI);
 arc(0,0,(width-width*0.780f),(width-width*0.780f),PI,TWO_PI);

  


  
  // draws the angle lines
  line(-width/2,0,width/2,0);
  line(0,0,(-width/2)*cos(radians(30)),(-width/2)*sin(radians(30)));
  line(0,0,(-width/2)*cos(radians(60)),(-width/2)*sin(radians(60)));
  line(0,0,(-width/2)*cos(radians(90)),(-width/2)*sin(radians(90)));
  line(0,0,(-width/2)*cos(radians(120)),(-width/2)*sin(radians(120)));
  line(0,0,(-width/2)*cos(radians(150)),(-width/2)*sin(radians(150)));
  line((-width/2)*cos(radians(30)),0,width/2,0);
  popMatrix();
}
public void drawObject() {
  pushMatrix();
  //translate(width/2,height-height*0.074); // moves the starting coordinats to new location
    translate(width/2,height-height*0.074f); // moves the starting coordinats to new location
  strokeWeight(6);
  stroke(255,10,10); // red color
  pixsDistance = iDistance*((height-height*0.8400f)*0.025f); // covers the distance from the sensor from cm to pixels 2M
  //pixsDistance = iDistance*((height-height*0.6700)*0.025); // covers the distance from the sensor from cm to pixels 1M
  //pixsDistance = iDistance*((height-height*0.1666)*0.025); // covers the distance from the sensor from cm to pixels
  //limiting the range to 200 cms
  if(iDistance<200){
  
  //draws the object according to the angle and the distance
  line(pixsDistance*cos(radians(iAngle)),-pixsDistance*sin(radians(iAngle)),(width-width*0.520f)*cos(radians(iAngle)),-(width-width*0.520f)*sin(radians(iAngle))); // draws the line according to the angle Forward
  //line(pixsDistance*sin(radians(iAngle - 90)),-pixsDistance*cos(radians(iAngle - 90)),(width-width*0.520)*sin(radians(iAngle - 90)),-(width-width*0.520)*cos(radians(iAngle - 90))); // draws the line according to the angle Reverse
  
}
  popMatrix();
}
public void drawLine() {
  pushMatrix();
  strokeWeight(4);
  stroke(42,52,99);//Sweep Color
  translate(width/2,height-height*0.074f); // moves the starting coordinats to new location

 line(0,0,(height-height*0.190f)*cos(radians(iAngle)),-(height-height*0.190f)*sin(radians(iAngle))); // draws the line according to the angle Forward
  //line(0,0,(height-height*0.190)*sin(radians(iAngle - 90)),-(height-height*0.190)*cos(radians(iAngle - 90))); // draws the line according to the angle Reverse
 
  popMatrix();
}
public void drawText() { // draws the texts on the screen
  
  pushMatrix();
  if(iDistance>200) {
  noObject = "Out of Range";
  }
  else {
  noObject = "In Range";
  }
  fill(0,0,0);
  noStroke();
  rect(0, height-height*0.0648f, width, height);
  fill(0,245,255);
  
  textSize(15);
  //1Meter graph
  //text("20cm",width-width*0.460,height-height*0.0833);
  //text("40cm",width-width*0.360,height-height*0.0833);
  //text("60cm",width-width*0.260,height-height*0.0833);
  //text("80cm",width-width*0.160,height-height*0.0833);
  //text("100cm",width-width*0.060,height-height*0.0833);
  
   //2Meter graph
  //text("20cm",width-width*0.460,height-height*0.0833);
  text("50cm",width-width*0.440f,height-height*0.0833f);
  text("100cm",width-width*0.320f,height-height*0.0833f);
  text("150cm",width-width*0.200f,height-height*0.0833f);
  text("200cm",width-width*0.070f,height-height*0.0833f);
  
  textSize(20);
  String[] lines = loadStrings("data.txt");
//println("there are " + lines[0] + " lines");
  text(lines[1], width-width*0.800f, height-height*0.0200f);
  text(lines[0], width-width*0.950f, height-height*0.0200f);
  
  textSize(20);
//text("SynerFlight Radar", width-width*0.800, height-height*0.0200);
  text("Angle: " + iAngle +" °", width-width*0.48f, height-height*0.0200f);
  text("Distance: ", width-width*0.27f, height-height*0.0200f);
  if(iDistance<200) {
  text("        " + iDistance +" cm", width-width*0.190f, height-height*0.0200f);
  }
  textSize(20);
  fill(0,245,255);
  translate((width-width*0.4994f)+width/2*cos(radians(30)),(height-height*0.0907f)-width/2*sin(radians(30)));
  rotate(-radians(-60));
  text("30°",0,0);
  resetMatrix();
  translate((width-width*0.503f)+width/2*cos(radians(60)),(height-height*0.0888f)-width/2*sin(radians(60)));
  rotate(-radians(-30));
  text("60°",0,0);
  resetMatrix();
  translate((width-width*0.507f)+width/2*cos(radians(90)),(height-height*0.0833f)-width/2*sin(radians(90)));
  rotate(radians(0));
  text("90°",0,0);
  resetMatrix();
  translate(width-width*0.513f+width/2*cos(radians(120)),(height-height*0.07129f)-width/2*sin(radians(120)));
  rotate(radians(-30));
  text("120°",0,0);
  resetMatrix();
  translate((width-width*0.5104f)+width/2*cos(radians(150)),(height-height*0.0574f)-width/2*sin(radians(150)));
  rotate(radians(-60));
  text("150°",0,0);
  popMatrix(); 
}
  public void settings() {  size (1000, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "synerflight_radar6" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
