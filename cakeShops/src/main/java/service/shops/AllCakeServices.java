package service.shops;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.CakeInvoice;
import service.core.CakeSpec;
import service.core.ClientApplication;
import service.core.NoSuchCakeException;

import java.io.IOException;
import java.net.*;
import java.util.*;

@CrossOrigin
@RestController
public class AllCakeServices {
    private Map<Integer, ClientApplication> clientApplicationHashMap = new HashMap<>();
    static int appID = 0;

    @RequestMapping(value="/applications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody ResponseEntity<Map<Integer, ClientApplication>> makeCake(@RequestBody CakeSpec cakeSpec) throws URISyntaxException, IOException {
    public @ResponseBody String makeCake(@RequestBody CakeSpec cakeSpec) throws URISyntaxException, IOException {

        //just for checking if received cakeSpec is correct
        System.out.println(cakeSpec.getCakeType());
        System.out.println(cakeSpec.getCounty());

        List<URL> cakeService = new LinkedList<>();
        List<CakeInvoice> returnedCakeInvoice = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CakeSpec> request = new HttpEntity<>(cakeSpec);

        //init URIs
        URI cleanCakes = new URI("http://localhost:8081/ccake");
        URI dreamCakes = new URI("http://localhost:8083/dcake");
        URI bakedCakes = new URI("http://localhost:8082/bcake");

        //open connection
        HttpURLConnection openConnection = (HttpURLConnection) cleanCakes.toURL().openConnection();
        int responseCode = openConnection.getResponseCode();

        //post data to URIs
        if(responseCode == HttpURLConnection.HTTP_BAD_METHOD) {
            System.out.println("\nThis service is available, but requires methods! \n<<<Sending appropriate methods to service>>>\n");
            returnedCakeInvoice.add(restTemplate.postForObject(cleanCakes.toString(), request, CakeInvoice.class));
            returnedCakeInvoice.add(restTemplate.postForObject(bakedCakes.toString(), request, CakeInvoice.class));
            returnedCakeInvoice.add(restTemplate.postForObject(dreamCakes.toString(), request, CakeInvoice.class));

        } else {
            System.out.println("This service is available but can't be used here.");
        }

        for (CakeInvoice invoice : returnedCakeInvoice){
            System.out.println("service: " + invoice.getCakery());
        }

        ClientApplication clientApplication = new ClientApplication(appID, cakeSpec, returnedCakeInvoice);
        System.out.println("invoices " + clientApplication.cakeInvoices().get(0).getCakery());
        System.out.println("2 costs: " + clientApplication.cakeInvoices().get(1).getPrice());
        System.out.println("3 costs: " + clientApplication.cakeInvoices().get(2).getPrice());

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/applications/" + appID;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));

        clientApplicationHashMap.put(appID,clientApplication);
        appID++;

        return new Gson().toJson(clientApplication);
//        return new ResponseEntity<>(clientApplicationHashMap, headers, HttpStatus.CREATED);
//        return new ResponseEntity<>(clientApplication, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/applications/{id}",method= RequestMethod.GET)
    public ClientApplication getApplication(@PathVariable int id){
        return clientApplicationHashMap.get(id);
    }

    @RequestMapping(value="/applications",method= RequestMethod.GET)
    public ResponseEntity<List<ClientApplication>> getApplications(){
        List<ClientApplication> applicationList = new ArrayList<>();
        for (ClientApplication clientApplication : clientApplicationHashMap.values()) {
            applicationList.add(clientApplication);
        }
        return new ResponseEntity<>(applicationList, HttpStatus.CREATED);
//        return new ArrayList<>(clientApplicationHashMap.values());
    }

}
