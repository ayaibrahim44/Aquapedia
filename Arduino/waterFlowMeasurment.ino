#define upperUS_trig 12
#define upperUS_echo 11
#define lowerUS_trig 8
#define lowerUS_echo 7

long t1, t2, velocity, alpha, pipeDiameter, distanceBetweenUSS, volumeFlowRate;
bool pipeStatus = false;
void setup() {
  // put your setup code here, to run once:
  Serial.begin (9600);
  pinMode(upperUS_trig, OUTPUT);
  pinMode(upperUS_echo, INPUT);
  pinMode(lowerUS_trig, OUTPUT);
  pinMode(lowerUS_echo, INPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  SonarSensor(upperUS_trig, upperUS_echo);
  SonarSensor(lowerUS_trig, lowerUS_echo);

  //two values should be equal if the medium is stable, and differ when the medium flow with velocity x.
  t1 = pulseIn(upperUS_echo, HIGH);
  t2 = pulseIn(lowerUS_echo, HIGH);

  velocity = distanceBetweenUSS* (t2-t1) / 2* cos(alpha)* (t1 * t2);

  if (velocity != 0){
    start_time = millis(); 
    pipeStatus = true;
    volumeFlowRate = 5;
    
  }
  
  

  

}

void SonarSensor(int trigPin,int echoPin){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

}
