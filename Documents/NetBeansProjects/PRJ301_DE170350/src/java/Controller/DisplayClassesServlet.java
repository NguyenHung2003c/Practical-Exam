package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/DisplayClassesServlet")
public class DisplayClassesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://HUNGHEO;databaseName=PRJ301_DE170350;encrypt=false;trustServerCertificate=true";
            String username = "sa";
            String password = "123";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT ClassID, Name, NumberOfStudents FROM DE170350 WHERE NumberOfStudents > 31";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    ResultSet resultSet = statement.executeQuery();

                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Classes with More Than 31 Students</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h2>Classes with More Than 31 Students</h2>");
                    out.println("<table border='1'>");
                    out.println("<tr><th>Class ID</th><th>Name</th><th>Number of Students</th></tr>");

                    while (resultSet.next()) {
                        out.println("<tr>");
                        out.println("<td>" + resultSet.getString("ClassID") + "</td>");
                        out.println("<td>" + resultSet.getString("Name") + "</td>");
                        out.println("<td>" + resultSet.getInt("NumberOfStudents") + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
