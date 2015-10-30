package com.vn.dailycookapp.service.recipeservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.entity.Recipe;
import org.entity.Recipe.Ingredient;
import org.entity.Recipe.Step;
import org.json.JSONObject;
import org.json.JsonTransformer;
import org.junit.Test;

import com.vn.dailycookapp.AbstractTest;
import com.vn.dailycookapp.service.HeaderField;

public class SearchRecipeTest extends AbstractTest {
	
	@Test
	public void searchWithTagTest() {
		{
			List<String> tags = new ArrayList<String>();
			tags.add("com rang");
			tags.add("bo");
			addRecipe(tags);
		}
		
		{
			List<String> tags = new ArrayList<String>();
			tags.add("ca nau");
			tags.add("mang");
			addRecipe(tags);
		}
		
		responseData = target("dailycook/recipe/search").queryParam("filter", "tags")
				.queryParam("keyword", "ca,mang").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println(responseData);
		@SuppressWarnings("unused")
		JSONObject responseObj = getResponse();
	}
	
	@Test
	public void searchWithName() {
		{
			List<String> tags = new ArrayList<String>();
			tags.add("com rang");
			tags.add("bo");
			addRecipe(tags);
		}
		
		{
			List<String> tags = new ArrayList<String>();
			tags.add("ca nau");
			tags.add("mang");
			addRecipe(tags);
		}
		
		responseData = target("dailycook/recipe/search").queryParam("filter", "name")
				.queryParam("keyword", "mon mi xao").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println(responseData);
		@SuppressWarnings("unused")
		JSONObject responseObj = getResponse();
	}
	
	@Test
	public void searchWithIngredientTest() {
		{
			List<String> tags = new ArrayList<String>();
			tags.add("com rang");
			tags.add("bo");
			addRecipe(tags);
		}
		
		{
			List<String> tags = new ArrayList<String>();
			tags.add("ca nau");
			tags.add("mang");
			addRecipe(tags);
		}
		
		responseData = target("dailycook/recipe/search").queryParam("filter", "ingredients")
				.queryParam("keyword", "thit bo,dua chua").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		System.out.println(responseData);
		@SuppressWarnings("unused")
		JSONObject responseObj = getResponse();
	}
	public void addRecipe(List<String> tags) {
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
		recipe.setCategoryIds(tags);
		
		// request create recipe
		getToken();
		String userId = "560b3f83f128c211acc9eff5";
		String recipeInfo = JsonTransformer.getInstance().marshall(recipe);
		
		Entity<String> entity = Entity.entity(recipeInfo, MediaType.APPLICATION_JSON_TYPE);
		responseData = target("dailycook/recipe/add").request().header(HeaderField.USER_ID, userId)
				.post(entity, String.class);
		
		getResponse();
	}
}
