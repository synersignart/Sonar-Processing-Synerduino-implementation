import processing.serial.*; // imports library for serial communication
import java.awt.event.KeyEvent; // imports library for reading the data from the serial port
import java.io.IOException;
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



void setup() {
 
String[] lines = loadStrings("data.txt");

for (int b = 0 ; b < lines.length; b++) {

  println("there are " + lines[0] + " lines");
//println("there are " + lines[2] + " lines");
//println("there are " + lines.length + " lines");
println(lines[b]);
}

 size (1000, 600); // ***CHANGE THIS TO YOUR SCREEN RESOLUTION***
 smooth();
 //myPort = new Serial(this, lines[0], 9600); // starts the serial communication see Data Folder
myPort = new Serial(this, lines[0], 57600); // starts the serial communication see Data Folder
 //myPort = new Serial(this, lines[0], 115200); // starts the serial communication see Data Folder
   myPort.bufferUntil('.');                      // reads the data from the serial port up to the character '.'. So actually it reads this: angle,distance.
}



void draw() {
    
  
  fill(42,52,99);  // Fade color simulating motion blur and slow fade of the moving line
  noStroke();
  fill(0,4); 
  rect(0, 0, width, height-height*0.065); 
  
  fill(0,247,255); // Radar color
  
  // calls the functions for drawing the radar
  drawRadar(); 
  drawLine();
  drawObject();
  drawText();
}
void serialEvent (Serial myPort) { // starts reading data from the Serial Port
  // reads the data from the Serial Port up to the character '.' and puts it into the String variable "data".
  data = myPort.readStringUntil('.');
  data = data.substring(0,data.length()-1);
  
  index1 = data.indexOf(","); // find the character ',' and puts it into the variable "index1"
  angle= data.substring(0, index1); // read the data from position "0" to position of the variable index1 or thats the value of the angle the Arduino Board sent into the Serial Port
  distance= data.substring(index1+1, data.length()); // read the data from position "index1" to the end of the data pr thats the value of the distance
  // converts the String variables into Integer
  iAngle = int(angle);
  iDistance = int(distance);
}
void drawRadar() {
  pushMatrix();
  translate(width/2,height-height*0.074); // moves the starting coordinats to new location
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
 arc(0,0,(width-width*0.030),(width-width*0.030),PI,TWO_PI);
 arc(0,0,(width-width*0.280),(width-width*0.280),PI,TWO_PI);
 arc(0,0,(width-width*0.530),(width-width*0.530),PI,TWO_PI);
 arc(0,0,(width-width*0.780),(width-width*0.780),PI,TWO_PI);

  


  
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
void drawObject() {
  pushMatrix();
  //translate(width/2,height-height*0.074); // moves the starting coordinats to new location
    translate(width/2,height-height*0.074); // moves the starting coordinats to new location
  strokeWeight(6);
  stroke(255,10,10); // red color
  pixsDistance = iDistance*((height-height*0.8400)*0.025); // covers the distance from the sensor from cm to pixels 2M
  //pixsDistance = iDistance*((height-height*0.6700)*0.025); // covers the distance from the sensor from cm to pixels 1M
  //pixsDistance = iDistance*((height-height*0.1666)*0.025); // covers the distance from the sensor from cm to pixels
  //limiting the range to 200 cms
  if(iDistance<200){
  
  //draws the object according to the angle and the distance
  line(pixsDistance*cos(radians(iAngle)),-pixsDistance*sin(radians(iAngle)),(width-width*0.520)*cos(radians(iAngle)),-(width-width*0.520)*sin(radians(iAngle))); // draws the line according to the angle Forward
  //line(pixsDistance*sin(radians(iAngle - 90)),-pixsDistance*cos(radians(iAngle - 90)),(width-width*0.520)*sin(radians(iAngle - 90)),-(width-width*0.520)*cos(radians(iAngle - 90))); // draws the line according to the angle Reverse
  
}
  popMatrix();
}
void drawLine() {
  pushMatrix();
  strokeWeight(4);
  stroke(42,52,99);//Sweep Color
  translate(width/2,height-height*0.074); // moves the starting coordinats to new location

 line(0,0,(height-height*0.190)*cos(radians(iAngle)),-(height-height*0.190)*sin(radians(iAngle))); // draws the line according to the angle Forward
  //line(0,0,(height-height*0.190)*sin(radians(iAngle - 90)),-(height-height*0.190)*cos(radians(iAngle - 90))); // draws the line according to the angle Reverse
 
  popMatrix();
}
void drawText() { // draws the texts on the screen
  
  pushMatrix();
  if(iDistance>200) {
  noObject = "Out of Range";
  }
  else {
  noObject = "In Range";
  }
  fill(0,0,0);
  noStroke();
  rect(0, height-height*0.0648, width, height);
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
  text("50cm",width-width*0.440,height-height*0.0833);
  text("100cm",width-width*0.320,height-height*0.0833);
  text("150cm",width-width*0.200,height-height*0.0833);
  text("200cm",width-width*0.070,height-height*0.0833);
  
  textSize(20);
  String[] lines = loadStrings("data.txt");
//println("there are " + lines[0] + " lines");
  text(lines[1], width-width*0.800, height-height*0.0200);
  text(lines[0], width-width*0.950, height-height*0.0200);
  
  textSize(20);
//text("SynerFlight Radar", width-width*0.800, height-height*0.0200);
  text("Angle: " + iAngle +" °", width-width*0.48, height-height*0.0200);
  text("Distance: ", width-width*0.27, height-height*0.0200);
  if(iDistance<200) {
  text("        " + iDistance +" cm", width-width*0.190, height-height*0.0200);
  }
  textSize(20);
  fill(0,245,255);
  translate((width-width*0.4994)+width/2*cos(radians(30)),(height-height*0.0907)-width/2*sin(radians(30)));
  rotate(-radians(-60));
  text("30°",0,0);
  resetMatrix();
  translate((width-width*0.503)+width/2*cos(radians(60)),(height-height*0.0888)-width/2*sin(radians(60)));
  rotate(-radians(-30));
  text("60°",0,0);
  resetMatrix();
  translate((width-width*0.507)+width/2*cos(radians(90)),(height-height*0.0833)-width/2*sin(radians(90)));
  rotate(radians(0));
  text("90°",0,0);
  resetMatrix();
  translate(width-width*0.513+width/2*cos(radians(120)),(height-height*0.07129)-width/2*sin(radians(120)));
  rotate(radians(-30));
  text("120°",0,0);
  resetMatrix();
  translate((width-width*0.5104)+width/2*cos(radians(150)),(height-height*0.0574)-width/2*sin(radians(150)));
  rotate(radians(-60));
  text("150°",0,0);
  popMatrix(); 
}
