package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

public class JoystickErrorMessageSilencer extends CommandBase {

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
