package org.frc5687.swerve.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.OutliersContainer;

public class Shooter extends OutliersSubsystem{

    private TalonFX _north;
    private TalonFX _south;
    private boolean _isShooting = false;

    public Shooter(OutliersContainer container) {
        super(container);
        _north = new TalonFX(RobotMap.CAN.TALONFX.NORTH_SHOOTER, "rio");
        _north.setInverted(Constants.Shooter.NORTH_MOTOR_INVERTED);
        _south = new TalonFX(RobotMap.CAN.TALONFX.SOUTH_SHOOTER, "rio");
        _south.setInverted(Constants.Shooter.SOUTH_MOTOR_INVERTED);
    }

    public void setNorthSpeed(double demand){
        _north.set(ControlMode.PercentOutput, demand);
        // _isShooting = true;
    }

    public void setSouthSpeed(double demand) {
        _south.set(ControlMode.PercentOutput, demand);
    }

    public double getNorthVelocityPer100ms(){
        return _north.getSelectedSensorVelocity();
    }

    public double getNorthVelocityRPM() {
        return getNorthVelocityPer100ms() / Constants.Shooter.TICKS_PER_ROTATION * Constants.Shooter.MS_TO_MINUETS;
    }

    public double getNorthFlywheelRPM() {
        return getSouthVelocityRPM() / Constants.Shooter.GEAR_RATIO;
    }

    public double getSouthVelocityPer100ms(){
        return _south.getSelectedSensorVelocity();
    }

    public double getSouthVelocityRPM() {
        return getSouthVelocityPer100ms() / Constants.Shooter.TICKS_PER_ROTATION * Constants.Shooter.MS_TO_MINUETS;
    }

    public double getSouthFlywheelRPM() {
        return getSouthVelocityRPM() / Constants.Shooter.GEAR_RATIO;
    }

    public boolean isFlywheelUptoSpeed() {
        return ((getSouthFlywheelRPM() + getNorthFlywheelRPM()) / 2.0) > Constants.Shooter.SHOOTING_FLYWHEEL_RPM; 
    }
    public double getTemp(){
        return _north.getTemperature();
    }

    @Override
    public void updateDashboard() {
        metric("Shooting", _isShooting);
        metric("Velocity", getNorthVelocityPer100ms());
        metric("IsFlywheelUptoSpeed", isFlywheelUptoSpeed());
        metric("SouthRPM", getSouthFlywheelRPM());
        metric("NorthRPM", getNorthFlywheelRPM());
        metric("Combined", ((getSouthFlywheelRPM() + getNorthFlywheelRPM()) / 2.0));
        metric("Temp", getTemp());
    }
}
