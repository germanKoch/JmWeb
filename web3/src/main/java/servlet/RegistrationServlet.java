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
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService service = new BankClientService();
        BankClient client = new BankClient(req.getParameter("name"), req.getParameter("password"), Long.parseLong(req.getParameter("money")));

        Map<String,Object> data = new HashMap<>();
        try {
            if (service.addClient(client)) {
                data.put("message","Add client successful");
            } else {
                data.put("message","Client not add");
            }
            resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", data));
        } catch (DBException e) {
            e.printStackTrace();
        }


    }
}
