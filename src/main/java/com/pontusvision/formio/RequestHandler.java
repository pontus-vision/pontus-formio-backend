package com.pontusvision.formio;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.Keycloak;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Path("rest_formio_backend")
//@RolesAllowed({ "pontus-role","offline_access" })

public class RequestHandler
{

  public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  @Inject
  KeycloakSecurityContext keycloakSecurityContext;

  @Inject
  @RestClient
  GremlinService gremlinService;

  Map<String, Pattern> compiledPatterns = new HashMap<>();

  @GET @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getProject() throws URISyntaxException, IOException
  {
    String json = "{\"_id\":\"5d455610cb0813f179c5c689\",\"type\":\"project\",\"tag\":\"0.0.0\",\"owner\":\"5b0c4d04cde046ca1593a06d\",\"plan\":\"trial\",\"steps\":[],\"framework\":\"react\",\"protect\":false,\"title\":\"test123\",\"name\":\"akptuiikaqggtnq\",\"access\":[{\"roles\":[],\"type\":\"create_own\"},{\"roles\":[\"5d455610cb081362b5c5c68a\"],\"type\":\"create_all\"},{\"roles\":[],\"type\":\"read_own\"},{\"roles\":[\"5d455610cb081362b5c5c68a\",\"5d455610cb08131582c5c68b\",\"5d455610cb08132afec5c68c\"],\"type\":\"read_all\"},{\"roles\":[],\"type\":\"update_own\"},{\"roles\":[\"5d455610cb081362b5c5c68a\"],\"type\":\"update_all\"},{\"roles\":[],\"type\":\"delete_own\"},{\"roles\":[\"5d455610cb081362b5c5c68a\"],\"type\":\"delete_all\"},{\"roles\":[],\"type\":\"team_read\"},{\"roles\":[],\"type\":\"team_write\"},{\"roles\":[],\"type\":\"team_admin\"}],\"trial\":\"2019-08-03T09:38:24.021Z\",\"created\":\"2019-08-03T09:38:24.021Z\",\"modified\":\"2019-08-03T11:00:08.583Z\",\"config\":{},\"apiCalls\":{\"used\":{\"submissionRequests\":1,\"formRequests\":4,\"emails\":0,\"forms\":6},\"limit\":{\"forms\":10,\"formRequests\":10000,\"submissionRequests\":10000,\"emails\":100},\"reset\":\"2019-09-01T00:00:00.000Z\"}}";
    return Response.ok(json, MediaType.APPLICATION_JSON).build();

    //
    //    try (InputStream inputStream = new URL("http://localhost:18443/formio/").openStream())
    //    {
    //      String data =  IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    //      return Response.ok(data, MediaType.TEXT_HTML).build();
    //
    //    }

  }

