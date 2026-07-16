package com.csc340.Swap_A_Bookaroo.uicontroller;

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

    // US-5: show the signup form
    @GetMapping("/new")
    public String signupForm(Model model) {
        model.addAttribute("providerProfile", new ProviderProfile());
        return "provider-signup";
    }

    // US-5: handle signup, then go straight to the new provider's own profile
    @PostMapping("/save")
    public String createProvider(ProviderProfile providerProfile) {
        ProviderProfile created = providerProfileService.createProviderProfile(providerProfile);
        if (created == null) {
            return "redirect:/providers/new?error=true";   // username already taken
        }
        return "redirect:/providers/" + created.getProviderProfileId();
    }

    // US-5: view one provider's own profile
    @GetMapping("/{id}")
    public String viewProvider(@PathVariable Long id, Model model) {
        model.addAttribute("provider", providerProfileService.getProviderProfileById(id));
        return "provider-profile";
    }

    // US-5: delete a provider
    @GetMapping("/{id}/delete")
    public String deleteProvider(@PathVariable Long id) {
        providerProfileService.deleteProviderProfile(id);
        return "redirect:/providers/new";
    }
}