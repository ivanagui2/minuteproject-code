package net.sf.minuteProject.configuration.bean.enumeration;

public enum Extension implements FileComment {

	java {
		public String lineCommentBeginning() {
			return "//";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/**";
		}
		public String textCommentMiddle() {
			return "*";
		}
		public String textCommentEnding() {
			return "*/";
		}
	},
	xml {
		public String lineCommentBeginning() {
			return "<!--";
		}
		public String lineCommentEnding() {
			return "-->";
		}
		public String textCommentBeginning() {
			return "<!--";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "-->";
		}
	},
	properties {
		public String lineCommentBeginning() {
			return "#";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "#";
		}
		public String textCommentMiddle() {
			return "#";
		}
		public String textCommentEnding() {
			return "#";
		}
	},
	sql {
		public String lineCommentBeginning() {
			return "--";
		}
		public String lineCommentEnding() {
			return "";
		}
		public String textCommentBeginning() {
			return "/*";
		}
		public String textCommentMiddle() {
			return "";
		}
		public String textCommentEnding() {
			return "*/";
		}
	};

}
