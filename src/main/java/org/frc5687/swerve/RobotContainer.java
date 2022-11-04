/* Team 5687 (C)2021 */
package org.frc5687.swerve;

import com.ctre.phoenix.sensors.Pigeon2;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.frc5687.swerve.commands.Drive;
import org.frc5687.swerve.commands.IdleIntake;
import org.frc5687.swerve.commands.OutliersCommand;
import org.frc5687.swerve.commands.RetractIntake;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Intake;
import org.frc5687.swerve.commands.IdleShooter;
import org.frc5687.swerve.commands.IdleIndexer;
import org.frc5687.swerve.commands.OutliersCommand;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.OutliersSubsystem;
import org.frc5687.swerve.subsystems.Shooter;
import org.frc5687.swerve.util.Limelight;
import org.frc5687.swerve.util.OutliersContainer;

public class RobotContainer extends OutliersContainer {

    private OI _oi;
    private AHRS _imu;
    private Limelight _limelight;
    //private Pigeon2 _pigeon;

    private Shooter _shooter;
    private Indexer _indexer;

    private Robot _robot;
    private DriveTrain _driveTrain;
    private Intake _intake;

    public RobotContainer(Robot robot, IdentityMode identityMode) {
        super(identityMode);
        _robot = robot;
    }

    public void init() {
        _oi = new OI();
        _imu = new AHRS(SPI.Port.kMXP, (byte) 200);
        _limelight = new Limelight("limelight");
       // _pigeon = new Pigeon2(RobotMap.CAN.PIGEON.PIGEON, "rio");
       // _pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_6_SensorFusion, 10, 10);

        _shooter = new Shooter(this);
        _indexer = new Indexer(this);
        _intake = new Intake(this);
        _driveTrain = new DriveTrain(this, _oi, _imu, _limelight);


        _oi.initializeButtons(_driveTrain, _shooter, _indexer, _intake);

        setDefaultCommand(_shooter, new IdleShooter(_shooter));
        setDefaultCommand(_driveTrain, new Drive(_driveTrain, _oi));
        setDefaultCommand(_intake, new IdleIntake(_intake));
        setDefaultCommand(_indexer, new IdleIndexer(_indexer));
        _robot.addPeriodic(this::controllerPeriodic, 0.005, 0.005);
    }

    public void periodic() {}

    public void disabledPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void teleopInit() {}

    @Override
    public void autonomousInit() {}

    private void setDefaultCommand(OutliersSubsystem subSystem, OutliersCommand command) {
        if (subSystem == null || command == null) {
            return;
        }
        CommandScheduler s = CommandScheduler.getInstance();
        s.setDefaultCommand(subSystem, command);
    }

    @Override
    public void updateDashboard() {
        _driveTrain.updateDashboard();
//        _intake.updateDashboard();
        _indexer.updateDashboard();
        _shooter.updateDashboard();
    }

    public void controllerPeriodic() {
        if (_driveTrain != null) {
            _driveTrain.controllerPeriodic();
        }
    }
}
