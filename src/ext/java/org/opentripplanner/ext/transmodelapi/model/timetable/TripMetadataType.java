package org.opentripplanner.ext.transmodelapi.model.timetable;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import org.opentripplanner.ext.transmodelapi.support.GqlUtil;
import org.opentripplanner.routing.api.response.TripSearchMetadata;

public class TripMetadataType {

  private TripMetadataType() {}

  public static GraphQLObjectType create(GqlUtil gqlUtil) {
    return GraphQLObjectType
        .newObject()
        .name("TripSearchData")
        .description("Trips search metadata.")
        .field(GraphQLFieldDefinition
            .newFieldDefinition()
            .name("searchWindowUsed")
            .description("The search-window used in the current trip request. Use the value in "
                + "the next request and set the request 'dateTime' to "
                + "'nextDateTime' or 'prevDateTime' to get the previous/next "
                + "\"window\" of results. No duplicate trips should be returned, "
                + "unless a trip is delayed and new realtime-data is available." + "Unit: minutes.")
            .deprecate("Use pageCursor instead")
            .type(new GraphQLNonNull(Scalars.GraphQLInt))
            .dataFetcher(e -> ((TripSearchMetadata) e.getSource()).searchWindowUsed.toMinutes())
            .build())
        .field(GraphQLFieldDefinition
            .newFieldDefinition()
            .name("nextDateTime")
            .description("This is the suggested search time for the \"next page\" or time "
                + "window. Insert it together with the 'searchWindowUsed' in the "
                + "request to get a new set of trips following in the time-window "
                + "AFTER the current search.")
            .deprecate("Use pageCursor instead")
            .type(gqlUtil.dateTimeScalar)
            .dataFetcher(e -> ((TripSearchMetadata) e.getSource()).nextDateTime.toEpochMilli())
            .build())
        .field(GraphQLFieldDefinition
            .newFieldDefinition()
            .name("prevDateTime")
            .description("This is the suggested search time for the \"previous page\" or "
                + "time-window. Insert it together with the 'searchWindowUsed' in "
                + "the request to get a new set of trips preceding in the "
                + "time-window BEFORE the current search.")
            .deprecate("Use pageCursor instead")
            .type(gqlUtil.dateTimeScalar)
            .dataFetcher(e -> ((TripSearchMetadata) e.getSource()).prevDateTime.toEpochMilli())
            .build())
        .build();
  }
}
