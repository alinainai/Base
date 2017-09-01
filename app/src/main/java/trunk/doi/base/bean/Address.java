package trunk.doi.base.bean;
/** 
 * 用户地址类 
 * @author  liyue: 
 * @date 创建时间：2016年10月27日 下午5:30:13  
 * @parameter  
 * @return  
 */
public class Address {
	
	
	private String aid;  //id
	private String phone; //手机号
	private String userpro;  // 省份编号
	private String usercity;  //市区编号
	private String userpart;  // 区域编号
	private String userName;  //姓名
	private String userStreet; //  街道编号
	private int isdefault;//默认   0默认   1普通
	private String userstr;//详细地址
	private String province; //省
	private String city;//市
	private String part;//区
	private String street;//街道
	
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserpro() {
		return userpro;
	}
	public void setUserpro(String userpro) {
		this.userpro = userpro;
	}
	public String getUsercity() {
		return usercity;
	}
	public void setUsercity(String usercity) {
		this.usercity = usercity;
	}
	public String getUserpart() {
		return userpart;
	}
	public void setUserpart(String userpart) {
		this.userpart = userpart;
	}
	public int getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}
	public String getUserstr() {
		return userstr;
	}
	public void setUserstr(String userstr) {
		this.userstr = userstr;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserStreet() {
		return userStreet;
	}
	public void setUserStreet(String userStreet) {
		this.userStreet = userStreet;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	
	
	

}
