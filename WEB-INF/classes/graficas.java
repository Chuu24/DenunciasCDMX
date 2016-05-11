import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class graficas extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
            ServletContext context = getServletContext();
            String csvFile = context.getRealPath("/csv/incidentespordelegacion");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		String horas = "";
		String cantidadS = "";


        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] general = line.split(cvsSplitBy);
		horas+=""+general[0]+",";
		cantidadS+=general[1]+",";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String s = "";

	for(int i = 0;i<horas.length()-1;i++){
	    s+=horas.charAt(i);
	}


        String  t = "";

        for(int j = 0;j<cantidadS.length()-1;j++){
            t+=cantidadS.charAt(j);
        }


        try{
            PrintWriter respuesta = response.getWriter();
            respuesta.print(t+"|"+s);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}