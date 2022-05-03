package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class DbStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();
    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class.getName());

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io  = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(DbStore.class.getClassLoader()
                                .getResourceAsStream("db.properties"))
                )
        )) {
            cfg.load(io);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>(9);
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(
                            it.getInt("id"),
                            it.getInt("session_id"),
                            it.getInt("row"),
                            it.getInt("cell"),
                            it.getInt("account_id")));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception information:", e);
        }
        return tickets;
    }

    public void buyTicket(Account account, Ticket ticket) {
        int accountId = saveAccount(account);
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "UPDATE ticket SET account_id = ? WHERE cell = ? AND row = ? AND session_id = ?")) {
            ps.setInt(1, accountId);
            ps.setInt(2, ticket.getCell());
            ps.setInt(3, ticket.getRow());
            ps.setInt(4, ticket.getSessionId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQL Exception information:", e);
        }
    }

    public int saveAccount(Account account) {
        int generatedAccountId = 0;
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO account(username, email, phone) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.execute();
            try (ResultSet generatedKey = ps.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    generatedAccountId = generatedKey.getInt("id");
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception information:", e);
        }
        return generatedAccountId;
    }

    public boolean isTicketFree(Ticket ticket) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "SELECT * FROM ticket WHERE cell = ? AND row = ? AND session_id = ?")) {
            ps.setInt(1, ticket.getCell());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getSessionId());
            try (ResultSet it = ps.executeQuery()) {
                if (it.next() && it.getInt("account_id") == 0) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception information:", e);
        }
        return result;
    }
}
