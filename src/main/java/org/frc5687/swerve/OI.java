/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.frc5687.swerve.subsystems.DriveTrain;
import org.frc5687.swerve.util.AxisButton;
import org.frc5687.swerve.util.Gamepad;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {
    protected Gamepad _driverGamepad;

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        _driverGamepad = new Gamepad(0);
    }

    public void initializeButtons(DriveTrain driveTrain) {}

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
        double speed = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_Y.getNumber());
        speed = applyDeadband(speed, 0.2);
        return speed;
    }

    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }

    @Override
    public void updateDashboard() {}
}
