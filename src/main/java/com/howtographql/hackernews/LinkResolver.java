package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains Link sub-queries
 */
public class LinkResolver implements GraphQLResolver<Link> {
    
    private final UserRepository userRepository;

    LinkResolver(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User postedBy(final Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }
}
