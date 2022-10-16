package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Indexer;

public class IdleIndexer extends OutliersCommand {

    private Indexer _indexer;

    public IdleIndexer(Indexer indexer) {
        _indexer = indexer;
        addRequirements(_indexer);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        if (_indexer.isBallOneDetected()) {
            _indexer.setIndexerSpeed(Constants.Indexer.STOP_SPEED);
        } else {
            _indexer.setIndexerSpeed(Constants.Indexer.IDLE_SPEED);
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
    
}
