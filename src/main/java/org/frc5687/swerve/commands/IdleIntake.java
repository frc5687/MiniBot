
package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Intake;

public class IdleIntake extends OutliersCommand{

    private Intake _intake;
    
    public IdleIntake(Intake intake){
        _intake = intake;
        addRequirements(_intake);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        _intake.setArmSpeed(_intake.getArmAngleControllerOutput(Constants.Intake.RETRACTED_ARM_ANGLE));
        _intake.setRollerSpeed(0.0);
    }

    @Override
    public boolean isFinished(){
        return super.isFinished();
    }
}