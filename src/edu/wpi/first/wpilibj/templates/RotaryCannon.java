/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author localuser
 */
public class RotaryCannon
{
    IShooter launcher;
    IRotator indexer;
    IRotator elevator;
    public RotaryCannon(IShooter launcher, IRotator indexer, IRotator elevator)
    {
        this.launcher = launcher;
        this.indexer = indexer;
        this.elevator = elevator;
    }
    public void fire(double fireMagnitude, double rotationMagnitude)
    {
        launcher.fire(fireMagnitude);
        indexer.rotate(rotationMagnitude);
    }
    public void setElevation(double magnitude)
    {
        elevator.rotate(magnitude);
    }
}
