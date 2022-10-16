package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class AutoIntake extends OutliersCommand{

    private Intake _intake;

    public AutoIntake(Intake intake){
        _intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute(){
        super.execute();
        _intake.setArmAngle(Constants.Intake.EXTENDED_ARM_ANGLE);
        _intake.setRollerSpeed(Constants.Intake.INTAKEING_SPEED);
    }

    @Override
    public boolean isFinished(){
        super.isFinished();
        return false;
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
    }
}