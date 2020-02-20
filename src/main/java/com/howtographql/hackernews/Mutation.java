package com.howtographql.hackernews;

import java.time.Instant;
import java.time.ZoneOffset;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

/**
 * Mutation root
 */
public class Mutation implements GraphQLRootResolver {
    
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(final LinkRepository linkRepository, final UserRepository userRepository, final VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public Link createLink(final String url, final String description, final DataFetchingEnvironment env) {
        final AuthContext context = env.getContext();
        final Link newLink = new Link(url, description, context.getUser().getId());
        linkRepository.saveLink(newLink);
        
        return newLink;
    }
    
    public User createUser(final String name, final AuthData auth) {
        final User newUser = new User(name, auth.getEmail(), auth.getPassword());
        
        return userRepository.saveUser(newUser);
    }

    public SigninPayload signinUser(final AuthData auth) {
        final User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
    
    public Vote createVote(final String linkId, final String userId) {
        return voteRepository.saveVote(new Vote(Instant.now().atZone(ZoneOffset.UTC), userId, linkId));
    }
}
