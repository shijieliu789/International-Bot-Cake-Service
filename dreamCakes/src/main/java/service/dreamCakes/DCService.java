package service.dreamCakes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

@CrossOrigin
@RestController
public class DCService extends AbstractCakeService {
    // All references are to be prefixed with an DC (e.g. DC001000)
    public static final String PREFIX = "DC";
    public static final String COMPANY = "Dream Cakes Bakery";

    private Map<String, CakeInvoice> invoices = new HashMap<>();

    @RequestMapping(value = "/dcake", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<CakeInvoice> createInvoice(@RequestBody CakeSpec cakeSpec) throws URISyntaxException {

        // generate cake invoice from cake specifications
        CakeInvoice invoice = generateCake(cakeSpec);

        //add to list of invoices
        invoices.put(invoice.getReference(), invoice);

        String bcpath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/dcake/" + invoice.getReference();
        HttpHeaders bcHeaders = new HttpHeaders();
        bcHeaders.setLocation(new URI(bcpath));
        return new ResponseEntity<>(invoice, bcHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/dcake/{reference}", method = RequestMethod.GET)
    public CakeInvoice getResource(@PathVariable("reference") String reference) {
        CakeInvoice createdCake = invoices.get(reference);
        if (createdCake == null) throw new NoSuchCakeException();
        return createdCake;
    }


    public CakeInvoice generateCake(CakeSpec cakeSpec) {
        return new CakeInvoice(COMPANY, generateReference(PREFIX), generatePrice(cakeSpec));
    }

    private double generatePrice(CakeSpec cakeSpec) {
        double price = 10;
        double specialityDiscount = 0.85;
        double seasonalMultiplier = getSeasonalMultiplier();
        double onOffer = 0.7;

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
                    price += CakeTypePrice.FRUIT_FILLED * seasonalMultiplier;
                    break;
                case "SHEET CAKE":
                    price += CakeTypePrice.SHEET_CAKE;
                    break;
                case "BISCUIT CAKE":
                    price += CakeTypePrice.BISCUIT_CAKE * onOffer;
                    break;
                case "MERINGUE CAKE":
                    price += CakeTypePrice.MERINGUE_CAKE;
                    break;
                case "CHEESE CAKE":
                    price += CakeTypePrice.CHEESE_CAKE * specialityDiscount;
                    break;
                case "CUPCAKES":
                    price += CakeTypePrice.CUPCAKES;
                    break;
            }
        }

        price += getToppingPrice(cakeSpec.getTopping(), specialityDiscount, seasonalMultiplier, onOffer);
        price += getFlavorPrice(cakeSpec.getFlavor(), specialityDiscount, seasonalMultiplier, onOffer);
        price += getIcingPrice(cakeSpec.getIcing(), specialityDiscount, seasonalMultiplier, onOffer);
        price += getServingSizePrice(cakeSpec.getServing(), specialityDiscount);
        price += getDecorCharges(cakeSpec.getDecor(), specialityDiscount);
        price += getOccasionCharges(cakeSpec.getOccasion());
        price += getOccasionCharges(cakeSpec.getOccasion());
        price += getDeliveryCharges(cakeSpec.getCounty(), specialityDiscount);

        System.out.println("Total Cake cost: " + price);
        return price;
    }


    //depending on month of the year prices of certain items may increase or decrease
    private double getSeasonalMultiplier() {
        double multiplier = 1;
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        if (currentMonth.equals(Month.MAY) || currentMonth.equals(Month.JUNE) || currentMonth.equals(Month.JULY)
            || currentMonth.equals(Month.AUGUST) || currentMonth.equals(Month.SEPTEMBER)) {
            multiplier = 0.7;
        } else if (currentMonth.equals(Month.DECEMBER) || currentMonth.equals(Month.JANUARY) ||currentMonth.equals(Month.FEBRUARY)) {
            multiplier = 1.3;
        }
        return multiplier;
    }


    private double getFlavorPrice(String flavor, double specialityDiscount, double seasonalMultiplier, double onOffer) {
        double flavorCost = 0;

        if (flavor.equals("VANILLA") || flavor.equals("CHOCOLATE") || flavor.equals("CARROT") ||
                flavor.equals("RED VELVET") || flavor.equals("COFFEE") || flavor.equals("LEMON")) {
            switch (flavor) {
                case "VANILLA":
                    flavorCost += FlavorPrice.VANILLA * onOffer;
                    break;
                case "CHOCOLATE":
                    flavorCost += FlavorPrice.CHOCOLATE * onOffer;
                    break;
                case "CARROT":
                    flavorCost += FlavorPrice.CARROT;
                    break;
                case "RED VELVET":
                    flavorCost += FlavorPrice.RED_VELVET * seasonalMultiplier;
                    break;
                case "COFFEE":
                    flavorCost += FlavorPrice.COFFEE * specialityDiscount;
                    break;
                case "LEMON":
                    flavorCost += FlavorPrice.LEMON;
                    break;
            }
        }
        System.out.println(flavor + ": " + flavorCost);
        return flavorCost;
    }

    private double getToppingPrice(String topping, double specialityDiscount, double seasonalMultiplier, double onOffer) {
        double toppingCost = 0;

        if (topping.equals("CHOCOLATE CHIPS") || topping.equals("OREO") || topping.equals("MANGO") || topping.equals("STRAWBERRY")
                || topping.equals("GRAHAM") || topping.equals("COOKIE ASSORTMENT")
                || topping.equals("FRUIT ASSORTMENT") || topping.equals("NUTS")) {
            switch (topping) {
                case "CHOCOLATE CHIPS":
                    toppingCost += ToppingsPrice.CHOCOLATE_CHIPS;
                    break;
                case "OREO":
                    toppingCost += ToppingsPrice.OREO * specialityDiscount;
                    break;
                case "MANGO":
                    toppingCost += ToppingsPrice.MANGO * seasonalMultiplier;
                    break;
                case "STRAWBERRY":
                    toppingCost += ToppingsPrice.STRAWBERRY * seasonalMultiplier;
                    break;
                case "GRAHAM":
                    toppingCost += ToppingsPrice.GRAHAM;
                    break;
                case "COOKIE ASSORTMENT":
                    toppingCost += ToppingsPrice.COOKIE_ASSORTMENT * specialityDiscount;
                    break;
                case "FRUIT ASSORTMENT":
                    toppingCost += ToppingsPrice.FRUIT_ASSORTMENT * seasonalMultiplier;
                    break;
                case "NUTS":
                    toppingCost += ToppingsPrice.NUTS * specialityDiscount;
                    break;
            }
        }
        System.out.println(topping + ": " + toppingCost);
        return toppingCost;
    }

    private double getIcingPrice(String icing, double specialityDiscount, double seasonalMultiplier, double onOffer) {
        double icingCost = 0;

        if (icing.equals("FONDANT") || icing.equals("BUTTERCREAM") || icing.equals("WHIPPED CREAM") ||
                icing.equals("ROYAL ICING") || icing.equals("CREAM CHEESE FROST") || icing.equals("MERINGUE")) {
            switch (icing) {
                case "FONDANT":
                    icingCost += IcingPrice.FONDANT * onOffer;
                    break;
                case "BUTTERCREAM":
                    icingCost += IcingPrice.BUTTERCREAM * specialityDiscount;
                    break;
                case "WHIPPED CREAM":
                    icingCost += IcingPrice.WHIPPED_CREAM;
                    break;
                case "ROYAL ICING":
                    icingCost += IcingPrice.ROYAL_ICING *seasonalMultiplier;
                    break;
                case "CREAM CHEESE FROST":
                    icingCost += IcingPrice.CREAM_CHEESE_FROST;
                    break;
                case "MERINGUE":
                    icingCost += IcingPrice.MERINGUE * seasonalMultiplier;
                    break;
            }
        }
        System.out.println(icing + ": " + icingCost);
        return icingCost;
    }

    private double getServingSizePrice(String serving, double specialityDiscount) {
        double servingSizeCost = 0;
        if (serving.equals("6 inches") || serving.equals("8 inches") || serving.equals("12 inches")) {
            switch (serving) {
                case "6 inches":
                    servingSizeCost += 6 ;
                    break;
                case "8 inches":
                    servingSizeCost += 8 * specialityDiscount;
                    break;
                case "12 inches":
                    servingSizeCost += 12 ;
                    break;
            }
        }
        System.out.println(serving + ": " + servingSizeCost);
        return servingSizeCost;
    }

    private double getDecorCharges(String decor, double specialityDiscount) {
        double decorCostRate = 0;
        if (decor.equals("BASIC") || decor.equals("MEDIOCRE") || decor.equals("HIGH_CLASS")) {
            switch (decor) {
                case "BASIC":
                    decorCostRate += DecorationIntricacy.BASIC;
                    break;
                case "MEDIOCRE":
                    decorCostRate += DecorationIntricacy.MEDIOCRE;
                    break;
                case "HIGH CLASS":
                    decorCostRate += DecorationIntricacy.HIGH_CLASS * specialityDiscount;
                    break;
            }
        }
        System.out.println(decor + ": " + decorCostRate);
        return decorCostRate;
    }

    private double getOccasionCharges(String occasion) {
        double occasionCharges = 0;
        if (occasion.equals("CASUAL") || occasion.equals("WEDDING") || occasion.equals("BIRTHDAY")) {
            switch (occasion) {
                case "CASUAL":
                    occasionCharges += 1;
                    break;
                case "WEDDING":
                    occasionCharges += 10;
                    break;
                case "BIRTHDAY":
                    occasionCharges += 5;
                    break;
            }
        }
        System.out.println(occasion + ": " + occasionCharges);
        return occasionCharges;
    }

    private double getDeliveryCharges(String county, double specialityDiscount) {
        double deliveryFee = 2;
        if (county.equals("DUBLIN") || county.equals("MEATH") || county.equals("LOUTH") || county.equals("WICKLOW")
                || county.equals("KILDARE") || county.equals("CARLOW")) {
            switch (county) {
                case "DUBLIN":
                    deliveryFee += 1;
                    break;
                case "WICKLOW":
                case "KILDARE":
                    deliveryFee += 3.5;
                    break;
                case "MEATH":
                case "LOUTH":
                    deliveryFee += 5;
                    break;
                case "CARLOW":
                    deliveryFee += 8;
                    break;
            }
        }
        System.out.println(county + ": " + specialityDiscount);
        return deliveryFee;
    }

}
