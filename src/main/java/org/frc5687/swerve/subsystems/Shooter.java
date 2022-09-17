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

    /**
     * Sets the speed of the north shooter wheel
     * @param speed double (-1.0, 1.0)
     */
    public void setNorthShooterSpeed(double speed) {
        _northWheel.set(TalonFXControlMode.PercentOutput, speed);
    }
    
    /**
     * Sets the speed of the south shooter wheel
     * @param speed double (-1.0, 1.0)
     */
    public void setSouthShooterSpeed(double speed) {
        _southWheel.set(TalonFXControlMode.PercentOutput, speed);
    }

    /**
     * Returns the velocity of the north shooter wheel
     * @return double 
     */
    public double getNorthShooterSpeed(){
        return _northWheel.getSelectedSensorVelocity();
    }

    /**
     * Returns the velocity of the souther wheel
     * @return double
     */
    public double getSouthShooterSpeed(){
        return _southWheel.getSelectedSensorVelocity();
    }

    @Override
    public void updateDashboard() {
        metric("North", getNorthShooterSpeed());
        metric("South", getSouthShooterSpeed());
    }
}
