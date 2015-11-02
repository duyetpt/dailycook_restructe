package com.vn.dailycookapp.restmodel.model;

import org.dao.CommentDAO;
import org.dao.RecipeDAO;
import org.dao.UserDAO;
import org.entity.Comment;
import org.entity.Notification;
import org.entity.Recipe;
import org.entity.User;
import org.json.JsonTransformer;

import com.vn.dailycookapp.entity.request.CommentInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.ListCommnetResponseData.CommentResponseInfo;
import com.vn.dailycookapp.notification.NotificationActionImp;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class CommentModel extends AbstractModel {
	private CommentInfo	commentInfo;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		try {
			recipeId = data[0];
			myId = data[1];
			commentInfo = JsonTransformer.getInstance().unmarshall(data[2], CommentInfo.class);
		} catch (Exception ex) {
			throw new InvalidParamException();
		}
	}
	
	// TODO notification, Test
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		
		Comment comment = new Comment();
		comment.setContent(commentInfo.getContent());
		comment.setOwner(myId);
		comment.setRecipeId(recipeId);
		// save comment
		CommentDAO.getInstance().save(comment);
		
		// Increate number comment of this recipe
		RecipeDAO.getInstance().increateCommentNumber(recipeId);
		
		// Get user Info
		User user = UserDAO.getInstance().getUser(myId);
		
		CommentResponseInfo cri = new CommentResponseInfo();
		cri.setAvatarUrl(user.getAvatarUrl());
		cri.setCommentId(comment.getId());
		cri.setContent(comment.getContent());
		cri.setUserName(user.getDisplayName());
		
		// Notification
		Recipe recipe = RecipeDAO.getInstance().get(recipeId);
		NotificationActionImp.getInstance().addNotification(recipeId, recipe.getTitle(), myId, recipe.getOwner(), Notification.NEW_COMMENT_TYPE);
		response.setData(cri);
		return response;
	}
	
}
