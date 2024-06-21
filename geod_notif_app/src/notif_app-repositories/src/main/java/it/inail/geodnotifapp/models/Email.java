package it.inail.geodnotifapp.models;

import java.util.List;

public class Email {
    private List<String> destinatari;
    //private String destinatari;
    private String oggetto;
    private String body;

    public Email(List<String> destinatari, String oggetto, String body) {
        this.destinatari = destinatari;
        this.oggetto = oggetto;
        this.body = body;
    }

//    public Email(String destinatari, String oggetto, String body) {
//        this.destinatari = destinatari;
//        this.oggetto = oggetto;
//        this.body = body;
//    }

    public Email() {}

    public List<String> getDestinatari() {
        return destinatari;
    }
//    public String getDestinatari() {
//        return destinatari;
//    }

    public void setDestinatari(List<String> destinatari) {
        this.destinatari = destinatari;
    }


//    public void setDestinatari(String destinatari) {
//        this.destinatari = destinatari;
//    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Email{" +
                "destinatari=" + destinatari +
                ", oggetto='" + oggetto + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
