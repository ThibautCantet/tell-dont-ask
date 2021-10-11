package it.gabrieletondi.telldontaskkata.useCase;

import java.util.ArrayList;
import java.util.List;

public class SellItemsRequest {
    private final List<SellItemRequest> requests;

    public SellItemsRequest() {
        requests = new ArrayList<>();
    }

    public List<SellItemRequest> getRequests() {
        return requests;
    }
}
