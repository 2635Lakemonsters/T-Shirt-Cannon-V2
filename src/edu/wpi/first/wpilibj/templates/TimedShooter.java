/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Relay;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author localuser
 */
public class TimedShooter implements IShooter
{

    Relay relay;
    Timer timer;
    boolean timerSet;
    public TimedShooter(Relay relay)
    {
        this.relay = relay;
        this.timer = new Timer();
        timerSet = false;
    }
   /**
    * 
    * @param magnitude time to fire in milliseconds
    * @return true if finished, false if not
    */
    public boolean fire(double magnitude)
    {
        if(!timerSet)
        {
            relay.set(Relay.Value.kForward);
            timer.schedule(new TurnOffRelayTask(relay), (long)magnitude);
            timerSet = true;
            return false;
        }
        //When the task executes the relay will be turned off, so reset the timer variable
        if(timerSet && relay.get() == Relay.Value.kOff)
        {
            timerSet = false;
            return true;
        }
        else
        {
            return false;
        }
       
    }
    
    
}
