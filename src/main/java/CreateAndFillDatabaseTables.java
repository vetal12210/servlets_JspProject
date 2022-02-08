import java.sql.SQLException;

import static util.CreateDatabaseTables.createDbTables;
import static util.FillDatabaseTables.fillTables;

public class CreateAndFillDatabaseTables {
    public static void main(String[] args) {
        try {
            createDbTables();
            fillTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
