package org.frc5687.swerve.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.OutliersContainer;

public class Shooter extends OutliersSubsystem{

    private TalonFX _northWheel;
    private TalonFX _southWheel;

    public Shooter(OutliersContainer container) {
        super(container);
        _northWheel = new TalonFX(RobotMap.CAN.TALONFX.NORTH_WHEEL);
        _southWheel = new TalonFX(RobotMap.CAN.TALONFX.SOUTH_WHEEL);
        _northWheel.setInverted(Constants.Shooter.NORTH_INVERTED);
        _southWheel.setInverted(Constants.Shooter.SOUTH_INVERTED);
    }

    //set North wheel speed
    public void setNorthShooterSpeed(double speed) {
        _northWheel.set(TalonFXControlMode.PercentOutput, speed);
    }
    //set south wheel speed
    public void setSouthShooterSpeed(double speed) {
        _southWheel.set(TalonFXControlMode.PercentOutput, speed);
    }
    public double getNorthShooterSpeed(){
        return _northWheel.getSelectedSensorVelocity();
    }

    public double getSouthShooterSpeed(){
        return _southWheel.getSelectedSensorVelocity();
    }

    @Override
    public void updateDashboard() {
        metric("North", getNorthShooterSpeed());
        metric("South", getSouthShooterSpeed());
    }
}
