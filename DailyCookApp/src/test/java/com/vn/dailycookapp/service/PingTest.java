package com.vn.dailycookapp.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

import org.entity.Recipe;
import org.entity.Recipe.Ingredient;
import org.entity.Recipe.Step;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.json.JsonTransformer;
import org.junit.Test;

public class PingTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(PingService.class);
	}
	
	@Test
	public void test() {
		final String helloYou = target("dailycook/ping").request().get(String.class);
		assertNotNull(helloYou);
	}
	
	public static void main(String[] args) {
		Recipe recipe = new Recipe();
		recipe.setOwner("560b3f83f128c211acc9eff5");
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
			indient1.setQuantity("100");
			indient1.setUnit("gram");
			ingredients.add(indient1);
		}
		{
			Ingredient indient1 = new Ingredient();
			indient1.setGroup("test");
			indient1.setName("com thoi");
			indient1.setQuantity("234");
			indient1.setUnit("bat");
			ingredients.add(indient1);
		}
		{
			Ingredient indient1 = new Ingredient();
			indient1.setGroup("test");
			indient1.setName("dua chua");
			indient1.setQuantity("234");
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
		
		String recipeInfo = JsonTransformer.getInstance().marshall(recipe);
		System.out.println(recipeInfo);
	}
}
