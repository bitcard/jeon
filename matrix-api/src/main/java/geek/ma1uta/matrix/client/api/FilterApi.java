package geek.ma1uta.matrix.client.api;

import geek.ma1uta.matrix.client.model.filter.FilterData;
import geek.ma1uta.matrix.client.model.filter.FilterResponse;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/_matrix/client/r0/user")
@JsonRest
public interface FilterApi {

    @POST
    @Path("/{userId}/filter")
    FilterResponse uploadFilter(@PathParam("userId") String userId, FilterData filterData);

    @GET
    @Path("/{userId}/filter/{filterId}")
    FilterData getFilter(@PathParam("userId") String userId, @PathParam("filterId") String filterId);
}