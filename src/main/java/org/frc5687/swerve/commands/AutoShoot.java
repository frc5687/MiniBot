package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.Shooter;

public class AutoShoot extends OutliersCommand {

    private Indexer _indexer;
    private Shooter _shooter;

    private long _delay;

    private AutoShootingState _state;
    
    public AutoShoot(Indexer indexer, Shooter shooter) {
        _indexer = indexer;
        _shooter = shooter;
        _state = AutoShootingState.WAITING;
        addRequirements(_indexer, _shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
        _delay = System.currentTimeMillis() + Constants.Shooter.DELAY;
        _state = AutoShootingState.WAITING;
    }

    @Override
    public void execute() {
        metric("State", _state.name());
        metric("Switch state", _indexer.isBallOneDetected() && _shooter.isFlywheelUptoSpeed());
        switch(_state) {
            case WAITING: {
                _indexer.setIndexerSpeed(0.0);
                _shooter.setNorthRPM(Constants.Shooter.SHOOTING_FLYWHEEL_RPM);
                _shooter.setSouthRPM(Constants.Shooter.SHOOTING_FLYWHEEL_RPM);
                if (_shooter.isFlywheelUptoSpeed() && _delay < System.currentTimeMillis()) {
                   _state = AutoShootingState.SHOOTING;
                }
                break;
            }
            case SHOOTING: {
                _shooter.setNorthRPM(Constants.Shooter.SHOOTING_FLYWHEEL_RPM);
                _shooter.setSouthRPM(Constants.Shooter.SHOOTING_FLYWHEEL_RPM);
                _indexer.setIndexerSpeed(Constants.Indexer.INDEX_SPEED);
                break;
            }
        }
    }

    @Override
    public boolean isFinished() {
            // TODO Auto-generated method stub
            return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }


    private enum AutoShootingState {
        WAITING(0),
        SHOOTING(1);

        private final int _value;
        AutoShootingState(int value) { _value = value; }

        public int getValue() { return _value; }
    }
}
