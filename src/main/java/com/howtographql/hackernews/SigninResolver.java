package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains SigninPayload sub-queries
 */
public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(final SigninPayload payload) {
        return payload.getUser();
    }
}
