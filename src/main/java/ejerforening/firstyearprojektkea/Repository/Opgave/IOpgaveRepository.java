package ejerforening.firstyearprojektkea.Repository.Opgave;

import ejerforening.firstyearprojektkea.Model.Opgave.Opgave;
import ejerforening.firstyearprojektkea.Model.Opgave.OpgaveOplysninger;
import ejerforening.firstyearprojektkea.Model.Opgave.OpgaveOversigt;

import java.util.List;

/**
 * Interface for OpgaveRepository, som Spring goer muligt at autowire, saa man ikke behoeves at autowire direkte til klassen,
 * saa interfacet ville havde vaeret muligt at genbruge et andet sted.
 * Indeholder hvilke metoder der skal implementeres, hvor interfacet bliver implementeret
 * @author Signe
 * @since 5-12-2019
 */
public interface IOpgaveRepository
{
    List<OpgaveOversigt> hentAlle();

    OpgaveOplysninger findValgteOpgaveOplysninger(int opgaveId);

    boolean opdaterOpgave(OpgaveOplysninger opgaveOplysninger);

    void slet(int opgaveId);

    int opretOpgave(Opgave opgave);

    boolean opretOplysninger(OpgaveOplysninger opgaveOplysninger);
}
