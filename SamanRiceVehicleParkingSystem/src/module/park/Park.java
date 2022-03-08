package module.park;

import java.sql.SQLException;

public interface Park {
    String park() throws SQLException, ClassNotFoundException;
}
