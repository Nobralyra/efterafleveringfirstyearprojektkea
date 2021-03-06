package ejerforening.firstyearprojektkea.Model.Arrangement;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author paivi
 * @since 7-12-2019
 * Klassen er en uadskillelig del af Arrangement (composition, jf. forklaring i Arrangement).
 * Den indeholder oplysninger om arrangement (alle oplysninger ud over stamdata navn og oprettelsesdato i Arrangement).
 * Den er annoteret som Component for at markere, at den er en komponent i en anden klasse.
 */
@Component
public class ArrangementOplysninger{

    /**
     * Datatypen LocalDate og LocalTime er javaklasser, som repraesenterer dato og tid.
     * arrangementId (FK) knytter i databasen ArrangementOplysninger til Arrangement.
     * ArrangementId hentes i systemet (er ikke bruger-input), så valideres ikke med annotation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int arranOplysId;
    private int arrangementId;
    @NotBlank(message = "Indtast venligst agenda")
    private String agenda;
    @NotNull(message = "Indtast venligst dato: dd/mm/åååå")
    private LocalDate dato;
    @NotNull(message = "Indtast venligst tidspunkt: hh.mm")
    private LocalTime startTidspunkt;
    @NotNull(message = "Indtast venligst tidspunkt: hh.mm")
    private LocalTime slutTidspunkt;
    @NotBlank(message = "Indtast venligst sted")
    private String sted;
    private LocalDate tilmeldingsfrist;
    private LocalDate sidstOpdateret;

    public ArrangementOplysninger(){}

    public int getArranOplysId() {
        return arranOplysId;
    }
    public void setArranOplysId(int arranOplysId) {
        this.arranOplysId = arranOplysId;
    }
    public int getArrangementId() {
        return arrangementId;
    }
    public void setArrangementId(int arrangementId) {
        this.arrangementId = arrangementId;
    }
    public String getAgenda() {
        return agenda;
    }
    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }
    public LocalDate getDato() {
        return dato;
    }
    public void setDato(LocalDate dato) {
        this.dato = dato;
    }
    public LocalTime getStartTidspunkt() {
        return startTidspunkt;
    }
    public void setStartTidspunkt(LocalTime startTidspunkt) {
        this.startTidspunkt = startTidspunkt;
    }
    public LocalTime getSlutTidspunkt() {
        return slutTidspunkt;
    }
    public void setSlutTidspunkt(LocalTime slutTidspunkt) {
        this.slutTidspunkt = slutTidspunkt;
    }
    public String getSted() {
        return sted;
    }
    public void setSted(String sted) {
        this.sted = sted;
    }
    public LocalDate getTilmeldingsfrist() {
        return tilmeldingsfrist;
    }
    public void setTilmeldingsfrist(LocalDate tilmeldingsfrist) {
        this.tilmeldingsfrist = tilmeldingsfrist;
    }
    public LocalDate getSidstOpdateret() {
        return sidstOpdateret;
    }
    public void setSidstOpdateret(LocalDate sidstOpdateret) {
        this.sidstOpdateret = sidstOpdateret;
    }
}
