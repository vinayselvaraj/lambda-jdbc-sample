package jdbcsample;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCSample {

  public String getCurrentTime(Context context) {
    LambdaLogger logger = context.getLogger();
    logger.log("Invoked JDBCSample.getCurrentTime");

    String currentTime = "unavailable";

    // Get time from DB server
    try {
      String url = "jdbc:mysql://HOSTNAME:3306";
      String username = "USERNAME";
      String password = "PASSWORD";

      Connection conn = DriverManager.getConnection(url, username, password);
      Statement stmt = conn.createStatement();
      ResultSet resultSet = stmt.executeQuery("SELECT NOW()");

      if (resultSet.next()) {
        currentTime = resultSet.getObject(1).toString();
      }

      logger.log("Successfully executed query.  Result: " + currentTime);

    } catch (Exception e) {
      e.printStackTrace();
      logger.log("Caught exception: " + e.getMessage());
    }

    return currentTime;
  }
}
