package com.howtographql.hackernews;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * Manages link persistence
 */
public class LinkRepository {
    
    private static final String POSTED_BY = "postedBy";
	private static final String DESCRIPTION = "description";
	private static final String URL = "url";
	private static final String UUID = "_id";
	private final MongoCollection<Document> links;

    LinkRepository(final MongoCollection<Document> links) {
        this.links = links;
    }

    public Link findById(final String id) {
        final Document doc = links.find(eq(UUID, new ObjectId(id))).first();
        return link(doc);
    }
    
    public List<Link> getAllLinks(final LinkFilter filter, final int skip, final int first) {
        final Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);
        
        final FindIterable<Document> documents = mongoFilter.map(links::find).orElseGet(links::find);
        
        final List<Link> allLinks = new ArrayList<>();
        for (final Document doc : documents.skip(skip).limit(first)) {
            allLinks.add(link(doc));
        }
        return allLinks;
    }
    
    public void saveLink(final Link link) {
        final Document doc = new Document();
        doc.append(URL, link.getUrl());
        doc.append(DESCRIPTION, link.getDescription());
        doc.append(POSTED_BY, link.getUserId());
        links.insertOne(doc);
    }

    private Bson buildFilter(final LinkFilter filter) {
        final String descriptionPattern = filter.getDescriptionContains();
        final String urlPattern = filter.getUrlContains();
        Bson descriptionCondition = null;
        Bson urlCondition = null;
        
        if (descriptionPattern != null && !descriptionPattern.isEmpty()) {
            descriptionCondition = regex(DESCRIPTION, ".*" + descriptionPattern + ".*", "i");
        }
        if (urlPattern != null && !urlPattern.isEmpty()) {
            urlCondition = regex(URL, ".*" + urlPattern + ".*", "i");
        }
        if (descriptionCondition != null && urlCondition != null) {
            return and(descriptionCondition, urlCondition);
        }
        
        return descriptionCondition != null ? descriptionCondition : urlCondition;
    }
    
    private Link link(final Document doc) {
        return new Link(
                doc.get(UUID).toString(),
                doc.getString(URL),
                doc.getString(DESCRIPTION),
                doc.getString(POSTED_BY));
    }
}
