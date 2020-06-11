package com.cnhis.cloudhealth.module.license.vo;

public class ZtreeNode {
	
	 	private Integer id;
	    private Integer state;
	    private Integer pId;
	    private String icon;
	    private String iconClose;
	    private String iconOpen;
	    private String name;
	    private boolean open;
	    private boolean isParent;
	    private String checked;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getState() {
			return state;
		}
		public void setState(Integer state) {
			this.state = state;
		}
		public Integer getpId() {
			return pId;
		}
		public void setpId(Integer pId) {
			this.pId = pId;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getIconClose() {
			return iconClose;
		}
		public void setIconClose(String iconClose) {
			this.iconClose = iconClose;
		}
		public String getIconOpen() {
			return iconOpen;
		}
		public void setIconOpen(String iconOpen) {
			this.iconOpen = iconOpen;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isOpen() {
			return open;
		}
		public void setOpen(boolean open) {
			this.open = open;
		}
		public boolean isParent() {
			return isParent;
		}
		public void setParent(boolean isParent) {
			this.isParent = isParent;
		}
	    public String getChecked() {
			return checked;
		}
		public void setChecked(String checked) {
			this.checked = checked;
		}
		
		@Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{id:\"");
	        sb.append(id);
	        sb.append("\",pId:\"");
	        sb.append(pId);
	        sb.append("\",name:\"");
	        sb.append(name);
	        sb.append("\",state:");
	        sb.append(state);
	        sb.append(",icon:\"");
	        sb.append(icon);
	        sb.append("\",iconClose:\"");
	        sb.append(iconClose);
	        sb.append("\",iconOpen:\"");
	        sb.append(iconOpen);
	        sb.append("\",open:\"");
	        sb.append(open);
	        sb.append("\",isParent:\"");
	        sb.append(isParent);
	        sb.append("\"}");
	        sb.append(checked);
	        sb.append("\"}");
	        return sb.toString();
	    }

}
