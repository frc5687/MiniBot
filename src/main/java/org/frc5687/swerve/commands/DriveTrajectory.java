/* Team 5687 (C)2021-2022 */
/* Team 5687 (C)2021-2022 */
package org.frc5687.swerve.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import org.frc5687.swerve.subsystems.DriveTrain;

public class DriveTrajectory extends OutliersCommand {

    private final DriveTrain _driveTrain;
    private Trajectory _trajectory;
    private Rotation2d _rot;
    private final Timer _timer;

    public DriveTrajectory(DriveTrain driveTrain, Trajectory trajectory, Rotation2d heading) {
        _driveTrain = driveTrain;
        _timer = new Timer();
        _trajectory = trajectory;
        _rot = heading;
        addRequirements(_driveTrain);
    }

    @Override
    public void initialize() {
        super.initialize();
        _timer.reset();
        _timer.start();
    }

    @Override
    public void execute() {
        super.execute();
        Trajectory.State goal = _trajectory.sample(_timer.get());
        _driveTrain.trajectoryFollower(goal, _rot);
    }

    @Override
    public boolean isFinished() {
        if (_timer.get() >= _trajectory.getTotalTimeSeconds()) {
            _driveTrain.drive(0, 0, 0, true);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        _timer.reset();
    }
}