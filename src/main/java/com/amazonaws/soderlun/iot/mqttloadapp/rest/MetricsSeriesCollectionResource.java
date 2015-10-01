package com.amazonaws.soderlun.iot.mqttloadapp.rest;

import com.amazonaws.soderlun.iot.mqttloadapp.runtime.MetricsSeriesRuntimeRegistry;
import com.amazonaws.soderlun.iot.mqttloadapp.runtime.RunningMetricsSeries;
import com.amazonaws.soderlun.iot.mqttloadapp.rest.MetricsSeriesResource;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author soderlun
 */
@Path("/series")
public class MetricsSeriesCollectionResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MetricsSeriesCollectionResource
     */
    public MetricsSeriesCollectionResource() {
    }

    /**
     * Retrieves representation of an instance of com.amazonaws.soderlun.iot.mqttloadapp.MetricsSeriesCollectionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        List<RunningMetricsSeries> configs = MetricsSeriesRuntimeRegistry.getInstance().getAllRunning();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (RunningMetricsSeries rms : configs) {
            builder.add(rms.toJsonObject());
        }
        
        StringWriter writer = new StringWriter();

        try (JsonWriter jw = Json.createWriter(writer)) {
            jw.writeArray(builder.build());
        }

        return writer.toString();        
    }



    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MetricsSeriesResource getMetricsSeriesResource(@PathParam("id") String id) {
        return MetricsSeriesResource.getInstance(id);
    }
}
