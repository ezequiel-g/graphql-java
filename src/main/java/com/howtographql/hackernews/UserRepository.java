package com.howtographql.hackernews;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

/**
 * Manages user persistence
 */
public class UserRepository {

	private static final String UUID = "_id";
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	
	private final MongoCollection<Document> users;

    public UserRepository(final MongoCollection<Document> users) {
        this.users = users;
    }
    
    public User findByEmail(final String email) {
        final Document doc = users.find(eq(EMAIL, email)).first();
        return user(doc);
    }

    public User findById(final String id) {
        final Document doc = users.find(eq(UUID, new ObjectId(id))).first();
        return user(doc);
    }
    
    public User saveUser(final User user) {
        final Document doc = new Document();
        doc.append(NAME, user.getName());
        doc.append(EMAIL, user.getEmail());
        doc.append(PASSWORD, user.getPassword());
        users.insertOne(doc);
        
        return new User(
                doc.get(UUID).toString(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }
    
    private User user(final Document doc) {
        return new User(
                doc.get(UUID).toString(),
                doc.getString(NAME),
                doc.getString(EMAIL),
                doc.getString(PASSWORD));
    }
}
