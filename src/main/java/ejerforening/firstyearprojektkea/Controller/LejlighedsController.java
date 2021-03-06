
package ejerforening.firstyearprojektkea.Controller;

import ejerforening.firstyearprojektkea.Model.Lejlighed.Lejlighed;
import ejerforening.firstyearprojektkea.Service.Lejlighed.IlejlighedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

/**Oprettet af Frederik Agger og Signe Nørløv Eskildsen
 * d. 28/11/2019
 **/
@Controller
public class LejlighedsController {

    @Autowired
    private IlejlighedService ilejlighedService;


    @GetMapping("/lejlighed")
    public String seLejlighedForside(){
        return "lejlighed/lejligheder";
    }

    /**
     * Lavet af FA 29-11-2019.
     * Denne metode linker til html siden seAlleLejligheder
     * som bliver opdateret med en tabel over
     * alle lejligheder i databasen
     * @param model
     * @return
     */

    @GetMapping("/lejlighed/oversigt")
    public String seLejligheder(Model model){
        model.addAttribute("lejligheder",ilejlighedService.hentAlle());
        return "lejlighed/oversigt";
    }

    @GetMapping("/lejlighed/form_opret_lejlighed")
    public String form_opret_lejligheder(){
        return "lejlighed/form_opret_lejlighed";
    }

    @GetMapping("/lejlighed/form_opdaterLejlighed")
    public String form_opdaterLejligheder(){
        return "lejlighed/form_opdaterLejlighed";
    }

    @GetMapping("/lejlighed/form_sletLejlighed")
    public String form_sletLejlighed(){
        return "lejlighed/form_sletLejlighed";
    }

    @GetMapping("/lejlighed/form_soegLejlighed")
    public String form_soegLejlighed(){
        return "lejlighed/form_soegLejlighed";
    }

    @PostMapping("/lejlighed/opret_lejlighed")
    public String opret_lejlighed(@ModelAttribute("lejlighed")@Valid Lejlighed lejlighed, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "Fejl indtastning";
        }
        ilejlighedService.opret(lejlighed);
        return "lejlighed/lejligheder";
    }

    @PostMapping("/lejlighed/opdater")
    public String opdaterLejlighed(@Valid Lejlighed lejlighed, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "Fejl indtastning";
        }
        ilejlighedService.opdaterLejlighed(lejlighed);
        return "lejlighed/lejligheder";
    }

    @GetMapping("/lejlighed/findLejlighed")
    public String findLejlighed(@ModelAttribute("lejlighed")@Valid Lejlighed lejlighed, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "Fejl indtastning";
        }
        model.addAttribute("lejlighed",ilejlighedService.findMedId(lejlighed.getLejlighedsid()));
        return "lejlighed/visSoegningsSide";
    }

    @PostMapping("/lejlighed/sletLejlighed")
    public String sletLejlighed(@Valid Lejlighed lejlighed, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Fejl indtastning";
        }
        ilejlighedService.sletLejlighed(lejlighed.getLejlighedsid());
        return "lejlighed/lejligheder";
    }
}