package com.howtographql.hackernews;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

/**
 * Class containing custom scalars 
 */
public final class Scalars {
    
    public static final GraphQLScalarType dateTime;
    
    static {
    	final Coercing<Object, Object> coercing = new Coercing<Object, Object>() {
    		
            @Override
            public String serialize(final Object input) {
                return ((ZonedDateTime)input).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }

            @Override
            public Object parseValue(final Object input) {
                return serialize(input);
            }

            @Override
            public ZonedDateTime parseLiteral(final Object input) {
                if (input instanceof StringValue) {
                    return ZonedDateTime.parse(((StringValue) input).getValue());
                } else if (input instanceof IntValue) {
                    return Instant.ofEpochMilli(((IntValue) input).getValue().longValue()).atZone(ZoneOffset.UTC);
                } else {
                    return null;
                }
            }
        };
        
		dateTime = new GraphQLScalarType("DateTime", "DataTime scalar", coercing);
    	
    }

	private Scalars() {
		// Util class best practice.
	}
    
    
}
