package com.example.location.model;

public class Offre {
    String titre;
    String description;
    float superficie;
    int pieces;
    int sdb;
    float loyer;

    public Offre(String titre, String description,float superficie, int pieces,    int sdb, float loyer){
        this.titre=titre;
        this.description=description;
        this.superficie=superficie;
        this.pieces=pieces;
        this.sdb=sdb;
        this.loyer=loyer;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getSdb() {
        return sdb;
    }

    public void setSdb(int sdb) {
        this.sdb = sdb;
    }

    public float getLoyer() {
        return loyer;
    }

    public void setLoyer(float loyer) {
        this.loyer = loyer;
    }
}
