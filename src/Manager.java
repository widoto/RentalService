import java.io.*;
import java.util.ArrayList;

public class Manager {
	//미래 산업사회를 위한 드론 소프트웨어
	private ArrayList<Product> productList = new ArrayList<Product>(); // 상품 어레이리스트
	private ArrayList<User> userList = new ArrayList<User>(); // 유저 어레이리스트
	private int revenue = 0; // 일일 매출 총액 변수
	
	public ArrayList<Product> getProductList()
	{
		return productList;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	// 생성자 1. 파일이 아예 없는 상태를 위한 생성자
	Manager (int maxProductCount, int maxUserCount) {
		productList = new ArrayList<Product>(maxProductCount); // 상품 어레이리스트 크기 설정
		userList = new ArrayList<User>(maxUserCount); // 대여 어레이리스트 크기 설정
	}
	//테이블에다가 겟 셀렉티드 로우에다가 -1반환(찾는거 없으면)
	// 생성자 2. 기존 파일이 있는 상태에서 데이터를 로드하기 위한 생성자
	Manager (int maxProductCount, int maxUserCount, ObjectInputStream dis) throws Exception // IOException, ClassNotFoundException
	{ 
		productList = new ArrayList<Product>(maxProductCount); // 상품 어레이리스트 크기 설정
		userList = new ArrayList<User>(maxUserCount); // 대여 어레이리스트 크기 설정
		// 데이터 로드
		int pCount = ((Integer) dis.readObject()).intValue(); // 저장된 상품 개수 객체 역직렬화
		for (int i = 0; i < pCount; i++) // 상품 어레이리스트 채우기
		{
			// 객체 역직렬화
			Product p = (Product) dis.readObject();
			// 상품 추가
			add(p);
		}
		int uCount = ((Integer) dis.readObject()).intValue(); // 저장된 유저 인원 객체 역직렬화
		for (int i = 0; i < uCount; i++) // 유저 어레이리스트 채우기
		{
			// 객체 역직렬화
			User u = (User) dis.readObject();
			// 유저 추가
			userList.add(u);
		}
		int lastRevenue = ((Integer) dis.readObject()).intValue();  // 저장된 매출액 객체 역직렬화
		revenue = lastRevenue; 
	}
	
	// 파일 저장
	void saveToFile(ObjectOutputStream dos) throws Exception // IOException, IndexOutOfBoundsException
	{
		// 상품 정보 입력
		Integer pCount = Integer.valueOf(productList.size());
		// 객체 직렬화
		dos.writeObject(pCount); 
		for (int i = 0; i < pCount; i++)
		{
			dos.writeObject(productList.get(i)); // 객체 직렬화
		}	
		// 유저 정보 입력
		Integer uCount = Integer.valueOf(userList.size()); 
		// 객체 직렬화
		dos.writeObject(uCount);
		for (int i = 0; i < uCount; i++)
		{
			// 객체 직렬화
			dos.writeObject(userList.get(i)); 
		}		
		// 일일 매출 총액 변수 객체 직렬화
		dos.writeObject(Integer.valueOf(revenue));				
	}
	
	// 상품 새로 등록
	public void add(Product p) throws Exception 
	{
		if (productList.contains(p)) // 기존에 등록한 물품인지 확인
			throw new Exception ("잘못된 상품 등록입니다.");
		else
			productList.add(p); // 추가
		/* try{
			checkCode(p); // 코드 중복 검색
			productList.add(p);
		}
		catch (Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");
		} */
	}
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		Product p = new Product(); //search함수를 안쓴다 치고
		p.setCode(productCode);
		
		int number = productList.indexOf(p); //remove(object o)
		productList.remove(number);
	}
	
	// 상품 객체 검색 // Ui에서 쓰기 때문에 일단 지울 수 없음.
	public int search(String productCode) throws Exception {
		// 상품 검색
		Product p2 = new Product(); // 현재는 productCode만 비교하므로
		p2.setCode(productCode);
		return productList.indexOf(p2);
	}

	// 상품 어레이리스트 i번째 리턴 // Ui에서 쓰기 때문에 일단 지울 수 없음.
	public Product productAt(int i) {
		//있으면 리턴 
		if (productList.isEmpty()) // 상품리스트가 비었다면 null 반환
			return null;
		else
			return productList.get(i); // 대여 어레이리스트 i번째 상품 객체 return
			//없으면 null 반환
	}
	
	// productCount 값 반환 // Ui에서 쓰기 때문에 일단 지울 수 없음.
	public int getProductCount() {
		return productList.size();
	}
	
	// 재고 개수에서 대여 개수 제외***search문
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				Product p = new Product(); // 현재는 productCode만 비교하므로
				p.setCode(code);
				searchNum = productList.indexOf(p); // productList에서 해당 코드의 인덱스 번호 검색
				// searchNum = search(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			Product p = productList.get(searchNum); // 해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 체크인
	public void checkIn(User u) throws Exception {
		// checkPhone(u); // 전화번호 중복 검색
		if (userList.contains(u)) // 유저가 중복되지 않는다는 의미로 indexOf는 -1을 반환하여야 함
		{
			throw new Exception ("잘못된 방법의 체크인입니다."); // 중복된 전화번호일 경우 익셉션 발생
		} else {
			subStock(u); // 재고 개수에서 대여 개수 제외
			userList.add(u);  // 대여 배열에 대여 정보 넣기
		}				
	}
	
	// userCount 값 반환 // Ui에서 쓰기 때문에 지울 수 없음.
	public int getUserCount() {
		return userList.size();
	}
	
	// 대여 어레이리스트 i번째 리턴 // Ui에서 쓰기 때문에 지울 수 없음.
	public User userAt(int i)
	{
		//있으면 리턴 
		if (userList.isEmpty()) // 유저리스트가 비었다면 null 반환
			return null;
		else
			return userList.get(i); // 대여 어레이리스트 i번째 유저 객체 return
		//없으면 null 반환
	}
	
	// 일치하는 회원번호 검색 // Ui에서 쓰기 때문에 지울 수 없음.
	public int searchUser(String phone) throws Exception { 
		User u = new User(); // 현재는 phone만 비교하므로
		u.setPhone(phone);
		return userList.indexOf(u); 
	}
	
	// 상품 재고 다시 추가***search문
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				Product p = new Product(); // 현재는 productCode만 비교하므로
				p.setCode(code);
				int number = productList.indexOf(p); // productList에서 해당 코드의 인덱스 번호 검색
				productAt(number).addNumber(); // 해당 인덱스의 product 객체의 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 상품 재고 여러개 추가
	public void addStock(int index, int n) throws Exception {
		// 이때 index는 이미 search된 인덱스, 상품의 잔고를 n만큼 추가
		productAt(index).addNumber(n);
	}
	
	// 상품 재고 여러개 추감
	public void subStock(int index, int n) throws Exception {
		// 이때 index는 이미 search된 인덱스, 상품의 잔고를 n만큼 추감
		productAt(index).subNumber(n);
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList.get(index).pay();// 금액 반환받기
			userList.remove(index); // userList에서 유저 삭제, 배열 정리하기
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}
	
	// 매출 반환
	public int getRevenue() {
		return revenue;
	}

}