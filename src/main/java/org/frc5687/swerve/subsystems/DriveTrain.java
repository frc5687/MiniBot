/* Team 5687 (C)2020-2022 */
package org.frc5687.swerve.subsystems;

import static org.frc5687.swerve.Constants.DifferentialSwerveModule.*;
import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.RobotMap.CAN.TALONFX.*;

import com.ctre.phoenix.sensors.Pigeon2;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.constraint.SwerveDriveKinematicsConstraint;
import edu.wpi.first.math.util.Units;
import org.frc5687.swerve.OI;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.Limelight;
import org.frc5687.swerve.util.OutliersContainer;

public class DriveTrain extends OutliersSubsystem {
    private DiffSwerveModule _frontRight;
    private DiffSwerveModule _frontLeft;
    private DiffSwerveModule _backRight;
    private DiffSwerveModule _backLeft;

    private SwerveDriveKinematics _kinematics;
    private SwerveDriveOdometry _odometry;

    private double _PIDAngle;

    // private Pigeon2 _imu;
    private AHRS _imu;
    private Limelight _limelight;

    private OI _oi;

    private HolonomicDriveController _controller;
    private ProfiledPIDController _angleController;

    public DriveTrain(OutliersContainer container, OI oi, AHRS imu, Limelight limelight) {
        super(container);
        try {
            _oi = oi;
            _imu = imu;
            _limelight = limelight;

            _frontRight =
                    new DiffSwerveModule(
                            NE_POSITION,
                            NE_INNER_FALCON,
                            NE_OUTER_FALCON,
                            RobotMap.DIO.ENCODER_NE,
                            NE_ENCODER_OFFSET);
            _frontLeft =
                    new DiffSwerveModule(
                            NW_POSITION,
                            NW_OUTER_FALCON,
                            NW_INNER_FALCON,
                            RobotMap.DIO.ENCODER_NW,
                            NW_ENCODER_OFFSET);
            _backRight =
                    new DiffSwerveModule(
                            SE_POSITION,
                            SE_INNER_FALCON,
                            SE_OUTER_FALCON,
                            RobotMap.DIO.ENCODER_SE,
                            SE_ENCODER_OFFSET);
            _backLeft =
                    new DiffSwerveModule(
                            SW_POSITION,
                            SW_OUTER_FALCON,
                            SW_INNER_FALCON,
                            RobotMap.DIO.ENCODER_SW,
                            SW_ENCODER_OFFSET);

            _kinematics =
                    new SwerveDriveKinematics(
                            _frontLeft.getModulePosition(),
                            _frontRight.getModulePosition(),
                            _backLeft.getModulePosition(),
                            _backRight.getModulePosition());
            _odometry = new SwerveDriveOdometry(_kinematics, getHeading());

            _controller =
                    new HolonomicDriveController(
                            new PIDController(kP, kI, kD),
                            new PIDController(kP, kI, kD),
                            new ProfiledPIDController(
                                    kP,
                                    kI,
                                    kD,
                                    new TrapezoidProfile.Constraints(
                                            PROFILE_CONSTRAINT_VEL, PROFILE_CONSTRAINT_ACCEL)));
            _angleController =
                    new ProfiledPIDController(
                            ANGLE_kP,
                            ANGLE_kI,
                            ANGLE_kD,
                            new TrapezoidProfile.Constraints(
                                    PROFILE_CONSTRAINT_VEL, PROFILE_CONSTRAINT_ACCEL));
            _angleController.enableContinuousInput(-Math.PI, Math.PI);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    // use for modules as controller is running at 200Hz.
    public void controllerPeriodic() {
        _frontRight.periodic();
        _frontLeft.periodic();
        _backRight.periodic();
        _backLeft.periodic();
    }

    @Override
    public void periodic() {
        _odometry.update(
                getHeading(),
                _frontLeft.getState(),
                _frontRight.getState(),
                _backLeft.getState(),
                _backRight.getState());
    }

    @Override
    public void updateDashboard() {
        metric("BR/Encoder Angle", _backRight.getModuleAngle());
        metric("BL/Encoder Angle", _backLeft.getModuleAngle());
        metric("FL/Encoder Angle", _frontLeft.getModuleAngle());
        metric("FR/Encoder Angle", _frontRight.getModuleAngle());

        metric("BR/Predicted Angle", _backRight.getPredictedAzimuthAngle());

        metric("BR/Encoder Azimuth Vel", _backRight.getAzimuthAngularVelocity());
        metric("BR/Predicted Azimuth Vel", _backRight.getPredictedAzimuthAngularVelocity());

        metric("BR/Encoder Wheel Vel", _backRight.getWheelVelocity());
        metric("BR/Predicted Wheel Vel", _backRight.getPredictedWheelVelocity());

        metric("Odometry Pose", getOdometryPose().toString());



        metric("Pigeon/Yaw", getYaw());
        metric("Heading", getHeading().getRadians());
        metric("AngleController/LockedAngle", _PIDAngle);
        metric("AngleController/power", _angleController.calculate(getHeading().getRadians(), _PIDAngle));

        metric("Limelight angle", getLimelightAngle());
        metric("Has target", hasTarget());
        // metric("Pigeon/Roll", _imu.getRoll());
        // metric("Pigeon/P, itch", _imu.getPitch());
    }

    public void setFrontRightModuleState(SwerveModuleState state) {
        _frontRight.setIdealState(state);
    }

    public void setFrontLeftModuleState(SwerveModuleState state) {
        _frontLeft.setIdealState(state);
    }

    public void setBackLeftModuleState(SwerveModuleState state) {
        _backLeft.setIdealState(state);
    }

    public void setBackRightModuleState(SwerveModuleState state) {
        _backRight.setIdealState(state);
    }

    public double getYaw() {
        return _imu.getYaw();
    }

    // yaw is negative to follow wpi coordinate system.
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-getYaw());
    }

