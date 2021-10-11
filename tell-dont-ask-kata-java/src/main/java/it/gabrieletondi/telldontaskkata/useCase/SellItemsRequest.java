package it.gabrieletondi.telldontaskkata.useCase;

import java.util.ArrayList;
import java.util.List;

public class SellItemsRequest {
    private List<SellItemRequest> requests;

    private SellItemsRequest() {
        requests = new ArrayList<>();
    }

    public SellItemsRequest(List<SellItemRequest> sellItemRequests) {
        this();
        requests = sellItemRequests;
    }

    public SellItemsRequest(SellItemRequest unknownProductRequest) {
        this();
        requests.add(unknownProductRequest);
    }

    public List<SellItemRequest> getRequests() {
        return requests;
    }
}
