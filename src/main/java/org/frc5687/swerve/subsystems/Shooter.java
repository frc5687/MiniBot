package org.frc5687.swerve.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
        _north.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0 , 200);
        _north.config_kP(0, Constants.Shooter.kP, 200);
        _north.config_kI(0, Constants.Shooter.kI, 200);
        _north.config_kD(0, Constants.Shooter.kD, 200);

        _south = new TalonFX(RobotMap.CAN.TALONFX.SOUTH_SHOOTER, "rio");
        _south.setInverted(Constants.Shooter.SOUTH_MOTOR_INVERTED);
        _south.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0 , 200);
        _south.config_kP(0, Constants.Shooter.kP, 200);
        _south.config_kI(0, Constants.Shooter.kI, 200);
        _south.config_kD(0, Constants.Shooter.kD, 200);


    }


    public void setNorthSpeed(double demand){
        _north.set(ControlMode.PercentOutput, demand);
        // _isShooting = true;
    }

    public void setNorthRPM(double RPM) {
        double ticks = RPM * Constants.Shooter.TICKS_PER_ROTATION / Constants.Shooter.MS_TO_MINUETS;
        metric("NorthTicks Setpoint", ticks);
        _north.set(ControlMode.Velocity, ticks);
    }

    public double calculateIdealNorthRPM(double dist){
        return (Constants.Shooter.NORTH_CUBIC_COEFF * (dist * dist * dist)) + 
        (Constants.Shooter.NORTH_SQUARE_COEFF * (dist * dist)) + 
        (Constants.Shooter.NORTH_LINEAR_COEFF * dist) + Constants.Shooter.NORTH_OFFSET_COEFF;
    }

    public void setSouthSpeed(double demand) {
        _south.set(ControlMode.PercentOutput, demand);
    }

    public void setSouthRPM(double RPM) {
        double ticks = RPM * Constants.Shooter.TICKS_PER_ROTATION / Constants.Shooter.MS_TO_MINUETS;
        metric("SouthTicks Setpoint", ticks);
        _south.set(ControlMode.Velocity, ticks);
    }

    public double calculateIdealSouthRPM (double dist){
        return (Constants.Shooter.SOUTH_CUBIC_COEFF * (dist * dist * dist)) + 
        (Constants.Shooter.SOUTH_SQUARE_COEFF * (dist * dist)) + 
        (Constants.Shooter.SOUTH_LINEAR_COEFF * dist) + Constants.Shooter.SOUTH_OFFSET_COEFF;
    }

    public double getNorthVelocityPer100ms(){
        return _north.getSelectedSensorVelocity();
    }

    public double getNorthVelocityRPM() {
        return getNorthVelocityPer100ms() / Constants.Shooter.TICKS_PER_ROTATION * Constants.Shooter.MS_TO_MINUETS;
    }

    public double getNorthFlywheelRPM() {
        return getNorthVelocityRPM() / Constants.Shooter.GEAR_RATIO;
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
        return Math.abs(((getSouthFlywheelRPM() + getNorthFlywheelRPM()) / 2.0) - Constants.Shooter.SHOOTING_FLYWHEEL_RPM) < Constants.Shooter.RPM_TOLERANCE;
    }
    public double getTemp(){
        return _north.getTemperature();
    }

    @Override
    public void updateDashboard() {
        metric("Shooting", _isShooting);
        metric("VelocityTicksNorth", getNorthVelocityPer100ms());
        metric("VelocityTicksSouth", getSouthVelocityPer100ms());
        metric("IsFlywheelUptoSpeed", isFlywheelUptoSpeed());
        metric("SouthRPM", getSouthFlywheelRPM());
        metric("NorthRPM", getNorthFlywheelRPM());
        metric("Combined", ((getSouthFlywheelRPM() + getNorthFlywheelRPM()) / 2.0));
        metric("Temp", getTemp());
    }
}
