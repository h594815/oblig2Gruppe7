package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		// See ClientSession class
		clients.put(user,new ClientSession(user,connection));
		
		Logger.log("Client session added for user: " + user);
	}

	public void removeClientSession(String user) {

		// TODO: disconnet the client (user) 
		// and remove client session for user from the storage
		clients.remove(user).disconnect();
		
		Logger.log("Client session removed for: " + user);
	}

	public void createTopic(String topic) {
		
		subscriptions.put(topic,ConcurrentHashMap.newKeySet());

		// TODO: create topic in the storage

		Logger.log("created topic" + topic);
	
	}

	public void deleteTopic(String topic) {

		subscriptions.remove(topic);
		
		Logger.log("deleted topic:" + topic);

		
		
	}

	public void addSubscriber(String user, String topic) {

		subscriptions.get(topic).add(user);
		Logger.log("Added user: " + user + " to topic: " + topic);
		// TODO: add the user as subscriber to the topic
		
	}

	public void removeSubscriber(String user, String topic) {

		subscriptions.get(topic).remove(user);
		Logger.log("removed user: " + user + " from topic: " + topic);

		
	}
}
