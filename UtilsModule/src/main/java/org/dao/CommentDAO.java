package org.dao;

import java.util.List;

import org.entity.Comment;
import org.mongodb.morphia.query.Query;

public class CommentDAO extends AbstractDAO<Comment> {

    private static final CommentDAO instance = new CommentDAO();

    private CommentDAO() {
        datastore.ensureIndexes(Comment.class);
    }

    public static CommentDAO getInstance() {
        return instance;
    }

    public List<Comment> list(String recipeId, int skip, int take) throws DAOException {
        List<Comment> comments = null;
        try {
            Query<Comment> query = datastore.createQuery(Comment.class).field("recipe_id").equal(recipeId).order("-create_time").offset(skip)
                    .limit(take);
            comments = query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }

        return comments;
    }

    public int getTotalNumber(String recipeId) throws DAOException {
        return (int) datastore.createQuery(Comment.class).field("recipe_id").equal(recipeId).countAll();
    }

    public void removeAllCommentOfRecipe(String recipeId) throws DAOException {
        try {
            Query<Comment> query = datastore.createQuery(Comment.class);
            query.filter("recipe_id", recipeId);
            
            datastore.delete(query);
        } catch (Exception ex) {
            logger.error("remove comment error", ex);
            throw new DAOException();
        }
    }
}
