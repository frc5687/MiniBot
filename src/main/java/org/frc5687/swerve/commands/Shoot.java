package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Shooter;

public class Shoot extends OutliersCommand{

    private final Shooter _shooter;
    
    public Shoot(Shooter shooter) {
        _shooter = shooter;
    }

    public void initialize() {
        super.initialize();
    }

    public void execute() {
        super.execute();
        // put ball-feeding code here
        _shooter.setNorthShooterSpeed(Constants.Shooter.NORTH_PERCENT_SPEED);
        _shooter.setSouthShooterSpeed(Constants.Shooter.SOUTH_PERCENT_SPEED);
    }

    public boolean isFinished() {
        super.isFinished();
        return false;
    }

    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
