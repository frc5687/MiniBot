package org.frc5687.swerve.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.OutliersContainer;

public class Shooter extends OutliersSubsystem{

    private TalonFX _right;
    private TalonFX _left;
    private boolean _isShooting = false;

    public Shooter(OutliersContainer container) {
        super(container);
        _right = new TalonFX(RobotMap.CAN.TALONFX.FRONT_SHOOTER, "Tomcat");
        _left = new TalonFX(RobotMap.CAN.TALONFX.BACK_SHOOTER, "Tomcat");
    }

    public void setSpeed(){
        _right.set(ControlMode.PercentOutput, Constants.Shooter.SHOOTER_SPEED);
        _left.set(ControlMode.PercentOutput, Constants.Shooter.SHOOTER_SPEED);
        _isShooting = true;
    }

    @Override
    public void updateDashboard() {
        metric("Shooting", _isShooting);
    }
}
