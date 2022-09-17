package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Shooter;

public class IdleShoot extends OutliersCommand{

    private final Shooter _shooter;

    public IdleShoot(Shooter shooter) {
        _shooter = shooter;
        addRequirements(_shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
        _shooter.setNorthShooterSpeed(Constants.Shooter.NORTH_IDLE_SPEED);
        _shooter.setSouthShooterSpeed(Constants.Shooter.SOUTH_IDLE_SPEED);
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
