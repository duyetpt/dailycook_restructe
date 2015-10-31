package org.dao;

import java.util.List;

import org.Unicode;
import org.entity.Ingredient;
import org.mongodb.morphia.query.Criteria;
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

	public List<Ingredient> list(String ingredient, int skip, int take)
			throws DAOException {
		try {
			Query<Ingredient> query = datastore.createQuery(Ingredient.class);
			query.field("normalize_name").contains(Unicode.toAscii(ingredient))
					.order("-popularPoint").limit(take).offset(skip);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public List<Ingredient> list(List<String> ingredients) throws DAOException {
		try {
			Query<Ingredient> query = datastore.createQuery(Ingredient.class);
			Criteria[] criterias = new Criteria[ingredients.size()];
			for (int i = 0; i < ingredients.size(); i++) {
				String ingredient = ingredients.get(i);
				criterias[i] = query.criteria("normalize_name").contains(
						Unicode.toAscii(ingredient));
			}
			query.or(criterias);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
