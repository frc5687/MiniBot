package org.frc5687.swerve.util;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;

public class Limelight {

    private PhotonCamera _camera;
    private PhotonPipelineResult _latest;
    private boolean _inDriverMode;

    public Limelight(String cameraName){
        _camera = new PhotonCamera(cameraName);
    }


    public double getLatency() {
        //Gets the latency of the camera
        return _camera.getLatestResult().getLatencyMillis();
    }

    public double getYaw() {
        //Gets the realitive yaw of the target
        double val = 0;
        try {
            if (_camera.getLatestResult().hasTargets()) {
                val = _camera.getLatestResult().getBestTarget().getYaw();
            }
        } catch (Exception e) {
            DriverStation.reportError("null val", false);
            return 0.0;
        }
        return val;
    }

    public double getArea(){
        //Get area of bounding box of target
        if (hasTarget()) {
            return _camera.getLatestResult().getBestTarget().getArea();
        }
        return 0.0;
    }

    public double getPitch(){
        //Gets realitive pitch of target
        if (hasTarget()) {
            return _camera.getLatestResult().getBestTarget().getPitch();
        }
        return 0.0;
    }

    public double getSkew(){
        if (hasTarget()) {
            return _camera.getLatestResult().getBestTarget().getSkew();
        }
        return 0.0;
    }

    public boolean hasTarget() {
        //Does the limelight have a target
        return _camera.getLatestResult().hasTargets();
    }

    public void enableLEDs(){
        _camera.setLED(VisionLEDMode.kOn);
    }

    public void disableLEDs(){
        _camera.setLED(VisionLEDMode.kOff);
    }

    public void LEDBlink(){
        _camera.setLED(VisionLEDMode.kBlink);
    }

    public void setDriverMode(boolean mode){
        _camera.setDriverMode(mode);
        _inDriverMode = mode;
    }

    public boolean getMode(){
        return _inDriverMode;
    }
}
//Kilroy Was Here