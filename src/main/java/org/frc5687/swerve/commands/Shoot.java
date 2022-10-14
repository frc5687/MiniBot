package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.Shooter;

public class Shoot extends OutliersCommand{

    private Shooter _shooter;

    public Shoot(Shooter shooter){
        _shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        super.execute();
        _shooter.setSpeed();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}