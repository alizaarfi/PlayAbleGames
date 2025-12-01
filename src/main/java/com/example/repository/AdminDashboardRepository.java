package com.example.repository;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Repository responsible for retrieving aggregated dashboard metrics
 * by calling database stored procedures.
 *
 * - Uses constructor injection to obtain a JDBC `DataSource`.
 * - Exposes simple methods to retrieve individual integer metrics.
 * - Each public method is annotated with `@Transactional` so calls run
 *   within a transactional context if required by the environment.
 */
@Singleton
public class AdminDashboardRepository {
    private final DataSource dataSource;

    /**
     * Constructor injection: the framework supplies a DataSource bean.
     *
     * @param dataSource JDBC data source used to open connections.
     */
    public AdminDashboardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Get the total number of flagged reviews from the database.
     *
     * This delegates to the generic stored-procedure caller.
     *
     * @return integer count reported by the `totalFlaggedReviews` procedure.
     */
    @Transactional
    public int getTotalFlagged() {
        return callProcedureWithOutParam("totalFlaggedReviews");
    }

    /**
     * Get the total number of users from the database.
     *
     * @return integer count reported by the `totalUsers` procedure.
     */
    @Transactional
    public int getTotalUsers() {
        return callProcedureWithOutParam("totalUsers");
    }

    /**
     * Get the total number of games from the database.
     *
     * @return integer count reported by the `totalGames` procedure.
     */
    @Transactional
    public int getTotalGames() {
        return callProcedureWithOutParam("totalGames");
    }


    /**
     * Helper that calls a stored procedure which returns a single OUT integer parameter.
     *
     * - Opens a Connection and prepares a CallableStatement using try-with-resources so
     *   both Connection and CallableStatement are closed automatically.
     * - Registers the first parameter as an OUT integer, executes the call, and retrieves the value.
     * - Wraps any SQLException or other exception into a RuntimeException so callers can handle it.
     *
     * @param procedureName name of the stored procedure to call (no schema prefix)
     * @return value of the OUT integer parameter returned by the procedure
     */
    private int callProcedureWithOutParam(String procedureName) {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL " + procedureName + "(?)}")) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to call procedure: " + procedureName, e);
        }
    }
}