package org.dao;

import java.util.List;

import org.entity.Tag;
import org.mongodb.morphia.query.Query;

public class TagDAO extends AbstractDAO<Tag> {
	private static final TagDAO instance = new TagDAO();

	private TagDAO() {
		datastore.ensureIndexes(Tag.class);
	}

	public static TagDAO getInstance() {
		return instance;
	}

	public void save(List<Tag> tags) throws DAOException {
		try {
			datastore.save(tags);
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
	
	public List<Tag> list(String tag) throws DAOException {
		try {
			Query<Tag> query = datastore.createQuery(Tag.class);
			query.field("normalizeTag").contains(tag).order("-popularPoint").limit(10);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
