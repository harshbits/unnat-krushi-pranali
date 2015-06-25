/*
 * Copyright 2013 Graham Smith
 */
package unnatkrushipranali.model;

/**
 * Simply indicates the type of the node in the tree so that it can be recovered
 * from the database. There are probably cleaner ways to handle this but in a
 * simple application like this the KISS (Keep It Simple, Stupid) principle
 * applies.
 *
 * @author Graham Smith
 */
public enum EntityProxyType {

	CLUSTERNODE, SENSORNODE, SENSORLIST,SOLENOIDVALVE, FARMER, FARM, FIELD, CROP;
}
