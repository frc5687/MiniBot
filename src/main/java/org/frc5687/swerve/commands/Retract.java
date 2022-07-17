package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Intake;

public class Retract extends OutliersCommand{

    private Intake _intake;
    private long _delay;
    
    public Retract(Intake intake){
        _intake = intake;
        addRequirements(_intake);
    }

    @Override
    public void initialize(){
        super.initialize();
        _delay = System.currentTimeMillis() + Constants.INTAKE.RETRACT_DELAY;
    }

    @Override
    public void execute(){
        super.execute();
        _intake.setSpeed(Constants.INTAKE.RETRACTING_SPEED);
    }

    @Override
    public boolean isFinished(){
        return System.currentTimeMillis() > _delay;
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
    }
}