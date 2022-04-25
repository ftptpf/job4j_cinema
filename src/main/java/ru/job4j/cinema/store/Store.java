package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface Store {
    Collection<Ticket> findAllTickets();
}
