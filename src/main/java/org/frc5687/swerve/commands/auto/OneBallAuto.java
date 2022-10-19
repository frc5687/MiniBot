package org.frc5687.swerve.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Rotation2d;

import org.frc5687.swerve.commands.Shoot;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.Shooter;
import org.frc5687.swerve.util.AutoChooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;



/** Shoot first ball then taxi out of tarmac. */
public class OneBallAuto extends SequentialCommandGroup {

    private Translation2d _translation;
    private Rotation2d _rotation;
    private Pose2d _destination;

    private Boolean _bypass;

    /** Construct a OneBall Auto SequentialCommandGroup */
    public OneBallAuto (
        DriveTrain driveTrain,
        Shooter shooter,
        Indexer indexer,
        AutoChooser.Position position
    ) {
        Double _velocity;

        _bypass = false;
        if (_bypass) {
            _translation = new Translation2d();
            _rotation = new Rotation2d();
            _destination = new Pose2d();
            _velocity = 0.2;
            addCommands(
                new Shoot(shooter),
                new DriveToPose(driveTrain, _destination, _velocity)
            );
            return;
        }

        switch(position) {
            case First:
                driveTrain.resetOdometry(Auto.RobotPositions.FIRST);
                _translation = new Translation2d (
                    Auto.BallPositions.BALL_ONE.getX(),
                    Auto.BallPositions.BALL_ONE.getY()
                    );
                _rotation = Auto.Rotations.BALL_ONE_FROM_FIRST;
                break;
            case Second:
                driveTrain.resetOdometry(Auto.RobotPositions.SECOND);
                _translation = new Translation2d (
                    Auto.BallPositions.BALL_ONE.getX(),
                    Auto.BallPositions.BALL_ONE.getY()
                    );
                _rotation = new Rotation2d();
                break;
            case Third:
                driveTrain.resetOdometry(Auto.RobotPositions.THIRD);
                _translation = new Translation2d (
                    Auto.BallPositions.BALL_TWO.getX(),
                    Auto.BallPositions.BALL_TWO.getY()
                    );
                _rotation = Auto.Rotations.BALL_TWO_FROM_THIRD;
                break;
            case Fourth:
                driveTrain.resetOdometry(Auto.RobotPositions.FOURTH);
                _translation = new Translation2d (
                    Auto.FieldPositions.SAFE_BALL_THREE.getX(),
                    Auto.FieldPositions.SAFE_BALL_THREE.getY()
                    );
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
        _velocity = 0.2;
        addCommands(
            new Shoot(catapult, indexer),
            new DriveToPose(driveTrain, _destination, _velocity)
        );
    }
}
