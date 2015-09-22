/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot 
{
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    //TODO: make these values correct
    int JOYSTICK_CHANNEL = 1;
    int FIRE_BUTTON = 1;
    
    int MOVE_AXIS = 1;
    int ROTATE_AXIS = 2;
    int ELEVATOR_AXIS = 3;
    
    
    int REAR_RIGHT_MOTOR_CHANNEL = 1;
    int REAR_LEFT_MOTOR_CHANNEL = 2;
    int FRONT_RIGHT_MOTOR_CHANNEL = 3;
    int FRONT_LEFT_MOTOR_CHANNEL = 4;
    
    int ROTATOR_MOTOR_CHANNEL = 1;
    
    int ROTATOR_ENCODER_A = 1;
    int ROTATOR_ENCODER_B = 2;
    
    String ROTATOR_P = "RotatorP";
    double ROTATOR_P_DEFAULT = 1.0;
    String ROTATOR_I = "RotatorI";
    double ROTATOR_I_DEFAULT = 0.0;
    String ROTATOR_D = "RotatorD";
    double ROTATOR_D_DEFAULT = 0.0;
    
    int ELEVATOR_ENCODER_A = 1;
    int ELEVATOR_ENCODER_B = 2;
    
    String ELEVATOR_P = "ElevatorP";
    double ELEVATOR_P_DEFAULT = 1.0;
    String ELEVATOR_I = "ElevatorI";
    double ELEVATOR_I_DEFAULT = 0.0;
    String ELEVATOR_D = "ElevatorD";
    double ELEVATOR_D_DEFAULT = 0.0;
    
    double ROTATOR_INCREMENT = 5.0;
    double FIRE_OPEN_TIME = 0.1;
    
    int ELEVATION_MOTOR_CHANNEL = 2;
    int CANNON_VALVE_RELAY = 3;
    
    
    Talon rearRightMotor;
    Talon rearLeftMotor;
    Talon frontRightMotor;
    Talon frontLeftMotor;
    RobotDrive drive;

    
    Talon rotatorMotor;
    
    Encoder rotatorEncoder;
    PIDController rotatorPid;
    double rotatorPosition;
    
    
    Talon elevatorMotor;
    Encoder elevatorEncoder;
    PIDController elevatorPid;
    double elevatorPosition;
    
    Relay cannonValveRelay;
  
    RotaryCannon cannon;
    
    //Interface for things like joystick, etc. Generally this will be set to a Joystick class.
    GenericHID controlInput;
    
    Preferences robotPreferences = Preferences.getInstance();
    
    public void robotInit() 
    {
        //START JOYSTICK INITIALIZATION
        controlInput = new Joystick(JOYSTICK_CHANNEL);
        //END
        
        //START DRIVE INITIALIZATION
        rearRightMotor = new Talon(REAR_RIGHT_MOTOR_CHANNEL);
        rearLeftMotor = new Talon(REAR_LEFT_MOTOR_CHANNEL);
        frontRightMotor = new Talon(FRONT_RIGHT_MOTOR_CHANNEL);
        frontLeftMotor = new Talon(FRONT_LEFT_MOTOR_CHANNEL);
        drive = new RobotDrive(rearLeftMotor, rearRightMotor, frontRightMotor, rearRightMotor);
        //END
        
        //START ROTATOR INITIALIZATION
        rotatorMotor = new Talon(ROTATOR_MOTOR_CHANNEL);
        rotatorEncoder = new Encoder(ROTATOR_ENCODER_A, ROTATOR_ENCODER_B);
        
        //Create a pid controller that gets its pid vaues from set preferences and is controlled by rotatorEncoder and actuates rotatorMotor
        rotatorPid = new PIDController(robotPreferences.getDouble(ROTATOR_P, ROTATOR_P_DEFAULT),
                                       robotPreferences.getDouble(ROTATOR_I, ROTATOR_I_DEFAULT),
                                       robotPreferences.getDouble(ROTATOR_D, ROTATOR_D_DEFAULT),
                                       rotatorEncoder,
                                       rotatorMotor
        );
        rotatorPid.enable();
        rotatorPosition = 0;
        //END
        
        //START ELEVATOR INITIALIZATION
        elevatorMotor = new Talon(ELEVATION_MOTOR_CHANNEL);
        elevatorEncoder = new Encoder(ELEVATOR_ENCODER_A, ELEVATOR_ENCODER_B);
        //Create a pid controller that gets its pid vaues from set preferences and is controlled by elevatorEncoder and actuates elevatorMotor
        elevatorPid = new PIDController(robotPreferences.getDouble(ELEVATOR_P, ELEVATOR_P_DEFAULT),
                                       robotPreferences.getDouble(ELEVATOR_I, ELEVATOR_I_DEFAULT),
                                       robotPreferences.getDouble(ELEVATOR_D, ELEVATOR_D_DEFAULT),
                                       elevatorEncoder,
                                       elevatorMotor
        );
        elevatorPid.enable();
        elevatorPosition = 0;
        //END
        
        //START CANNON INITIALIZATION
        cannonValveRelay = new Relay(CANNON_VALVE_RELAY);
        cannon = new RotaryCannon(new TimedShooter(cannonValveRelay), 
                new EncoderRotator(rotatorPid),
                new EncoderRotator(elevatorPid)
        );
        //END   
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit()
    {
       controlInput = new Joystick(JOYSTICK_CHANNEL);
       //Set pid when enabling robot
       elevatorPid.setPID(robotPreferences.getDouble(ELEVATOR_P, ELEVATOR_P_DEFAULT),
                          robotPreferences.getDouble(ELEVATOR_I, ELEVATOR_I_DEFAULT),
                          robotPreferences.getDouble(ELEVATOR_D, ELEVATOR_D_DEFAULT));
       
       rotatorPid.setPID(robotPreferences.getDouble(ROTATOR_P, ROTATOR_P_DEFAULT),
                         robotPreferences.getDouble(ROTATOR_I, ROTATOR_I_DEFAULT),
                         robotPreferences.getDouble(ROTATOR_D, ROTATOR_D_DEFAULT));
    }
    public void teleopPeriodic() 
    {
        //Change assignments on rotator and elevator position from = to += when dealing with EncoderRotator implementations
        if(controlInput.getRawButton(FIRE_BUTTON))
        {
            rotatorPosition += ROTATOR_INCREMENT;
            cannon.fire(FIRE_OPEN_TIME, rotatorPosition);
        }
        elevatorPosition += controlInput.getRawAxis(ELEVATOR_AXIS);
        cannon.setElevation(elevatorPosition);
        
        drive.arcadeDrive(controlInput.getRawAxis(MOVE_AXIS), controlInput.getRawAxis(ROTATE_AXIS));
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    
    }
    
}
