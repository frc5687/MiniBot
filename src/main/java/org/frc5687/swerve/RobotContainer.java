/* Team 5687 (C)2021 */
package org.frc5687.swerve;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.frc5687.swerve.commands.Drive;
import org.frc5687.swerve.commands.OutliersCommand;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.OutliersSubsystem;
import org.frc5687.swerve.util.OutliersContainer;

public class RobotContainer extends OutliersContainer {

    private OI _oi;
    private Pigeon2 _pigeon;

    private Robot _robot;
    private DriveTrain _driveTrain;

    public RobotContainer(Robot robot, IdentityMode identityMode) {
        super(identityMode);
        _robot = robot;
    }

    public void init() {
        _oi = new OI();
        _pigeon = new Pigeon2(RobotMap.CAN.PIGEON.PIGEON, "Tomcat");

        _pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_6_SensorFusion, 10, 10);

        
        _driveTrain = new DriveTrain(this, _oi, _pigeon);

        setDefaultCommand(_driveTrain, new Drive(_driveTrain, _oi));
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
    }

    public void controllerPeriodic() {
        if (_driveTrain != null) {
            _driveTrain.controllerPeriodic();
        }
    }
}
