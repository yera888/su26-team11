package com.csc340.Swap_A_Bookaroo.uicontroller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.csc340.Swap_A_Bookaroo.entities.ProviderProfile;
import com.csc340.Swap_A_Bookaroo.service.ProviderProfileService;

@Controller
@RequestMapping("/providers")
public class ProviderProfileUiController {

    private final ProviderProfileService providerProfileService;

    public ProviderProfileUiController(
            ProviderProfileService providerProfileService) {
        this.providerProfileService = providerProfileService;
    }

    @GetMapping("/new")
    public String signupForm(Model model) {
        model.addAttribute("providerProfile", new ProviderProfile());
        return "provider-signup";
    }

    @PostMapping("/save")
    public String createProvider(ProviderProfile providerProfile) {
        ProviderProfile created =
                providerProfileService.createProviderProfile(providerProfile);

        return created == null
                ? "redirect:/providers/new?error=true"
                : "redirect:/login?registered=true";
    }

    @GetMapping("/me")
    public String viewCurrentProvider(
            Authentication authentication,
            Model model) {
        ProviderProfile provider = providerProfileService
                .getProviderProfileByUsername(authentication.getName());

        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("provider", provider);
        return "provider-profile";
    }

    // Retains compatibility with the existing profile links while ensuring
    // that a provider can only open their own profile ID.
    @GetMapping("/{id}")
    public String viewProvider(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {
        ProviderProfile provider = providerProfileService
                .getProviderProfileByUsername(authentication.getName());

        if (provider == null || !provider.getProviderProfileId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("provider", provider);
        return "provider-profile";
    }
}