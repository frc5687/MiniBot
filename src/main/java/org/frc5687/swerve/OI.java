/* Team 5687 (C)2020-2021 */
package org.frc5687.swerve;

import static org.frc5687.swerve.Constants.DriveTrain.*;
import static org.frc5687.swerve.util.Helpers.*;

import edu.wpi.first.wpilibj.Joystick;
import org.frc5687.lib.oi.Gamepad;
import org.frc5687.swerve.subsystems.*;
import org.frc5687.swerve.commands.Shoot;
import org.frc5687.swerve.util.OutliersProxy;

public class OI extends OutliersProxy {
    protected Gamepad _driverGamepad;

    private double yIn = 0;
    private double xIn = 0;

    public OI() {
        _driverGamepad = new Gamepad(0);
    }

    public void initializeButtons(DriveTrain driveTrain, Shooter _shooter) {
        _driverGamepad.getAButton().whenPressed(driveTrain::resetYaw);
        _driverGamepad.getBButton().whenHeld(new Shoot(_shooter));
    }

    public boolean snap() {
        return _driverGamepad.getYButton().get();
    }

    public boolean autoAim() {
        return _driverGamepad.getXButton().get();
    }

    public double getDriveY() {
        yIn = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        yIn = applyDeadband(yIn, TRANSLATION_DEADBAND);

        double yOut = yIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        yOut = (yOut + (yIn * 2)) / 3.0;
        return yOut;
    }

    public double getDriveX() {
        xIn = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_X.getNumber());
        xIn = applyDeadband(xIn, TRANSLATION_DEADBAND);

        double xOut = xIn / (Math.sqrt(yIn * yIn + (xIn * xIn)) + Constants.EPSILON);
        xOut = (xOut + (xIn * 2)) / 3.0;
        return xOut;
    }

    public double getRotationX() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_X.getNumber());
        speed = applyDeadband(speed, ROTATION_DEADBAND);
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
