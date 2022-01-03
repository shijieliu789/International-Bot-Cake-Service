package service.shops;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.CakeInvoice;
import service.core.CakeSpec;
import service.core.ClientApplication;
import service.core.NoSuchCakeException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@CrossOrigin
@RestController
public class AllCakeServices {
    private Map<Integer, ClientApplication> clientApplicationHashMap = new HashMap<>();
    static int appID = 0;

    @RequestMapping(value="/applications", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<Integer, ClientApplication>> makeCake(@RequestBody CakeSpec cakeSpec) throws URISyntaxException, IOException {
        //just for checking if received cakeSpec is correct
        System.out.println(cakeSpec.getCakeType());
        System.out.println(cakeSpec.getCounty());

        List<CakeInvoice> cakes = new LinkedList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CakeSpec> request = new HttpEntity<>(cakeSpec);

        //init URIs
        URI cleanCakes = new URI("http://localhost:8081/ccake");
        URI bakedCakes = new URI("http://localhost:8082/bcake");

        //open connection
        HttpURLConnection openConnection = (HttpURLConnection) cleanCakes.toURL().openConnection();
        int responseCode = openConnection.getResponseCode();

        //post data to URIs
        if(responseCode == HttpURLConnection.HTTP_BAD_METHOD) {
            System.out.println("\nThis service is available, but requires methods! \n<<<Sending appropriate methods to service>>>\n");
            cakes.add(restTemplate.postForObject(cleanCakes, request, CakeInvoice.class));
            cakes.add(restTemplate.postForObject(bakedCakes, request, CakeInvoice.class));
        } else {
            System.out.println("This service is available but can't be used here.");
        }

        ClientApplication clientApplication=new ClientApplication(appID,cakeSpec,cakes);
        System.out.println(clientApplication.cakeInvoices().get(0).getPrice());
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/applications/" + appID;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));

        clientApplicationHashMap.put(appID,clientApplication);
        appID++;
        return new ResponseEntity<>(clientApplicationHashMap, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/applications/{id}",method= RequestMethod.GET)
    public ClientApplication getApplication(@PathVariable int id){
        return clientApplicationHashMap.get(id);
    }

    @RequestMapping(value="/applications",method= RequestMethod.GET)
    public List<ClientApplication> getApplications(){
        return new ArrayList<>(clientApplicationHashMap.values());
    }
}
