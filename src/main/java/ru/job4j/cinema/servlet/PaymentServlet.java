package ru.job4j.cinema.servlet;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String rowAndCell = req.getParameter("placeValue");
        int row = Character.getNumericValue(rowAndCell.charAt(0));
        int cell = Character.getNumericValue(rowAndCell.charAt(1));
        Ticket ticket = new Ticket(1, row, cell, 0);
        if (DbStore.instOf().isTicketFree(ticket)) {
            Account account = new Account(
                    req.getParameter("username"),
                    req.getParameter("email"),
                    req.getParameter("phone")
            );
            DbStore.instOf().buyTicket(account, ticket);
        } else {
            req.setAttribute("alreadySold", ticket);
        }
        req.getRequestDispatcher("afterpayment.jsp").forward(req, resp);
    }
}
