package org.frc5687.swerve.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.util.OutliersContainer;
import org.frc5687.swerve.util.ProximitySensor;

public class Indexer extends OutliersSubsystem {

    private TalonFX _indexer;
    private ProximitySensor _firstBallSensor;

    public Indexer(OutliersContainer container) {
        super(container);

        _indexer = new TalonFX(RobotMap.CAN.TALONFX.INDEXER);
        _indexer.setInverted(Constants.Indexer.INVERTED);
        _indexer.setNeutralMode(NeutralMode.Coast);
        _indexer.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, Constants.Indexer.CAN_TIMEOUT);

        _firstBallSensor = new ProximitySensor(RobotMap.DIO.BALL_SENSOR_ONE);
    }

    @Override
    public void periodic() {
        super.periodic();
    }

    public void setIndexerSpeed(double demand) {
        _indexer.set(ControlMode.PercentOutput, demand);
    }

    public boolean isBallOneDetected() {
        return _firstBallSensor.get();
    }

    @Override
    public void updateDashboard() {
        metric("BallDetected", isBallOneDetected());
        // TODO Auto-generated method stub
    }


}
