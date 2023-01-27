import java.io.*;
import java.util.ArrayList;

public class Manager {
	//�̷� �����ȸ�� ���� ��� ����Ʈ����
	private ArrayList<Product> productList = new ArrayList<Product>(); // ��ǰ ��̸���Ʈ
	private ArrayList<User> userList = new ArrayList<User>(); // ���� ��̸���Ʈ
	private int revenue = 0; // ���� ���� �Ѿ� ����
	
	public ArrayList<Product> getProductList()
	{
		return productList;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	// ������ 1. ������ �ƿ� ���� ���¸� ���� ������
	Manager (int maxProductCount, int maxUserCount) {
		productList = new ArrayList<Product>(maxProductCount); // ��ǰ ��̸���Ʈ ũ�� ����
		userList = new ArrayList<User>(maxUserCount); // �뿩 ��̸���Ʈ ũ�� ����
	}
	//���̺��ٰ� �� ����Ƽ�� �ο쿡�ٰ� -1��ȯ(ã�°� ������)
	// ������ 2. ���� ������ �ִ� ���¿��� �����͸� �ε��ϱ� ���� ������
	Manager (int maxProductCount, int maxUserCount, ObjectInputStream dis) throws Exception // IOException, ClassNotFoundException
	{ 
		productList = new ArrayList<Product>(maxProductCount); // ��ǰ ��̸���Ʈ ũ�� ����
		userList = new ArrayList<User>(maxUserCount); // �뿩 ��̸���Ʈ ũ�� ����
		// ������ �ε�
		int pCount = ((Integer) dis.readObject()).intValue(); // ����� ��ǰ ���� ��ü ������ȭ
		for (int i = 0; i < pCount; i++) // ��ǰ ��̸���Ʈ ä���
		{
			// ��ü ������ȭ
			Product p = (Product) dis.readObject();
			// ��ǰ �߰�
			add(p);
		}
		int uCount = ((Integer) dis.readObject()).intValue(); // ����� ���� �ο� ��ü ������ȭ
		for (int i = 0; i < uCount; i++) // ���� ��̸���Ʈ ä���
		{
			// ��ü ������ȭ
			User u = (User) dis.readObject();
			// ���� �߰�
			userList.add(u);
		}
		int lastRevenue = ((Integer) dis.readObject()).intValue();  // ����� ����� ��ü ������ȭ
		revenue = lastRevenue; 
	}
	
	// ���� ����
	void saveToFile(ObjectOutputStream dos) throws Exception // IOException, IndexOutOfBoundsException
	{
		// ��ǰ ���� �Է�
		Integer pCount = Integer.valueOf(productList.size());
		// ��ü ����ȭ
		dos.writeObject(pCount); 
		for (int i = 0; i < pCount; i++)
		{
			dos.writeObject(productList.get(i)); // ��ü ����ȭ
		}	
		// ���� ���� �Է�
		Integer uCount = Integer.valueOf(userList.size()); 
		// ��ü ����ȭ
		dos.writeObject(uCount);
		for (int i = 0; i < uCount; i++)
		{
			// ��ü ����ȭ
			dos.writeObject(userList.get(i)); 
		}		
		// ���� ���� �Ѿ� ���� ��ü ����ȭ
		dos.writeObject(Integer.valueOf(revenue));				
	}
	
	// ��ǰ ���� ���
	public void add(Product p) throws Exception 
	{
		if (productList.contains(p)) // ������ ����� ��ǰ���� Ȯ��
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		else
			productList.add(p); // �߰�
		/* try{
			checkCode(p); // �ڵ� �ߺ� �˻�
			productList.add(p);
		}
		catch (Exception e) {
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		} */
	}
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		Product p = new Product(); //search�Լ��� �Ⱦ��� ġ��
		p.setCode(productCode);
		
		int number = productList.indexOf(p); //remove(object o)
		productList.remove(number);
	}
	
	// ��ǰ ��ü �˻� // Ui���� ���� ������ �ϴ� ���� �� ����.
	public int search(String productCode) throws Exception {
		// ��ǰ �˻�
		Product p2 = new Product(); // ����� productCode�� ���ϹǷ�
		p2.setCode(productCode);
		return productList.indexOf(p2);
	}

