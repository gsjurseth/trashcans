package com.apigee.trashcans.inventory;

//import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TrashcanEndpoint {
  private static final String NAMESPACE_URI = "http://trashcans.apigee.com/inventory";

  private TrashcanRepository trashcanRepository;

  @Autowired
  public TrashcanEndpoint(TrashcanRepository trashcanRepository) {
    this.trashcanRepository = trashcanRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTrashcanRequest")
  @ResponsePayload
  public GetTrashcanResponse getTrashcan(@RequestPayload GetTrashcanRequest request) {
    GetTrashcanResponse response = new GetTrashcanResponse();
    response.setTrashcan(trashcanRepository.findTrashcan(request.getName()));

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addTrashcanStockRequest")
  @ResponsePayload
  public AddTrashcanStockResponse addTrashcanStock(@RequestPayload AddTrashcanStockRequest request) {
    AddTrashcanStockResponse response = new AddTrashcanStockResponse();
    response.setTrashcan(trashcanRepository.addTrashcanStock( request.getTrashcan() ));

    return response;
  }
}
