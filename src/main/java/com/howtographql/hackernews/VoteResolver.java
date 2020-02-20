package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains vote sub-queries
 */
public class VoteResolver implements GraphQLResolver<Vote> {
    
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    VoteResolver(final LinkRepository linkRepository, final UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public User user(final Vote vote) {
        return userRepository.findById(vote.getUserId());
    }
    
    public Link link(final Vote vote) {
        return linkRepository.findById(vote.getLinkId());
    }
}
