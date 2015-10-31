package org.dao;

import java.util.List;

import org.entity.Ingredient;

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
}
