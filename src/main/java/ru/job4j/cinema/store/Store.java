package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface Store {
    Collection<Ticket> findAllTickets();
    boolean isTicketFree(Ticket ticket);
    void buyTicket(Account account, Ticket ticket);
}
