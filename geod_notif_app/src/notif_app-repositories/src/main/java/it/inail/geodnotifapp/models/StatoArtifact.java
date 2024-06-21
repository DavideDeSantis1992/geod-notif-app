package it.inail.geodnotifapp.models;

public class StatoArtifact {
    private String descrizione;

    public StatoArtifact() {
    }

    public StatoArtifact(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "StatoArtifact{" +
                "descrizione='" + descrizione + '\'' +
                '}';
    }
}
