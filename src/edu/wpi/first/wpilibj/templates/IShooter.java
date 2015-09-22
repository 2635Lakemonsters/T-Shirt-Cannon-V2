/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 * Interface to define aspects of a component that shoot. fire returns false if a failiure occurs
 * @author localuser
 */
public interface IShooter
{
    boolean fire(double magnitude);
}
