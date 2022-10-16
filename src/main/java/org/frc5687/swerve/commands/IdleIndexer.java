package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Indexer;

public class IdleIndexer extends OutliersCommand {

    private Indexer _indexer;
    private IndexingState _state;

    private long _detectedDelay;

    public IdleIndexer(Indexer indexer) {
        _indexer = indexer;
        _state = IndexingState.IDLE;
        _detectedDelay = 0;
        addRequirements(_indexer);
    }

    @Override
    public void initialize() {
        super.initialize();
        _state = IndexingState.IDLE;
    }

    @Override
    public void execute() {
        switch(_state) {
            case IDLE:
                _indexer.setIndexerSpeed(Constants.Indexer.IDLE_SPEED);
                if (_indexer.isBallOneDetected()) {
                    _state = IndexingState.FIRST_BALL_DETECTED;
                }
                break;
            case FIRST_BALL_DETECTED:
                _indexer.setIndexerSpeed(Constants.Indexer.STOP_SPEED);
                _state = IndexingState.WAITING_FOR_BALL;
                break;
            case WAITING_FOR_BALL:
                _indexer.setIndexerSpeed(Constants.Indexer.STOP_SPEED);
                if (_indexer.isBallTwoDetected()) {
                    _detectedDelay = System.currentTimeMillis() + Constants.Indexer.DELAY;
                    _state = IndexingState.SECOND_BALL_DETECTED;
                }
                break;
            case SECOND_BALL_DETECTED:
                if (_detectedDelay > System.currentTimeMillis()) {
                    _indexer.setIndexerSpeed(Constants.Indexer.INDEX_SPEED);
                } else {
                    _indexer.setIndexerSpeed(Constants.Indexer.STOP_SPEED);
                    _state = IndexingState.STOP;
                }
                break;
            case STOP:
                _indexer.setIndexerSpeed(Constants.Indexer.INDEX_SPEED);
                break;
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

    private enum IndexingState {
        IDLE(0),
        FIRST_BALL_DETECTED(1),
        WAITING_FOR_BALL(2),
        SECOND_BALL_DETECTED(3),
        STOP(4);

        private final int _value;
        IndexingState(int value) { _value = value; }

        public int getValue() { return _value; }
    }
    
}
