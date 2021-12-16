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

    @RequestMapping(value="/cake", method= RequestMethod.POST)
    public @ResponseBody ResponseEntity<CakeInvoice> createInvoice(@RequestBody CakeSpec cakeSpec) throws URISyntaxException {

        // generate cake invoice from cake specifications
        CakeInvoice invoice = generateCake(cakeSpec);

        //add to list of invoices
        invoices.put(invoice.getReference(), invoice);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/cake/"+invoice.getReference();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(invoice, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/cake/{reference}",method=RequestMethod.GET)
    public CakeInvoice getResource(@PathVariable("reference") String reference){
        CakeInvoice createdCake = invoices.get(reference);
        if (createdCake == null) throw new NoSuchCakeException();
        return createdCake;
    }

    public CakeInvoice generateCake(CakeSpec cakeSpec) {
        return new CakeInvoice(COMPANY, generateReference(PREFIX), generatePrice(cakeSpec));
    }

    public double generatePrice(CakeSpec cakeSpec) {
        double price = 0;
        if(Objects.equals(cakeSpec.getCakeType(), "TRADITIONAL_STACK")) {
            System.out.println(cakeSpec.getCakeType());
            price += CakeTypePrice.TRADITIONAL_STACK;
        }
        if(Objects.equals(cakeSpec.getTopping(), "OREO")) {
            System.out.println(cakeSpec.getTopping());
            price += ToppingsPrice.OREO;
        }
        if(Objects.equals(cakeSpec.getFlavor(), "VANILLA")) {
            System.out.println(cakeSpec.getFlavor());
            price += FlavorPrice.VANILLA;
        }
        if(Objects.equals(cakeSpec.getIcing(), "FONDANT")) {
            System.out.println(cakeSpec.getIcing());
            price += IcingPrice.FONDANT;
        }
        if(Objects.equals(cakeSpec.getServing(), "1")) {
            System.out.println(cakeSpec.getServing());
            price += 3;
        }
        if(Objects.equals(cakeSpec.getDecor(), "BASIC")) {
            System.out.println(cakeSpec.getDecor());
            price += DecorationIntricacy.BASIC;
        }
        if(Objects.equals(cakeSpec.getOccasion(), "FUN")) {
            System.out.println(cakeSpec.getOccasion());
            price += 5;
        }
        if(Objects.equals(cakeSpec.getCounty(), "DUBLIN")) {
            System.out.println(cakeSpec.getCounty());
            price += 6;
        }
        System.out.println(price);
        return price;
    }

}
