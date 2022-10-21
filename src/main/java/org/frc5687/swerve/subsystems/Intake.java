package org.frc5687.swerve.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.RobotMap.CAN.TALONFX;
import org.frc5687.swerve.util.OutliersContainer;

import edu.wpi.first.wpilibj.Servo;

import static org.frc5687.swerve.Constants.DriveTrain.PROFILE_CONSTRAINT_ACCEL;
import static org.frc5687.swerve.Constants.DriveTrain.PROFILE_CONSTRAINT_VEL;

public class Intake extends OutliersSubsystem{
    private TalonFX _roller;
    private TalonFX _arm;

    private DutyCycleEncoder _armEncoder;
    private PIDController _armController;


    public Intake(OutliersContainer container){
        super(container);
        _roller = new TalonFX(TALONFX.INTAKE_ROLLER);
        _roller.setInverted(Constants.Intake.ROLLER_INVERTED);

        _arm = new TalonFX(TALONFX.INTAKE_ARM);
        _arm.setInverted(Constants.Intake.ARM_INVERTED);
        _arm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.Intake.CAN_TIMOUT);

        _armEncoder = new DutyCycleEncoder(RobotMap.DIO.ENCODER_ARM);
        _armEncoder.setDistancePerRotation(2.0 * Math.PI);

        _armController = new PIDController(
                Constants.Intake.kP, // proportional constant.
                Constants.Intake.kI, // integral constant.
                Constants.Intake.kD // derivative constant.
                // we are adding a trapezoidal profile to the controller, so the arm has smooth control
//                new TrapezoidProfile.Constraints(
//                        Constants.Intake.PROFILE_CONSTRAINT_VEL, // max velocity constraint rad/s
//                        Constants.Intake.PROFILE_CONSTRAINT_ACCEL // max acceleration constraint is rad/s^2
//                )
        );
        _armController.setTolerance(Units.degreesToRadians(5));
    }

    public void setArmSpeed(double demand) {
        _arm.set(ControlMode.PercentOutput, demand);
    }

    /**
     * @param angleReference angle that the arm wants to be in radians.
     * @return Output of the ProfilePIDController given a reference.
     */
    public double getArmAngleControllerOutput(double angleReference) {
        metric("Angle ref", angleReference);
        double pow = _armController.calculate(getArmAngle(), angleReference);
        metric("controller power", pow);
        return pow;
    }
    /**
     * Bore encoder measurement in radians.
     * @return angle in radians
     */
    public double getArmAngle() {
        return _armEncoder.getDistance() % (2.0 * Math.PI) - Constants.Intake.ENCODER_OFFSET;
    }

    public void setRollerSpeed(double demand) {
        _roller.set(ControlMode.PercentOutput, demand);
    }
    
    @Override
    public void updateDashboard() {
        metric("Arm Angle Radians", getArmAngle());
        metric("Arm Angle Degrees", Units.radiansToDegrees(getArmAngle()));
    }
}