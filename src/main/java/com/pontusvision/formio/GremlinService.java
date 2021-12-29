package com.pontusvision.formio;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
//import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RegisterRestClient
public interface GremlinService
{

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  @Singleton
  @Path("pvgdpr_graph")
  Response runQuery(GremlinQuery query);
}


