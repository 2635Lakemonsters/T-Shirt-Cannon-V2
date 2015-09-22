/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author localuser
 */
public class EncoderRotator implements IRotator
{
    
    PIDController rotatorPid;

    public EncoderRotator(PIDController rotatorPid)
    {
        this.rotatorPid = rotatorPid;
    }
    public boolean rotate(double magnitude)
    {
        rotatorPid.setSetpoint(magnitude);
        return true;
    }
    
}
