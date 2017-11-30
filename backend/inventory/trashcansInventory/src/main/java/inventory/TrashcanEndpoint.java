package inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import io.spring.guides.gs_producing_web_service.GetTrashcanRequest;
import io.spring.guides.gs_producing_web_service.GetTrashcanResponse;

@Endpoint
public class TrashcanEndpoint {
  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

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
}
