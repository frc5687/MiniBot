package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Shooter;
import org.frc5687.swerve.Constants;
import org.frc5687.swerve.OI;

public class DriveShooter extends OutliersCommand {
    
    private final Shooter _shooter;
    private final DriveTrain _drivetrain;
    private final OI _oi;

    public DriveShooter (Shooter shooter, DriveTrain drivetrain, OI oi){
        _shooter = shooter;
        _drivetrain = drivetrain;
        _oi = oi;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();

        
    }

}
