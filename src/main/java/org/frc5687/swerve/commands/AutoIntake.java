package org.frc5687.swerve.commands;

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
        _intake.IntakeBall();
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