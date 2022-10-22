package org.frc5687.swerve.commands.auto;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc5687.swerve.commands.AutoAim;
import org.frc5687.swerve.commands.AutoShoot;
import org.frc5687.swerve.commands.DriveTrajectory;
import org.frc5687.swerve.config.Auto;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.Shooter;
import org.frc5687.swerve.util.AutoChooser;


/** Shoot first ball, taxi out of tarmac, intake second ball, shoot it */
public class OneBallAuto extends SequentialCommandGroup {

    private Translation2d _translation;
    private Rotation2d _rotation;
    private Trajectory _trajectory;
    private Pose2d _destination;

    private Boolean _bypass;

    /** Construact a TwoBall Auto SequentialCommandGroup */
    public OneBallAuto(
            DriveTrain driveTrain,
            Shooter shooter,
            Indexer indexer,
            AutoChooser.Position position
    ) {
        switch(position) {
            case First:
                driveTrain.resetOdometry(Auto.RobotPositions.FIRST);
                _trajectory = TrajectoryGenerator.generateTrajectory(
                        Auto.TrajectoryPoints.PositionOneToBallOne.waypoints,
                        driveTrain.getConfig());
                _rotation = Auto.Rotations.BALL_ONE_FROM_FIRST;
                break;
            case Second:
                driveTrain.resetOdometry(Auto.RobotPositions.SECOND);
                _trajectory = TrajectoryGenerator.generateTrajectory(
                        Auto.TrajectoryPoints.PositionTwoToBallOne.waypoints,
                        driveTrain.getConfig()
                );
                _rotation = new Rotation2d();
                break;
            case Third:
                driveTrain.resetOdometry(Auto.RobotPositions.THIRD);
                _trajectory = TrajectoryGenerator.generateTrajectory(
                        Auto.TrajectoryPoints.PositionThreeToBallTwo.waypoints,
                        driveTrain.getConfig());
                _rotation = Auto.Rotations.BALL_TWO_FROM_THIRD;
                break;
            case Fourth:
                driveTrain.resetOdometry(Auto.RobotPositions.FOURTH);
                _trajectory = TrajectoryGenerator.generateTrajectory(
                        Auto.TrajectoryPoints.PositionFourToBallThree.waypoints,
                        driveTrain.getConfig());
                _rotation = Auto.Rotations.BALL_THREE_FROM_FOURTH;
                break;
            default:
                _translation = new Translation2d (
                        driveTrain.getOdometryPose().getX(),
                        driveTrain.getOdometryPose().getY()
                );
                _rotation = driveTrain.getOdometryPose().getRotation();
        }

        _destination = new Pose2d(_translation, _rotation);

        addCommands(
                new DriveTrajectory(driveTrain, _trajectory, _rotation),
                new AutoAim(driveTrain),
                new AutoShoot(indexer, shooter)
        );
    }
}
