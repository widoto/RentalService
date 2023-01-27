
public class Product implements java.io.Serializable { // 직렬화 가능 클래스 만들기

	private static final long serialVersionUID = 1L;
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	// 인수 있는 생성자 1 
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// 인수 없는 빈 생성자 2
	Product() {}
	
	// equals 함수 재정의
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Product)) // Product가 아닌 객체라면 비교 불가
			return false;
		Product p = (Product) obj;
		return p.productCode.equals(this.productCode); // 찾고 있는 상품코드와 같은지 확인
	}
	
	// 상품 이름 반환
	public String getName()
	{
		return productName;
	}
	
	// 상품 코드 반환
	public String getCode()
	{
		return productCode;
	}
		
	//상품 코드 세팅
	public void setCode(String pCode) 
	{
		productCode = pCode;
	}
	// 상품 개수 반환
	public int getNumber()
	{
		return productNumber;
	}
	
	// 상품 개수 추가
	public void addNumber()
	{
		productNumber++;
	}
	
	// 잔고 추가
	public void addNumber(int n)
	{
		productNumber += n;
	}
	
	// 상품 대여 가능한지 확인 후 상품 개수 1개 삭제
	public void subNumber() throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("재고가 부족합니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber--; // 재고 수 1개 감소
	}
	
	// 상품 대여 가능한지 확인 후 상품 개수 n개 삭제 // 렌트된 수량을 또 빼준다..........
	public void subNumber(int n) throws Exception {
		if(productNumber - n < 0) // n개 만큼 삭제하면 0보다 작게될때 
			throw new Exception("재고가 부족합니다."); // 익셉션 발생
		else 
			productNumber -= n; // 재고 수 추감
	}	
	
	// 상품 가격 반환
	public int getPrice()
	{
		return price;
	}
	
	// 재고 검색 함수 (재고가 있으면 true, 아니면 false 반환)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
}