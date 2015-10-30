package com.vn.dailycookapp.service.recipeservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.entity.Recipe;
import org.entity.Recipe.Ingredient;
import org.entity.Recipe.Step;
import org.json.JSONObject;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

/**
 * Create a recipe
 * create some ingredients
 * create some step
 * add some category
 * 
 * @author duyetpt
 *
 */
public class AddRecipeTest extends AbstractTest {
	
	@Test
	@SuppressWarnings("unused")
	public void test() {
		Recipe recipe = new Recipe();
		recipe.setIntervalCook(20);
		recipe.setTitle("Mon mi xao");
		recipe.setPictureUrl("http://www.wn.com.vn/product_images/uploaded_images/cach-rang-com-ngon-8-.jpg");
		recipe.setStory("Toi da hoc duoc no, khi di du lich o Italia. NGON, DEP VA BO DUONG");
		// add ingredients
		List<Ingredient> ingredients = new ArrayList<>();
		{
			Ingredient indient1 = new Ingredient();
			indient1.setGroup("test");
			indient1.setName("thit bo");
			indient1.setQuantity("1321");
			indient1.setUnit("gram");
			ingredients.add(indient1);
		}
		{
			Ingredient indient1 = new Ingredient();
			indient1.setGroup("test");
			indient1.setName("com thoi");
			indient1.setQuantity("23");
			indient1.setUnit("bat");
			ingredients.add(indient1);
		}
		{
			Ingredient indient1 = new Ingredient();
			indient1.setGroup("test");
			indient1.setName("dua chua");
			indient1.setQuantity("33");
			indient1.setUnit("gram");
			ingredients.add(indient1);
		}
		
		recipe.setIngredients(ingredients);
		
		// add steps
		List<Step> steps = new ArrayList<>();
		{
			Step step = new Step();
			step.setDescription("Bam thi bo");
			step.setStepNo(1);
			step.setPictureUrl("http://yeunoitro.net/wp-content/uploads/2015/03/thit-bo-thai-mieng.jpg");
			steps.add(step);
		}
		{
			Step step = new Step();
			step.setDescription("Rua rua => giam bot do chua");
			step.setStepNo(1);
			step.setPictureUrl("http://media.tinmoi.vn/2015/01/08/an-dua-ca-muoi.jpg");
			steps.add(step);
		}
		{
			Step step = new Step();
			step.setDescription("Rang com");
			step.setStepNo(1);
			steps.add(step);
		}
		
		recipe.setSteps(steps);
		List<String> tags = new ArrayList<String>();
		tags.add("com rang");
		tags.add("bo");
		
		recipe.setCategoryIds(tags);
		
		// request create recipe
		getToken();
		String userId = "560b3f83f128c211acc9eff5";
//		String recipeInfo = JsonTransformer.getInstance().marshall(recipe);
		String recipeInfo = "{\"ingredients\" : [ { \"unit\" : \"gram\", \"name\" : \"thit bo\", \"quantity\" : \"100\" , \"group\" : \"test\" }, { \"unit\" : \"bat\", \"name\" : \"com thoi\", \"quantity\" : \"100\", \"group\" : \"test\" }, { \"unit\" : \"gram\", \"name\" : \"dua chua\", \"quantity\" : \"100\", \"group\" : \"test\" } ], \"story\" : \"Toi da hoc duoc no, khi di du lich o Italia. NGON, DEP VA BO DUONG\", \"title\" : \"Mon mi xao\", \"categoryIds\" : [ \"com rang\", \"bo\" ], \"pictureUrl\" : \"http://www.wn.com.vn/product_images/uploaded_images/cach-rang-com-ngon-8-.jpg\", \"intervalCook\" : 20, \"steps\" : [ { \"picture_url\" : \"http://yeunoitro.net/wp-content/uploads/2015/03/thit-bo-thai-mieng.jpg\", \"description\" : \"Bam thi bo\", \"stepNo\" : 1 }, { \"picture_url\" : \"http://media.tinmoi.vn/2015/01/08/an-dua-ca-muoi.jpg\", \"description\" : \"Rua rua => giam bot do chua\", \"stepNo\" : 1 }, { \"description\" : \"Rang com\", \"stepNo\" : 1 } ] }";
		
		Entity<String> entity = Entity.entity(recipeInfo, MediaType.APPLICATION_JSON_TYPE);
		responseData = target("dailycook/recipe/add").request().header(HeaderField.USER_ID, userId)
				.post(entity, String.class);
		
		JSONObject responseObj = getResponse();
	}
	
}
