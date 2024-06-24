package it.inail.geodnotifapp.dto;

public class StatoNotifica {
    private String descrizione;

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public StatoNotifica(String descrizione) {
        this.descrizione = descrizione;
    }

    public StatoNotifica() {
    }

    @Override
    public String toString() {
        return "StatoNotifica{" +
                "descrizione='" + descrizione + '\'' +
                '}';
    }
}
