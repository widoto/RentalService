import java.util.*;
import java.text.SimpleDateFormat;

public class User implements java.io.Serializable { // 직렬화 가능 클래스 만들기

	private static final long serialVersionUID = 1L;
	private String name; // 이름
	private String phone; // 전화번호
	private String rentalDay; // 대여 날짜
	private String returnDay; // 반납 예정 날짜
	private String[] codeList = new String[3]; // 대여 물품 코드 배열
	private int[] payList = new int[3]; // 대여 금액 배열
	private int rentalCount = 0; // 대여 물품 카운트
	Calendar getToday = Calendar.getInstance();
	
	// 인수 있는 생성자 1 
	User(String name, String phone, String rentalDay, String returnDay)
	{
		this.name = name;
		this.phone = phone;
		this.rentalDay = rentalDay;
		this.returnDay = returnDay;
	}
	
	// 인수 없는 빈 생성자 2
	User() {}
	
	// equals 함수 재정의
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))  // User가 아닌 객체라면 비교 불가
			return false;
		User u = (User) obj;
		return u.phone.equals(this.phone); // 찾고 있는 전화번호와 같은지 확인
	}

	// 이름 반환
	public String getName()
	{
		return name;
	}
	
	// 전화번호 반환
	public String getPhone()
	{
		return phone;
	}
	
	// 전화번호 세팅
	public void setPhone(String uPhone)
	{
		phone = uPhone;
	}
	
	// 대여 일자 반환
	public String getRentalDay()
	{
		return rentalDay;
	}
	
	// 반납 예정 일자 반환
	public String getReturnDay()
	{
		return returnDay;
	}
	
	// 대여 물품 코드 배열 인덱스 카운트 반환
	public int getRentalCount() {
		return rentalCount;
	}
	
	// 대여 물품 코드/ 금액 배열에 코드 추가
	public void addProduct(String code, int money)
	{
		codeList[rentalCount] = code;
		payList[rentalCount] = money;
		rentalCount++;
	}
	
	// 대여 물품 코드 배열 i번째 리턴
	public String codeAt(int i)
	{
		// 대여 물품 코드 배열 i번째 상품 객체 return
		return codeList[i];
	}
	
	// 대여 금액 배열 i번째 리턴
	public int payAt(int i)
	{
		// 대여 금액 배열 i번째 상품 객체 return
		return payList[i];
	}
	
	// 결제 함수
	public int pay() throws Exception
	{
		// 물건 금액 합 계산하기
		int money = 0;
		for(int i = 0; i < rentalCount; i++)
		{
			money += payList[i];
		}
		
		// 반납 일자 입력하기
		getToday.setTime(new Date());
		
		// 대여 일자 불러오기
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDay);
		Calendar rentalDate = Calendar.getInstance();
		rentalDate.setTime(date1);
		
		// 반납 예정 일자 불러오기
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(returnDay);
		Calendar returnDate = Calendar.getInstance();
		returnDate.setTime(date2);
		
		// 대여하기로 한 기간 계산하기 (반납 예정 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day1 = (int) ((returnDate.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));

		// 대여 기간 계산하기 (반납 일자 - 대여 일자)
		// 밀리초를 차이 구한 후 일 수로 변환
		int day2 = (int) ((getToday.getTimeInMillis() - rentalDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		/*금액 관련 규칙 설정
		 * 날짜 지켜서 반납 => 상품 금액 총 합 x 대여 기간
		 * 먼저 반납 => 상품 금액 총 합 x 실제 대여 기간(남은 기간은 가격 책정 x)
		 * 나중에 반납 => 상품 금액 총 합 x 실제 대여 기간 + 연체료(하루 대여료)*/
		
		// 총 금액 계산하기
		if (day1 == day2) // 반납 예정 날짜에 반납
			return money *= (day1 + 1); // 당일 대여 반납도 1일로 처리
		
		else if (day1 > day2)// 반납 예정 날짜보다 빨리 반납
			return money *= (day2 + 1); // 당일 대여 반납도 1일로 처리
		
		else // 반납 예정 날짜보다 늦게 반납
			return money *= (day2 + 2); // 연체료 (하루 대여료) 추가하여서 반환
	}
}