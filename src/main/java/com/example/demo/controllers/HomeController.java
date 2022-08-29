package com.example.demo.controllers;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.example.demo.com.domain.CDSContractItems;
import com.example.demo.com.domain.Customers;

import com.example.demo.com.domain.WorklogProject;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.FieldValueSet;
import com.microsoft.graph.models.ListItem;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.ListItemCollectionPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class HomeController {
    private FieldValueSet fields;


    public ArrayList<WorklogProject> getWorklogProject(String customerName){

        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("1824b3be-1cf3-4e28-991c-1fe7c360af3f")
                .clientSecret("9tQ8Q~ahUUqFT3yUjG5RTXSNhuI2OElPDfsVIcZ7")
                .tenantId("333d6240-83af-4e05-849b-b817a8f58b78")
                .build();

        TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(clientSecretCredential);

        GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        LinkedList<Option> requestOptions = new LinkedList<Option>();
        requestOptions.add(new QueryOption("expand", "fields(select=AccountName,DateOfWork,WorkTime,WorkerName,ProjectCode,Title,ToBeInvoiced,ServiceType,EmployeeFee,WorkCost)"));


        ListItemCollectionPage items = graphClient.sites("0fb9566e-c86e-42c1-a3ef-e7c2cef48b54").lists("2daaab75-9a8a-42a7-a6e5-f02e9a13b41d").items()
                .buildRequest( requestOptions )
                .filter("fields/AccountName eq '"+customerName+"'")
                .orderBy("fields/DateOfWork desc")
                .get();


        List<ListItem> itemList = items.getCurrentPage();



        ArrayList<WorklogProject> worklogProjectArrayList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        for (ListItem item : itemList) {
            WorklogProject worklogProject = new WorklogProject();
            try {
                worklogProject.setAccountName(item.fields.additionalDataManager().get("AccountName").toString().replaceAll("\"",""));
                worklogProject.setProjectCode(item.fields.additionalDataManager().get("ProjectCode").toString().replaceAll("\"",""));
                worklogProject.setWorkTitle(item.fields.additionalDataManager().get("Title").toString().replaceAll("\"",""));
                //worklogProject.setServiceType(item.fields.additionalDataManager().get("ServiceType").toString().replaceAll("\"",""));
                worklogProject.setWorkerName(item.fields.additionalDataManager().get("WorkerName").toString().replaceAll("\"",""));
                worklogProject.setDateOfWork( LocalDateTime.parse(item.fields.additionalDataManager().get("DateOfWork").toString().replaceAll("\"",""),dateTimeFormatter));
                worklogProject.setWorkTime(Double.parseDouble(item.fields.additionalDataManager().get("worktime").toString()));
                worklogProject.setEmployeeFee(Double.parseDouble(item.fields.additionalDataManager().get("EmployeeFee").toString()));
                worklogProject.setWorkCost(worklogProject.getWorkCost());
                worklogProject.setToBeInvoiced(Boolean.parseBoolean(item.fields.additionalDataManager().get("ToBeInvoiced").toString()));
            }
            catch(Exception e) {
                System.out.println(e+"-");
            }
            worklogProjectArrayList.add(worklogProject);
        }

        return worklogProjectArrayList;

    }

    @GetMapping("/")
    public String homePage(Model model){

        ArrayList<WorklogProject> worklogProject = getWorklogProject("Dualtrend Kft.");

        Map<String, List<WorklogProject>> worklogByProject = new HashMap<>();
        worklogByProject = worklogProject.stream().collect(Collectors.groupingBy(WorklogProject::getProjectCode));

        return "contracts";
    }


    //@RequestMapping (value = "myContracts", method = RequestMethod.POST)
    @GetMapping("myContracts")
    public String myContracts(Model model, @RequestParam(required = false) String id) throws URISyntaxException{

        String billingoID= id;
        System.out.println(id);

        //CustomerData
        RestTemplate template = new RestTemplate();
        RequestEntity<String> reqEntityCust = new RequestEntity<>("",
                HttpMethod.POST,
                new URI("https://prod-214.westeurope.logic.azure.com:443/workflows/561b8817b1454399a0c7d70a507db510/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=53MaQFIRb_bYv9xE4rwEYbeyNInv4vf9P1EBbc0RfAA"));
        ArrayList<Customers> customers = new ArrayList<>();
        ResponseEntity<Customers[]> responseEntityCus = template.exchange(reqEntityCust,Customers[].class);
        for (Customers customer : responseEntityCus.getBody()){
            Customers customers1 = new Customers();
            customers1.setCustomer(customer.getCustomer());
            customers1.setBillingo(customer.getBillingo());
            customers.add(customers1);
        }

        //billingoid
        if (billingoID==null){
            billingoID=customers.get(1).getCustomer()+"";
        }
        System.out.println(billingoID);


        //ContractItemData
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestEntity<String> reqEntity = new RequestEntity<>("{\"id\":\""+billingoID+"\"}",headers,
                HttpMethod.POST,
                new URI("https://prod-37.westeurope.logic.azure.com:443/workflows/bf298e5d854a4d2085357713ab5c5f5a/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=BPMLdJL9UyriNMgnlW-Fdu7fV8eUSH37HN7bo0EnwNI"));
        ArrayList<CDSContractItems> CDSItemList = new ArrayList<>();
        ResponseEntity<CDSContractItems[]> responseEntity = template.exchange(reqEntity,CDSContractItems[].class);
        for (CDSContractItems cdsContractItems : responseEntity.getBody()){
            CDSContractItems cdsContractItem = new CDSContractItems();
            try {
                cdsContractItem.setProductName(cdsContractItems.getProductName());
                cdsContractItem.setCustomer(cdsContractItems.getCustomer());
                cdsContractItem.setAppliedDevizaType(cdsContractItems.getAppliedDevizaType());
                cdsContractItem.setQuantity(cdsContractItems.getQuantity());
                cdsContractItem.setEurPrice(cdsContractItems.getEurPrice());
                cdsContractItem.setHufPrice(cdsContractItems.getHufPrice());
                cdsContractItem.setValidFrom(cdsContractItems.getValidFrom());
                cdsContractItem.setValidTillm(cdsContractItems.getValidTillm());
                cdsContractItem.setContractNumber(cdsContractItems.getContractNumber());
                cdsContractItem.setPriceString(cdsContractItems.getPriceStringCalc());
                cdsContractItem.setPriceStringAll(cdsContractItems.getPriceStringCalcAll());
            }catch(Exception e) {
                    System.out.println(e+"-");
                }
            CDSItemList.add(cdsContractItem);
        }

        Date dateNow = new Date(System.currentTimeMillis());
        List<CDSContractItems> CDSItemListLive = CDSItemList.stream().filter(c -> c.getValidTillm() == null || dateNow.before(c.getValidTillm())).collect(Collectors.toList());
        List<CDSContractItems> CDSItemListArchive = CDSItemList.stream().filter(c -> c.getValidTillm() != null && c.getValidTillm().before(dateNow)).collect(Collectors.toList());

        //Groupped worklog
        ArrayList<WorklogProject> worklogProject = getWorklogProject(billingoID);
        Map<String, List<WorklogProject>> worklogByProject = new HashMap<>();
        worklogByProject = worklogProject.stream().collect(Collectors.groupingBy(WorklogProject::getProjectCode));


        //model
        model.addAttribute("listItemsLive",CDSItemListLive);
        model.addAttribute("listItemsArchive",CDSItemListArchive);
        model.addAttribute("custItems",customers);
        model.addAttribute("custName",billingoID);
        model.addAttribute("worklogProjectData", worklogProject);
        model.addAttribute("worklogProjectGroupped", worklogByProject);


        return "contracts";
    }

}
