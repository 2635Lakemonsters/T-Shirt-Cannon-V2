/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Relay;
import java.util.TimerTask;

/**
 *
 * @author localuser
 */
public class TurnOffRelayTask extends TimerTask
{
    Relay toTurnOff;
    public TurnOffRelayTask(Relay toTurnOff)
    {
        this.toTurnOff = toTurnOff;
    }
    public void run()
    {
        toTurnOff.set(Relay.Value.kOff);
    }
    
}
