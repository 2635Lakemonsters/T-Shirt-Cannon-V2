/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author localuser
 */
public class SimpleRotator implements IRotator
{

    SpeedController motor;

    public SimpleRotator(SpeedController motor)
    {
        this.motor = motor;
    }
    
    public boolean rotate(double magnitude)
    {
        motor.set(magnitude);
        return true;
    }
    
}
