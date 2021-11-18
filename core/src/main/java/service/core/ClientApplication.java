package service.core;

import java.util.List;

public class ClientApplication {
    private int id;
    private CakeSpec cakeSpec;
    private List<CakeInvoice> cakeInvoices;

    public ClientApplication(int id, CakeSpec cakeSpec, List<CakeInvoice> cakeInvoices) {
        this.id = id;
        this.cakeSpec = cakeSpec;
        this.cakeInvoices = cakeInvoices;
    }

    public ClientApplication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CakeSpec getCakeSpec() {
        return cakeSpec;
    }

    public void setCakeSpec(CakeSpec cakeSpec) {
        this.cakeSpec = cakeSpec;
    }

    public List<CakeInvoice> cakeInvoices() {
        return cakeInvoices;
    }

    public void setQuotations(List<CakeInvoice> cakeInvoices) {
        this.cakeInvoices = cakeInvoices;
    }
}
