/* Team 5687 (C)2020-2022 */
package org.frc5687.swerve;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class Constants {
    public static final int TICKS_PER_UPDATE = 1;
    public static final double METRIC_FLUSH_PERIOD = 1.0;
    public static final double UPDATE_PERIOD = 0.02;
    public static final double EPSILON = 0.00001;

    public static class DriveTrain {

        public static final double WIDTH = 0.4191; //meters between the wheels, taken from CAD
        public static final double LENGTH = 0.4191; //meters between the wheels, taken from CAD
        public static final Translation2d NW_POSITION =
                new Translation2d(WIDTH / 2.0, LENGTH / 2.0);
        public static final double NW_ENCODER_OFFSET = -0.122; // radians old -0.071
        public static final Translation2d NE_POSITION =
                new Translation2d(WIDTH / 2.0, -LENGTH / 2.0);
        public static final double NE_ENCODER_OFFSET = -0.122; // radians old -1.01
        public static final Translation2d SW_POSITION =
                new Translation2d(-WIDTH / 2.0, LENGTH / 2.0);
        public static final double SE_ENCODER_OFFSET = -0.122; // radians old -0.122
        public static final Translation2d SE_POSITION =
                new Translation2d(-WIDTH / 2.0, -LENGTH / 2.0);
        public static final double SW_ENCODER_OFFSET = -0.122; // radians old 0.585 + Math.PI

        public static final double DEADBAND = 0.1;

        public static final double MAX_MPS = 3.5; // Max speed of robot (m/s) 0.5 for testing

        public static final double MAX_ANG_VEL =
                Math.PI * 2; // Max rotation rate of robot (rads/s) for testing
        public static final double MAX_MPSS = 0.25; // Max acceleration of robot (m/s^2) for testing

        public static final double ANGLE_kP = 2.5;
        public static final double ANGLE_kI = 0.0;
        public static final double ANGLE_kD = 0.0;

        public static final double kP = 10.5;
        public static final double kI = 0.0;
        public static final double kD = 0.5;
        public static final double PROFILE_CONSTRAINT_VEL = 3.0 * Math.PI;
        public static final double PROFILE_CONSTRAINT_ACCEL = Math.PI;
        public static final double AIM_kP = 8.5;
        public static final double AIM_kI = 0.0;
        public static final double AIM_kD = 0.5;
    }

    public static class DifferentialSwerveModule {

        // update rate of our modules 5ms.
        public static final double kDt = 0.005;

        public static final double FALCON_FREE_SPEED =
                Units.rotationsPerMinuteToRadiansPerSecond(6380);
        public static final int TIMEOUT = 200;
        public static final double GEAR_RATIO_WHEEL = 6.46875 / 1.2;
        public static final double GEAR_RATIO_STEER = 9.2 / 1.2;
        public static final double FALCON_RATE = 600.0;
        public static final double WHEEL_RADIUS = 0.0508; // Meters with compression.
        public static final double MAX_MODULE_SPEED_MPS =
                (FALCON_FREE_SPEED / GEAR_RATIO_WHEEL) * WHEEL_RADIUS;
        public static final double TICKS_TO_ROTATIONS = 2048.0;
        public static final double VOLTAGE = 12.0;
        public static final double FEED_FORWARD = VOLTAGE / (FALCON_FREE_SPEED / GEAR_RATIO_WHEEL);

        public static final boolean ENABLE_CURRENT_LIMIT = true;
        public static final double CURRENT_LIMIT = 30.0;
        public static final double CURRENT_THRESHOLD = 30.0;
        public static final double CURRENT_TRIGGER_TIME = 0.0;

        // Create Parameters for DiffSwerve State Space
        public static final double INERTIA_WHEEL = 0.005;
        public static final double INERTIA_STEER = 0.004;
        // A weight for how aggressive each state should be ie. 0.08 radians will try to control the
        // angle more aggressively than the wheel angular velocity.
        public static final double Q_AZIMUTH_ANG_VELOCITY = 1.1; // radians per sec
        public static final double Q_AZIMUTH = 0.08; // radians
        public static final double Q_WHEEL_ANG_VELOCITY = 5; // radians per sec
        // This is for Kalman filter which isn't used for azimuth angle due to angle wrapping.
        // Model noise are assuming that our model isn't as accurate as our sensors.
        public static final double MODEL_AZIMUTH_ANGLE_NOISE = .1; // radians
        public static final double MODEL_AZIMUTH_ANG_VELOCITY_NOISE = 5.0; // radians per sec
        public static final double MODEL_WHEEL_ANG_VELOCITY_NOISE = 5.0; // radians per sec
        // Noise from sensors. Falcon With Gearbox causes us to have more uncertainty so we increase
        // the noise.
        public static final double SENSOR_AZIMUTH_ANGLE_NOISE = 0.01; // radians
        public static final double SENSOR_AZIMUTH_ANG_VELOCITY_NOISE = 0.1; // radians per sec
        public static final double SENSOR_WHEEL_ANG_VELOCITY_NOISE = 0.1; // radians per sec
        public static final double CONTROL_EFFORT = VOLTAGE;
    }

    public static class Intake {
        public static final int CAN_TIMOUT = 200;
        public static final double RETRACTED_ARM_ANGLE = Units.degreesToRadians(86);
        public static final double EXTENDED_ARM_ANGLE = Units.degreesToRadians(57);
        public static final double INTAKEING_SPEED = 1.00;
        public static final double IDLE_INTAKEING_SPEED = 0.00; //-0.30;
        public static final double CLEANING_SPEED = 0.00;
        public static final boolean ROLLER_INVERTED = false;
        public static final boolean ARM_INVERTED = false;

        public static final double ENCODER_OFFSET = 0.0;

        public static final double kP = 0.8;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

//        public static final double PROFILE_CONSTRAINT_VEL = 0.5 * Math.PI;
//        public static final double PROFILE_CONSTRAINT_ACCEL =  0.25 * Math.PI;
    }

    public static class Shooter {
        public static final double SHOOTER_SHOOT_SPEED = 1.0;
        public static final double SHOOTER_IDLE_SPEED = 0.3;
        public static final boolean NORTH_MOTOR_INVERTED = false;
        public static final boolean SOUTH_MOTOR_INVERTED = true;

        public static final double kP = 0.5;
        public static final double kI = 0.0;
        public static final double kD = 3.5;

        public static final double GEAR_RATIO = 1.0;
        public static final double TICKS_PER_ROTATION = 2048;
        public static final double MS_TO_MINUETS = 600;
        public static final double SHOOTING_FLYWHEEL_RPM = 2900; // rpm
        public static final double WHEEL_RADIUS = Units.inchesToMeters(2.0);
        public static final long DELAY = 200;
        public static final double RPM_TOLERANCE = 300;
    }

    public static class Indexer {
            public static final boolean INVERTED = false;
            public static final int CAN_TIMEOUT = 200;

            public static final double IDLE_SPEED = 0.3;
            public static final double STOP_SPEED = 0.0;
            public static final double INDEX_SPEED = 0.9;
            public static final long DELAY = 500;
    }
}