  @POST @Path("project")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response createProject(String jsonBody, @Context UriInfo uriInfo)
  {
    /*
    {
    "title": "Default",
    "name": "default",
    "description": "An example application",
    "template":"https://cdn.rawgit.com/formio/formio-app-basic/2.1.0/src/project.json",
    "settings": {
        "cors": "*"
    }
}
     */

    String json = "{\n"
        + "  \"modified\": \"2016-09-07T23:18:16.544Z\",\n"
        + "  \"title\": \"Default\",\n"
        + "  \"description\": \"An example application\",\n"
        + "  \"name\": \"oaqugrvolpkayzn\",\n"
        + "  \"created\": \"2016-09-07T23:18:15.659Z\",\n"
        + "  \"_id\": \"57d0a03776f943a4007e1513\",\n"
        + "  \"access\": [\n"
        + "    {\n"
        + "      \"type\": \"create_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"read_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"update_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"delete_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    }\n"
        + "  ],\n"
        + "  \"steps\": [],\n"
        + "  \"plan\": \"basic\",\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\",\n"
        + "  \"apiCalls\": {\n"
        + "    \"used\": 0,\n"
        + "    \"remaining\": 1000,\n"
        + "    \"limit\": 1000,\n"
        + "    \"reset\": \"2016-10-01T00:00:00.000Z\"\n"
        + "  }\n"
        + "}";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }

  @GET @Path("current")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @QueryParam("token")
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getCurrent2(String jsonBody,@QueryParam("token") String token, @Context UriInfo uriInfo)
  {

    String name  = keycloakSecurityContext.getIdToken().getName();
    String email = keycloakSecurityContext.getIdToken().getEmail();

    String json = "{\"owner\":null,\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"_id\":\"" + name
        + "\",\"form\":\"5b71e372739d8b5563ba1f9f\",\"data\":{\"email\":\"" + email
        + "\"},\"access\":[],\"externalIds\":[],\"created\":\"2018-08-13T20:00:58.182Z\",\"modified\":\"2018-08-13T20:00:58.183Z\"}";

    String example ="{\n"
        + "  \"_id\": \"57d010fe76f943a4007e11da\",\n"
        + "  \"modified\": \"2016-09-07T23:11:03.165Z\",\n"
        + "  \"data\": {\n"
        + "    \"fullName\": \"Form.io Demo\",\n"
        + "    \"name\": \"formiodemo\",\n"
        + "    \"email\": \"demo+formio@form.io\"\n"
        + "  },\n"
        + "  \"form\": \"553db94e72f702e714dd9779\",\n"
        + "  \"created\": \"2016-09-07T13:07:10.892Z\",\n"
        + "  \"externalIds\": [\n"
        + "    {\n"
        + "      \"type\": \"hubspotContact\",\n"
        + "      \"id\": \"1880164\",\n"
        + "      \"_id\": \"57d0110076f943a4007e11dd\",\n"
        + "      \"modified\": \"2016-09-07T23:11:03.165Z\",\n"
        + "      \"created\": \"2016-09-07T13:07:12.481Z\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"access\": [],\n"
        + "  \"roles\": [\n"
        + "    \"55cd5c3ca51a96bef99ef551\",\n"
        + "    null\n"
        + "  ],\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\"\n"
        + "}\n";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }

  @GET
  @PUT @Path("project/{projectid}")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })

  public Response getProject(String jsonBody, @PathParam("projectid") String formId
      , @Context UriInfo uriInfo)
  {

    String json = "{\n"
        + "  \"modified\": \"2016-09-07T23:18:16.544Z\",\n"
        + "  \"title\": \"Default\",\n"
        + "  \"description\": \"An example application\",\n"
        + "  \"name\": \"oaqugrvolpkayzn\",\n"
        + "  \"created\": \"2016-09-07T23:18:15.659Z\",\n"
        + "  \"_id\": \"57d0a03776f943a4007e1513\",\n"
        + "  \"access\": [\n"
        + "    {\n"
        + "      \"type\": \"create_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"read_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"update_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"delete_all\",\n"
        + "      \"roles\": [\n"
        + "        \"57d0a03776f943a4007e1514\"\n"
        + "      ]\n"
        + "    }\n"
        + "  ],\n"
        + "  \"steps\": [],\n"
        + "  \"plan\": \"basic\",\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\",\n"
        + "  \"apiCalls\": {\n"
        + "    \"used\": 0,\n"
        + "    \"remaining\": 1000,\n"
        + "    \"limit\": 1000,\n"
        + "    \"reset\": \"2016-10-01T00:00:00.000Z\"\n"
        + "  }\n"
        + "}";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }




  @GET @Path("access")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getAccess(String jsonBody, @Context UriInfo uriInfo)
  {

    String json = "{\n"
        + "  \"roles\": {\n"
        + "    \"anonymous\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1516\",\n"
        + "      \"title\": \"Anonymous\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": true\n"
        + "    },\n"
        + "    \"authenticated\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1515\",\n"
        + "      \"title\": \"Authenticated\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": false\n"
        + "    },\n"
        + "    \"administrator\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1514\",\n"
        + "      \"title\": \"Administrator\",\n"
        + "      \"admin\": true,\n"
        + "      \"default\": false\n"
        + "    }\n"
        + "  },\n"
        + "  \"forms\": {\n"
        + "    \"user\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1517\",\n"
        + "      \"title\": \"User\",\n"
        + "      \"name\": \"user\",\n"
        + "      \"path\": \"user\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"admin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1518\",\n"
        + "      \"title\": \"Admin\",\n"
        + "      \"name\": \"admin\",\n"
        + "      \"path\": \"admin\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userLogin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1519\",\n"
        + "      \"title\": \"User Login\",\n"
        + "      \"name\": \"userLogin\",\n"
        + "      \"path\": \"user/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userRegister\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151a\",\n"
        + "      \"title\": \"User Register\",\n"
        + "      \"name\": \"userRegister\",\n"
        + "      \"path\": \"user/register\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"adminLogin\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151b\",\n"
        + "      \"title\": \"Admin Login\",\n"
        + "      \"name\": \"adminLogin\",\n"
        + "      \"path\": \"admin/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  }\n"
        + "}";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }



  @GET @Path("export")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getExport(String jsonBody, @Context UriInfo uriInfo)
  {

    String json = "{\n"
        + "  \"roles\": {\n"
        + "    \"anonymous\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1516\",\n"
        + "      \"title\": \"Anonymous\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": true\n"
        + "    },\n"
        + "    \"authenticated\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1515\",\n"
        + "      \"title\": \"Authenticated\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": false\n"
        + "    },\n"
        + "    \"administrator\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1514\",\n"
        + "      \"title\": \"Administrator\",\n"
        + "      \"admin\": true,\n"
        + "      \"default\": false\n"
        + "    }\n"
        + "  },\n"
        + "  \"forms\": {\n"
        + "    \"user\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1517\",\n"
        + "      \"title\": \"User\",\n"
        + "      \"name\": \"user\",\n"
        + "      \"path\": \"user\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"admin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1518\",\n"
        + "      \"title\": \"Admin\",\n"
        + "      \"name\": \"admin\",\n"
        + "      \"path\": \"admin\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userLogin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1519\",\n"
        + "      \"title\": \"User Login\",\n"
        + "      \"name\": \"userLogin\",\n"
        + "      \"path\": \"user/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userRegister\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151a\",\n"
        + "      \"title\": \"User Register\",\n"
        + "      \"name\": \"userRegister\",\n"
        + "      \"path\": \"user/register\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"adminLogin\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151b\",\n"
        + "      \"title\": \"Admin Login\",\n"
        + "      \"name\": \"adminLogin\",\n"
        + "      \"path\": \"admin/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  }\n"
        + "}";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }


  @POST @Path("import")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response postImport(String jsonBody, @Context UriInfo uriInfo)
  {

    String json = "{\n"
        + "  \"roles\": {\n"
        + "    \"anonymous\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1516\",\n"
        + "      \"title\": \"Anonymous\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": true\n"
        + "    },\n"
        + "    \"authenticated\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1515\",\n"
        + "      \"title\": \"Authenticated\",\n"
        + "      \"admin\": false,\n"
        + "      \"default\": false\n"
        + "    },\n"
        + "    \"administrator\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1514\",\n"
        + "      \"title\": \"Administrator\",\n"
        + "      \"admin\": true,\n"
        + "      \"default\": false\n"
        + "    }\n"
        + "  },\n"
        + "  \"forms\": {\n"
        + "    \"user\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1517\",\n"
        + "      \"title\": \"User\",\n"
        + "      \"name\": \"user\",\n"
        + "      \"path\": \"user\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"admin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1518\",\n"
        + "      \"title\": \"Admin\",\n"
        + "      \"name\": \"admin\",\n"
        + "      \"path\": \"admin\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"read_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"update_own\",\n"
        + "          \"roles\": []\n"
        + "        },\n"
        + "        {\n"
        + "          \"type\": \"delete_own\",\n"
        + "          \"roles\": []\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\",\n"
        + "            \"57d0a03776f943a4007e1515\",\n"
        + "            \"57d0a03776f943a4007e1514\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userLogin\": {\n"
        + "      \"_id\": \"57d0a03776f943a4007e1519\",\n"
        + "      \"title\": \"User Login\",\n"
        + "      \"name\": \"userLogin\",\n"
        + "      \"path\": \"user/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"userRegister\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151a\",\n"
        + "      \"title\": \"User Register\",\n"
        + "      \"name\": \"userRegister\",\n"
        + "      \"path\": \"user/register\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    \"adminLogin\": {\n"
        + "      \"_id\": \"57d0a03876f943a4007e151b\",\n"
        + "      \"title\": \"Admin Login\",\n"
        + "      \"name\": \"adminLogin\",\n"
        + "      \"path\": \"admin/login\",\n"
        + "      \"submissionAccess\": [\n"
        + "        {\n"
        + "          \"type\": \"create_own\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ],\n"
        + "      \"access\": [\n"
        + "        {\n"
        + "          \"type\": \"read_all\",\n"
        + "          \"roles\": [\n"
        + "            \"57d0a03776f943a4007e1516\"\n"
        + "          ]\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  }\n"
        + "}";

    return Response.ok(json, MediaType.APPLICATION_JSON).build();

  }

  @POST @Path("user/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(String jsonBody, @Context UriInfo uriInfo){

    /*
    {
    "data": {
        "email": "{{username}}",
        "password": "{{password}}"
    }
}
     */

    String res = "{\n"
        + "  \"_id\": \"57d010fe76f943a4007e11da\",\n"
        + "  \"modified\": \"2016-09-07T23:11:03.165Z\",\n"
        + "  \"data\": {\n"
        + "    \"fullName\": \"Form.io Demo\",\n"
        + "    \"name\": \"formiodemo\",\n"
        + "    \"email\": \"demo+formio@form.io\"\n"
        + "  },\n"
        + "  \"form\": \"553db94e72f702e714dd9779\",\n"
        + "  \"created\": \"2016-09-07T13:07:10.892Z\",\n"
        + "  \"externalIds\": [\n"
        + "    {\n"
        + "      \"type\": \"hubspotContact\",\n"
        + "      \"id\": \"1880164\",\n"
        + "      \"_id\": \"57d0110076f943a4007e11dd\",\n"
        + "      \"modified\": \"2016-09-07T23:11:03.165Z\",\n"
        + "      \"created\": \"2016-09-07T13:07:12.481Z\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"access\": [],\n"
        + "  \"roles\": [\n"
        + "    \"55cd5c3ca51a96bef99ef551\",\n"
        + "    null\n"
        + "  ],\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\"\n"
        + "}";

    return Response.ok(res, MediaType.APPLICATION_JSON).build();
  }



  @POST @Path("admin")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createAdmin(String jsonBody, @Context UriInfo uriInfo){

    /*
    {
    "data": {
        "email": "{{username}}",
        "password": "{{password}}"
    }
}
     */

    String res = "{\n"
        + "  \"modified\": \"2016-09-07T23:18:39.075Z\",\n"
        + "  \"data\": {\n"
        + "    \"email\": \"admin@example.com\"\n"
        + "  },\n"
        + "  \"form\": \"57d0a03776f943a4007e1518\",\n"
        + "  \"created\": \"2016-09-07T23:18:39.069Z\",\n"
        + "  \"_id\": \"57d0a04f76f943a4007e1524\",\n"
        + "  \"externalIds\": [],\n"
        + "  \"access\": [],\n"
        + "  \"roles\": [\n"
        + "    \"57d0a03776f943a4007e1514\"\n"
        + "  ],\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\"\n"
        + "}";

    return Response.ok(res, MediaType.APPLICATION_JSON).build();
  }

  @POST @Path("admin/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response adminLogin(String jsonBody, @Context UriInfo uriInfo){

    /*
    {
    "data": {
        "email": "{{username}}",
        "password": "{{password}}"
    }
}
     */

    String res = "{\n"
        + "  \"_id\": \"57d0a04f76f943a4007e1524\",\n"
        + "  \"modified\": \"2016-09-07T23:18:39.075Z\",\n"
        + "  \"data\": {\n"
        + "    \"email\": \"admin@example.com\"\n"
        + "  },\n"
        + "  \"form\": \"57d0a03776f943a4007e1518\",\n"
        + "  \"created\": \"2016-09-07T23:18:39.069Z\",\n"
        + "  \"externalIds\": [],\n"
        + "  \"access\": [],\n"
        + "  \"roles\": [\n"
        + "    \"57d0a03776f943a4007e1514\"\n"
        + "  ],\n"
        + "  \"owner\": \"57d010fe76f943a4007e11da\"\n"
        + "}";

    return Response.ok(res, MediaType.APPLICATION_JSON).build();
  }



  @GET @Path("token")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response logout()
  {
    String json = "{\n"
        + "  \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7Il9pZCI6IjViYWI3ZjQ4ZjdlMGQxYWU1ZDk2NTg5OSJ9LCJmb3JtIjp7Il9pZCI6IjViYWI3ZjM4ZjdlMGQxNWYyZTk2NTg4OSIsInByb2plY3QiOiI1YmFiN2YzOGY3ZTBkMTZkYjY5NjU4ODQifSwib3JpZ2luIjoiaHR0cHM6Ly9hcGkudGVzdC1mb3JtLmlvIiwicHJvamVjdCI6eyJfaWQiOiI1YmFiN2YzOGY3ZTBkMTZkYjY5NjU4ODQifSwiaWF0IjoxNTM3OTY2MjIzLCJhbGxvdyI6IkdFVDovY3VycmVudCIsInRlbXBUb2tlbiI6dHJ1ZSwiaXNBZG1pbiI6ZmFsc2UsImV4cCI6MTUzNzk2OTgyM30.DITbY1DE9WyC82t1OukKHMeAlgZ_PW4km8KPVmuMSDI\",\n"
        + "  \"key\": \"nA1MkLl0zRcRuCK4ANl8ussF2Aq8tn\"\n"
        + "}"
    return Response.ok(json, MediaType.APPLICATION_JSON).build();
  }

  @GET @Path("current/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response logout()
  {
    String json = "{\n"
        + "  \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7Il9pZCI6IjViYWI3ZjQ4ZjdlMGQxYWU1ZDk2NTg5OSJ9LCJmb3JtIjp7Il9pZCI6IjViYWI3ZjM4ZjdlMGQxNWYyZTk2NTg4OSIsInByb2plY3QiOiI1YmFiN2YzOGY3ZTBkMTZkYjY5NjU4ODQifSwib3JpZ2luIjoiaHR0cHM6Ly9hcGkudGVzdC1mb3JtLmlvIiwicHJvamVjdCI6eyJfaWQiOiI1YmFiN2YzOGY3ZTBkMTZkYjY5NjU4ODQifSwiaWF0IjoxNTM3OTY2MjIzLCJhbGxvdyI6IkdFVDovY3VycmVudCIsInRlbXBUb2tlbiI6dHJ1ZSwiaXNBZG1pbiI6ZmFsc2UsImV4cCI6MTUzNzk2OTgyM30.DITbY1DE9WyC82t1OukKHMeAlgZ_PW4km8KPVmuMSDI\",\n"
        + "  \"key\": \"nA1MkLl0zRcRuCK4ANl8ussF2Aq8tn\"\n"
        + "}"
    return Response.ok(json, MediaType.APPLICATION_JSON).build();
  }





  @GET @Path("logout")
  @Produces(MediaType.TEXT_PLAIN)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response logout()
  {
    return Response.ok("OK", MediaType.TEXT_PLAIN).build();
  }

  @POST @Path("user/register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response userRegister(String jsonBody, @Context UriInfo uriInfo){

    /*
    {
      "data": {
        "name": "{{newusername}}",
        "email": "{{newuseremail}}",
        "password": "password123"
      }
    }
     */

    return Response.ok(jsonBody, MediaType.APPLICATION_JSON).build();
  }



  public JsonObject getQueryResultAsJson (GremlinQuery query)
  {
    String res = getQueryResult(query);
    if (res.startsWith("\"") && res.endsWith("\""))
    {
      res = res.substring(1, res.length() - 1);
    }
    JsonObject b = Json.createReader(new StringReader(StringEscapeUtils.unescapeJava(res))).readObject();

    return b;
  }

  public String getQueryResult(GremlinQuery query)
  {
    Response resp = gremlinService.runQuery(query);

    String respStr = resp.readEntity(String.class);

    JsonObject respJson = Json.createReader(new StringReader(respStr)).readObject();

    JsonArray respJsonArray  = respJson.getJsonObject("result").getJsonObject("data").getJsonArray("@value");

    if (respJsonArray.size() > 0)
    {
      return  respJsonArray.get(0).toString();
    }

    return "{}";
  }

  public static String getFormCreateErrorOutputMissing(int status, String message)
  {
    JsonObject model =
        Json.createObjectBuilder()
            .add("status", status)
            .add("message", "form validation error: " + message + " is required.")
            .add("errors",
                Json.createObjectBuilder()
                    .add("path",
                        Json.createObjectBuilder()
                            .add("path", "path")
                            .add("name", "ValidationError")
                            .add("message", message + " is required.")
                    )
            ).build();

    String json = model.toString();

    return json;

  }

  public JsonArray getCreateFormAccess()
  {
    // "access":[{"roles":["5b71e372739d8b5563ba1f9b","5b71e372739d8b5563ba1f9c","5b71e372739d8b5563ba1f9d"],"type":"read_all"}]

    JsonArray access =
        Json.createArrayBuilder()
            .add(
                Json.createObjectBuilder()
                    .add("roles"
                        , Json.createArrayBuilder()
                              .add("5b71e372739d8b5563ba1f9b")
                              .add("5b71e372739d8b5563ba1f9c")
                              .add("5b71e372739d8b5563ba1f9d")
                    )

                    .add("type", "read_all")
            ).build();

    return access;

  }

  /*
    Sample request:
    {"display":"form"
     ,"components":[
       {"autofocus":false, "input":true,"tableView":true,"inputType":"text","inputMask":"","label":"Text","key":"text",
         "placeholder":"", "prefix":"", "suffix":"","multiple":false,"defaultValue":"","protected":false,"unique":false,
         "persistent":true,"hidden":false,"clearOnHide":true,"spellcheck":true,"validate":{"required":false,"minLength":"",
         "maxLength":"","pattern":"","custom":"","customPrivate":false},"conditional":{"show":"","when":null,"eq":""},
         "type":"textfield","labelPosition":"top", "tags":[],"properties":{}}
      ,{"autofocus":false,"input":true,"label":"Submit","tableView":false,"key":"submit","size":"md","leftIcon":"",
        "rightIcon":"","block":false,"action":"submit","disableOnInvalid":false,"theme":"primary","type":"button"}]
      ,"type":"form"
      ,"tags":["common"]
-     ,"page":0
      ,"submissionAccess":[
        {"type":"create_own","roles":["5b71e372739d8b5563ba1f9c"]}
       ,{"type":"read_own","roles":["5b71e372739d8b5563ba1f9c"]}
       ,{"type":"update_own","roles":["5b71e372739d8b5563ba1f9c"]}
       ,{"type":"delete_own","roles":["5b71e372739d8b5563ba1f9c"]}
      ]
      ,"title":"test"
      ,"name":"test"
      ,"path":"test"}


      Sample result:

      { "display":"form"
        "components":[{"autofocus":false,"input":true,"tableView":true,"inputType":"text","inputMask":"","label":"Text",
            "key":"text","placeholder":"","prefix":"","suffix":"","multiple":false,"defaultValue":"","protected":false,
            "unique":false,"persistent":true,"hidden":false,"clearOnHide":true,"spellcheck":true,"validate":{"required":false,
            "minLength":"","maxLength":"","pattern":"","custom":"","customPrivate":false},"conditional":{"show":"","when":null,
            "eq":""},"type":"textfield","labelPosition":"top","tags":[],"properties":{}}
            ,{"autofocus":false,"input":true,"label":"Submit","tableView":false,"key":"submit","size":"md","leftIcon":"",
            "rightIcon":"","block":false,"action":"submit","disableOnInvalid":false,"theme":"primary","type":"button"}
         ]
        ,"type":"form"
        ,"tags":["common"]
        ,"submissionAccess":[
           {"roles":["5b71e372739d8b5563ba1f9c"],"type":"create_own"}
          ,{"roles":["5b71e372739d8b5563ba1f9c"],"type":"read_own"}
          ,{"roles":["5b71e372739d8b5563ba1f9c"],"type":"update_own"}
          ,{"roles":["5b71e372739d8b5563ba1f9c"],"type":"delete_own"}]
        ,"title":"test"
        ,"name":"test"
        ,"path":"test"
+       ,_id":"5d4633b3e86b6500169d4449"
+       ,"owner":"5b71e37a739d8b5563ba1fa9",
+       ,"access":[{"roles":["5b71e372739d8b5563ba1f9b","5b71e372739d8b5563ba1f9c","5b71e372739d8b5563ba1f9d"],"type":"read_all"}]
+       ,"created":"2019-08-04T01:24:03.333Z"
+       ,"modified":"2019-08-04T01:24:03.368Z"
+       ,"machineName":"test"
        }

   */
  @POST @Path("form")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response postForm(String jsonBody, @Context UriInfo uriInfo)
  {

    JsonObject orig = Json.createReader(new StringReader(jsonBody)).readObject();

    JsonObjectBuilder obj = Json.createObjectBuilder();
    orig.forEach(obj::add); // copy source into target
    if (!orig.containsKey("path"))
    {
      String json = getFormCreateErrorOutputMissing(400, "path");
      return Response.status(400).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    GremlinQuery query = new GremlinQuery();
    query.gremlin = "g.addV('Object.Form').property('Metadata.Type.Object.Form','Object.Form')"
        + ".property('Object.Form.Metadata_GUID', formId).property('Object.Form.Text', formData).iterate();";
    query.bindings = new HashMap<>();
    String formId =
        Generators.timeBasedGenerator(EthernetAddress.fromInterface())
                  .generate().toString().replaceAll(Pattern.quote("-"), "");

    obj.remove("page");
    obj.add("_id", Json.createValue(formId));
    obj.add("owner", Json.createValue(getOwner()));
    obj.add("access", getCreateFormAccess());
    obj.add("machineName", Json.createValue("localhost"));
    obj.add("modified", Json.createValue(dateFormat.format(new Date())));
    obj.add("created", Json.createValue(dateFormat.format(new Date())));

    query.bindings.put("formId", formId);
    query.bindings.put("formData", obj.build().toString());

    String  resp = getQueryResult(query);

    return Response.ok(obj.build().toString(), MediaType.APPLICATION_JSON).build();
  }

  @POST
  @PUT @Path("form/{formid}")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response putForm(String jsonBody, @PathParam("formid") String formId
      , @Context UriInfo uriInfo)
  {

    JsonObject orig = Json.createReader(new StringReader(jsonBody)).readObject();

    JsonObjectBuilder obj = Json.createObjectBuilder();
    orig.forEach(obj::add); // copy source into target
    obj.remove("page");
    obj.add("modified", (JsonValue) Json.createValue(dateFormat.format(new Date())));

    String formData = obj.build().toString();

    GremlinQuery query = new GremlinQuery();
    query.gremlin = "g.V().has('Object.Form.Metadata_GUID', eq(formId)).property('Object.Form.Text', formData).iterate();";
    query.bindings = new HashMap<>();
    query.bindings.put("formId", formId);

    query.bindings.put("formData", formData);

    String res = getQueryResult(query);

    return Response.ok(formData, MediaType.APPLICATION_JSON).build();

  }

  @GET @Path("form")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getForm(
      @QueryParam("type") String type,
      @QueryParam("sort") String sort,
      @QueryParam("skip") Long skip,
      @QueryParam("limit") Long limit,
      @QueryParam("tags") String tags
  )
  {

    if ("resource".equalsIgnoreCase(type))
    {

      GremlinQuery query = new GremlinQuery();
      query.bindings = new HashMap<>();
      query.bindings.put("skip", skip);

      query.gremlin =
          "g.V()\n"
              + " .has('Metadata.Type.Object.Form', eq('Object.Form'))\n"
              + " .limit(limit + skip)\n"
              + " .skip(skip)\n"
              + " .values('Object.Form.Text')\n"
              + " .toList()";

      Response result = gremlinService.runQuery(query);

      if (result.getStatus() == 200)
      {
        JsonObject obj = Json.createReader(new StringReader(result.getEntity().toString())).readObject();

        List<Map<String, Object>> querRes = new ArrayList<>();

        List<ReactSelectOptions> selectOptions = new ArrayList<>(querRes.size());

        for (Map<String, Object> res : querRes)
        {
          selectOptions.add(new ReactSelectOptions(res.get("val").toString(), res.get("id").toString()));
        }

        FormioSelectResults retVal = new FormioSelectResults(selectOptions);

//        return retVal;
      }



      String json = "[{\"type\":\"resource\",\"tags\":[],\"owner\":null,\"components\":[{\"type\":\"email\",\"persistent\":true,\"unique\":false,\"protected\":false,\"defaultValue\":\"\",\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your email address\",\"key\":\"email\",\"label\":\"Email\",\"inputType\":\"email\",\"tableView\":true,\"input\":true},{\"type\":\"password\",\"persistent\":true,\"protected\":true,\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your password.\",\"key\":\"password\",\"label\":\"Password\",\"inputType\":\"password\",\"tableView\":false,\"input\":true},{\"type\":\"button\",\"theme\":\"primary\",\"disableOnInvalid\":true,\"action\":\"submit\",\"block\":false,\"rightIcon\":\"\",\"leftIcon\":\"\",\"size\":\"md\",\"key\":\"submit\",\"tableView\":false,\"label\":\"Submit\",\"input\":true}],\"_id\":\"21eba722bb6a11e986acc607f9317e4e\",\"title\":\"User\",\"name\":\"user\",\"path\":\"user\",\"submissionAccess\":[{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"create_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"read_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"update_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"delete_all\"},{\"roles\":[],\"type\":\"create_own\"},{\"roles\":[],\"type\":\"read_own\"},{\"roles\":[],\"type\":\"update_own\"},{\"roles\":[],\"type\":\"delete_own\"}],\"access\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\",\"5b71e372739d8b5563ba1f9c\",\"5b71e372739d8b5563ba1f9b\"],\"type\":\"read_all\"}],\"machineName\":\"user\",\"created\":\"2018-08-13T20:00:50.422Z\",\"modified\":\"2018-08-13T20:00:50.451Z\"},{\"type\":\"resource\",\"tags\":[],\"owner\":null,\"components\":[{\"type\":\"email\",\"persistent\":true,\"unique\":false,\"protected\":false,\"defaultValue\":\"\",\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your email address\",\"key\":\"email\",\"label\":\"Email\",\"inputType\":\"email\",\"tableView\":true,\"input\":true},{\"type\":\"password\",\"persistent\":true,\"protected\":true,\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your password.\",\"key\":\"password\",\"label\":\"Password\",\"inputType\":\"password\",\"tableView\":false,\"input\":true},{\"type\":\"button\",\"theme\":\"primary\",\"disableOnInvalid\":true,\"action\":\"submit\",\"block\":false,\"rightIcon\":\"\",\"leftIcon\":\"\",\"size\":\"md\",\"key\":\"submit\",\"tableView\":false,\"label\":\"Submit\",\"input\":true}],\"_id\":\"5b71e372739d8b5563ba1f9f\",\"title\":\"Admin\",\"name\":\"admin\",\"path\":\"admin\",\"submissionAccess\":[{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"create_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"read_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"update_all\"},{\"roles\":[\"5b71e372739d8b5563ba1f9b\"],\"type\":\"delete_all\"},{\"roles\":[],\"type\":\"create_own\"},{\"roles\":[],\"type\":\"read_own\"},{\"roles\":[],\"type\":\"update_own\"},{\"roles\":[],\"type\":\"delete_own\"}],\"access\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\",\"5b71e372739d8b5563ba1f9c\",\"5b71e372739d8b5563ba1f9b\"],\"type\":\"read_all\"}],\"machineName\":\"admin\",\"created\":\"2018-08-13T20:00:50.467Z\",\"modified\":\"2018-08-13T20:00:50.474Z\"}]";
      return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    else
    {

      String json = "[{\"type\":\"form\",\"tags\":[],\"owner\":null,\"components\":[{\"type\":\"email\",\"persistent\":true,\"unique\":false,\"protected\":false,\"defaultValue\":\"\",\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your email address\",\"key\":\"email\",\"lockKey\":true,\"label\":\"Email\",\"inputType\":\"email\",\"tableView\":true,\"input\":true},{\"type\":\"password\",\"persistent\":true,\"protected\":true,\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your password.\",\"key\":\"password\",\"lockKey\":true,\"label\":\"Password\",\"inputType\":\"password\",\"tableView\":false,\"input\":true},{\"type\":\"button\",\"theme\":\"primary\",\"disableOnInvalid\":true,\"action\":\"submit\",\"block\":false,\"rightIcon\":\"\",\"leftIcon\":\"\",\"size\":\"md\",\"key\":\"submit\",\"tableView\":false,\"label\":\"Submit\",\"input\":true}],\"_id\":\"21eba722bb6a11e986acc607f9317e4e\",\"title\":\"User Login\",\"name\":\"userLogin\",\"path\":\"user/login\",\"access\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"read_all\"}],\"submissionAccess\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"create_own\"}],\"machineName\":\"userLogin\",\"created\":\"2018-08-13T20:00:50.524Z\",\"modified\":\"2018-08-13T20:00:50.529Z\"}]";
      return Response.ok(json, MediaType.APPLICATION_JSON).build();

    }
  }

  @GET @Path("form/{formid}")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getFormById(
      @PathParam("formid") String formId

  )
  {
    //    String formUser = "{\"type\":\"form\",\"tags\":[],\"owner\":null,\"components\":[{\"type\":\"email\",\"persistent\":true,\"unique\":false,\"protected\":false,\"defaultValue\":\"\",\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your email address\",\"key\":\"email\",\"lockKey\":true,\"label\":\"Email\",\"inputType\":\"email\",\"tableView\":true,\"input\":true},{\"type\":\"password\",\"persistent\":true,\"protected\":true,\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your password.\",\"key\":\"password\",\"lockKey\":true,\"label\":\"Password\",\"inputType\":\"password\",\"tableView\":false,\"input\":true},{\"type\":\"button\",\"theme\":\"primary\",\"disableOnInvalid\":true,\"action\":\"submit\",\"block\":false,\"rightIcon\":\"\",\"leftIcon\":\"\",\"size\":\"md\",\"key\":\"submit\",\"tableView\":false,\"label\":\"Submit\",\"input\":true}],\"_id\":\"21eba722bb6a11e986acc607f9317e4e\",\"title\":\"User Login\",\"name\":\"userLogin\",\"path\":\"user/login\",\"access\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"read_all\"}],\"submissionAccess\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"create_own\"}],\"machineName\":\"userLogin\",\"created\":\"2018-08-13T20:00:50.524Z\",\"modified\":\"2018-08-13T20:00:50.529Z\"}";

    GremlinQuery query = new GremlinQuery();
    query.gremlin = "g.V().has('Object.Form.Metadata_GUID', eq(formId)).values('Object.Form.Text');";
    query.bindings = new HashMap<>();
    query.bindings.put("formId", formId);

    JsonObject b = getQueryResultAsJson(query);


    return Response.ok(b.toString(), MediaType.APPLICATION_JSON).build();

  }

  @GET @Path("form/{formid}/submission")
  @Produces(MediaType.APPLICATION_JSON)
  //  @RolesAllowed({ "pontus-role","offline_access" })
  public Response getFormByIdSubmission(
      @PathParam("formid") String formId

  )
  {
    String formUser = "{\"type\":\"form\",\"tags\":[],\"owner\":null,\"components\":[{\"type\":\"email\",\"persistent\":true,\"unique\":false,\"protected\":false,\"defaultValue\":\"\",\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your email address\",\"key\":\"email\",\"lockKey\":true,\"label\":\"Email\",\"inputType\":\"email\",\"tableView\":true,\"input\":true},{\"type\":\"password\",\"persistent\":true,\"protected\":true,\"suffix\":\"\",\"prefix\":\"\",\"placeholder\":\"Enter your password.\",\"key\":\"password\",\"lockKey\":true,\"label\":\"Password\",\"inputType\":\"password\",\"tableView\":false,\"input\":true},{\"type\":\"button\",\"theme\":\"primary\",\"disableOnInvalid\":true,\"action\":\"submit\",\"block\":false,\"rightIcon\":\"\",\"leftIcon\":\"\",\"size\":\"md\",\"key\":\"submit\",\"tableView\":false,\"label\":\"Submit\",\"input\":true}],\"_id\":\"21eba722bb6a11e986acc607f9317e4e\",\"title\":\"User Login\",\"name\":\"userLogin\",\"path\":\"user/login\",\"access\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"read_all\"}],\"submissionAccess\":[{\"roles\":[\"5b71e372739d8b5563ba1f9d\"],\"type\":\"create_own\"}],\"machineName\":\"userLogin\",\"created\":\"2018-08-13T20:00:50.524Z\",\"modified\":\"2018-08-13T20:00:50.529Z\"}";

    return Response.ok(formUser, MediaType.APPLICATION_JSON).build();

  }

  @GET @Path("vertex_prop_values") @Produces(MediaType.APPLICATION_JSON)
  public FormioSelectResults getVertexPropertyValues(
      @QueryParam("search") String search
      , @QueryParam("limit") Long limit
      , @QueryParam("skip") Long skip

  )
  {
    final String bizCtx = "BizCtx";

    final AtomicBoolean matches = new AtomicBoolean(false);

    keycloakSecurityContext.getAuthorizationContext().getPermissions().forEach(perm -> {
          Map<String, Set<String>> claims = perm.getClaims();
          if (claims != null)
          {
            claims.forEach((s, strings) -> {
                  if (bizCtx.equals(s))
                  {
                    strings.forEach(allowedVal -> {
                      Pattern patt = compiledPatterns.computeIfAbsent(allowedVal, Pattern::compile);
                      matches.set(patt.matcher(search).matches());

                    });
                  }
                }
            );
          }
        }
    );

    if (matches.get())
    {

      if (limit == null)
      {
        limit = 100L;
      }

      if (skip == null)
      {
        skip = 0L;
      }

      //    Client client = ClientFactory.newClient();
      //    WebTarget target = client.target("http://localhost:8080/howtodoinjava");
      //    Form form = new Form().param("customer", "Bill").param("product", "book");
      //    Response response = target.request().post(Entity.form(form));

      GremlinQuery query = new GremlinQuery();

      JsonObjectBuilder b = Json.createObjectBuilder();
      b.add("limit", limit);
      b.add("skip", skip);
      b.add("search", search);

      query.bindings = new HashMap<>();
      query.bindings.put("skip", skip);
      query.bindings.put("limit", limit);
      query.bindings.put("search", search);

      query.gremlin =
          "g.V()\n"
              + " .has(search, neq(''))\n"
              + " .limit(limit + skip)\n"
              + " .skip(skip)\n"
              + " .as('matches')\n"
              + " .match(\n"
              + "     __.as('matches').values(search).as('val')\n"
              + "   , __.as('matches').id().as('id')\n"
              + " )\n"
              + " .select('id', 'val')\n"
              + " .toList()";

      Response result = gremlinService.runQuery(query);

      if (result.getStatus() == 200)
      {
        JsonObject obj = Json.createReader(new StringReader(result.getEntity().toString())).readObject();

        List<Map<String, Object>> querRes = new ArrayList<>();

        List<ReactSelectOptions> selectOptions = new ArrayList<>(querRes.size());

        for (Map<String, Object> res : querRes)
        {
          selectOptions.add(new ReactSelectOptions(res.get("val").toString(), res.get("id").toString()));
        }

        FormioSelectResults retVal = new FormioSelectResults(selectOptions);

        return retVal;
      }

    }

    return new FormioSelectResults();

  }

  private String getRoles()
  {
    String keycloakBaseUrl = "";
    String username= "";
    String password= "";

    Keycloak keycloak = Keycloak.getInstance(
        keycloakBaseUrl,
        "master",
        username,
        password,
        "admin-cli");




    keycloak.realm("pontus").groups().groups().forEach(group ->
    {
//      group.
    });

//    RolesRepresentation representation = keycloakSecurityContext.getAuthorizationContext().
    return null;

  }



  private String getOwner()
  {
    AccessToken token = keycloakSecurityContext.getToken();
//    token.getPreferredUsername();
    return token.getId();
  }

  public class User
  {

    private final String userName;

    User(KeycloakSecurityContext securityContext)
    {
      this.userName = securityContext.getToken().getPreferredUsername();
    }

    public String getUserName()
    {
      return userName;
    }
  }
}