package com.howtographql.hackernews;

import static com.mongodb.client.model.Filters.eq;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

/**
 * Manages vote persistence
 */
public class VoteRepository {

    private static final String UUID = "_id";
    private static final String USER_ID = "userId";
    private static final String LINK_ID = "linkId";
    private static final String CREATED_AT = "createdAt";
    
	private final MongoCollection<Document> votes;

    VoteRepository(final MongoCollection<Document> votes) {
        this.votes = votes;
    }

    public List<Vote> findByUserId(final String userId) {
        final List<Vote> list = new ArrayList<>();
        for (final Document doc : votes.find(eq(USER_ID, userId))) {
            list.add(vote(doc));
        }
        return list;
    }

    public List<Vote> findByLinkId(final String linkId) {
        final List<Vote> list = new ArrayList<>();
        for (final Document doc : votes.find(eq(LINK_ID, linkId))) {
            list.add(vote(doc));
        }
        return list;
    }

    public Vote saveVote(Vote vote) {
        final Document doc = new Document();
        doc.append(USER_ID, vote.getUserId());
        doc.append(LINK_ID, vote.getLinkId());
        doc.append(CREATED_AT, Scalars.dateTime.getCoercing().serialize(vote.getCreatedAt()));
        votes.insertOne(doc);
        return new Vote(
                doc.get(UUID).toString(),
                vote.getCreatedAt(),
                vote.getUserId(),
                vote.getLinkId());
    }
    
    private Vote vote(final Document doc) {
        return new Vote(
                doc.get(UUID).toString(),
                ZonedDateTime.parse(doc.getString(CREATED_AT)),
                doc.getString(USER_ID),
                doc.getString(LINK_ID)
        );
    }
}
