package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Indexer;
import org.frc5687.swerve.subsystems.Shooter;

public class Shoot extends OutliersCommand{

    private Indexer _indexer;
    private Shooter _shooter;

    private long _delay;

    private AutoShootingState _state;

    public Shoot(Shooter shooter, Indexer indexer){
        _shooter = shooter;
        _indexer = indexer;
        _state = AutoShootingState.WAITING;
        addRequirements(shooter, indexer);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        super.execute();
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
    public void end(boolean interrupted) {
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