/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;

import org.frc5687.swerve.commands.AutoIntake;
import org.frc5687.swerve.commands.AutoShoot;
import org.frc5687.swerve.commands.Index;
import org.frc5687.swerve.commands.Shoot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.subsystems.Intake;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.Shooter;
import org.frc5687.swerve.util.AxisButton;
import org.frc5687.swerve.util.Gamepad;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {
    protected Gamepad _driverGamepad;
    protected Gamepad _operatorGamepad;

    private JoystickButton _intake;

    private JoystickButton _shootBTN;
    private JoystickButton _indexBTN;
    private JoystickButton _intakeBTN;
    private AxisButton _autoAim;

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        _driverGamepad = new Gamepad(0);
        _operatorGamepad = new Gamepad(1);
        _shootBTN = new JoystickButton(_operatorGamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());
        _indexBTN = new JoystickButton(_driverGamepad, Gamepad.Buttons.Y.getNumber());
        _intakeBTN = new JoystickButton(_operatorGamepad, Gamepad.Buttons.A.getNumber());
        _autoAim = new AxisButton(_driverGamepad, Gamepad.Axes.RIGHT_TRIGGER.getNumber(), 0.2);
    }

    public void initializeButtons(DriveTrain driveTrain, Shooter shooter, Indexer indexer, Intake intake) {
        _shootBTN.whenHeld(new AutoShoot(indexer, shooter));
        // -0.8 meaning when trigger is -80% it counts as a button press.
        _intakeBTN.whenHeld(new AutoIntake(intake));
    }

    public boolean autoAim() {
        return _autoAim.get();
    }

    public double getDriveY() {
        //        yIn = getSpeedFromAxis(_leftJoystick, _leftJoystick.getYChannel());
        yIn = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        yIn = applyDeadband(yIn, DEADBAND);

        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        //        xIn = -getSpeedFromAxis(_leftJoystick, _leftJoystick.getXChannel());
        xIn = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_X.getNumber());
        xIn = applyDeadband(xIn, DEADBAND);

        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_X.getNumber());
        speed = applyDeadband(speed, 0.2);
        return speed;
    }

    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {
        metric("Raw x", xIn);
        metric("Raw y", yIn);
    }
}
