package ejerforening.firstyearprojektkea.Model.Opgave;

import javax.persistence.Id;

/**
 * Klassen er den model der vises ud til htmlsiden, saa man kan se det som en model view klasse.
 * Det er de felter bestyrelsen har brug for at se, for at kunne vaelge hvem de vil rette, se detaljer eller slette.
 * Tabellerne fra databasen der vises er opgave og lejlighed.
 * @Author Signe
 * @since 4-12-2019
 */
public class OpgaveOversigt
{
    /**
     * Attributerne har validerings annotationerm som definerer:
     *
     * @Id = primary key i tabellen
     */
    @Id
    private int opgaveId;
    private String navn;
    private int lejlighedsId;

    public OpgaveOversigt()
    {
    }

    public OpgaveOversigt(int opgaveId, String navn, int lejlighedsId)
    {
        this.opgaveId = opgaveId;
        this.navn = navn;
        this.lejlighedsId = lejlighedsId;
    }

    public void setOpgaveId(int opgaveId)
    {
        this.opgaveId = opgaveId;
    }

    public void setNavn(String navn)
    {
        this.navn = navn;
    }

    public void setLejlighedsId(int lejlighedsId)
    {
        this.lejlighedsId = lejlighedsId;
    }

    public long getOpgaveId()
    {
        return opgaveId;
    }

    public String getNavn()
    {
        return navn;
    }

    public int getLejlighedsId()
    {
        return lejlighedsId;
    }
}
