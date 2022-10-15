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

    public void setSpeed(double demand){
        _north.set(ControlMode.PercentOutput, demand);
        _south.set(ControlMode.PercentOutput, demand);
        _isShooting = true;
    }

    public double getVelocity(){
        return _north.getSelectedSensorVelocity();
    }

    public double getTemp(){
        return _north.getTemperature();
    }

    @Override
    public void updateDashboard() {
        metric("Shooting", _isShooting);
        metric("Velocity", getVelocity());
        metric("Temp", getTemp());
    }
}
