package service.core;

import java.util.List;

public class ClientApplication {
    private int id;
    private CakeSpec info;
    private List<CakeInvoice> cakeInvoices;

    public ClientApplication(int id, CakeSpec info, List<CakeInvoice> cakeInvoices) {
        this.id = id;
        this.info = info;
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

    public CakeSpec getInfo() {
        return info;
    }

    public void setInfo(CakeSpec info) {
        this.info = info;
    }

    public List<CakeInvoice> cakeInvoices() {
        return cakeInvoices;
    }

    public void setQuotations(List<CakeInvoice> cakeInvoices) {
        this.cakeInvoices = cakeInvoices;
    }
}
