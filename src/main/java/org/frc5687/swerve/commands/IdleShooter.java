package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Shooter;

import edu.wpi.first.wpilibj.AddressableLED;

public class IdleShooter extends OutliersCommand {

    private Shooter _shooter;
    
    public IdleShooter(Shooter shooter){
        _shooter = shooter;
        addRequirements(_shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        _shooter.setNorthSpeed(Constants.Shooter.SHOOTER_IDLE_SPEED);
        _shooter.setSouthSpeed(Constants.Shooter.SHOOTER_IDLE_SPEED);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
