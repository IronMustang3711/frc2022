// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


public class ShuffleboardCommandsTab {

    static class JoystickErrorMessageSilencer extends CommandBase {
        @Override
        public boolean runsWhenDisabled() {
            return true;
        }
      
        @SuppressWarnings("all")
        @Override
        public void initialize() {
          try {
      
            Field nextMessageTime = DriverStation.class.getDeclaredField("m_nextMessageTime");
            nextMessageTime.setAccessible(true);
            nextMessageTime.setDouble(DriverStation.getInstance(), Double.MAX_VALUE);
      
          } catch (NoSuchFieldException | IllegalAccessException | InaccessibleObjectException e) {
            e.fillInStackTrace();
            var ccw = new CharArrayWriter(120);
            e.printStackTrace(new PrintWriter(ccw));
            DriverStation.reportError(new String(ccw.toCharArray()), e.getStackTrace());
      
          }
        }
      
        @Override
        public boolean isFinished() {
          return true;
        }
    }

    public static void create(RobotContainer robot){
        var tab = Shuffleboard.getTab("Commands");
        tab.add(new JoystickErrorMessageSilencer());

        for(var sys : robot.subsystems){
            var lst = tab.getLayout(sys.getName(), BuiltInLayouts.kList);
            lst.add(new ClearFaults(sys));
            lst.add(new StopMotors(sys));
            lst.add(new EncoderReset(sys));

        }

    }
}
