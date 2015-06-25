/*
 * Copyright 2013 Graham Smith
 */
package unnatkrushipranali.model;

/**
 * This class is a lightweight representation of the underlying entity type. The
 * label is the name of either the {@code Department} or the {@code Person}. As
 * can be seen it's not bound to the name in the underlying POJO which would be
 * ideal so the application would have to deal with any data model changes
 * bubbling up from the data model.
 * <p>
 * In this demonstration application it wouldn't be a problem to hold the actual
 * {@code Department} and {@code Person} objects in here as they are small and
 * there's very few of them. In a real application however the tree structure is
 * likely to be large and it could end up consuming a significant amount of
 * memory if the actual objects are held. By removing the actual objects and
 * just holding what is necessary for display in the tree significant savings
 * are made.
 * <p>
 * While this system will allow for large trees very large trees will probably
 * need to use this mechanism and lazy loading but that's for another example.
 *
 * @author Graham Smith
 */
public class EntityProxy {

	private int id;
	private String label;
	private EntityProxyType type;

        public EntityProxy(String label){
            this.label=label;
        }
	public EntityProxy(int id, EntityProxyType type, String label) {
		this.id = id;
		this.type = type;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public EntityProxyType getType() {
		return type;
	}

	public void setType(EntityProxyType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getLabel();
	}
}
