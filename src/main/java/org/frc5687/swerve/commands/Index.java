package org.frc5687.swerve.commands;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.subsystems.Indexer;

public class Index extends OutliersCommand {

    private Indexer _indexer;

    public Index(Indexer indexer) {
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
        _indexer.setIndexerSpeed(Constants.Indexer.INDEX_SPEED);
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
