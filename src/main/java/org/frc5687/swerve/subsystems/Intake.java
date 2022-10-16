package org.frc5687.swerve.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.RobotMap.CAN.TALONFX;
import org.frc5687.swerve.util.OutliersContainer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;


public class Intake extends OutliersSubsystem{

    private TalonFX _roller;
    private Servo _armServo;


    public Intake(OutliersContainer container){
        super(container);
        _roller = new TalonFX(TALONFX.INTAKE);
        _roller.setInverted(Constants.Intake.INVERTED);
        _armServo = new Servo(RobotMap.PWM.ARM_SERVO);
    }

    public void setArmAngle(double angle) {
        _armServo.setAngle(angle);
    }

    public void setRollerSpeed(double demand) {
        _roller.set(ControlMode.PercentOutput, demand);
    }
    
    @Override
    public void updateDashboard() {
    }  
}