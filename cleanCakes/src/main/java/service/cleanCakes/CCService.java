package service.cleanCakes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CCService extends AbstractCakeService {
    public static final String PREFIX = "CC";
    public static final String COMPANY = "Clean Cakes Confectionary";

    private Map<String, CakeInvoice> invoices = new HashMap<>();

    @RequestMapping(value="/cake", method= RequestMethod.POST)
    public @ResponseBody ResponseEntity<CakeInvoice> createInvoice(@RequestBody CakeSpec cakeSpec) throws URISyntaxException {
        System.out.println(cakeSpec.getCakeType());
        System.out.println(cakeSpec.getCounty());

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
        //TO BE IMPLEMENTED
        return new CakeInvoice(COMPANY, generateReference(PREFIX), generatePrice(cakeSpec));
    }

    public double generatePrice(CakeSpec cakeSpec){
        // TO BE IMPLEMENTED
        return 777;
    }
}
