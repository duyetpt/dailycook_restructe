package org.dao;

import java.util.List;

import org.entity.Ingredient;
import org.mongodb.morphia.query.Query;

public class IngredientDAO extends AbstractDAO<Ingredient> {

	private static final IngredientDAO instance = new IngredientDAO();

	private IngredientDAO() {
		datastore.ensureIndexes(Ingredient.class);
	}

	public static IngredientDAO getInstance() {
		return instance;
	}

	public void save(List<Ingredient> ingredients) throws DAOException {
		try {
			datastore.save(ingredients);
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public List<Ingredient> list(String ingredient) throws DAOException {
		try {
			Query<Ingredient> query = datastore.createQuery(Ingredient.class);
			query.field("normalize_name").contains(ingredient).order("-popularPoint").limit(10);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
