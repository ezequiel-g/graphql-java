package com.howtographql.hackernews;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

/**
 * Query root. Contains top-level queries.
 */
class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Query(final LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks(final LinkFilter filter, final Number skip, final Number first) {
        return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
    }

}
