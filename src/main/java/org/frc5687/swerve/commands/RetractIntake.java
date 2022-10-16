package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Intake;

public class RetractIntake extends OutliersCommand{

    private Intake _intake;
    
    public RetractIntake(Intake intake){
        _intake = intake;
        addRequirements(_intake);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        _intake.setArmAngle(Constants.Intake.RETRACTED_ARM_ANGLE);
        super.execute();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
    }
}