import com.ibm.watson.developer_cloud.language_translation.v2.LanguageTranslation;
import com.ibm.watson.developer_cloud.language_translation.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translation.v2.model.TranslationResult;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONObject;

public class WatsonAnswers extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            String pregunta = request.getParameter("question");
            LanguageTranslation languageTranslation;
            languageTranslation = new LanguageTranslation();
            languageTranslation.setUsernameAndPassword("758e13c6-ad58-4b9e-97e8-1aa933b0f146", "HZfzYpXgfCNx");
            languageTranslation.setEndPoint("https://gateway.watsonplatform.net/language-translation/api");

            final String url = "https://dal09-gateway.watsonplatform.net/instance/582/deepqa/v1/question";

            TranslationResult translationResult = languageTranslation.translate(pregunta, Language.SPANISH, Language.ENGLISH).execute();
            String quest = translationResult.getTranslations().get(0).getTranslation();
            System.out.println(quest);
            String q = "{\"question\" : { \"questionText\" : \"" + quest + "\"}}";

            Authenticator.setDefault (new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication ("udem_student4", "NSroEf36".toCharArray());
                }
            });
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("X-SyncTimeout", "30");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(q);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

            conn.disconnect();

            JSONObject jObj = new JSONObject(builder.toString());

            String fAnswer = "";
            
            try{
                fAnswer = jObj.getJSONObject("question").getJSONArray("evidencelist").getJSONObject(0).getString("text");
            }catch(Exception e1){
                fAnswer = jObj.getJSONObject("question").getJSONArray("evidencelist").getJSONObject(1).getString("text");
            }

            String theAnswers = fAnswer;

            TranslationResult translationResults = languageTranslation.translate(theAnswers, Language.ENGLISH, Language.SPANISH).execute();
            String translations = translationResults.getTranslations().get(0).getTranslation();
            
            response.setCharacterEncoding("utf-8");
            PrintWriter respuesta = response.getWriter();
            respuesta.print(translations);
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