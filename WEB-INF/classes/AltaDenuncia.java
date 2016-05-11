import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;

public class AltaDenuncia extends HttpServlet{
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormats;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            String error="";
            
            String del = request.getParameter("delegacion");
            String hora = request.getParameter("horad");
            String fecha  = request.getParameter("fechad");

            boolean act = true;
            
            String usuarioB = getServletContext().getInitParameter("usuario");
            String servidorB = getServletContext().getInitParameter("servidor");
            String passB = getServletContext().getInitParameter("pass");

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + servidorB + "/acosodf";
            Connection con = DriverManager.getConnection(url,usuarioB,passB);

            Statement stat = con.createStatement();

            if(!(del.equals("")) && !(fecha.equals("")) && !(hora.equals(""))){
                final Calendar calendar = Calendar.getInstance();
                Calendar hoy = Calendar.getInstance();
                hoy.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormats = new SimpleDateFormat("kk:mm");
                String horad = simpleDateFormats.format(hoy.getTime());
                String fechad = simpleDateFormat.format(hoy.getTime());
                stat.executeUpdate("INSERT INTO denuncias(delegacion, hora_denuncia, fecha_denuncia, hora, fecha) VALUES (\"" + del + "\",\"" + horad + "\",\"" + fechad + "\",\"" + hora + "\",\"" + fecha + "\");");
            }else{
                act = false;
                error="";
            }

            stat.close();
            con.close();
            
            response.setCharacterEncoding("utf-8");
            PrintWriter respuesta = response.getWriter();
            respuesta.print("Ok");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        try{
            RequestDispatcher disp =  getServletContext().getRequestDispatcher("/index.html");

            if(disp!=null){
                    disp.forward(request,response);
            }
        }catch(Exception e){
            
        }
    }
}