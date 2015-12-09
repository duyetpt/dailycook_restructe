package org.dao;

import java.util.Iterator;
import java.util.List;

import org.Unicode;
import org.bson.types.ObjectId;
import org.entity.Tag;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Sort;
import org.mongodb.morphia.query.Criteria;
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
            Query<Tag> query = datastore.createQuery(Tag.class);
            Criteria[] criterials = new Criteria[tags.size()];
            for (int i = 0; i < tags.size(); i++) {
                Tag tag = tags.get(i);
                criterials[i] = query.criteria("normalizeTag").equal(
                        tag.getNormalizeTag());
            }
            query.or(criterials);
            List<Tag> list = query.asList();
            for (Tag tag : tags) {
                for (Tag t : list) {
                    // if tag exist increase number tagged
                    if (t.getNormalizeTag().equals(tag.getNormalizeTag())) {
                        tag.setId(t.getId());
                        increaseNumberTagged(t.getId());
                    }
                }
                // if tag is not exist save tag
                if (tag.getId() == null) {
                    tag.setNumberTaged(1);
                    datastore.save(tag);
                }
            }

        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public List<Tag> list(String tag, int skip, int take) throws DAOException {
        try {
            Query<Tag> query = datastore.createQuery(Tag.class);
            query.field("normalizeTag").contains(Unicode.toAscii(tag)).order("-popularPoint")
                    .limit(take).offset(skip);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public List<Tag> list(List<String> tags) throws DAOException {
        try {
            Query<Tag> query = datastore.createQuery(Tag.class);
            Criteria[] criterias = new Criteria[tags.size()];
            for (int i = 0; i < tags.size(); i++) {
                String tag = tags.get(i);
                criterias[i] = query.criteria("normalizeTag").contains(
                        Unicode.toAscii(tag));
            }
            query.or(criterias);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public Iterator<Tag> getTop(int top) throws DAOException {
        try {
            AggregationPipeline aggregation = datastore.createAggregation(Tag.class);
            Iterator<Tag> list = aggregation.sort(new Sort("numberTaged", -1)).limit(top).aggregate(Tag.class);
            return list;
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public void increaseNumberTagged(ObjectId tagId) throws DAOException {
        increaseForField(tagId.toHexString(), 1, Tag.class, "numberTaged");
    }
}
