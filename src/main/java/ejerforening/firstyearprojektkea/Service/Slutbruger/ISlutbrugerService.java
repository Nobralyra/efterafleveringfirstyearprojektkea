package ejerforening.firstyearprojektkea.Service.Slutbruger;

import ejerforening.firstyearprojektkea.Model.Slutbruger.SlutbrugerOversigt;

import java.util.List;

public interface ISlutbrugerService
{
    List<SlutbrugerOversigt> hentAlle();

    void slet(int slutbrugerId);
}
