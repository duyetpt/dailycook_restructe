package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dao.CommentDAO;
import org.entity.Comment;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.ListCommnetResponseData;
import com.vn.dailycookapp.entity.response.ListCommnetResponseData.CommentResponseInfo;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

/**
 * 
 * @author duyetpt
 *         Get list comment by recipeId
 *         Get owner information of this comment
 *         Get total number of comment of this recipe
 *         Convert to data for response
 */
public class GetCommentModel extends AbstractModel {
	private int	skip;
	private int	take;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		try {
			recipeId = data[0];
			skip = Integer.parseInt(data[1]);
			take = Integer.parseInt(data[2]);
		} catch (Exception ex) {
			throw new InvalidParamException();
		}
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<Comment> listComment = CommentDAO.getInstance().list(recipeId, skip, take);
		ListCommnetResponseData responseData = new ListCommnetResponseData();
		List<CommentResponseInfo> listCommentInfo = new ArrayList<ListCommnetResponseData.CommentResponseInfo>();
		
		if (listComment == null || listComment.isEmpty()) {
			responseData.setList(listCommentInfo);
			responseData.setTotalCommentNumber(0);
			response.setData(responseData);
			return response;
		}
		
		Set<String> userIds = new HashSet<String>();
		Map<String, String> userComment = new HashMap<>();
		
		// Get comment info
		for (Comment comment : listComment) {
			CommentResponseInfo commentInfo = new CommentResponseInfo();
			commentInfo.setCommentId(comment.getId());
			commentInfo.setContent(comment.getContent());
			
			listCommentInfo.add(commentInfo);
			userComment.put(comment.getId(), comment.getOwner());
			
			if (!userIds.contains(comment.getOwner())) {
				userIds.add(comment.getOwner());
			}
		}
		
		// get User Info
		List<CompactUserInfo> users = UserCache.getInstance().list(userIds);
		for (CommentResponseInfo info : listCommentInfo) {
			String userId = userComment.get(info.getCommentId());
			CompactUserInfo user = null;
			for (CompactUserInfo usr : users) {
				if (usr.getUserId().equals(userId)) {
					user = usr;
					break;
				}
			}
			
			info.setAvatarUrl(user.getAvatarUrl());
			info.setUserName(user.getDisplayName());
		}
		
		responseData.setList(listCommentInfo);
		responseData.setTotalCommentNumber(CommentDAO.getInstance().getTotalNumber(recipeId));
		
		response.setData(responseData);
		return response;
	}
	
}
