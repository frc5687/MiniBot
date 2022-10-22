package org.frc5687.swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.DriveTrain;

public class AutoAim extends OutliersCommand {

    private DriveTrain _driveTrain;

    private PIDController _aimController;

    public AutoAim(DriveTrain driveTrain) {
        _driveTrain = driveTrain;
        _aimController = new PIDController(Constants.DriveTrain.AIM_kP, Constants.DriveTrain.AIM_kI, Constants.DriveTrain.AIM_kD);
        addRequirements(_driveTrain);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        super.execute();
        if (_driveTrain.hasTarget()) {
            _driveTrain.drive(0, 0, _aimController.calculate(_driveTrain.getLimelightAngle()), true);
        }
    }
    @Override
    public boolean isFinished() {
        if (_driveTrain.onTarget()) {
            _driveTrain.drive(0, 0, 0, true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}