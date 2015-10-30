package com.vn.dailycookapp.entity.response;

import java.util.List;

public class ListCommnetResponseData {
	
	private int					totalCommentNumber;
	private List<CommentResponseInfo>	list;
	
	public static class CommentResponseInfo {
		private String	commentId;
		private String	content;
		private String	userName;
		private String	avatarUrl;
		
		public String getCommentId() {
			return commentId;
		}
		
		public void setCommentId(String commentId) {
			this.commentId = commentId;
		}
		
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		public String getUserName() {
			return userName;
		}
		
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		public String getAvatarUrl() {
			return avatarUrl;
		}
		
		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}
	}
	
	public int getTotalCommentNumber() {
		return totalCommentNumber;
	}
	
	public void setTotalCommentNumber(int totalCommentNumber) {
		this.totalCommentNumber = totalCommentNumber;
	}
	
	public List<CommentResponseInfo> getList() {
		return list;
	}
	
	public void setList(List<CommentResponseInfo> list) {
		this.list = list;
	}
	
}
