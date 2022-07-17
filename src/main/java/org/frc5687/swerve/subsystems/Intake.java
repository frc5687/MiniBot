package org.frc5687.swerve.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.Constants.INTAKE;
import org.frc5687.swerve.RobotMap.CAN.TALONFX;
import org.frc5687.swerve.util.OutliersContainer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;


public class Intake extends OutliersSubsystem{

    private TalonFX _intake;
    private Timer _timer;
    private DigitalInput _intakeBeamBreak;
    private Intake_State _state;

    public Intake(OutliersContainer container){
        super(container);
        _intake = new TalonFX(TALONFX.INTAKE);
        _timer = new Timer();
        _intakeBeamBreak = new DigitalInput(RobotMap.DIO.INTAKE_BEAM_BREAK);
        _state = Intake_State.UNKNOW;
    }

    /**
     * Intake a ball
     */
    public void IntakeBall(){
        _intake.set(ControlMode.PercentOutput, INTAKE.INTAKEING_SPEED);
        _state = Intake_State.INTAKEING;
    }

    /**
     * Sets the intake to idle speed
     */
    public void Idle(){
        _intake.set(ControlMode.PercentOutput, INTAKE.IDLE_INTAKEING_SPEED);
        _state = Intake_State.IDLE;
    }

    /**
     * Stops the intake from... well intaking
     */
    public void Kill(){
        _intake.set(ControlMode.PercentOutput, 0.0);
        _state = Intake_State.KILL;
    }

    /**
     * Spins the intake backwards to clean it out
     */
    public void Clean(){
        _intake.set(ControlMode.PercentOutput, INTAKE.CLEANING_SPEED);
        _state = Intake_State.CLEANING;
    }

    /**
     * Is the intakes beam break triggered
     * @return boolean
     */
    public boolean isTriggered(){
        return !_intakeBeamBreak.get();
    }

    public void setSpeed(double demand){
        _intake.set(ControlMode.PercentOutput, demand);
    }
    
    private enum Intake_State{
        INTAKEING(0),
        RETRACTING(1),
        IDLE(2),
        CLEANING(3),
        KILL(4),
        UNKNOW(5);

        private final int _value;
        Intake_State(int value){
            _value = value;
        }

        public int getValue(){
            return _value;
        }
    }
    
    @Override
    public void updateDashboard() {
        metric("Intake falcon velocity", _intake.getSelectedSensorVelocity());
        metric("Intake state", _state.getValue());
        metric("Beam broken", isTriggered());
    }  
}