package ejerforening.firstyearprojektkea.Repository.Opgave;

import ejerforening.firstyearprojektkea.Model.Opgave.Opgave;
import ejerforening.firstyearprojektkea.Model.Opgave.OpgaveOplysninger;
import ejerforening.firstyearprojektkea.Model.Opgave.OpgaveOversigt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Repository implementerer metoderne fra interfacet IOpgaveRepository autowirer jdbctemplate, som bruges til at udfoere sql-statements med.
 * List er et interface, som indeholder sorteret raekkefoelge af objekter.
 *
 * @author Signe
 * @since 4-12-2019
 */
@Repository
public class OpgaveRepository implements IOpgaveRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private int generetOpgaveId;

    /**
     * Metoden henter alle vaerierne fra de valgte kolonner fra opgaveregister_view.
     * jdbcTemplate bruger en query her, for at fecthe det data, som hver opgave har og RowMapper sørger for at retunere det som List, som bruges til at
     * vise på htmlen oversigt over Opgaver.
     *
     * BeanPropertyRowMapper er interfacet af RowMapper, som kun kan hente en raekke af gangen fra databasen.
     * Raekken bliver som et object af den type, den har faaet som parameter.
     *
     * Med hjaelp af view kan de tre tabeller, som opgave, opgaveoplysninger og lejlighed er i databasen, retuneres som et samlet objekt af parameter,
     * hvor her oenskes det kun at få vaerdierne fra opgaveId, navn og lejlighedsid med tilbage og bruger List<OpgaveOversigt> som reference for instansen
     * af OpgaveOversigt.
     *
     * @return result - indeholder listen med OpgaveOversigt
     */
    @Override
    public List<OpgaveOversigt> hentAlle()
    {
        RowMapper<OpgaveOversigt> rowmapper = new BeanPropertyRowMapper<>(OpgaveOversigt.class);
        String sql = "SELECT opgaveId, navn, lejlighedsId FROM opgaveregister_view";

        List<OpgaveOversigt> resultHentAlle =  jdbcTemplate.query(sql, rowmapper);
        return resultHentAlle;
    }


    /**
     * Metoden henter vaerdierne fra alle kolonner fra opgaveregister_view, hvor opgaveId matcher. Der burde kun være oprettet en opgave med det opgaveId,
     * som er blevet sendt med, men med LIMIT 1 ved databasen, at hvis den finder et match, skal den ikke kigge videre efter flere.
     *
     * Med hjaelp af view kan de tre tabeller, som opgave, opgaveoplysninger og lejlighed er i databasen, retuneres som et samlet objekt af parameter,
     * som saetter attributterne i OpgaveOplysninger, for at vise detaljerne af opgaven på htmlsiden.
     * for at vise på opgaveoversigten.
     *
     * @param opgaveId kommer fra den valgte opgave der oenskes at se detaljer for.
     * @return den fundne opgave, hvor ved at indikere, at man kun oensker det foerste element i listen kan der bruges .get(0), da List starter på 0
     * og ikke 1.
     */
    @Override
    public OpgaveOplysninger findValgteOpgaveOplysninger(int opgaveId)
    {
        RowMapper<OpgaveOplysninger> rowmapper = new BeanPropertyRowMapper<>(OpgaveOplysninger.class);
        String sql = "SELECT * FROM opgaveregister_view WHERE opgaveId=? LIMIT 1";
        List<OpgaveOplysninger> resultfindValgteOpgaveOplysninger = jdbcTemplate.query(sql, rowmapper, opgaveId);
        return resultfindValgteOpgaveOplysninger.get(0);
    }


    /**
     * Metoden har OpgaveOplysning som parameter, saa naa en valgt opgave skal opdateres, kan thymeleaf på htmlsiden flette inputsne ind i objektet,
     * og metoden opdaterOpgave kan med getmetoderne hente vaerdierne ind i lokale variabler, saa SP_opdaterOpgave (Stored Procedure) har det data
     * den skal bruge for at opdatere de 3 tabeller det drejer sig om.
     * Der er valgt en Stored Procedure, for ikke at skulle lave 3 INSERT INTO i koden, men lade databasen om at goere arbejdet.
     * jdbcTemplate update bliver brugt til INSERT, UPDATE og DELETE statements.
     *
     * @param opgaveOplysninger objektet som har vaerdier, som hentes med getmetoderne
     * @return true
     */
    @Override
    public boolean opdaterOpgave(OpgaveOplysninger opgaveOplysninger)
    {
        int opgaveId = opgaveOplysninger.getOpgaveId();
        int opgaveOplysningerId = opgaveOplysninger.getOpgaveOplysningerId();
        String navn = opgaveOplysninger.getNavn();
        int lejlighedsId = opgaveOplysninger.getLejlighedsId();
        String beskrivelse = opgaveOplysninger.getBeskrivelse();
        int varighed = opgaveOplysninger.getVarighed();
        int svaerhedsgrad = opgaveOplysninger.getSvaerhedsgrad();
        LocalDate startDato = opgaveOplysninger.getStartDato();
        LocalDate slutDato = opgaveOplysninger.getSlutDato();
        LocalDate sidstOpdateret = LocalDate.now();
        String sql = "CALL SP_opdaterOpgave(?,?,?,?,?,?,?,?,?,?)";
        int erOpgaveOpdateret = jdbcTemplate.update(sql, opgaveId, opgaveOplysningerId, navn, lejlighedsId, beskrivelse, varighed, svaerhedsgrad, startDato, slutDato, sidstOpdateret);
        return true;
    }


    /**
     * Metoden sletter opgaver, som faar opgaveId givet med som parameter, for at databasen kan finde en matchene opgaveId i tabellen opgave og delete den..
     * Der ønskes ingen returvaerdi, da controlleren viser oversigten igen, hvor opgaven ikke laengere er paa hjemmesiden
     * @param opgaveId kommer fra den valgte opgave, der oenskes slettet.
     */
    @Override
    public void slet(int opgaveId)
    {
        String sql = "DELETE FROM opgave WHERE opgave.opgaveId = ?;";
        jdbcTemplate.update(sql, opgaveId);
    }


    /**
     * Metoden opretter opgaven, ved at hente navn fra opgave og kalder LocalDate.now() for dags dato.
     * Saettes ind i INSERT og jdbcTemplate kontakter databasen og eksekvere sql'en.
     * Tabellen opgave i databasen har paa navn UNIQUE, som soerger for at der ikke kan oprettes en opgave
     * i databasen med samme navn.
     * @param opgave objektet som har vaerdier, som hentes med getmetoderne
     * @return
     */
    @Override
    public int opretOpgave(Opgave opgave)
    {
        String navn = opgave.getNavn();
        LocalDate oprettelsesDato = LocalDate.now();
        String sql = "INSERT INTO opgave(navn, oprettelsesDato) VALUES(?,?)";
        int opgaveOpdateret  = jdbcTemplate.update(sql, navn, oprettelsesDato);

        return opgaveOpdateret;
    }


    /**
     * Metoden opretter opgaveOplysninger til den opgave der lige er blevet oprettet, 
     * med getmetoder fra OpgaveOplysninger kalder LocalDate.now() for dags dato.
     * Saettes ind i INSERT og jdbcTemplate kontakter databasen og eksekvere sql'en.
     * @param opgaveOplysninger objektet som har vaerdier, som hentes med getmetoderne
     * @return
     */
    @Override
    public boolean opretOplysninger(OpgaveOplysninger opgaveOplysninger)
    {
        int opgaveId = opgaveOplysninger.getOpgaveId();
        String beskrivelse = opgaveOplysninger.getBeskrivelse();
        int varighed = opgaveOplysninger.getVarighed();
        int svaerhedsgrad = opgaveOplysninger.getSvaerhedsgrad();
        LocalDate startDato = opgaveOplysninger.getStartDato();
        LocalDate slutDato = opgaveOplysninger.getSlutDato();
        LocalDate sidstOpdateret = LocalDate.now();
        String sql = "INSERT INTO opgaveOplysninger(opgaveId, beskrivelse, varighed, svaerhedsgrad, startDato, slutDato, sidstOpdateret) VALUES(?,?,?,?,?,?,?)";
        int opgaveOplysningerOprettet = jdbcTemplate.update(sql, opgaveId, beskrivelse, varighed, svaerhedsgrad, startDato, slutDato, sidstOpdateret);

        return true;
    }
}
