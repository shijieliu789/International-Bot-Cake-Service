package service.cleanCakes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class CCService extends AbstractCakeService {
    public static final String PREFIX = "CC";
    public static final String COMPANY = "Clean Cakes Confectionary";

    private Map<String, CakeInvoice> invoices = new HashMap<>();

    @RequestMapping(value = "/cake", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<CakeInvoice> createInvoice(@RequestBody CakeSpec cakeSpec) throws URISyntaxException {

        // generate cake invoice from cake specifications
        CakeInvoice invoice = generateCake(cakeSpec);

        //add to list of invoices
        invoices.put(invoice.getReference(), invoice);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/cake/" + invoice.getReference();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(invoice, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/cake/{reference}", method = RequestMethod.GET)
    public CakeInvoice getResource(@PathVariable("reference") String reference) {
        CakeInvoice createdCake = invoices.get(reference);
        if (createdCake == null) throw new NoSuchCakeException();
        return createdCake;
    }

    public CakeInvoice generateCake(CakeSpec cakeSpec) {
        return new CakeInvoice(COMPANY, generateReference(PREFIX), generatePrice(cakeSpec));
    }

    public double generatePrice(CakeSpec cakeSpec) {
        double price = 0;
        double multiplier = 0.2;

        if (cakeSpec.getCakeType().equals("TRADITIONAL STACK") || cakeSpec.getCakeType().equals("BUTTER CAKE")
                || cakeSpec.getCakeType().equals("FRUIT FILLED") || cakeSpec.getCakeType().equals("SHEET CAKE")
                || cakeSpec.getCakeType().equals("BISCUIT CAKE") || cakeSpec.getCakeType().equals("MERINGUE CAKE")
                || cakeSpec.getCakeType().equals("CHEESE CAKE") || cakeSpec.getCakeType().equals("CUPCAKES")) {
            System.out.println(cakeSpec.getCakeType());
            switch (cakeSpec.getCakeType()) {
                case "TRADITIONAL STACK":
                    price += CakeTypePrice.TRADITIONAL_STACK;
                    break;
                case "BUTTER CAKE":
                    price += CakeTypePrice.BUTTER_CAKE;
                    break;
                case "FRUIT FILLED":
                    price += CakeTypePrice.FRUIT_FILLED;
                    break;
                case "SHEET CAKE":
                    price += CakeTypePrice.SHEET_CAKE;
                    break;
                case "BISCUIT CAKE":
                    price += CakeTypePrice.BISCUIT_CAKE;
                    break;
                case "MERINGUE CAKE":
                    price += CakeTypePrice.MERINGUE_CAKE;
                    break;
                case "CHEESE CAKE":
                    price += CakeTypePrice.CHEESE_CAKE;
                    break;
                case "CUPCAKES":
                    price += CakeTypePrice.CUPCAKES;
                    break;
            }
        }

        if (cakeSpec.getTopping().equals("CHOCOLATE CHIPS") || cakeSpec.getTopping().equals("OREO")
                || cakeSpec.getTopping().equals("MANGO") || cakeSpec.getTopping().equals("STRAWBERRY")
                || cakeSpec.getTopping().equals("GRAHAM") || cakeSpec.getTopping().equals("COOKIE ASSORTMENT")
                || cakeSpec.getTopping().equals("FRUIT ASSORTMENT") || cakeSpec.getTopping().equals("NUTS")) {
            System.out.println(cakeSpec.getTopping());
            switch (cakeSpec.getTopping()) {
                case "CHOCOLATE CHIPS":
                    price += ToppingsPrice.CHOCOLATE_CHIPS;
                    break;
                case "OREO":
                    price += ToppingsPrice.OREO;
                    break;
                case "MANGO":
                    price += ToppingsPrice.MANGO;
                    break;
                case "STRAWBERRY":
                    price += ToppingsPrice.STRAWBERRY;
                    break;
                case "GRAHAM":
                    price += ToppingsPrice.GRAHAM;
                    break;
                case "COOKIE ASSORTMENT":
                    price += ToppingsPrice.COOKIE_ASSORTMENT;
                    break;
                case "FRUIT ASSORTMENT":
                    price += ToppingsPrice.FRUIT_ASSORTMENT;
                    break;
                case "NUTS":
                    price += ToppingsPrice.NUTS;
                    break;
            }
        }

        if (cakeSpec.getFlavor().equals("VANILLA") || cakeSpec.getFlavor().equals("CHOCOLATE")
                || cakeSpec.getFlavor().equals("CARROT") || cakeSpec.getFlavor().equals("RED VELVET")
                || cakeSpec.getFlavor().equals("COFFEE") || cakeSpec.getFlavor().equals("LEMON")) {
            System.out.println(cakeSpec.getFlavor());
            switch (cakeSpec.getFlavor()) {
                case "VANILLA":
                    price += FlavorPrice.VANILLA;
                    break;
                case "CHOCOLATE":
                    price += FlavorPrice.CHOCOLATE;
                    break;
                case "CARROT":
                    price += FlavorPrice.CARROT;
                    break;
                case "RED VELVET":
                    price += FlavorPrice.RED_VELVET;
                    break;
                case "COFFEE":
                    price += FlavorPrice.COFFEE;
                    break;
                case "LEMON":
                    price += FlavorPrice.LEMON;
                    break;
            }
        }

        if (cakeSpec.getIcing().equals("FONDANT") || cakeSpec.getIcing().equals("BUTTERCREAM")
                || cakeSpec.getIcing().equals("WHIPPED CREAM") || cakeSpec.getIcing().equals("ROYAL ICING")
                || cakeSpec.getIcing().equals("CREAM CHEESE FROST") || cakeSpec.getIcing().equals("MERINGUE")) {
            System.out.println(cakeSpec.getIcing());
            switch (cakeSpec.getIcing()) {
                case "FONDANT":
                    price += IcingPrice.FONDANT;
                    break;
                case "BUTTERCREAM":
                    price += IcingPrice.BUTTERCREAM;
                    break;
                case "WHIPPED CREAM":
                    price += IcingPrice.WHIPPED_CREAM;
                    break;
                case "ROYAL ICING":
                    price += IcingPrice.ROYAL_ICING;
                    break;
                case "CREAM CHEESE FROST":
                    price += IcingPrice.CREAM_CHEESE_FROST;
                    break;
                case "MERINGUE":
                    price += IcingPrice.MERINGUE;
                    break;
            }
        }

        if (cakeSpec.getServing().equals("6 inches") || cakeSpec.getServing().equals("8 inches") || cakeSpec.getServing().equals("12 inches")) {
            System.out.println(cakeSpec.getServing());
            switch (cakeSpec.getServing()) {
                case "6 inches":
                    price += 6 * multiplier;
                    break;
                case "8 inches":
                    price += 8 * multiplier;
                    break;
                case "12 inches":
                    price += 12 * multiplier;
                    break;
            }
        }

        if (cakeSpec.getDecor().equals("BASIC") || cakeSpec.getDecor().equals("MEDIOCRE") || cakeSpec.getDecor().equals("HIGH_CLASS")) {
            System.out.println(cakeSpec.getDecor());
            switch (cakeSpec.getDecor()) {
                case "BASIC":
                    price += DecorationIntricacy.BASIC;
                    break;
                case "MEDIOCRE":
                    price += DecorationIntricacy.MEDIOCRE;
                    break;
                case "HIGH CLASS":
                    price += DecorationIntricacy.HIGH_CLASS;
                    break;
            }
        }

        if (cakeSpec.getOccasion().equals("CASUAL") || cakeSpec.getOccasion().equals("WEDDING") || cakeSpec.getOccasion().equals("BIRTHDAY")) {
            System.out.println(cakeSpec.getOccasion());
            switch (cakeSpec.getOccasion()) {
                case "CASUAL":
                    price += 3;
                    break;
                case "WEDDING":
                    price += 15;
                    break;
                case "BIRTHDAY":
                    price += 7;
                    break;
            }
        }

        if (cakeSpec.getCounty().equals("DUBLIN") || cakeSpec.getCounty().equals("MEATH")
                || cakeSpec.getCounty().equals("LOUTH") || cakeSpec.getCounty().equals("WICKLOW")
                || cakeSpec.getCounty().equals("KILDARE") || cakeSpec.getCounty().equals("CARLOW")) {
            System.out.println(cakeSpec.getCounty());
            switch (cakeSpec.getCounty()) {
                case "DUBLIN":
                    price += 2;
                    break;
                case "MEATH":
                case "WICKLOW":
                case "LOUTH":
                case "KILDARE":
                    price += 5;
                    break;
                case "CARLOW":
                    price += 9;
                    break;
            }
        }

        System.out.println(price);
        return price;
    }

}