    /**
     * Deprecated as the pigeon has no sure function
     */
    public void resetYaw() {
        _imu.reset();
    }

    /**
     * Method to set correct module speeds and angle based on wanted vx, vy, omega
     *
     * @param vx velocity in x direction
     * @param vy velocity in y direction
     * @param omega angular velocity (rotating speed)
     * @param fieldRelative forward is always forward no mater orientation of robot.
     */
    public void drive(double vx, double vy, double omega, boolean fieldRelative) {
        if (Math.abs(vx) < DEADBAND && Math.abs(vy) < DEADBAND && Math.abs(omega) < DEADBAND) {
            setFrontRightModuleState(
                    new SwerveModuleState(0, new Rotation2d(_frontRight.getModuleAngle())));
            setFrontLeftModuleState(
                    new SwerveModuleState(0, new Rotation2d(_frontLeft.getModuleAngle())));
            setBackRightModuleState(
                    new SwerveModuleState(0, new Rotation2d(_backRight.getModuleAngle())));
            setBackLeftModuleState(
                    new SwerveModuleState(0, new Rotation2d(_backLeft.getModuleAngle())));
            _PIDAngle = getHeading().getRadians();
            _angleController.reset(_PIDAngle);
        } else if (Math.abs(omega) > 0) {
            SwerveModuleState[] swerveModuleStates =
                    _kinematics.toSwerveModuleStates(
                            fieldRelative
                                    ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                            vx, vy, omega, getHeading())
                                    : new ChassisSpeeds(vx, vy, omega));
            SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, MAX_MODULE_SPEED_MPS);
            setFrontLeftModuleState(swerveModuleStates[0]);
            setFrontRightModuleState(swerveModuleStates[1]);
            setBackLeftModuleState(swerveModuleStates[2]);
            setBackRightModuleState(swerveModuleStates[3]);
            _PIDAngle = getHeading().getRadians();
            _angleController.reset(_PIDAngle);
        } else {
            SwerveModuleState[] swerveModuleStates =
                    _kinematics.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    vx,
                                    vy,
                                    _angleController.calculate(
                                            getHeading().getRadians(), _PIDAngle),
                                    new Rotation2d(_PIDAngle)));
            SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, MAX_MODULE_SPEED_MPS);
            setFrontLeftModuleState(swerveModuleStates[0]);
            setFrontRightModuleState(swerveModuleStates[1]);
            setBackLeftModuleState(swerveModuleStates[2]);
            setBackRightModuleState(swerveModuleStates[3]);
        }
    }

    public SwerveDriveKinematicsConstraint getKinematicConstraint() {
        return new SwerveDriveKinematicsConstraint(_kinematics, MAX_MPS);
    }

    public TrajectoryConfig getConfig() {
        return new TrajectoryConfig(MAX_MPS, MAX_MPSS)
                .setKinematics(_kinematics)
                .addConstraint(getKinematicConstraint());
    }

    public void trajectoryFollower(Trajectory.State goal, Rotation2d heading) {
        ChassisSpeeds adjustedSpeeds =
                _controller.calculate(_odometry.getPoseMeters(), goal, heading);
        SwerveModuleState[] moduleStates = _kinematics.toSwerveModuleStates(adjustedSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_MODULE_SPEED_MPS);
        setFrontLeftModuleState(moduleStates[0]);
        setFrontRightModuleState(moduleStates[1]);
        setBackLeftModuleState(moduleStates[2]);
        setBackRightModuleState(moduleStates[3]);
    }

    public Pose2d getOdometryPose() {
        return _odometry.getPoseMeters();
    }

    public void startModules() {
        _frontRight.start();
        _frontLeft.start();
        _backLeft.start();
        _backRight.start();
    }

    public double getLimelightAngle() {
        return Units.degreesToRadians(_limelight.getYaw());
//        return 0.0;
    }
    public boolean hasTarget() {
        return _limelight.hasTarget();
    }
}
