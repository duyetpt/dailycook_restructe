package org.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.DCAUtilsException;
import org.entity.Meal;
import org.mongodb.morphia.query.Query;

import com.mongodb.DuplicateKeyException;

public class MealDAO extends AbstractDAO<Meal> {

	private static final MealDAO instance = new MealDAO();

	private MealDAO() {
		datastore.ensureIndexes(Meal.class);
	}

	public static MealDAO getInstance() {
		return instance;
	}

	public void addRecipeToMeal(String day, String time, String recipeId,
			String userId) throws DCAUtilsException {
		try {
			Query<Meal> query = datastore.createQuery(Meal.class)
					.filter("day", day).filter("time", time);
			Meal existedMeal = query.get();
			if (existedMeal == null) {
				Meal meal = new Meal();
				meal.setDay(day);
				meal.setTime(time);
				meal.setUserId(userId);
				Set<String> recipeIds = new HashSet<>();
				recipeIds.add(recipeId);
				meal.setRecipeIds(recipeIds);
				StringBuilder sb = new StringBuilder();
				sb.append(userId).append("_").append(day).append("_")
						.append(time);
				meal.setIndex(sb.toString());
				try {
					save(meal);
				} catch (DuplicateKeyException ex) {
					throw new ExistedDataException("Meal duplication key"
							+ meal.getIndex());
				}
			} else {
				pushToArray(existedMeal.getId(), "recipeIds", recipeId,
						Meal.class);
			}
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public void removeRecipeToMeal(String mealId, String recipeId)
			throws DAOException {
		try {
			pullToArray(mealId, "recipeIds", recipeId, Meal.class);
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public List<Meal> getPlanMeal(String userId) throws DAOException {
		try {
			Query<Meal> query = datastore.createQuery(Meal.class)
					.field("userId").equal(userId);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
