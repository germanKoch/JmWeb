package servlet;

import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransactionServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("moneyTransactionPage.html", new HashMap<>()));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService service = new BankClientService();
        BankClient sender = new BankClient(req.getParameter("senderName"),req.getParameter("senderPass"));
        try {
            if (service.validateClient(sender) && service.sendMoneyToClient(sender, req.getParameter("nameTo"), Long.parseLong(req.getParameter("count")))) {
                resp.getWriter().println("The transaction was successful");
            } else {
                resp.getWriter().println("transaction rejected");
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        //resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", data));
    }
}
