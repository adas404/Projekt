/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.Date;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Adaś
 */
@Stateless
@LocalBean
public class UstawieniaMaila {
    private int port = 465;
    private String uzytkownik = "cars.wieszjakjezdzisz@gmail.com";
    private String haslo = "cars123456789";
    private String serwer = "smtp.gmail.com";
    private String from = "cars.wieszjakjezdzisz@gmail.com";
    private boolean auth =true;
    private enum protokol {SMTP};
    private boolean debug = false;
    
    
    public void wyslijMail(String gdzie, String temat, String body){
        Properties ustawienia = new Properties();
        ustawienia.put("mail.smtp.host", serwer);
        ustawienia.put("mail.smtp.port", port);
        ustawienia.put("mail.smtp.ssl.enable", true);
        
        Authenticator autt = null;
        if (auth){
            ustawienia.put("mail.smtp.auth", true);
            autt = new Authenticator() {
               private PasswordAuthentication hs = new PasswordAuthentication(uzytkownik, haslo);
               @Override
               public PasswordAuthentication getPasswordAuthentication(){
                   return hs;
               }
        };
    }
        Session session = Session.getInstance(ustawienia, autt);
        session.setDebug(debug);
        MimeMessage wiadomosc = new MimeMessage(session);
        try{
            wiadomosc.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(gdzie)};
            wiadomosc.setRecipients(Message.RecipientType.TO, address);
            wiadomosc.setSubject(temat);
            wiadomosc.setSentDate(new Date());
            wiadomosc.setText(body);
            Transport.send(wiadomosc);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Udało się!", "Twoja wiadomość została wysłana"));
        }catch (MessagingException e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Przykro nam!", "Nie udało się wysłać wiadomości"));
        }
    }
            
}
