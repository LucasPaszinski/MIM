#include <modbus.h>
#include <modbusDevice.h>
#include <modbusRegBank.h>
#include <modbusSlave.h>
#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
modbusDevice regBank;
modbusSlave slave;

void setup()
{ 
   
  regBank.setId(3);
  lcd.begin(16, 2);
  lcd.clear();
  
  pinMode(8, OUTPUT);          // sets the digital pin 2 as output
  pinMode(9, OUTPUT);          // sets the digital pin 3 as output
  pinMode(10, OUTPUT);          // sets the digital pin 4 as output

  digitalWrite(8, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(10, HIGH);
//Add Analog Output registers 40001-40020 to the register bank
  regBank.add(40001);  
  regBank.add(40002);  
  regBank.add(40003);  
  regBank.add(40004);  
  slave._device = &regBank;  

  slave.setBaud(9600);
  regBank.set(40001,9999);
  regBank.set(40002,9999);
  regBank.set(40003,9999);
  regBank.set(40004,9999); 

  randomSeed(analogRead(0));
}

void loop()
{
  lcd.setCursor(0, 0);
  lcd.print("ON");
  slave.run();
  if(GetRun() == 2 && GetInform() != 0 && GetInform() != 1)
  {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("RunCLP(ON)");
    lcd.setCursor(0, 1);
    lcd.print("Agente Iniciou");
    AllLedOff();
    InformTask(GetInform());  
  }
}

void InformTask(int _inform)
{
      if(GetInform() == _inform)
      {
        SetStatus(0);
        LedBlueOn();
        long timeBegin = millis();
        int chanceOfFailure = random(10);
        while(GetDone() != 2)
        {
           slave.run();
           lcd.clear();
           lcd.setCursor(0, 0);
           lcd.print("Serviço:" + GetInform())+" ";
           lcd.setCursor(0, 1);
           lcd.print("Esta Acontecendo");
              
           long timeNow = millis();       
           
           if(chanceOfFailure < 8)
           {
             if(timeNow-timeBegin>30000)
             {
               SetStatus(((_inform*2)-1));
               SetDone(2);
               SetRun(1);
               LedGreenOn();
                        
               lcd.clear();
               lcd.setCursor(4, 0);
               lcd.print("Concluido");
               lcd.setCursor(0, 1);
               lcd.print("Sucesso! :D");
             }
           }
           else
           {
             if(timeNow-timeBegin>45000)
             {
               SetStatus((_inform*2));
               SetDone(2);
               SetRun(1);
               LedRedOn();
               lcd.clear();
               lcd.setCursor(4, 0);
               lcd.print("Concluido");
               lcd.setCursor(0, 1);
               lcd.print("Falhou! D:");
             }           
           }
        
        }
      }
}

int GetRun()
{
  return regBank.get(40001); 
}

void SetRun(int _run)
{
  regBank.set(40001, _run);
}

int GetDone()
{
  return regBank.get(40002);
}

void SetDone(int _done)
{
  regBank.set(40002, _done);
}

int GetInform()
{
  return regBank.get(40003);
}

void SetInform(int _inform)
{
  regBank.set(40003, _inform); 
}

int GetStatus()
{
  return regBank.get(40004);
}
  
void SetStatus(int _status)
{
  return regBank.set(40004, _status);
}

void AllLedOff()
{
  digitalWrite(8, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(10, HIGH);
}

void LedRedOn()
{
  digitalWrite(8, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(10, HIGH);
}

void LedGreenOn()
{
  digitalWrite(8, HIGH);
  digitalWrite(9, LOW);
  digitalWrite(10, HIGH);
}

void LedBlueOn()
{
  digitalWrite(8, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
}
 
