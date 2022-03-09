// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import frc.robot.util.TalonUtil;

/** Add your docs here. */
public class TalonConfigs {

    static interface TalonConfigurator {
        void configure(WPI_TalonSRX talon);
    }

    // MAX_ARM = 918 V == 100
    static class HangarConfigs {
        static void configureArm(WPI_TalonSRX talon) {

            TalonSRXConfiguration config = new TalonSRXConfiguration();
            /******** CURRENT LIMIT (ENABLED BELOW) **********/
            config.peakCurrentLimit = 20;
            config.peakCurrentDuration = 100;
            config.continuousCurrentLimit = 7;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;
            /****************** RAMPING *********************/
            config.openloopRamp = 0.0;
            config.closedloopRamp = 0.0;
            /******************* OUTPUT LIMITS **************/
            // config.peakOutputForward = 0.5;
            // config.peakOutputReverse = -0.5;

            config.peakOutputForward = 1.0;
            config.peakOutputReverse = -1.0;

            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;


            /************LIMITS *******************/
            config.forwardSoftLimitThreshold = 950;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -100;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 300;
            config.motionAcceleration = 200;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = true;

            //FANCY POSITION
            config.slot0.kP = 1.0;
            config.slot0.kI = 0.0;
            config.slot0.kD = 0.0;
            config.slot0.kF = 1.0;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            //CURRENT
            config.slot1.kP = 1.0;
            config.slot1.kI = 0.0;
            config.slot1.kD = 0.0;
            config.slot1.kF = 1.0;
            config.slot1.integralZone = 10;
            config.slot1.allowableClosedloopError = 10;
            config.slot1.maxIntegralAccumulator = 100;
            config.slot1.closedLoopPeakOutput = 1.0;
            config.slot1.closedLoopPeriod = 1;


            TalonUtil.requireOK(talon.configAllSettings(config, 50));

            talon.enableCurrentLimit(true);
            talon.enableVoltageCompensation(false);
            talon.selectProfileSlot(0, 0);

            talon.setInverted(false);
            talon.setSensorPhase(true);
            talon.setNeutralMode(NeutralMode.Brake);
        }

        static void configureWinch(WPI_TalonSRX talon) {

            TalonSRXConfiguration config = new TalonSRXConfiguration();

            /******** CURRENT LIMIT (ENABLED BELOW) **********/
            // config.peakCurrentLimit = 40;
            // config.peakCurrentDuration = 1;
            // config.continuousCurrentLimit = 40;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;
            /********** RAMPING ****************/
            config.openloopRamp = 0.0;// 1.0;
            config.closedloopRamp =0.0;// 1.0;


            /***********OUTPUT LIMITS */
            config.peakOutputForward = 1.0;
            config.peakOutputReverse = -1.0;

            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;
            /********* SOFT LIMITS **********/
            config.forwardSoftLimitThreshold = 100;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -50000;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 3000;
            config.motionAcceleration = 6000;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = false;

            // FANCY POSITION SLOT
            config.slot0.kP = 2.0;
            config.slot0.kI = 0.0;
            config.slot0.kD = 0.0;
            config.slot1.kF = 10.0;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            // CURRENT
            config.slot1.kP = 0;
            config.slot1.kI = 0.0;
            config.slot1.kD = 0.0;
            config.slot1.kF = 0.01;
            config.slot1.integralZone = 10;
            config.slot1.allowableClosedloopError = 10;
            config.slot1.maxIntegralAccumulator = 100;
            config.slot1.closedLoopPeakOutput = 0.5;
            config.slot1.closedLoopPeriod = 1;

            TalonUtil.requireOK(talon.configAllSettings(config, 50));

            talon.setInverted(false);
            talon.setSensorPhase(false);

            talon.setNeutralMode(NeutralMode.Brake);
            talon.enableCurrentLimit(false);
            talon.enableVoltageCompensation(false);
            talon.selectProfileSlot(0, 0);
        }

        static void configureHook(WPI_TalonSRX talon) {

            TalonSRXConfiguration config = new TalonSRXConfiguration();
            /******** CURRENT LIMIT (ENABLED BELOW) **********/
            config.peakCurrentLimit = 15;
            config.peakCurrentDuration = 100;
            config.continuousCurrentLimit = 4;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            config.openloopRamp = 0.0;
            config.closedloopRamp = 0.0;
            /****** OUTPUT LIMITS **********/
            // config.peakOutputForward = 0.6;
            // config.peakOutputReverse = -0.6;
            
            config.peakOutputForward = 1.0;
            config.peakOutputReverse = -1.0;
            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;
            
            
            config.forwardSoftLimitThreshold = 100;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -1000;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 1000;
            config.motionAcceleration = 1000;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = true;

            // SLOT O: FANCY POSITION
            config.slot0.kP = 0.50;
           // config.slot0.kI = 0.001;
            config.slot0.kD = 0.0;
            config.slot0.kF = 0.50;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            // SLOT 1: CURRENT
            config.slot1.kP = 0.0;
            config.slot1.kI = 0.0;
            config.slot1.kD = 0.0;
            config.slot1.kF = 1.0;
            config.slot1.integralZone = 10;
            config.slot1.allowableClosedloopError = 10;
            config.slot1.maxIntegralAccumulator = 100;
            config.slot1.closedLoopPeakOutput = 1.0;
            config.slot1.closedLoopPeriod = 1;
           
            TalonUtil.requireOK(talon.configAllSettings(config, 50));
            talon.setInverted(false);
            talon.setSensorPhase(true);

            talon.setNeutralMode(NeutralMode.Brake);
            talon.enableCurrentLimit(false);
            talon.enableVoltageCompensation(false);
            talon.selectProfileSlot(0, 0);
        }
    }

    public static void configureHangarTalons(HangarSubsystem hangar) {

        for(var talon : hangar.talons){
            TalonUtil.requireOK(talon.configFactoryDefault(50));
        }
    
        HangarConfigs.configureArm(hangar.arm);
        HangarConfigs.configureHook(hangar.hook);
        HangarConfigs.configureWinch(hangar.winch);
    }
}