	// ��ǰ ��̸���Ʈ i��° ���� // Ui���� ���� ������ �ϴ� ���� �� ����.
	public Product productAt(int i) {
		//������ ���� 
		if (productList.isEmpty()) // ��ǰ����Ʈ�� ����ٸ� null ��ȯ
			return null;
		else
			return productList.get(i); // �뿩 ��̸���Ʈ i��° ��ǰ ��ü return
			//������ null ��ȯ
	}
	
	// productCount �� ��ȯ // Ui���� ���� ������ �ϴ� ���� �� ����.
	public int getProductCount() {
		return productList.size();
	}
	
	// ��� �������� �뿩 ���� ����***search��
	public void subStock(User u) throws Exception {
		// ��� �������� �뿩 ���� �����ϱ�
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			int searchNum;
			try {
				Product p = new Product(); // ����� productCode�� ���ϹǷ�
				p.setCode(code);
				searchNum = productList.indexOf(p); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
				// searchNum = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
			} 
			catch (Exception e) {
				throw new Exception("�߸��� ����� üũ���Դϴ�.");
			}
			Product p = productList.get(searchNum); // �ش� �ε����� product ��ü
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// üũ��
	public void checkIn(User u) throws Exception {
		// checkPhone(u); // ��ȭ��ȣ �ߺ� �˻�
		if (userList.contains(u)) // ������ �ߺ����� �ʴ´ٴ� �ǹ̷� indexOf�� -1�� ��ȯ�Ͽ��� ��
		{
			throw new Exception ("�߸��� ����� üũ���Դϴ�."); // �ߺ��� ��ȭ��ȣ�� ��� �ͼ��� �߻�
		} else {
			subStock(u); // ��� �������� �뿩 ���� ����
			userList.add(u);  // �뿩 �迭�� �뿩 ���� �ֱ�
		}				
	}
	
	// userCount �� ��ȯ // Ui���� ���� ������ ���� �� ����.
	public int getUserCount() {
		return userList.size();
	}
	
	// �뿩 ��̸���Ʈ i��° ���� // Ui���� ���� ������ ���� �� ����.
	public User userAt(int i)
	{
		//������ ���� 
		if (userList.isEmpty()) // ��������Ʈ�� ����ٸ� null ��ȯ
			return null;
		else
			return userList.get(i); // �뿩 ��̸���Ʈ i��° ���� ��ü return
		//������ null ��ȯ
	}
	
	// ��ġ�ϴ� ȸ����ȣ �˻� // Ui���� ���� ������ ���� �� ����.
	public int searchUser(String phone) throws Exception { 
		User u = new User(); // ����� phone�� ���ϹǷ�
		u.setPhone(phone);
		return userList.indexOf(u); 
	}
	
	// ��ǰ ��� �ٽ� �߰�***search��
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			try {
				Product p = new Product(); // ����� productCode�� ���ϹǷ�
				p.setCode(code);
				int number = productList.indexOf(p); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
				productAt(number).addNumber(); // �ش� �ε����� product ��ü�� ��� �߰��ϱ�
			}
			catch (Exception e) {
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
	}
	
	// ��ǰ ��� ������ �߰�
	public void addStock(int index, int n) throws Exception {
		// �̶� index�� �̹� search�� �ε���, ��ǰ�� �ܰ� n��ŭ �߰�
		productAt(index).addNumber(n);
	}
	
	// ��ǰ ��� ������ �߰�
	public void subStock(int index, int n) throws Exception {
		// �̶� index�� �̹� search�� �ε���, ��ǰ�� �ܰ� n��ŭ �߰�
		productAt(index).subNumber(n);
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			int money = userList.get(index).pay();// �ݾ� ��ȯ�ޱ�
			userList.remove(index); // userList���� ���� ����, �迭 �����ϱ�
			revenue += money; // ���⿡ �߰��ϱ�
		}
		catch(Exception e) {
			throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
		}
	}
	
	// ���� ��ȯ
	public int getRevenue() {
		return revenue;
	}

}