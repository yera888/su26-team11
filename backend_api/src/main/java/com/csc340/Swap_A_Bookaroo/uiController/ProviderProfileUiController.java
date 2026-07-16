package com.csc340.Swap_A_Bookaroo.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@Controller
@RequestMapping("/providers")
public class ProviderProfileUiController {

    private final ProviderProfileService providerProfileService;

    public ProviderProfileUiController(ProviderProfileService providerProfileService) {
        this.providerProfileService = providerProfileService;
    }

    @GetMapping                              // list all providers
    public String listProviders(Model model) {
        model.addAttribute("providers", providerProfileService.getAllProviderProfiles());
        return "provider-list";
    }

    @GetMapping("/new")                       // show signup form
    public String signupForm(Model model) {
        model.addAttribute("providerProfile", new ProviderProfile());
        return "provider-signup";
    }

    @PostMapping("/save")                      // handle signup (no @RequestBody, like HxH)
    public String createProvider(ProviderProfile providerProfile) {
        providerProfileService.createProviderProfile(providerProfile);
        return "redirect:/providers";
    }

    @GetMapping("/{id}")                        // view one provider
    public String viewProvider(@PathVariable Long id, Model model) {
        model.addAttribute("provider", providerProfileService.getProviderProfileById(id));
        return "provider-profile";
    }

    @GetMapping("/{id}/delete")                 // delete (GET, like HxH)
    public String deleteProvider(@PathVariable Long id) {
        providerProfileService.deleteProviderProfile(id);
        return "redirect:/providers";
    }
}