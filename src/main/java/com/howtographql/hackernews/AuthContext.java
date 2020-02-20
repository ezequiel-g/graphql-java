package com.howtographql.hackernews;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.servlet.GraphQLContext;

/**
 * Objects of this class are intended to be used as context during operation execution.
 * Can be used to obtain the currently logged-in user.
 */
public class AuthContext extends GraphQLContext {
    
    private final User user;
    
    public AuthContext(final User user, final Optional<HttpServletRequest> request, final Optional<HttpServletResponse> response) {
        super(request, response);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
