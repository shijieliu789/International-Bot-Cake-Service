package service.bakedCakes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BCService extends AbstractCakeService {
    public static final String PREFIX = "BC";
    public static final String COMPANY = "Baked Cakes Limited";
    private static final String[] busyDays = new String[]{"01-01", "03-17", "10-31", "12-25"};

    private final Map<String, CakeInvoice> invoices = new HashMap<>();

    @RequestMapping(value = "/bcake", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<CakeInvoice> createInvoice(@RequestBody CakeSpec cakeSpec) throws URISyntaxException {

        //generate cake invoice from cake specifications
        CakeInvoice invoice = generateCake(cakeSpec);

        //add to list of invoices
        invoices.put(invoice.getReference(), invoice);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/bcake/" + invoice.getReference();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(invoice, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/bcake/{reference}", method = RequestMethod.GET)
    public CakeInvoice getResource(@PathVariable("reference") String reference) {
        CakeInvoice createdCake = invoices.get(reference);
        if (createdCake == null) throw new NoSuchCakeException();
        return createdCake;
    }

    public CakeInvoice generateCake(CakeSpec cakeSpec) {
        return new CakeInvoice(COMPANY, generateReference(PREFIX), generatePrice(cakeSpec));
    }

    //checks is today is weekend or approaching holiday
    public boolean isBusyDay() {
        LocalDate currentDay = LocalDate.now();
        for (String day : busyDays) {
            LocalDate busyDay = LocalDate.parse(currentDay.getYear() + "-" + day, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(currentDay.atStartOfDay(), busyDay.atStartOfDay());
            long diffDays = diff.toDays();
            if (diffDays >= 0 && diffDays <= 7) return true;
        }
        return currentDay.getDayOfWeek().getValue() == 6 || currentDay.getDayOfWeek().getValue() == 7;
    }

    public boolean isLuxuryCake(CakeSpec cakeSpec) {
        return !cakeSpec.getCakeType().equals("SHEET CAKE") || cakeSpec.getTopping().equals("MANGO")
                || cakeSpec.getFlavor().equals("RED VELVET") || cakeSpec.getDecor().equals("HIGH_CLASS");
    }

    public boolean isDublin(CakeSpec cakeSpec) {
        return cakeSpec.getCounty().equals("DUBLIN");
    }

    public double calculateSizePrice(CakeSpec cakeSpec, double basePrice) {
        double sizePrice = 0;
        switch (cakeSpec.getServing()) {
            case "6 inches":
                sizePrice = basePrice * 1.2;
                break;
            case "8 inches":
                sizePrice = basePrice * 1.3;
                break;
            case "12 inches":
                sizePrice = basePrice * 1.4;
                break;
        }
        if (cakeSpec.getCakeType().equals("SHEET CAKE")) sizePrice *= 0.8;
        if (cakeSpec.getDecor().equals("HIGH CLASS")) sizePrice *= 1.1;
        return sizePrice;
    }

    public void printCakeSpec(CakeSpec cakeSpec, double price){
        System.out.println("Cake Type: " + cakeSpec.getCakeType() + "\n" +
                "Topping: " + cakeSpec.getTopping() + "\n" +
                "Flavor: " + cakeSpec.getFlavor() + "\n" +
                "Icing Type: " + cakeSpec.getIcing() + "\n" +
                "Size: " + cakeSpec.getServing() + "\n" +
                "Decoration Intricacy: " + cakeSpec.getDecor() + "\n" +
                "Occasion: " + cakeSpec.getOccasion() + "\n" +
                "County: " + cakeSpec.getCounty() + "\n" +
                "Busy days or weekend: " + isBusyDay() + "\n" +
                "Luxury cake: " + isLuxuryCake(cakeSpec) + "\n" +
                "Price: " + price);
    }

    public double generatePrice(CakeSpec cakeSpec) {
        double price = 10;
        double luxuryCakeFee = 10;
        double deliveryPrice = 10;
        double premiumOptionMultiplier = 1.2;
        double basicOptionMultiplier = 0.6;
        double busyDayMultiplier = 1.1;
        double normalDayMultiplier = 0.9;

        if (isLuxuryCake(cakeSpec)) price += luxuryCakeFee;

        switch (cakeSpec.getCakeType()) {
            case "TRADITIONAL STACK":
                price += CakeTypePrice.TRADITIONAL_STACK * basicOptionMultiplier;
                break;
            case "BUTTER CAKE":
                price += CakeTypePrice.BUTTER_CAKE;
                break;
            case "FRUIT FILLED":
                price += CakeTypePrice.FRUIT_FILLED * premiumOptionMultiplier;
                break;
            case "SHEET CAKE":
                price += CakeTypePrice.SHEET_CAKE * basicOptionMultiplier;
                break;
            case "BISCUIT CAKE":
                price += CakeTypePrice.BISCUIT_CAKE;
                break;
            case "MERINGUE CAKE":
                price += CakeTypePrice.MERINGUE_CAKE * premiumOptionMultiplier;
                break;
            case "CHEESE CAKE":
                price += CakeTypePrice.CHEESE_CAKE;
                break;
            case "CUPCAKES":
                price += CakeTypePrice.CUPCAKES;
                break;
        }

        //increase price based of topping type
        switch (cakeSpec.getTopping()) {
            case "CHOCOLATE CHIPS":
                price += ToppingsPrice.CHOCOLATE_CHIPS * basicOptionMultiplier;
                break;
            case "OREO":
                price += ToppingsPrice.OREO;
                break;
            case "MANGO":
                price += ToppingsPrice.MANGO * premiumOptionMultiplier;
                break;
            case "STRAWBERRY":
                price += ToppingsPrice.STRAWBERRY * basicOptionMultiplier;
                break;
            case "GRAHAM":
                price += ToppingsPrice.GRAHAM * basicOptionMultiplier;
                break;
            case "COOKIE ASSORTMENT":
                price += ToppingsPrice.COOKIE_ASSORTMENT;
                break;
            case "FRUIT ASSORTMENT":
                price += ToppingsPrice.FRUIT_ASSORTMENT;
                break;
            case "NUTS":
                price += ToppingsPrice.NUTS * premiumOptionMultiplier;
                break;
        }

        //increase price based on flavor
        switch (cakeSpec.getFlavor()) {
            case "VANILLA":
                price += FlavorPrice.VANILLA * basicOptionMultiplier;
                break;
            case "CHOCOLATE":
                price += FlavorPrice.CHOCOLATE * basicOptionMultiplier;
                break;
            case "CARROT":
                price += FlavorPrice.CARROT;
                break;
            case "RED VELVET":
                price += FlavorPrice.RED_VELVET * premiumOptionMultiplier;
                break;
            case "COFFEE":
                price += FlavorPrice.COFFEE * premiumOptionMultiplier;
                break;
            case "LEMON":
                price += FlavorPrice.LEMON;
                break;
        }

        //increase price based on icing type
        switch (cakeSpec.getIcing()) {
            case "FONDANT":
                price += IcingPrice.FONDANT * basicOptionMultiplier;
                break;
            case "BUTTERCREAM":
                price += IcingPrice.BUTTERCREAM;
                break;
            case "WHIPPED CREAM":
                price += IcingPrice.WHIPPED_CREAM * basicOptionMultiplier;
                break;
            case "ROYAL ICING":
                price += IcingPrice.ROYAL_ICING;
                break;
            case "CREAM CHEESE FROST":
                price += IcingPrice.CREAM_CHEESE_FROST * basicOptionMultiplier;
                break;
            case "MERINGUE":
                price += IcingPrice.MERINGUE * premiumOptionMultiplier;
                break;
        }

        //increase price based on decoration intricacy
        switch (cakeSpec.getDecor()) {
            case "BASIC":
                price += DecorationIntricacy.BASIC * basicOptionMultiplier;
                break;
            case "MEDIOCRE":
                price += DecorationIntricacy.MEDIOCRE;
                break;
            case "HIGH CLASS":
                price += DecorationIntricacy.HIGH_CLASS * premiumOptionMultiplier;
                break;
        }

        // increase price for wedding cake
        if (cakeSpec.getOccasion().equals("WEDDING")) price *= premiumOptionMultiplier;

        // calculate price based on size
        price += calculateSizePrice(cakeSpec, price);

        // if delivery is required add delivery price
        if (!isDublin(cakeSpec)) price += deliveryPrice;

        // if day is not a workday or there is a holiday coming up then increase price
        if (isBusyDay()) price *= busyDayMultiplier;
        else price *= normalDayMultiplier;

        printCakeSpec(cakeSpec, price);

        return price;
    }

}
